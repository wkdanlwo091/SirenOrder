package com.example.sirenorder.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.common.Pagination;
import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.CartVO;
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
	
	@RequestMapping(value = "searchStore", method = RequestMethod.POST)//가게 이름을 return 한다. 
	@ResponseBody
	public Object searchStore(HttpServletRequest request) throws Exception {
		String chain = request.getParameter("chain");
		ArrayList<StoreVO> arrList = storebiz.getChain(chain);
		if(arrList.size() == 0) {
			System.out.println("찾는 가게가 없습니다.");
		}
		else {
			return arrList;
		}
		return "fail";//로그인 첫 페이지로 /index.html
	}
	//checkout 장바구니 보기
	
	public ArrayList<PointVO> makeCart(HttpSession httpSession) throws Exception {
		HashMap<CartVO, Integer> cartProduct =  (HashMap<CartVO, Integer>) httpSession.getAttribute("cartProduct");

		Iterator<CartVO> itr;
		if(cartProduct != null) {
			 itr = cartProduct.keySet().iterator();
		}else{
			return null;
		}
		
		HashMap<String, Boolean> chain_names = new HashMap<String, Boolean >();
		while (itr.hasNext()) {
			CartVO tmp = itr.next();
			if(chain_names.get(tmp.getChain_name()) == null) {
				chain_names.put(tmp.getChain_name(), true);
			}
		}
		
		Iterator<String> itr2 = chain_names.keySet().iterator();
		ArrayList<PointVO> arrayList = new ArrayList<PointVO>();
		while (itr2.hasNext()) {
			String chain_names_next = itr2.next();
			PointVO pointVO = pointbiz.getByChain_name(chain_names_next);
			arrayList.add(pointVO);
		}
		return arrayList;
	}
	
	@RequestMapping(value = "/checkOut.html", method = RequestMethod.GET)
	public ModelAndView checkOut(HttpServletRequest request) throws Exception {
		System.out.println("checkout에 들어왔다. ");
		ModelAndView model = new ModelAndView();
		HttpSession httpSession = request.getSession();
		
		ArrayList<PointVO> arrayList = makeCart(httpSession);
		
		if(arrayList == null) {
			model.addObject("checkout", "clicked");
			model.setViewName("thymeleaf/main");
			return model;
		}
		model.addObject("arrayList", arrayList);
		model.addObject("checkout", "clicked");
		model.setViewName("thymeleaf/main");
		return model;
	}
	
	public void usePoint(PointList pointlist, String users_id) throws Exception {
		for(int i = 0 ;i < pointlist.getChain_name().length;i++) {//포인트의 개수에 따라서 point를 update한다. 
			if(pointlist.getUseOrNot()[i] == 1) {//포인트를 사용할 것이면
				
				String chain_name = pointlist.getChain_name()[i];
				int point = pointlist.getPoint()[i];
				String point_id = pointlist.getPoint_id()[i];
				
				System.out.println(users_id);
				
				PointVO pointVO = new PointVO();
				pointVO.setPoint(point);
				pointVO.setPoint_id(point_id);
				pointVO.setChain_name(chain_name);
				pointVO.setUsers_id(users_id);
				System.out.println(pointVO);
				pointbiz.update(pointVO);
			}
		}
	}
	
	public void getPoint(String chain_name) throws Exception {
		pointbiz.getByChain_name(chain_name);
	}
	
	public void makePoint_store(PointList pointlist, String users_id) throws Exception {//두 지점 사용 했을 때 banapresso 홍대, banapresso 신촌 중 처음 나오는 것에서 포인트 사용 했다고 저장 
		for(int i= 0;i < pointlist.getChain_name().length;i++) {
			String chain_name = pointlist.getChain_name()[i];
			int point = pointlist.getPoint()[i];
			int useOrNot = pointlist.getUseOrNot()[i];// 1이면 point 사용 0이면 사용 안함
			
			if(useOrNot == 1) {//포인트 사용 
				Point_storeVO point_storeVO = new Point_storeVO();
				PointVO temp = pointbiz.getByChain_name(chain_name);
				point_storeVO.setPoint_store_id("point_store_id");
				point_storeVO.setPoint_id(temp.getPoint_id());
				StoreVO storeVO = storebiz.get(pointlist.getStore_name()[i]);//store_name 으로 store_id 얻기 
				
				String store_id = storeVO.getStore_id();
				
				point_storeVO.setStore_id(store_id);
				point_storeVO.setUsers_id(temp.getUsers_id());
				point_storeVO.setChain_name(temp.getChain_name());
				point_storeVO.setPoint_date(new java.sql.Date( System.currentTimeMillis() ));//자바 date to 오라클 date
				point_storeVO.setUsed_point(point);
				point_storebiz.register(point_storeVO);	
			}else {
				
			}
		}
	}
	
	public void makeOrders(String users_id,  PointList pointlist) throws Exception {//orders 테이블에 insert
		int totalPrice = 0;
		for(int i = 0 ;i < pointlist.getChain_name().length;i++) {
			totalPrice += pointlist.getTotalPrice()[i];
		}
		OrdersVO ordersVO = new OrdersVO();
		ordersVO.setOrders_id("orders_id");
		ordersVO.setOrders_date(new java.sql.Date(System.currentTimeMillis()));
		ordersVO.setPayment_way("before_card");
		ordersVO.setUsers_id(users_id);
		ordersVO.setTotal_price(totalPrice);
		ordersbiz.register(ordersVO);
		System.out.println("orders completed");
	}
	public void makeOrders_detail(PointList pointList) throws Exception {//orders_detail 테이블에 insert
		String orders_id = "orders_id"+Integer.toString(orders_detailbiz.getOrders_seq()-1 );//orders_list에 연결된 orders_id
		System.out.println(orders_id);
		String [][] tempName = pointList.getProductName();
		int productNum = 0;
		
		for(int i= 0 ;i < 5; i++) {
			if(tempName[i][0] == null)break;
			for(int j = 0 ;j < 5;j++) {
				if(tempName[i][j] != null)productNum++;
				if(tempName[i][j] == null )break;

				Orders_detailVO orders_detailVO = new Orders_detailVO();
				orders_detailVO.setOrders_detail_id("orders_detail_id");
				orders_detailVO.setPrice(pointList.getProductPrice()[i][j]);
				orders_detailVO.setQuantity(pointList.getProductQuantity()[i][j]);
				orders_detailVO.setOrders_id(orders_id);
				orders_detailVO.setProduct_id( productbiz.getProduct_id(tempName[i][j]));
				orders_detailbiz.register(orders_detailVO);

			}
		}
		//orders의 last sequence num을 가지고와서 그를 기준으로 외래키 참조하고 orders_detail을 만든다. 
	}
	
	@RequestMapping(value = "buyProduct", method = RequestMethod.POST)
	public ModelAndView buyProduct(@ModelAttribute("pointlist") PointList pointList,HttpServletRequest request) throws Exception {
		HttpSession httpSession = request.getSession();
		String users_id = (String) httpSession.getAttribute("userId");
		ModelAndView model = new ModelAndView();
		//포인트가 미사용 이라면 
		if(pointList.getChain_name() == null) {//포인트 아무 것도 안 쓴 겨우 orders 와 orders_detail을 만든다. 
			System.out.println("null");
		}else {//point 사용한 경우 point update 및 point_store생성 
			usePoint(pointList, users_id);
			makePoint_store(pointList,users_id);
		}
		
		makeOrders(users_id, pointList);
		makeOrders_detail(pointList);
		
		model.addObject("checkout", "clicked");
		model.setViewName("thymeleaf/main");
		return model;
	}
	@RequestMapping(value = "/orderStatus.html", method = RequestMethod.GET)
	public ModelAndView orderStatus(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int page , 
			@RequestParam(required = false, defaultValue = "1") int range  
			) throws Exception {
		HttpSession httpSession = request.getSession();
		String users_id = (String) httpSession.getAttribute("userId");
		ModelAndView model = new ModelAndView();
		//포인트가 미사용 이라면 
		if(users_id.equals(null)) {
			model.setViewName("redirect:/index.html");
		}
		
		int listCnt = orders_detailjoinproductbiz.getOrders_detailCnt();
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, range, listCnt);
		int startList = pagination.getStartList();
		int listSize = pagination.getListSize();
		ArrayList<Orders_detailJoinProductVO> list = orders_detailjoinproductbiz.getOrdersStatus(startList, listSize);
		
		
		

		
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		

		model.addObject("order", "clicked");
		return model;
	}
}
