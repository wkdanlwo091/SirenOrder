package com.example.sirenorder.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.UserVO;

@Controller
public class UserController {

	@Resource(name = "userbiz")
	Biz<String, UserVO> userbiz;

	
	// login 
	@RequestMapping("/login.top")
	public ModelAndView login(HttpServletRequest request) {
		System.out.println("entered login.top");
		ModelAndView mv = new ModelAndView();
		mv.addObject("center", "../user/login");
		mv.setViewName("main/main");
		return mv;
	}
	@RequestMapping("/register.html")
	public String register(HttpServletRequest request) {
		System.out.println("entered login.top");
		return "thymeleaf/register";
	}
}
