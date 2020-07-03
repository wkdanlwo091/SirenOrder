package com.example.sirenorder.controller;

import java.util.List;
import java.util.Scanner;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.example.sirenorder.vo.UserVO;


//사업자 웹페이지 담당	
@Controller
public class OwnerController {
	@Resource(name = "userbiz")
	Biz<String, UserVO> userbiz;
	
	@Resource(name = "orders_detailbiz")
	Biz<String, Orders_detailVO> orders_detailbiz;

	@Resource(name = "orders_detailjoinproductbiz")
	Biz<String, Orders_detailJoinProductVO> orders_detailjoinproductbiz;

	@RequestMapping(value = "/ownermain.html", method=RequestMethod.GET) //처음 들어 왔을 때 
	public String ownermain(HttpServletRequest request) throws Exception {
		HttpSession httpSession = request.getSession();
		String users_id = (String) httpSession.getAttribute("userId");
		if(users_id == null ) {
			return "/index.html";
		}
		if(userbiz.get(users_id).getRole().equals("owner")) {
				
		}else {
			return "thymeleaf/main";
		}
 
    	return "thymeleaf/ownermain";
	}
	

	
	@RequestMapping(value = "/ownerOrderStatus.html", method=RequestMethod.GET) //
	public ModelAndView ownerOrderStatus(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int page , 
			@RequestParam(required = false, defaultValue = "1") int range,
			@RequestParam(required = false, defaultValue = "nothing") String orders_detail_id 
			) throws Exception{
		ModelAndView model = new ModelAndView();
		HttpSession httpSession = request.getSession();
		String store_name = (String) httpSession.getAttribute("store_name");
		if(httpSession.getAttribute("userId") == null) {//아이디 로그인 안 했을 시 로그인 해라로 간다. 
			model.setViewName("redirect:/index.html");
			return model;
		}
		if(orders_detail_id.equals("nothing")) {
			
		}else {//not_done에서 done으로 바꾼다. 
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
		listSize =  pagination.getListSize();

		PaginationOwner paginationOwner = new PaginationOwner();
		paginationOwner.setStartList(startList);
		paginationOwner.setStore_name(store_name);
		List<Orders_detailJoinProductVO> List = orders_detailjoinproductbiz.getOrders_detailJoinProductByStore_name(paginationOwner);
		model.addObject("pagination", pagination);
		model.addObject("store_name", store_name);
		 
		model.addObject("ownerOrderStatus", "clicked");
		model.setViewName("thymeleaf/ownermain");
		model.addObject("store_name", store_name);//체인점 중 가게를 구분하기 위한 변수 
		if(List.size() == 0) {
			//System.out.println("가게에 물건이 없습니다.");
			model.addObject("List", List);
		}
		else {
			//System.out.println("가게에 물건이 있습니다.");
			model.addObject("List", List);
		}
		return model;
	}
	
	//물건을 not_done에서 done으로 변환 
	@RequestMapping(value = "/ownerOrderFinsh.html", method=RequestMethod.POST) //
	public ModelAndView ownerOrderFinsh(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int page , 
			@RequestParam(required = false, defaultValue = "1") int range,
			Orders_detail_idList orders_detail_idList
			) throws Exception{
		ModelAndView model = new ModelAndView();
		HttpSession httpSession = request.getSession();
		String store_name = (String) httpSession.getAttribute("store_name");
		if(httpSession.getAttribute("userId") == null) {//아이디 로그인 안 했을 시 로그인 해라로 간다. 
			model.setViewName("redirect:/index.html");
			return model;
		}
		
		System.out.println(orders_detail_idList);
		
		Scanner scan = new Scanner(System.in);
		scan.next();
		
		int listCnt;
		int startList;
		int listSize;
		listCnt = orders_detailbiz.getOrders_detailCntByStore_name(store_name);
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, range, listCnt);
		startList = pagination.getStartList();
		listSize =  pagination.getListSize();

		PaginationOwner paginationOwner = new PaginationOwner();
		paginationOwner.setStartList(startList);
		paginationOwner.setStore_name(store_name);
		List<Orders_detailJoinProductVO> List = orders_detailjoinproductbiz.getOrders_detailJoinProductByStore_name(paginationOwner);
		model.addObject("pagination", pagination);
		model.addObject("store_name", store_name);
		 
		model.addObject("ownerOrderStatus", "clicked");
		model.setViewName("thymeleaf/ownermain");
		model.addObject("store_name", store_name);//체인점 중 가게를 구분하기 위한 변수 
		if(List.size() == 0) {
			//System.out.println("가게에 물건이 없습니다.");
			model.addObject("List", List);
		}
		else {
			//System.out.println("가게에 물건이 있습니다.");
			model.addObject("List", List);
		}
		return model;
	}
	
	@RequestMapping(value = "/ownerOrderStatus.html", method=RequestMethod.POST) //가입 신청 했을 때
	public ModelAndView ownerOrderStatusPost() throws Exception {
		ModelAndView model = new ModelAndView();
    	return model;
	}
}