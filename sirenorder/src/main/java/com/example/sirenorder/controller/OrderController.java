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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.CartVO;
import com.example.sirenorder.vo.OrdersVO;
import com.example.sirenorder.vo.Orders_detailVO;
import com.example.sirenorder.vo.PointVO;
import com.example.sirenorder.vo.Point_storeVO;
import com.example.sirenorder.vo.Pointlist;
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
	
	@RequestMapping(value = "/checkOut.html", method = RequestMethod.GET)
	public ModelAndView checkOut(HttpServletRequest request) throws Exception {
		System.out.println("checkout에 들어왔다. ");
		ModelAndView model = new ModelAndView();
		HttpSession httpSession = request.getSession();
		HashMap<CartVO, Integer> cartProduct =  (HashMap<CartVO, Integer>) httpSession.getAttribute("cartProduct");
		Iterator<CartVO> itr;
		if(cartProduct != null) {
			 itr = cartProduct.keySet().iterator();
		}else{
			model.addObject("checkout", "clicked");
			model.setViewName("thymeleaf/main");
			return model;
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
		System.out.println("arraylist size는 "  + arrayList.size());
		model.addObject("arrayList", arrayList);
		model.addObject("checkout", "clicked");
		model.setViewName("thymeleaf/main");
		return model;
	}
	
	public void usePoint(Pointlist pointlist, String users_id) throws Exception {
		for(int i = 0 ;i < pointlist.getChain_name().length;i++) {//포인트의 개수에 따라서 point를 update한다. 
			if(pointlist.getUseOrNot()[i] == 1) {//포인트를 사용할 것이면
				String chain_name = pointlist.getChain_name()[i];
				int point = pointlist.getPoint()[i];
				PointVO pointVO = new PointVO();
				pointVO.setPoint(point);
				pointVO.setChain_name(chain_name);
				pointVO.setUsers_id(users_id);
				pointbiz.update(new PointVO());
			}
		}
	}
	
	public void getPoint(String chain_name) throws Exception {
		pointbiz.getByChain_name(chain_name);
	}
	
	public void makePoint_store(Pointlist pointlist, String users_id) {
		for(int i= 0;i < pointlist.getChain_name().length;i++) {
			String chain_name = pointlist.getChain_name()[i];
			int point = pointlist.getPoint()[i];
			int useOrNot = pointlist.getUseOrNot()[i];// 1이면 point 사용 0이면 사용 안함
			if(useOrNot == 1) {//포인트 사용 
				Point_storeVO point_storeVO = new Point_storeVO();
				point_storeVO.setPoint_store_id("point_store_id");
				System.out.println("store name is " + pointlist.getStore_name()[0]);
				PointVO temp = pointbiz.getByChain_name(chain_name);
				point_storeVO.setPoint_id(temp.getPoint_id());
				point_storeVO.setStore_id(pointlist.getStore_name()[i]);
				point_storeVO.setUsers_id(temp.getUsers_id());
				point_storeVO.setChain_name(temp.getChain_name());

				point_storeVO.setPoint_date(new java.sql.Date( System.currentTimeMillis() ));//자바 date to 오라클 date
				point_storeVO.setUsed_point(point);
			}else {
				
			}
		}
	}
	@RequestMapping(value = "buyProduct", method = RequestMethod.POST)
	public ModelAndView buyProduct(@ModelAttribute("pointlist") Pointlist pointlist,HttpServletRequest request) throws Exception {
		HttpSession httpSession = request.getSession();
		String users_id = (String) httpSession.getAttribute("users_id");
		//포인트가 미사용 이라면 
		
		if(pointlist.getChain_name() == null) {//포인트 아무 것도 안 쓴 겨우 orders 와 orders_detail을 만든다. 
			System.out.println("null");
		}else {//point 사용한 경우 point update 및 point_store생성 
			usePoint(pointlist, users_id);
			makePoint_store(pointlist,users_id);
		} 
		Scanner scan = new Scanner(System.in);
		scan.next();
		//chain의 상품들의 합보다 point 값이 크다면 상품들의 값을 뺀다. 
		//cart의 chain_name 기준으로 상품의 가격을 전달 
		for(int i = 0 ;i < pointlist.getChain_name().length; i++) {
			if(pointlist.getUseOrNot().equals("yes")) {//point 사용
				
			}else {
				PointVO pointVO = new PointVO();
				pointVO.setChain_name(pointlist.getChain_name()[i]);
			}
		}
		
		
		
		ModelAndView model = new ModelAndView();
		HashMap<CartVO, Integer> cartProduct =  (HashMap<CartVO, Integer>) httpSession.getAttribute("cartProduct");
		Iterator<CartVO> itr;
		if(cartProduct != null) {
			 itr = cartProduct.keySet().iterator();
		}else{
			model.addObject("checkout", "clicked");
			model.setViewName("thymeleaf/main");
			return model;
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
		System.out.println("arraylist size는 "  + arrayList.size());
		model.addObject("arrayList", arrayList);
		model.addObject("checkout", "clicked");
		model.setViewName("thymeleaf/main");
		return model;
	}

}
