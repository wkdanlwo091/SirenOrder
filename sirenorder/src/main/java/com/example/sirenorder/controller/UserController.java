package com.example.sirenorder.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.UserVO;
@Controller
public class UserController {

	@Resource(name = "userbiz")
	Biz<String, UserVO> userbiz;
	
	
//아래거 작동한다. 	
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String login() {
//		return "thymeleaf/index";//로그인 첫 페이지로 /index.html
//	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login() {
		return "thymeleaf/index";//로그인 첫 페이지로 /index.html
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)//로그인 실패한 경우
	public ModelAndView loginFail(ModelAndView model, UserVO user, HttpServletRequest request) {//
		String users_id = user.getUsers_id();
		System.out.println("id의 값은 " + users_id);	
		UserVO result = userbiz.get(users_id);//디비에서 사용자 이름 가져오기
		
		if(result == null) {
			System.out.println("아이디 없습니다.");
			model.setViewName("thymeleaf/index");
			model.addObject("login", "fail");
		}
		else if(result != null) {//세션에 아이디 비밀번호 저장 후 메인 페이지로 이동한다.
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("userId", result.getUsers_id());
			httpSession.setAttribute("userName", result.getUsers_name());
			model.setViewName("thymeleaf/main");
			return model;
		}
		
		
		return model;//id 없다. 
	}

	
	
//	@RequestMapping(value = "/", method = RequestMethod.POST)//로그인 실패한 경우
//	public String loginView(UserVO user, HttpServletRequest request ,RedirectAttributes redirect) {//
//		String users_id = user.getUsers_id();
//		System.out.println("id의 값은 " + users_id);	
//		UserVO result = userbiz.get(users_id);//디비에서 사용자 이름 가져오기
//		
//		if(result == null) {
//			System.out.println("아이디 없습니다.");
//		}
//		
//		else if(result != null) {//세션에 아이디 비밀번호 저장 후 메인 페이지로 이동한다.
//			HttpSession httpSession = request.getSession();
//			httpSession.setAttribute("userId", result.getUsers_id());
//			httpSession.setAttribute("userName", result.getUsers_name());
//			return "thymeleaf/main";
//		}
//		
//		/*
//		ArrayList<UserVO> every = userbiz.get();
//		int i = 0 ;
//		for(UserVO tmp: every) {
//			System.out.println(i + "유저 정보는 " + tmp.getUsers_id());//가져오기는 하는데 null이라...
//			System.out.println(i + "유저 정보는 " + tmp.getUsers_password());//가져오기는 하는데 null이라...
//			System.out.println(i + "유저 정보는 " + tmp.getUsers_name());//가져오기는 하는데 null이라...
//			System.out.println(i + "유저 정보는 " + tmp.getUsers_address());//가져오기는 하는데 null이라...
//			System.out.println(i + "유저 정보는 " + tmp.getSex());//가져오기는 하는데 null이라...
//			System.out.println(i + "유저 정보는 " + tmp.getRole());//가져오기는 하는데 null이라...
//			i++;
//		}
//		*/
//		//왜 lombok에서 vo값 userId 이 인식이 안되는가? 예를들어 tmp.getUsers
//
//		//insert 성공 
//		/*
//		UserVO mine = new UserVO();
//		mine.setUsers_id("01087484206");
//		mine.setUsers_password("1234");
//		mine.setUsers_name("jang");
//		mine.setUsers_address("none");
//		mine.setSex("male");
//		mine.setRole("user");
//		try {
//			userbiz.register(mine);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		*/
//		/*
//		System.out.println(result.getUsers_id());
//		*/
//			//login fail t login page로
//		
//		return "redirect:/?login=fail";//id 없다. 
//	}
	
    @ResponseBody	
	@RequestMapping(value = "/idCheck", method=RequestMethod.POST) 
	public int idCheck(@RequestBody UserVO user) {
    	System.out.println("하하 들어왔따");
		//아이디 중복 체크 검사하고 정상이면 넣는다.
    	System.out.println(user.getUsers_id());
		UserVO result = userbiz.get(user.getUsers_id());//디비에서 사용자 이름 가져오기
		if(result == null) {
			System.out.println("중복아니다.");	
			return 1;
		}else {
			System.out.println("중복이다");
		}
		return 0;//0을 돌려주면 중복이 아니다. 
	}

	
	@RequestMapping(value = "/register.html", method=RequestMethod.POST) 
	public String register(UserVO user) throws Exception {
    	UserVO m = new UserVO();
    	System.out.println(user);
    	/*
    	m.setUsers_id(user.getUsers_id());
    	m.setUsers_name(user.getUsers_name());
    	m.setUsers_password(user.getUsers_password());
    	m.setUsers_address(user.getUsers_address());
    	m.setSex(user.getSex());
    	m.setRole("user");
    	userbiz.register(m);
    	*/
    	return "redirect:/";//여기서 login 페이지로 redirect 시켜준다. 이렇게 해도 되고 modelandview로 model addattribute로 msg값을 줘도 된
	}
    
    @RequestMapping(value = "/register.html", method=RequestMethod.GET)
	public String register() {
		return "thymeleaf/register";
	}
	
	@RequestMapping("/logout.html")//별 문제 없다. 
	public String logout(HttpServletRequest request) {
		System.out.println("entered login.top");
		return "thymeleaf/register";
	}
	@RequestMapping("/forgot.html")// 비밀번호 잊어버렸을 때 실행 하는 함수 , 별 문제 없다. 
	public String forgot(HttpServletRequest request) {
		return "thymeleaf/forgot";
	}
	
    @RequestMapping(value="/requestObject", method=RequestMethod.POST)// simpleWithObject는 연습을 위한 function이다.
    @ResponseBody
    public String simpleWithObject(UserVO user) {
        //필요한 로직 처리
    	System.out.println("유저의 이름은" + user.getUsers_id());
        return user.getUsers_id();
    }
}