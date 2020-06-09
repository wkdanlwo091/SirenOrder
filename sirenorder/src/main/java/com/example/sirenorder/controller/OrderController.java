package com.example.sirenorder.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.CartVO;
import com.example.sirenorder.vo.PointVO;
import com.example.sirenorder.vo.StoreVO;

@Controller
public class OrderController {

	@Resource(name = "storebiz")
	Biz<String, StoreVO> storebiz;
	@Resource(name = "pointbiz")
	Biz<String, PointVO> pointbiz;
	
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

	
	@RequestMapping(value = "buyProduct", method = RequestMethod.GET)
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

}
