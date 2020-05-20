package com.example.sirenorder.controller;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.UserVO;
@Controller
public class UserController {

	@Resource(name = "userbiz")
	Biz<String, UserVO> userbiz;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model, UserVO vo) {
		
		HttpSession httpSession = request.getSession();
		try {
			if(request.getParameter("login").equals("fail")) {
				System.out.println("로그인 실패 ");
				model.addAttribute("login", "fail");
			}
		}catch(Exception e) {
		}
		
		if(httpSession.getAttribute("id") == null) { 
			System.out.println("세션이 없습니다.1");
		}else {
			System.out.println("세션이 있습니다.2");
			return "redirect:main";
		}
		
		vo.setUsers_id("");
		return "thymeleaf/index";//로그인 첫 페이지로 /index.html
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)//로그인 실패한 경우
	public String loginView(@ModelAttribute UserVO user, HttpServletRequest request ,RedirectAttributes redirect) {//
		String users_id = user.getUsers_id();
		System.out.println("id의 값은 " + users_id);	
		System.out.println("post 방식 ");

		UserVO result = userbiz.get(users_id);//디비에서 사용자 이름 가져오기
		if(result == null) {
			System.out.println("아이디 없습니다.");
		}
		else if(result != null) {//세션에 아이디 비밀번호 저장 후 메인 페이지로 이동한다.
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("userId", result.getUsers_id());
			httpSession.setAttribute("userName", result.getUsers_name());
			return "thymeleaf/main";
		}
		
		/*
		ArrayList<UserVO> every = userbiz.get();
		int i = 0 ;
		for(UserVO tmp: every) {
			System.out.println(i + "유저 정보는 " + tmp.getUsers_id());//가져오기는 하는데 null이라...
			System.out.println(i + "유저 정보는 " + tmp.getUsers_password());//가져오기는 하는데 null이라...
			System.out.println(i + "유저 정보는 " + tmp.getUsers_name());//가져오기는 하는데 null이라...
			System.out.println(i + "유저 정보는 " + tmp.getUsers_address());//가져오기는 하는데 null이라...
			System.out.println(i + "유저 정보는 " + tmp.getSex());//가져오기는 하는데 null이라...
			System.out.println(i + "유저 정보는 " + tmp.getRole());//가져오기는 하는데 null이라...
			i++;
		}
		*/
		//왜 lombok에서 vo값 userId 이 인식이 안되는가? 예를들어 tmp.getUsers

		//insert 성공 
		/*
		UserVO mine = new UserVO();
		mine.setUsers_id("01087484206");
		mine.setUsers_password("1234");
		mine.setUsers_name("jang");
		mine.setUsers_address("none");
		mine.setSex("male");
		mine.setRole("user");
		try {
			userbiz.register(mine);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		/*
		System.out.println(result.getUsers_id());
		*/
			//login fail t login page로
		
		return "redirect:/?login=fail";//id 없다. 
	}

	@RequestMapping("/register.html")//별 문제 없다. 
	public String register(UserVO user) {
		System.out.println("entered login.top");
		return "thymeleaf/register";
	}
	
	@RequestMapping("/logout.html")//별 문제 없다. 
	public String logout(HttpServletRequest request) {
		System.out.println("entered login.top");
		return "thymeleaf/register";
	}
	
	@RequestMapping("/forgot.html")//별 문제 없다. 
	public String forgot(HttpServletRequest request) {
		return "thymeleaf/forgot";
	}
	
	@RequestMapping("/hello")
	public String hello() {
		System.out.println("haha");
		return "thymeleaf/hello";
	}
}