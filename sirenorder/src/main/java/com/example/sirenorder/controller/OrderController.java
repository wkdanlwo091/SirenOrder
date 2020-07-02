package com.example.sirenorder.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.common.Pagination;
import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.CartVO;
import com.example.sirenorder.vo.ChainVO;
import com.example.sirenorder.vo.OrdersVO;
import com.example.sirenorder.vo.Orders_detailJoinProductVO;
import com.example.sirenorder.vo.Orders_detailVO;
import com.example.sirenorder.vo.PointList;
import com.example.sirenorder.vo.PointVO;
import com.example.sirenorder.vo.Point_storeVO;
import com.example.sirenorder.vo.ProductVO;
import com.example.sirenorder.vo.StoreVO;

@Controller
public class OrderController {

	@Resource(name = "storebiz")
	Biz<String, StoreVO> storebiz;
	@Resource(name = "pointbiz")
	Biz<String, PointVO> pointbiz;
	@Resource(name = "point_storebiz")
	Biz<String, Point_storeVO> point_storebiz;
	@Resource(name = "ordersbiz")
	Biz<String, OrdersVO> ordersbiz;
	@Resource(name = "orders_detailbiz")
	Biz<String, Orders_detailVO> orders_detailbiz;
	@Resource(name = "orders_detailjoinproductbiz")
	Biz<String, Orders_detailJoinProductVO> orders_detailjoinproductbiz;
	@Resource(name = "productbiz")
	Biz<String, ProductVO> productbiz;
	@Resource(name = "chainbiz")
	Biz<String, ChainVO> chainbiz;

	@RequestMapping(value = "searchStore", method = RequestMethod.POST) // 가게 이름을 return 한다.
	@ResponseBody
	public Object searchStore(HttpServletRequest request) throws Exception {
		String chain = request.getParameter("chain").trim();// 검색 했을 때 뒤에 오는 스페이스를 자르기
		ArrayList<StoreVO> arrList = storebiz.getChain(chain);
		if (arrList.size() == 0) {
			System.out.println("찾는 가게가 없습니다.");
		} else {
			return arrList;
		}
		return "fail";// 로그인 첫 페이지로 /index.html
	}
	// checkout 장바구니 보기

	public ArrayList<PointVO> bringPointInfo(HttpSession httpSession) throws Exception {// 카트에 대한 포인트 정보를 넣는다.
		HashMap<CartVO, Integer> cartProduct = (HashMap<CartVO, Integer>) httpSession.getAttribute("cartProduct");
		Iterator<CartVO> itr;
		if (cartProduct != null) {
			itr = cartProduct.keySet().iterator();
		} else {
			return null;
		}
		HashMap<String, Boolean> chain_names = new HashMap<String, Boolean>();
		while (itr.hasNext()) {
			CartVO tmp = itr.next();
			if (chain_names.get(tmp.getChain_name()) == null) {
				chain_names.put(tmp.getChain_name(), true);
			}
		}
		Iterator<String> itr2 = chain_names.keySet().iterator();
		ArrayList<PointVO> arrayList = new ArrayList<PointVO>();
		while (itr2.hasNext()) {
			String chain_names_next = itr2.next();
			PointVO pointVO = pointbiz.getByChain_name(chain_names_next);
			if (pointVO != null)
				arrayList.add(pointVO);
		}
		return arrayList;
	}
	
	@RequestMapping(value = "/checkOut.html", method = RequestMethod.GET) // 주문하기 페이지
	public ModelAndView checkOut(HttpServletRequest request) throws Exception {
		System.out.println("checkout에 들어왔다.");
		ModelAndView model = new ModelAndView();
		HttpSession httpSession = request.getSession();
		String users_id = (String) httpSession.getAttribute("userId");
		// 포인트가 미사용 이라면
		if (users_id == null) {
			model.setViewName("redirect:/index.html");
			return model;
		}
		ArrayList<PointVO> arrayList = bringPointInfo(httpSession);// 포인트정보를 넣는다.
		if (arrayList == null) {
			model.addObject("checkout", "clicked");
			model.setViewName("thymeleaf/main");
			return model;
		}
		model.addObject("arrayList", arrayList);
		model.addObject("checkout", "clicked");
		model.setViewName("thymeleaf/main");
		return model;
	}
	
