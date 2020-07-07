package com.example.sirenorder.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.common.Pagination;
import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.Orders_detailJoinProductVO;
import com.example.sirenorder.vo.Orders_detailVO;
import com.example.sirenorder.vo.Orders_detail_idList;
import com.example.sirenorder.vo.PaginationOwner;
import com.example.sirenorder.vo.ProductVO;
import com.example.sirenorder.vo.StoreVO;
import com.example.sirenorder.vo.StoreVO;
import com.example.sirenorder.vo.Store_nameAndDate;
import com.example.sirenorder.vo.SumAndOrders_date;
import com.example.sirenorder.vo.UserVO;

//사업자 웹페이지 담당	
@Controller
public class OwnerController {
	@Resource(name = "userbiz")
	Biz<String, UserVO> userbiz;
	
	@Resource(name = "orders_detailbiz")
	Biz<String, Orders_detailVO> orders_detailbiz;
	
	@Resource(name = "productbiz")
	Biz<String, ProductVO> productbiz;
	
	@Resource(name = "orders_detailjoinproductbiz")
	Biz<String, Orders_detailJoinProductVO> orders_detailjoinproductbiz;
	
	@Resource(name = "storebiz")
	Biz<String, StoreVO> storebiz;

	@RequestMapping(value = "/ownermain.html", method = RequestMethod.GET) // 처음 들어 왔을 때
	public ModelAndView ownermain(HttpServletRequest request) throws Exception {
		HttpSession httpSession = request.getSession();
		ModelAndView model = new ModelAndView();
		String users_id = (String) httpSession.getAttribute("userId");
		if (users_id == null) {
			model.setViewName("redirect:/index.html");
			return model;//로그인 첫 페이지로 /index.html
		}
		if (userbiz.get(users_id).getRole().equals("owner")) {

		} else {
			model.setViewName("redirect:/main.html");
			return model;
		}
		model.addObject("ownerOrderStatus", "clicked");
		model.setViewName("thymeleaf/ownermain");
		return model;
	}

	@RequestMapping(value = "/addItem.html", method = RequestMethod.GET) // 처음 들어 왔을 때 상점이 가지고 있는 아이템 list를 return한다. 
	public ModelAndView addItem(HttpServletRequest request) throws Exception {
		HttpSession httpSession = request.getSession();
		ModelAndView model = new ModelAndView();
		String users_id = (String) httpSession.getAttribute("userId");
		if (users_id == null) {
			model.setViewName("redirect:/index.html");
			return model;//로그인 첫 페이지로 /index.html
		}
		
		if (userbiz.get(users_id).getRole().equals("owner")) {

		} else {
			model.setViewName("redirect:/main.html");
			return model;
		}
		
		model.addObject("addItem", "clicked");
		model.setViewName("thymeleaf/ownermain");
		return model;
	}
	@RequestMapping(value = "/addItem.html", method = RequestMethod.POST) // 처음 들어 왔을 때 상점에 걸린 제품들을 return 한다. 
	public ModelAndView addItemPost(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") String product_name,
			@RequestParam(required = false, defaultValue = "1") String image_url,
			@RequestParam(required = false, defaultValue = "1") int price
			) throws Exception {
		HttpSession httpSession = request.getSession();
		ModelAndView model = new ModelAndView();
		String users_id = (String) httpSession.getAttribute("userId");
		if (users_id == null) {
			model.setViewName("redirect:/index.html");
			return model;//로그인 첫 페이지로 /index.html
		}
		if (userbiz.get(users_id).getRole().equals("owner")) {
		} else {
			model.setViewName("redirect:/main.html");
			return model;
		}
		ProductVO productVO = new ProductVO();
		productVO.setChain_name(storebiz.get().get(0).getChain_name());
		productVO.setImage(image_url);
		productVO.setPrice(price);
		productVO.setProduct_id("product_id");
		productVO.setProduct_name(product_name);
		productbiz.register(productVO);
		
		model.addObject("addItem", "clicked");
		model.setViewName("thymeleaf/ownermain");
		return model;
	}

