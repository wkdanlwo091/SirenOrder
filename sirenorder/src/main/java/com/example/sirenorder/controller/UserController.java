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
import org.springframework.web.servlet.ModelAndView;
import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.UserVO;
@Controller
public class UserController {

	@Resource(name = "userbiz")
	Biz<String, UserVO> userbiz;
	// login 
	//@GetMapping("login")
	
	@RequestMapping("/")
	public String home(HttpServletRequest request) {
		if(request.getParameter("login").equals("fail")) {
			System.out.println("login fail");
		}
		System.out.println("login page");
		return "index.html";//thymeleaf/index하면 에러가 나네 왠지 몰라도
	}
	@RequestMapping("/login.html")
	public String login(@ModelAttribute UserVO user, HttpSession session, Model model) {
		String users_id = user.getUsers_id();
		System.out.println("id의 값은 " + users_id);
		UserVO result = userbiz.get(users_id);//디비에서 사용자 이름 가져오기

		if(result == null) {
			model.addAttribute("login", "failed");
		}
		else if(result != null) {
			//main 화면으로 
			session.setAttribute("userId", result.getUsers_id());
			session.setAttribute("userName", result.getUsers_name());
			return "thymeleaf/hello";
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
		
		//아이디 비번이 틀렸다고하면 이 값을 model에 넘겨줘야 한다. 희안하게 thymeleaf/index하면 에러가 나더라  아마도 thymeleaf 입력부분이 이상한듯 
		return "redirect:index.html?login=fail";
	}

	@RequestMapping("/register.html")//별 문제 없다. 
	public String register(HttpServletRequest request) {
		System.out.println("entered login.top");
		return "thymeleaf/register";
	}
	@RequestMapping("/hello")
	public String hello() {
		System.out.println("haha");
		return "thymeleaf/hello";
	}
}