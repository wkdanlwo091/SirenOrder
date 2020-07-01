package com.example.sirenorder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.common.Pagination;
import com.example.sirenorder.vo.ProductVO;


//사업자 웹페이지 담당	
@Controller
public class OwnerController {
	@RequestMapping(value = "/ownermain.html", method=RequestMethod.GET) //가입 신청 했을 때
	public String ownermain() throws Exception {
    	return "thymeleaf/ownermain";
	}
	
	@RequestMapping(value = "/ownerOrderStatus.html", method=RequestMethod.GET) //가입 신청 했을 때
	public ModelAndView ownerOrderStatus(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int page , 
			@RequestParam(required = false, defaultValue = "1") int range ) throws Exception{
		ModelAndView model = new ModelAndView();
		HttpSession httpSession = request.getSession();
		if(httpSession.getAttribute("userId") == null) {//아이디 로그인 안 했을 시 로그인 해라로 간다. 
			model.setViewName("redirect:/index.html");
			return model;
		}

		int listCnt;
		int startList;
		int listSize;
		listCnt = productbiz.getListCnt(chain_name);//상품 갯수 가져오기
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, range, listCnt);
		startList = pagination.getStartList();
		listSize =  pagination.getListSize();
		 
		List<ProductVO> List = productbiz.getProductList(chain_name, startList, listSize);
		model.addObject("pagination", pagination);
		model.addObject("chain_name", chain_name);
		model.addObject("store_name", store_name);

		model.addObject("ownerOrderStatus", "clicked");
		model.setViewName("thymeleaf/main");
		model.addObject("store_name", store_name);//체인점 중 가게를 구분하기 위한 변수 
		if(List.size() == 0) {
			//System.out.println("가게에 물건이 없습니다.");
			model.addObject("List", List);
		}
		else {
			//System.out.println("가게에 물건이 있습니다.");
			model.addObject("List", List);
		}
		return model;
	}
	
	@RequestMapping(value = "/ownerOrderStatus.html", method=RequestMethod.POST) //가입 신청 했을 때
	public ModelAndView ownerOrderStatusPost() throws Exception {
    	return "thymeleaf/ownermain";
	}
}