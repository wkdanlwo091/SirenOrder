package com.example.sirenorder.controller;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.StoreVO;

@Controller
public class OrderController {

	@Resource(name = "storebiz")
	Biz<String, StoreVO> storebiz;
	
	@RequestMapping(value = "searchStore", method = RequestMethod.POST)
	@ResponseBody
	public Object searchStore(HttpServletRequest request) throws Exception {
		System.out.println("searchStore 들어왔다");
		String chain = request.getParameter("chain");
		System.out.println("chain name 은" + chain);
		ArrayList<StoreVO> arrList = storebiz.getChain(chain);
		System.out.println("chain 아이디는  " + chain);
		if(arrList.size() == 0) {
			System.out.println("찾는 가게가 없습니다.");
		}
		else {
			for(StoreVO u: arrList) {
				System.out.println("체인 이름은 " + u.getStore_name());
			}
			return arrList;
		}
		
		return "fail";//로그인 첫 페이지로 /index.html
	}
	
	
	
	//가게의 판매 물건을 가져오는 컨트롤러이다. 
	@RequestMapping(value = "sendStore", method = RequestMethod.POST)
	@ResponseBody
	public Object sendStore(HttpServletRequest request) throws Exception {
		System.out.println("searchStore 들어왔다");
		String store_name = request.getParameter("store_name");
		ArrayList<StoreVO> arrList = storebiz.getChain(store_name);
		if(arrList.size() == 0) {
			System.out.println("찾는 가게가 없습니다.");
		}
		else {
			for(StoreVO u: arrList) {
				System.out.println("체인 이름은 " + u.getStore_name());
			}
			return arrList;
		}
		
		return "fail";//로그인 첫 페이지로 /index.html
	}

}