	@RequestMapping(value = "/ownerOrderStatus.html", method = RequestMethod.GET) //
	public ModelAndView ownerOrderStatus(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "1") int range,
			@RequestParam(required = false, defaultValue = "nothing") String orders_detail_id) throws Exception {
		ModelAndView model = new ModelAndView();
		HttpSession httpSession = request.getSession();
		String store_name = (String) httpSession.getAttribute("store_name");
		if (httpSession.getAttribute("userId") == null) {// 아이디 로그인 안 했을 시 로그인 해라로 간다.
			model.setViewName("redirect:/index.html");
			return model;
		}
		if (orders_detail_id.equals("nothing")) {

		} else {// not_done에서 done으로 바꾼다.
			Orders_detailVO m = new Orders_detailVO();
			m.setOrders_detail_id(orders_detail_id);
			orders_detailbiz.update(m);
		}

		int listCnt;
		int startList;
		int listSize;
		listCnt = orders_detailbiz.getOrders_detailCntByStore_name(store_name);
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, range, listCnt);
		startList = pagination.getStartList();
		listSize = pagination.getListSize();

		PaginationOwner paginationOwner = new PaginationOwner();
		paginationOwner.setStartList(startList);
		paginationOwner.setStore_name(store_name);
		List<Orders_detailJoinProductVO> List = orders_detailjoinproductbiz
				.getOrders_detailJoinProductByStore_name(paginationOwner);
		model.addObject("pagination", pagination);
		model.addObject("store_name", store_name);

		model.addObject("ownerOrderStatus", "clicked");
		model.setViewName("thymeleaf/ownermain");
		model.addObject("store_name", store_name);// 체인점 중 가게를 구분하기 위한 변수
		if (List.size() == 0) {
			// System.out.println("가게에 물건이 없습니다.");
			model.addObject("List", List);
		} else {
			// System.out.println("가게에 물건이 있습니다.");
			model.addObject("List", List);
		}
		return model;
	}

	// 물건을 not_done에서 done으로 변환
	@RequestMapping(value = "/ownerOrderStatus.html", method = RequestMethod.POST) //
	public ModelAndView ownerOrderFinsh(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "1") int range, 
			Orders_detail_idList orders_detail_idList
			// 주문완료 되었다고 변경하는 정보를 담은 배열 클래스
	) throws Exception {
		ModelAndView model = new ModelAndView();
		HttpSession httpSession = request.getSession();
		String store_name = (String) httpSession.getAttribute("store_name");
		if (httpSession.getAttribute("userId") == null) {// 아이디 로그인 안 했을 시 로그인 해라로 간다.
			model.setViewName("redirect:/index.html");
			return model;
		}
		for (int i = 0; i < orders_detail_idList.getOrders_detail_id().length; i++) {
			if (orders_detail_idList.getOrders_detail_id()[i] != null) {
				Orders_detailVO temp = new Orders_detailVO();
				temp.setOrders_detail_id(orders_detail_idList.getOrders_detail_id()[i]);
				orders_detailbiz.update(temp);
			}
		}
		
		int listCnt;
		int startList;
		int listSize;
		listCnt = orders_detailbiz.getOrders_detailCntByStore_name(store_name);
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, range, listCnt);
		startList = pagination.getStartList();
		listSize = pagination.getListSize();

		PaginationOwner paginationOwner = new PaginationOwner();
		paginationOwner.setStartList(startList);
		paginationOwner.setStore_name(store_name);
		List<Orders_detailJoinProductVO> List = orders_detailjoinproductbiz
				.getOrders_detailJoinProductByStore_name(paginationOwner);
		model.addObject("pagination", pagination);
		model.addObject("store_name", store_name);

		model.addObject("ownerOrderStatus", "clicked");
		model.setViewName("thymeleaf/ownermain");
		model.addObject("store_name", store_name);// 체인점 중 가게를 구분하기 위한 변수
		if (List.size() == 0) {
			// System.out.println("가게에 물건이 없습니다.");
			model.addObject("List", List);
		} else {
			// System.out.println("가게에 물건이 있습니다.");
			model.addObject("List", List);
		}
		return model;
	}
	
	//매출액 보기 
	@RequestMapping(value = "/incomeChart.html", method = RequestMethod.GET) //
	public ModelAndView ownerIncome(HttpServletRequest request, 
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date from,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date to
			// 주문완료 되었다고 변경하는 정보를 담은 배열 클래스
	) throws Exception {
		ModelAndView model = new ModelAndView();
		HttpSession httpSession = request.getSession();
		String store_name = (String) httpSession.getAttribute("store_name");
		if (httpSession.getAttribute("userId") == null) {// 아이디 로그인 안 했을 시 로그인 해라로 간다.
			model.setViewName("redirect:/index.html");
			return model;
		}
		System.out.println("entered");
		model.addObject("incomeChart", "clicked");
		model.setViewName("thymeleaf/ownermain");
		return model;
	}
	
	
	//연도 월을 지정한 form 을 받아서 돌려주는 함수 
	@RequestMapping(value = "/incomeChart.html", method = RequestMethod.POST) //
	public ModelAndView ownerIncomePost(HttpServletRequest request, 
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date from,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date to,
			@RequestParam(required = false) String option
			// 주문완료 되었다고 변경하는 정보를 담은 배열 클래스
	) throws Exception {
		ModelAndView model = new ModelAndView();
		HttpSession httpSession = request.getSession();
		String store_name = (String) httpSession.getAttribute("store_name");
		if (httpSession.getAttribute("userId") == null) {// 아이디 로그인 안 했을 시 로그인 해라로 간다.
			model.setViewName("redirect:/index.html");
			return model;
		}
		Store_nameAndDate temp = new Store_nameAndDate();
		temp.setStore_name(store_name);
		temp.setFrom( new java.sql.Date(from.getTime()));
		temp.setTo( new java.sql.Date(to.getTime()));
		JSONArray ja = new JSONArray();
		ArrayList<SumAndOrders_date> list = orders_detailbiz.getIncomeBystore_nameDayRange(temp);// 디비에서 데이터 가져오는 것
		if(option.equals("년")) {
			int currentYear = 0;
			int currentSum = 0;

			for(int j = 0;  j <  list.size(); j++) {
				if(j == 0 ) {//첫번 째 년도 
					currentYear = list.get(j).getOrders_date().getYear() + 1900;// date가 -1900한 것을 return 하므로 1900 더한다. 
				}
				if(list.get(j).getOrders_date().getYear()+1900 != currentYear) {// 년도 바뀔 때 전의 sum, year 더한다. 바뀐 연도로 교환, sum 초기화
					JSONObject jo = new JSONObject();
					jo.put("currentDate", currentYear);
					jo.put("currentSum", currentSum);
					ja.add(jo);
					currentYear = list.get(j).getOrders_date().getYear() + 1900;
					currentSum = 0;
				}
				
				currentSum += list.get(j).getSum();
				if(j == list.size()-1) {//마지막에 json object 더하기 
					JSONObject jo = new JSONObject();

					jo.put("currentDate", currentYear);
					jo.put("currentSum", currentSum);
					ja.add(jo);
				}
			}
		}else if(option.equals("월")) {///2017 2018 2019 년 이면  3년치 데이터 다 가져와서 365개의 데이터 평균 내보네  
			int currentMonth = 0;
			int currentSum = 0;
			
			for(int j = 0;  j <  list.size(); j++) {
				if(j == 0 ) {
					currentMonth =  list.get(j).getOrders_date().getMonth()+1 ;//6월이면 5월을 return해서 1을 더해주었다. 
				}
				if(list.get(j).getOrders_date().getMonth()+1  != currentMonth) {// 월이 바뀔 때마다 month와 sum을 바꾼다. 
					JSONObject jo = new JSONObject();
					jo.put("currentDate", currentMonth);
					jo.put("currentSum", currentSum);
					ja.add(jo);
					currentMonth = list.get(j).getOrders_date().getMonth()+1;
					currentSum = 0;
				}
				currentSum += list.get(j).getSum();
				
				if(j == list.size()-1) {//마지막에 json object 더하기 
					JSONObject jo = new JSONObject();
					jo.put("currentDate", currentMonth);
					jo.put("currentSum", currentSum);
					ja.add(jo);
				}
			}
			///1월 이면 31개의 평균  2월  30개의 평균 치를 데이터로 내보네 
		}else if(option.equals("일")) {
			
			for(int j = 0;  j <  list.size(); j++) {
					JSONObject jo = new JSONObject();
					jo.put("currentDate", list.get(j).getOrders_date());
					jo.put("currentSum", list.get(j).getSum());
					ja.add(jo);
			}

		}
		model.addObject("ja", ja);//javascript에서 string으로 받는다. 
		model.addObject("message", "exist");
		model.addObject("incomeChart", "clicked");
		model.setViewName("thymeleaf/ownermain");
		return model;
	}
}