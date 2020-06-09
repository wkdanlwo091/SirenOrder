package com.example.sirenorder.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.example.sirenorder.*;
import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.Point_storeVO;

@Controller
public class Point_storeController {
	@Resource(name = "point_storebiz")
	Biz<String, Point_storeVO> point_storebiz;
	@RequestMapping(value = "/points.html", method = RequestMethod.GET)
	public ModelAndView points(HttpServletRequest request) {
		//로그인 안됐으면 바로 로그인 페이지로 가야지 
		HttpSession httpSession = request.getSession();
		String users_id = (String)httpSession.getAttribute("userId");
		ModelAndView model = new ModelAndView();
		if(users_id == null) {
			model.setViewName("redirect:/index.html");
			return model;//로그인 첫 페이지로 /index.html
		}
		//user에 맞는 point_store를 뽑는다. 
		model.addObject("points", "clicked");
		model.setViewName("thymeleaf/points");

		return model;
	}
	
	@RequestMapping(value = "usePoint", method = RequestMethod.GET)
	public ModelAndView points(HttpServletRequest request) {
		//로그인 안됐으면 바로 로그인 페이지로 가야지 
		HttpSession httpSession = request.getSession();
		String users_id = (String)httpSession.getAttribute("userId");
		ModelAndView model = new ModelAndView();
		if(users_id == null) {
			model.setViewName("redirect:/index.html");
			return model;//로그인 첫 페이지로 /index.html
		}
		//user에 맞는 point_store를 뽑는다. 
		model.addObject("points", "clicked");
		model.setViewName("thymeleaf/points");

		checkOut(request);
		return model;
	}
	

	
}