	public void usePoint(PointList pointlist, String users_id) throws Exception {// 체인별로 포인트 사용하기
		if (pointlist.getChain_name() == null) {// point를 쓰는 것이 없으면 return ;
			return;
		}
		for (int i = 0; i < pointlist.getChain_name().length; i++) {// 포인트의 개수에 따라서 point를 update한다.
			if (pointlist.getUseOrNot()[i] == 1) {// 포인트를 사용할 것이면
				String chain_name = pointlist.getChain_name()[i];
				int point = pointlist.getPoint()[i];
				String point_id = pointlist.getPoint_id()[i];
				System.out.println(users_id);

				PointVO pointVO = new PointVO();
				pointVO.setPoint(-point);
				pointVO.setPoint_id(point_id);
				pointVO.setChain_name(chain_name);
				pointVO.setUsers_id(users_id);
				System.out.println(pointVO);
				pointbiz.update(pointVO);// point를 사용한 만큼 차감하기
			}
		}
	}
	
	public void addPoint(PointList pointlist, String users_id) throws Exception {// 체인별로 포인트 사용하기
		for (int i = 0; i < pointlist.getAll_chain_name().length; i++) {// 포인트의 개수에 따라서 point를 update한다.
			String chain_name = pointlist.getAll_chain_name()[i];
			PointVO pointVO = pointbiz.getByChain_name(chain_name);
			double point_rate = pointVO.getPoint_rate();
			pointVO.setPoint((int)(pointlist.getAll_chain_price()[i] *  point_rate));
			pointVO.setPoint_id(pointVO.getPoint_id());
			pointVO.setChain_name(chain_name);
			pointVO.setUsers_id(users_id);
			System.out.println(pointVO);
			pointbiz.update(pointVO);// point를 add하기
		}
	}
	
	public void makePoint_store(PointList pointlist, String users_id) throws Exception {
		// 두 지점 사용 했을 때 banapresso 홍대, banapresso 신촌 중 처음 나오는 것에서 포인트 사용 했다고 저장
		if (pointlist.getChain_name() == null) {// point를 쓰는 것이 없으면 return ;
			return;
		}
		for (int i = 0; i < pointlist.getChain_name().length; i++) {
			String chain_name = pointlist.getChain_name()[i];
			int point = pointlist.getPoint()[i];
			int useOrNot = pointlist.getUseOrNot()[i];// 1이면 point 사용 0이면 사용 안함
			if (useOrNot == 1) {// 포인트 사용
				Point_storeVO point_storeVO = new Point_storeVO();
				PointVO temp = pointbiz.getByChain_name(chain_name);
				point_storeVO.setPoint_store_id("point_store_id");
				point_storeVO.setPoint_id(temp.getPoint_id());
				StoreVO storeVO = storebiz.get(pointlist.getStore_name()[i]);// store_name 으로 store_id 얻기

				String store_id = storeVO.getStore_id();
				point_storeVO.setStore_id(store_id);
				point_storeVO.setUsers_id(temp.getUsers_id());
				point_storeVO.setChain_name(temp.getChain_name());
				point_storeVO.setPoint_date(new java.sql.Date(System.currentTimeMillis()));// 자바 date to 오라클 date
				point_storeVO.setUsed_point(point);

				point_storebiz.register(point_storeVO);
			} else {

			}
		}
	}

	public void makePoints(String users_id, int all_chain_price, String chain_name) throws Exception {
		// 포인트 이름 없는 경우 포인트를 만든다.
		// 포인트 비율 어떻게 ?? 구매액의 0.5%?
		// point가 없는 경우 만든다.
		// 포인트 검색

		double point_rate = chainbiz.getByChain_name(chain_name).getPoint_rate();
		PointVO pointVO = new PointVO();
		pointVO.setChain_name(chain_name);
		pointVO.setUsers_id(users_id);
		pointVO.setPoint_rate(point_rate);
		pointVO.setPoint((int) (all_chain_price * point_rate));// 포인트는 구매액* point_rate
		pointVO.setPoint_id("point_id");
		pointbiz.register(pointVO);

		System.out.println("made point");
	}
	
