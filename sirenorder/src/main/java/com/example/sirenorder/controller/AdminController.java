package com.example.sirenorder.controller;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.UserVO;

@Controller
public class AdminController {
	@Resource(name = "userbiz")
	Biz<String, UserVO> userbiz;

	@RequestMapping(value = "/adminmain.html", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();

		model.addObject("search", "clicked");
		model.addObject("usercheck", "clicked");
		model.setViewName("thymeleaf/adminmain");

		return model;// id 없다.
	}

	@RequestMapping(value = "/usercheck.html", method = RequestMethod.GET)
	public ModelAndView usercheck1(HttpServletRequest request, @RequestParam(required = false) String users_id) {
		ModelAndView model = new ModelAndView();
		if (users_id != null) {
			ArrayList<UserVO> userVO = new ArrayList<UserVO>();
			userVO.add(userbiz.get(users_id));
			model.addObject("userVOs", userVO);
			model.addObject("usercheck", "clicked");
			model.setViewName("thymeleaf/adminmain");
		} else {
			System.out.println("all users");
			ArrayList<UserVO> userVO = userbiz.get();
			System.out.println(userVO.get(0));
			// 모든 user 가져오기 페이지네이션은 추후에 적용하겠다. owner을 클릭하면 owner에게 chain_name과 store_name을
			// 할당 할 수 있다.
			model.addObject("userVOs", userVO);
			model.addObject("usercheck", "clicked");
			model.setViewName("thymeleaf/adminmain");
		}
		return model;// id 없다.
	}

	@RequestMapping(value = "/usercheck.html", method = RequestMethod.POST)
	public ModelAndView usercheck(HttpServletRequest request, @RequestParam(required = false) String users_id) {
		ModelAndView model = new ModelAndView();
		if (users_id != null) {
			ArrayList<UserVO> userVO = new ArrayList<UserVO>();
			userVO.add(userbiz.get(users_id));
			model.addObject("userVOs", userVO);
			model.addObject("usercheck", "clicked");
			model.setViewName("thymeleaf/adminmain");
		} else {
			System.out.println("all users");
			ArrayList<UserVO> userVO = userbiz.get();
			// 모든 user 가져오기 페이지네이션은 추후에 적용하겠다. owner을 클릭하면 owner에게 chain_name과 store_name을
			// 할당 할 수 있다.
			model.addObject("userVOs", userVO);
			model.addObject("usercheck", "clicked");
			model.setViewName("thymeleaf/adminmain");
		}
		return model;// id 없다.
	}

	@RequestMapping(value = "/updateownerinfo", method = RequestMethod.POST)
	public ModelAndView updateownerinfo(HttpServletRequest request,
			@RequestParam(required = false) String users_id, 
			@RequestParam(required = false) String chain_name, 
			@RequestParam(required = false) String store_name
			) {
		ModelAndView model = new ModelAndView();
		
		userbiz.get(users_id);
		UserVO userVO = new UserVO();
		userbiz.update(id);
		//여기서 user의 store_name 업데이트 하고 store_name 

		
		model.addObject("usercheck", "clicked");
		model.setViewName("thymeleaf/adminmain");
		return model;// id 없다.
	}

}