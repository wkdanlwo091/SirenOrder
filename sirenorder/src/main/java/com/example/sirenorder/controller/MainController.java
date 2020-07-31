package com.example.sirenorder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	@RequestMapping(value = "/main.html", method = RequestMethod.GET)
	public ModelAndView mainView(HttpServletRequest request) {
		//로그인 안됐으면 바로 로그인 페이지로 가야지 
		HttpSession httpSession = request.getSession();
		String users_id = (String)httpSession.getAttribute("userId");
		
		ModelAndView model = new ModelAndView();
		if(users_id == null) {
			model.setViewName("redirect:/index.html");
			return model;//로그인 첫 페이지로 /index.html
		}
		model.addObject("order", "clicked");
		model.setViewName("thymeleaf/main");
		return model;
	}
	
	@RequestMapping(value = "/tables.html", method = RequestMethod.GET)
	public String example() {
		return "thymeleaf/notifications";//로그인 첫 페이지로 /index.html
	}
	@RequestMapping(value = "table_index", method = RequestMethod.GET)
	public String table_index() {
		return "/table_index.html";
	}
}