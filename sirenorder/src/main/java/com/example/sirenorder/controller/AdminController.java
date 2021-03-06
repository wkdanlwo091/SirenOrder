package com.example.sirenorder.controller;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.ChainVO;
import com.example.sirenorder.vo.StoreVO;
import com.example.sirenorder.vo.UserVO;

@Controller
public class AdminController {
	
	@Resource(name = "userbiz")
	Biz<String, UserVO> userbiz;
	@Resource(name = "storebiz")
	Biz<String, StoreVO> storebiz;
	@Resource(name = "chainbiz")
	Biz<String, ChainVO> chainbiz;

	
	//
	@RequestMapping(value = "/adminmain.html", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		
		System.out.println("adminmain came");
		model.addObject("usercheck", "clicked");//usercheck.html을 띄우기
		model.addObject("search", "clicked");//usercheck 안에 있는 search object 할당
		model.setViewName("thymeleaf/adminmain");

		return model;// id 없다.
	}

	//사용자 체크 
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
	
	//submit 하면 오는 메소드
	@RequestMapping(value = "/usercheck.html", method = RequestMethod.POST)
	public ModelAndView usercheck(HttpServletRequest request, @RequestParam(required = false) String users_id) {
		ModelAndView model = new ModelAndView();
		if (users_id != null) {// 회원검색 
			ArrayList<UserVO> userVO = new ArrayList<UserVO>();
			UserVO userVO_id = userbiz.get(users_id);
			
			if(userVO_id != null) {
				userVO.add(userbiz.get(users_id));
			}else {
				
			}

			model.addObject("userVOs", userVO);
			model.addObject("usercheck", "clicked");
			model.setViewName("thymeleaf/adminmain");
		} else {// 모든 회원 보기 
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
	
	
	//owner 정보 업데이트 -->  오너의 chain 과 store 업데이트 한다. 
	@RequestMapping(value = "updateownerinfo", method = RequestMethod.POST)
    @ResponseBody
	public String updateownerinfo(HttpServletRequest request,
			@RequestParam(required = false) String users_id, 
			@RequestParam(required = false) String chain_name, 
			@RequestParam(required = false) String store_name
			) throws Exception {
		chain_name = chain_name.trim();
		store_name = store_name.trim();

		userbiz.get(users_id);
		UserVO userVO = new UserVO();
		userVO.setUsers_id(users_id);
		userVO.setStore_name(store_name);
		userbiz.updateStore_name(userVO);
		
		//여기서 chain_name에 따라서 있으면 안 만들고 없으면 만든다. 
		ChainVO chainVO = chainbiz.getByChain_name(chain_name);
		
		if(chainVO != null) {
			
		}else {//체인 만든다. 체인 만든 것은 owner가 chain의  대표가 된다. owner를 owner first로 변환한다. 
			chainVO = new ChainVO();
			chainVO.setChain_id("chain_id");
			chainVO.setPoint_rate(0.1);
			chainVO.setChain_name(chain_name);
			chainbiz.register(chainVO);
			//user의 role을 변경 
			userVO = new UserVO();
			userVO.setRole("owner_first");
			userVO.setUsers_id(users_id);
			userbiz.updateRole(userVO);
			System.out.println("user update success yes ");
		}
		//여기서 user의 store_name 업데이트 한다. 
		//store_name이 중복 안되면 success return 한다.  
		if(storebiz.getStore_id(store_name) == null) {
			StoreVO storeVO = new StoreVO();
			storeVO.setStore_id("store_id");
			storeVO.setStore_name(store_name);
			storeVO.setChain_id(chainbiz.getByChain_name(chain_name).getChain_id());
			storeVO.setChain_name(chain_name);
			storeVO.setPoint_rate(0.1);
			storeVO.setLimit(10000);
			storebiz.register(storeVO);
			return "success";
		}
		return "fail";// id 없다.
	}
}