package com.example.sirenorder.controller;

import java.util.Scanner;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	public String login(HttpServletRequest request) {
		HttpSession temp = request.getSession();
		if (temp.getAttribute("userId") == null) {
			System.out.println("session null");
		} else {
			System.out.println("session not null");
			if (temp.getAttribute("owner") != null) {
				return "redirect:ownermain.html";
			}
			return "redirect:/main.html";
		}
		return "thymeleaf/index";// 로그인 첫 페이지로 /index.html
	}

	@RequestMapping("/androidData")
	public String androidData(HttpServletRequest request) {
		//토큰이 변했을 수도 있으므로 항상 update한다.
		UserVO temp = new UserVO();
		temp.setToken(request.getParameter("token"));
		temp.setUsers_id(request.getParameter("id"));
		try {
			userbiz.updateToken(temp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("yes " + request.getParameter("token")  + " " + request.getParameter("password") + " " + request.getParameter("id"));
		return "thymeleaf/main.html";// 로그인 첫 페이지로 /index.html
	}
	
	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public String loginIndex(HttpServletRequest request) {	
		HttpSession temp = request.getSession();
		if (temp.getAttribute("userId") == null) {
			System.out.println("session null");
		} else {
			System.out.println("session not null");
			if (temp.getAttribute("owner") != null) {
				return "redirect:ownermain.html";
			}
			return "redirect:/main.html";
		}
		return "thymeleaf/index";// 로그인 첫 페이지로 /index.html
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST) // 로그인 실패한 경우
	public ModelAndView loginFail(UserVO user, HttpServletRequest request) {//
		String users_id = user.getUsers_id();
		System.out.println("id의 값은 " + users_id);
		UserVO result = userbiz.get(users_id);// 디비에서 사용자 이름 가져오기
		ModelAndView model = new ModelAndView();
		if (result == null) {
		} else if (result != null) {// 세션에 아이디 비밀번호 저장 후 메인 페이지로 이동한다.
			// 아이디는 있는데 비밀번호 틀린경우
			if (user.getUsers_password().equals(result.getUsers_password())) {
				System.out.println("비번 맞습니다.");
				// 아래 값은 계속 유지된다.
				HttpSession httpSession = request.getSession();
				httpSession.setAttribute("userId", result.getUsers_id());
				httpSession.setAttribute("userName", result.getUsers_name());
				// model.setViewName("thymeleaf/main");
				if (result.getRole().equals("user")) {// 유저
					model.setViewName("redirect:/main.html");// 메인 컨트롤러의 thymeleaf/main으로 간다.
				} else if (result.getRole().equals("owner")) {// 사업자
					httpSession.setAttribute("owner", "owner");
					httpSession.setAttribute("store_name", result.getStore_name());//store 이름 지정해주었다. 
					model.setViewName("redirect:/ownermain.html");// 메인 컨트롤러의 thymeleaf/ownermain으로 간다.
				}
				return model;
			} else {
				System.out.println("비번이 틀립니다.");
				System.out.println(result.getUsers_password());
			}
		}
		model.setViewName("thymeleaf/index");
		model.addObject("login", "fail");
		return model;// id 없다.
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
	@RequestMapping(value = "/idCheck", method = RequestMethod.POST)
	public int idCheck(@RequestBody UserVO user) {
		System.out.println("하하 들어왔따");
		// 아이디 중복 체크 검사하고 정상이면 넣는다.
		System.out.println(user.getUsers_id());
		UserVO result = userbiz.get(user.getUsers_id());// 디비에서 사용자 이름 가져오기
		if (result == null) {
			System.out.println("중복아니다.");
			return 1;
		} else {
			System.out.println("중복이다");
		}
		return 0;// 0을 돌려주면 중복이 아니다.
	}

	@RequestMapping(value = "/register.html", method = RequestMethod.POST) // 가입 신청 했을 때
	public String register(UserVO user) throws Exception {
		UserVO m = new UserVO();
		System.out.println(user);
		m.setUsers_id(user.getUsers_id());
		m.setUsers_name(user.getUsers_name());
		m.setUsers_password(user.getUsers_password());
		m.setUsers_address(user.getUsers_address());
		m.setSex(user.getSex());
		m.setRole(user.getRole());
		if (user.getRole().equals("owner")) {
			m.setStore_name(user.getStore_name());
		}else {
			m.setStore_name("");
		}
		m.setToken("");
		userbiz.register(m);
		return "redirect:/";// 여기서 login 페이지로 redirect 시켜준다. 이렇게 해도 되고 modelandview로 model addattribute로
							// msg값을 줘도 된
	}

	@RequestMapping(value = "/register.html", method = RequestMethod.GET) // register 페이지 왔을 때
	public String register() {
		return "thymeleaf/register";
	}

	@RequestMapping("/logout.html") // 별 문제 없다.
	public String logout(HttpServletRequest request) {
		System.out.println("entered login.top");
		HttpSession session = request.getSession();
		session.invalidate();// 로그인 정보 , 장바구니 세션 정보 등 모든 정보 없앤다
		return "redirect:/";
	}

	@RequestMapping("/forgot.html") // 비밀번호 잊어버렸을 때 실행 하는 함수 , 별 문제 없다.
	public String forgot(HttpServletRequest request) {
		return "thymeleaf/forgot";
	}

	@RequestMapping("/profile.html") // 내 정보 보기
	public ModelAndView profile(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		if (httpSession.getAttribute("userId") == null) {// 아이디 로그인 안 했을 시 로그인 해라로 간다.
			ModelAndView model = new ModelAndView();
			model.setViewName("redirect:/index.html");
			return model;
		}
		ModelAndView model = new ModelAndView();
		model.addObject("profile", "clicked");
		model.setViewName("thymeleaf/main");
		return model;
	}
	@RequestMapping("/ownerprofile.html") // 내 정보 보기
	public ModelAndView ownerprofile(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		if (httpSession.getAttribute("userId") == null) {// 아이디 로그인 안 했을 시 로그인 해라로 간다.
			ModelAndView model = new ModelAndView();
			model.setViewName("redirect:/index.html");
			return model;
		}
		ModelAndView model = new ModelAndView();
		model.addObject("profile", "clicked");
		model.setViewName("thymeleaf/ownermain");
		return model;
	}
	
	@RequestMapping(value = "/getProfile", method = RequestMethod.POST)
	@ResponseBody
	public String getProfile(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		// 필요한 로직 처리
		System.out.println("getting profile");
		HttpSession session = request.getSession();
		String users_id = (String) session.getAttribute("userId");
		System.out.println(users_id);
		UserVO user = userbiz.get(users_id);
		jsonObject.put("users_name", user.getUsers_name());
		jsonObject.put("sex", user.getSex());
		jsonObject.put("users_address", user.getUsers_address());
		jsonObject.put("role", user.getRole());
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "updateProfile", method = RequestMethod.POST)
	@ResponseBody
	public String updateProfile(HttpServletRequest request) throws Exception {
		String users_password = request.getParameter("users_password");
		HttpSession httpSession = request.getSession();
		String users_id = (String) httpSession.getAttribute("userId");
		UserVO userVO = userbiz.get(users_id);
		
		if (userVO.getUsers_password().equals(users_password)) {
			// update한다.
			String users_new_address = request.getParameter("users_adderss");
			System.out.println(users_new_address);
			String users_new_name = request.getParameter("users_name");
			String users_new_password = request.getParameter("users_new_password");

			UserVO newUserVO = new UserVO();

			newUserVO.setUsers_address(users_new_address);
			newUserVO.setUsers_password(users_new_password);
			newUserVO.setUsers_name(users_new_name);
			newUserVO.setUsers_id(users_id);

			userbiz.update(newUserVO);
			return "success";
		} else {
		}
		return "fail";
	}
	
	@RequestMapping(value = "quitProfile", method = RequestMethod.POST)
	@ResponseBody
	public String quitProfile(HttpServletRequest request) throws Exception {
		HttpSession httpSession = request.getSession();
		String users_id = (String) httpSession.getAttribute("userId");
		
		System.out.println("hello QuitProfile" +  users_id);
		
		if (users_id == null) {
			return "fail";
		} else {
			userbiz.delete(users_id);
			httpSession.invalidate();
		}
		
		
		//user 지우면 on delete cascade 에 의해서 참조하는 외래키들의 데이터들이 모두 지워진다. 
		
		return "success";
	}
	@RequestMapping(value = "checkPassword", method = RequestMethod.POST)
	@ResponseBody
	public String checkPassword(HttpServletRequest request) throws Exception {
		HttpSession httpSession = request.getSession();
		String users_id = (String) httpSession.getAttribute("userId");
		String password = (String) request.getParameter("password");

		System.out.println(userbiz.get(users_id).getUsers_password()  +"  "  + password);
		if(userbiz.get(users_id).getUsers_password().equals(password)) {
			return "success";
		} 
		
		return "fail";
	}
	
	@RequestMapping(value = "/requestObject", method = RequestMethod.POST) // simpleWithObject는 연습을 위한 function이다.
	@ResponseBody
	public String simpleWithObject(UserVO user) {
		// 필요한 로직 처리
		System.out.println("유저의 이름은" + user.getUsers_id());
		return user.getUsers_id();
	}
}