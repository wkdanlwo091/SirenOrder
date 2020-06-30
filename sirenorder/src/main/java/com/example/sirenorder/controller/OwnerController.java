package com.example.sirenorder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.sirenorder.vo.UserVO;

//사업자 웹페이지 담당	
@Controller
public class OwnerController {
	@RequestMapping(value = "/ownermain.html", method=RequestMethod.GET) //가입 신청 했을 때
	public String register(UserVO user) throws Exception {
    	return "ownermain.html";//여기서 login 페이지로 redirect 시켜준다. 이렇게 해도 되고 modelandview로 model addattribute로 msg값을 줘도 된
	}
}