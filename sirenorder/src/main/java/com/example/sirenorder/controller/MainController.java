package com.example.sirenorder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	
	@RequestMapping(value = "/main.html", method = RequestMethod.GET)
	public String mainView() {
		return "thymeleaf/main";//로그인 첫 페이지로 /index.html
	}
	@RequestMapping(value = "/tables.html", method = RequestMethod.GET)
	public String example() {
		return "thymeleaf/notifications";//로그인 첫 페이지로 /index.html
	}
}