	public void makeOrders(String users_id, HttpSession httpSession, int allTotalPrice) throws Exception {// orders 테이블에
		OrdersVO ordersVO = new OrdersVO();
		ordersVO.setOrders_id("orders_id");
		ordersVO.setOrders_date(new java.sql.Date(System.currentTimeMillis()));
		ordersVO.setPayment_way("before_card");
		ordersVO.setUsers_id(users_id);
		ordersVO.setTotal_price(allTotalPrice);
		System.out.println(ordersVO);
		ordersbiz.register(ordersVO);
	}
	public void makeOrders_detail(HttpSession httpSession) throws Exception {// orders_detail 테이블에 insert
		String orders_id = "orders_id" + Integer.toString(orders_detailbiz.getOrders_seq() - 1);// orders_list에 연결된
		System.out.println("orders_id 는 " + orders_id);
		HashMap<CartVO, Integer> cartProduct = (HashMap<CartVO, Integer>) httpSession.getAttribute("cartProduct");
		Iterator<CartVO> itr = cartProduct.keySet().iterator();
		while (itr.hasNext()) {
			CartVO tmp = itr.next();
			Orders_detailVO orders_detailVO = new Orders_detailVO();
			orders_detailVO.setOrders_detail_id("orders_detail_id");
			orders_detailVO.setPrice(tmp.getPrice());
			orders_detailVO.setQuantity(tmp.getNumber());
			orders_detailVO.setOrders_id(orders_id);
			orders_detailVO.setProduct_name(tmp.getProduct_name());
			orders_detailVO.setProduct_id(productbiz.getProduct_id(tmp.getProduct_name()));
			orders_detailVO.setOrders_date(new java.sql.Date(System.currentTimeMillis()));
			orders_detailVO.setStatus("not_done");// 주문이 들어갔고 아직 주문완료 전이다.
			orders_detailVO.setStore_name(tmp.getStore_name());
			orders_detailbiz.register(orders_detailVO);
			// orders의 last sequence num을 가지고와서 그를 기준으로 외래키 참조하고 orders_detail을 만든다.
		}
	}
	
	@RequestMapping(value = "buyProduct", method = RequestMethod.GET) // 가게 이름을 return 한다.
	public String buyProductGet(HttpServletRequest request) throws Exception {
		return "redirect:/index.html";// 로그인 첫 페이지로 /index.html
	}

