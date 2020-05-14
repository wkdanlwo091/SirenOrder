package com.example.sirenorder.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.UserVO;

@Controller
public class UserController {

	@Resource(name = "userbiz")
	Biz<String, UserVO> userbiz;

	
	// login 
	@RequestMapping("/login.html")
	public String login(@ModelAttribute UserVO user, HttpSession session) {
		System.out.println(user.getUsers_id());
		UserVO result = userbiz.get(user.getUsers_id());//디비에서 사용자 이름 가져오기
		if(result.getUsers_id() != null) {
			//main 화면으로 
			session.setAttribute("userId", result.getUsers_id());
			session.setAttribute("userName", result.getUsers_name());
			return "thymeleaf/main";
		}
			//login fail 다시 login page로 
		return "thymeleaf/login";
	}
	@RequestMapping("/register.html")
	public String register(HttpServletRequest request) {
		System.out.println("entered login.top");
		return "thymeleaf/register";
	}
}