	@RequestMapping(value = "buyProduct", method = RequestMethod.POST) // 물건을 살 때 부르는 컨트롤러
	public ModelAndView buyProduct(@ModelAttribute("pointlist") PointList pointList, HttpServletRequest request)
			throws Exception {
		HttpSession httpSession = request.getSession();
		String users_id = (String) httpSession.getAttribute("userId");
		ModelAndView model = new ModelAndView();

		for (int i = 0; i < pointList.getAll_chain_name().length; i++) {
			// 포인트 있나 없나 체크
			if (pointbiz.getByChain_name(pointList.getAll_chain_name()[i]) == null) {
				// point 없으면 System.out.println("point 존재" + pointList.getAll_chain_name()[i]);
				System.out.println("point 무존재" + pointList.getAll_chain_name()[i]);

				makePoints(users_id, pointList.getAll_chain_price()[i], pointList.getAll_chain_name()[i]);
			} else {// 포인트 있으면 체인 이름에 따라서
				System.out.println("point 존재" + pointList.getAll_chain_name()[i]);
				usePoint(pointList, users_id);// 포인트 사용
				addPoint(pointList, users_id);							// 포인트 쌓기
				makePoint_store(pointList, users_id);
			}
		}
		makeOrders(users_id, httpSession, pointList.getAllTotalPrice());
		makeOrders_detail(httpSession);

		httpSession.removeAttribute("cartProduct");// 구매 후 카트 세션 파괴
		httpSession.setAttribute("totalIndex", 0);// 장바구니 수 0으로 초기화
		if (httpSession.getAttribute("cartProduct") == null) {
			System.out.println("session 파괴되었다. ");
		}
		model.addObject("order", "clicked");
		model.setViewName("thymeleaf/main");
		return model;
	}
	@RequestMapping(value = "/currentOrderStatus.html", method = RequestMethod.GET) // 현재 주문들어간 상품 보기 (주문 완료전) not_done
	public ModelAndView orderStatus(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "1") int range 
			 ) throws Exception {
		HttpSession httpSession = request.getSession();
		String users_id = (String) httpSession.getAttribute("userId");
		ModelAndView model = new ModelAndView();
		// 포인트가 미사용 이라면
		if (users_id.equals(null)) {
			model.setViewName("redirect:/index.html");
		}
		// orders_detail과 product가 join 되었다. mybatis의 resultmap을 사용하여
		int listCnt = orders_detailjoinproductbiz.getOrders_detailCnt();// 페이지네이션을 위해서 orders_detail 개수를 센다
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, range, listCnt);
		int startList = pagination.getStartList();
		int listSize = pagination.getListSize();
		ArrayList<Orders_detailJoinProductVO> List = orders_detailjoinproductbiz.getOrdersStatus(pagination);
		model.addObject("pagination", pagination);
		model.addObject("currentOrderStatus", "clicked");
		model.addObject("List", List);
		model.setViewName("thymeleaf/main");
		return model;
	}

	@RequestMapping(value = "/ordersHistory.html", method = RequestMethod.GET) // 물건을 산기록을 보는 컨트롤러 ( 물건들의 날짜 범위 검색 전)
	public ModelAndView ordersHistory(HttpServletRequest request,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date from,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date to,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "1") int range) throws Exception {
		HttpSession httpSession = request.getSession();
		String users_id = (String) httpSession.getAttribute("userId");
		ModelAndView model = new ModelAndView();

		// 포인트가 미사용 이라면
		if (users_id == null) {
			model.setViewName("redirect:/index.html");
			return model;
		}

		if (from == null && to == null) { // 페이지네이션 아니고 아예 처음 들어왔을 때
			// 맨 처음 페이지에 들어왔을 때
			System.out.println("다른 스테이트 입니다. ");
			model.addObject("from", "");// 페이지에 orders_hitory 안 내놓기 위해
			model.addObject("to", "");//
		} else {
			// 같은 시작일 마침일 내에서 페이지네이션을 했을 경우
			System.out.println("paginiation");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// 프론트의 자바스크립트에 날짜 표시하는 것
			String beforeFromState = formatter.format(httpSession.getAttribute("from"));// 전의 시작일
			System.out.println(httpSession.getAttribute("to"));
			String beforeToState = formatter.format(httpSession.getAttribute("to"));// 전의 마침일
			String currentFromState = null;
			String currentToState = null;
			if (from != null)
				currentFromState = formatter.format(from);// 새로 들어온 시작일
			else {// 들어온 데이터가 없으면 다시 return
				httpSession.setAttribute("from", "");
				httpSession.setAttribute("to", "");
				model.addObject("ordersHistory", "clicked");
				model.setViewName("thymeleaf/main");
			}
			if (to != null)
				currentToState = formatter.format(to);// 새로 들어온 마침일
			System.out.println("start");
			System.out.println(beforeFromState);
			System.out.println(beforeToState);
			System.out.println(currentFromState);
			System.out.println(currentToState);
			System.out.println("done");
			if (beforeFromState.equals(currentFromState) && beforeToState.equals(currentToState)) // 전의 스테이트 현재 스테이트 같으면
			{// 같은 시작일, 끝일에 페이지네이션을 사용한 경우 들어온다.
				ArrayList<Orders_detailVO> detailTemp = null;
				detailTemp = (ArrayList<Orders_detailVO>) httpSession.getAttribute("orders_detailList");
				// 전의 스테이트에서 받은 orders_detailList 세션을 사용한다.
				System.out.println(page + " " + range + " ");
				int listCnt = detailTemp.size();// 주문 상품 각각의 개수
				Pagination pagination = new Pagination();
				pagination.pageInfo(page, range, listCnt);
				int startList = pagination.getStartList();
				int listSize = pagination.getListSize();

				// 상품이미지를 추가하기 위한것
				ArrayList<Orders_detailJoinProductVO> List = new ArrayList<Orders_detailJoinProductVO>();
				for (int i = (page - 1) * 6; i < (page - 1) * 6 + 6; i++) {// 6개의 데이터만 전송
					if (i >= detailTemp.size()) { /// detailTemp (orders_detail ) 사이즈를 넘어가면 break ex) 1 >= 1 이면 break;
						break;
					}
					Orders_detailJoinProductVO tempVO = new Orders_detailJoinProductVO();
					ProductVO productVO = new ProductVO();
					productVO.setImage(productbiz.get(detailTemp.get(i).getProduct_id()).getImage());// 이미지 설정
					productVO.setProduct_name(productbiz.get(detailTemp.get(i).getProduct_id()).getProduct_name());
					productVO.setChain_name(productbiz.get(detailTemp.get(i).getProduct_id()).getChain_name());
					tempVO.setOrders_date(detailTemp.get(i).getOrders_date());
					tempVO.setPrice(detailTemp.get(i).getPrice());
					tempVO.setQuantity(detailTemp.get(i).getQuantity());
					tempVO.setProductVO(productVO);
					List.add(tempVO);
				}
				model.addObject("pagination", pagination);
				model.addObject("List", List);
				model.addObject("from", formatter.format(from));// from을 페이지의 datepicker의 ${from} value에 넣기 위함
				model.addObject("to", formatter.format(to));// to을 페이지의 datepicker의 ${from} value에 넣기 위함
			}
		}
//		
//		if (httpSession.getAttribute("from") != null && httpSession.getAttribute("to") != null) {
//			
//			
//			
//			
//		} 
		model.addObject("ordersHistory", "clicked");
		model.setViewName("thymeleaf/main");
		return model;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
	}

	@RequestMapping(value = "/ordersHistory.html", method = RequestMethod.POST) // 물건을 산 기록을 보는 컨트롤러 (날짜 검색 후
	public ModelAndView ordersHistoryAfter(HttpServletRequest request,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date from,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date to,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "1") int range) throws Exception {
		HttpSession httpSession = request.getSession();
		String users_id = (String) httpSession.getAttribute("userId");
		ModelAndView model = new ModelAndView();
		if (users_id == null) {// 로그인 안된상태는 return ;
			model.setViewName("redirect:/index.html");
			return model;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// 프론트의 자바스크립트에 날짜 표시하는 것
		model.addObject("from", formatter.format(from));// from을 페이지의 datepicker의 ${from} value에 넣기 위함
		model.addObject("to", formatter.format(to));// to을 페이지의 datepicker의 ${from} value에 넣기 위함
		System.out.println("orderHistory에 왔습니다. post");
		ArrayList<Orders_detailVO> detailTemp = null;
		// 맨 처음 들어온 경우
		// pagination 첫페이지
		// 날짜에 따라서 디비에서 OrdersVO 데이터를 가져온다. OrdersVO의 orders_id를 기반으로 orders_detail들을
		// 가져온다.
		httpSession.setAttribute("from", from);
		httpSession.setAttribute("to", to);

		java.sql.Date sqlDateFrom = new java.sql.Date(from.getTime());
		java.sql.Date sqlDateTo = new java.sql.Date(to.getTime());
		ArrayList<OrdersVO> temp = ordersbiz.getByDateFromTo(users_id, sqlDateFrom, sqlDateTo);// 여기서 orders_id 가져온다.

		detailTemp = new ArrayList<Orders_detailVO>();// detailTemp는 orders_detail 모음
		for (int i = 0; i < temp.size(); i++) {
			ArrayList<Orders_detailVO> orders_detailTemp = orders_detailbiz
					.getOrders_detailByOrdersId(temp.get(i).getOrders_id());
			detailTemp.addAll(orders_detailTemp);
		}
		httpSession.setAttribute("orders_detailList", detailTemp);

		// 페이지네이션 관련
		int listCnt = detailTemp.size();// 주문 상품 각각의 개수
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, range, listCnt);
		int startList = pagination.getStartList();
		int listSize = pagination.getListSize();

		// 상품이미지를 추가하기 위한것
		ArrayList<Orders_detailJoinProductVO> List = new ArrayList<Orders_detailJoinProductVO>();
		for (int i = (page - 1) * 6; i < (page - 1) * 6 + 6; i++) {// 6개의 데이터만 전송
			if (i >= detailTemp.size()) { /// detailTemp (orders_detail ) 사이즈를 넘어가면 break ex) 1 >= 1 이면 break;
				break;
			}
			Orders_detailJoinProductVO tempVO = new Orders_detailJoinProductVO();
			ProductVO productVO = new ProductVO();
			productVO.setImage(productbiz.get(detailTemp.get(i).getProduct_id()).getImage());// 이미지 설정
			productVO.setProduct_name(productbiz.get(detailTemp.get(i).getProduct_id()).getProduct_name());
			productVO.setChain_name(productbiz.get(detailTemp.get(i).getProduct_id()).getChain_name());
			tempVO.setOrders_date(detailTemp.get(i).getOrders_date());
			tempVO.setPrice(detailTemp.get(i).getPrice());
			tempVO.setQuantity(detailTemp.get(i).getQuantity());
			tempVO.setProductVO(productVO);
			List.add(tempVO);
		}
		model.addObject("pagination", pagination);
		model.addObject("List", List);
		model.addObject("ordersHistory", "clicked");
		model.setViewName("thymeleaf/main");
		return model;
	}
}