package com.example.sirenorder.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.common.Pagination;
import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.OrdersVO;
import com.example.sirenorder.vo.Orders_detailJoinProductVO;
import com.example.sirenorder.vo.Orders_detailVO;
import com.example.sirenorder.vo.Point_storeJoinStoreVO;
import com.example.sirenorder.vo.Point_storeVO;
import com.example.sirenorder.vo.ProductVO;
import com.example.sirenorder.vo.StoreVO;

@Controller
public class Point_storeController {
	@Resource(name = "point_storebiz")
	Biz<String, Point_storeVO> point_storebiz;
	
	@RequestMapping(value = "/points.html", method = RequestMethod.GET)
	public ModelAndView reviewPoints(HttpServletRequest request) {
		//로그인 안됐으면 바로 로그인 페이지로 가야지 
		HttpSession httpSession = request.getSession();
		String users_id = (String)httpSession.getAttribute("userId");
		ModelAndView model = new ModelAndView();
		if(users_id == null) {
			model.setViewName("redirect:/index.html");
			return model;//로그인 첫 페이지로 /index.html
		}
		//user에 맞는 point_store를 뽑는다. 
		model.addObject("points", "clicked");
		model.setViewName("thymeleaf/points");

		return model;
	}
	
	@RequestMapping(value = "usePoint", method = RequestMethod.GET)
	public ModelAndView usePoint(HttpServletRequest request) {
		//로그인 안됐으면 바로 로그인 페이지로 가야지 
		HttpSession httpSession = request.getSession();
		String users_id = (String)httpSession.getAttribute("userId");
		ModelAndView model = new ModelAndView();
		if(users_id == null) {
			model.setViewName("redirect:/index.html");
			return model;//로그인 첫 페이지로 /index.html
		}
		//user에 맞는 point_store를 뽑는다. 
		model.addObject("points", "clicked");
		model.setViewName("thymeleaf/points");
		return model;
	}
	
	@RequestMapping(value = "pointHistory.html", method = RequestMethod.GET)//pointHistory에 처음 들어왔을 때, 페이지네이션으로 범위검색 했을 때 
	public ModelAndView pointHistorybefore(HttpServletRequest request,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date from,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date to,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "1") int range) throws Exception {
		//로그인 안됐으면 바로 로그인 페이지로 가야지 
		HttpSession httpSession = request.getSession();
		String users_id = (String)httpSession.getAttribute("userId");
		ModelAndView model = new ModelAndView();
		if(users_id == null) {
			model.setViewName("redirect:/index.html");
			return model;//로그인 첫 페이지로 /index.html
		}
		if (from == null && to == null) { // 페이지네이션 아니고 아예 처음 들어왔을 때
			// 맨 처음 페이지에 들어왔을 때
			model.addObject("from", "");// 페이지에 orders_hitory 안 내놓기 위해
			model.addObject("to", "");//
		} else {
			// 같은 시작일 마침일 내에서 페이지네이션을 했을 경우   ex) 6월 1일 부터 7월 1일 까지 데이터가 많아서 페이지네이션이 된 경우 
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// 프론트의 자바스크립트에 날짜 표시하는 것
			String beforeFromState = formatter.format(httpSession.getAttribute("from"));// 전의 시작일
			String beforeToState = formatter.format(httpSession.getAttribute("to"));// 전의 마침일
			String currentFromState = null;
			String currentToState = null;
			
			if (from != null)
				currentFromState = formatter.format(from);// 새로 들어온 시작일
			else {// 들어온 데이터가 없으면 다시 return
				httpSession.setAttribute("from", "");
				httpSession.setAttribute("to", "");
				model.addObject("pointHistory", "clicked");
				model.setViewName("thymeleaf/main");
				return model;
			}
			
			if (to != null)
				currentToState = formatter.format(to);// 새로 들어온 마침일
			else {
				httpSession.setAttribute("from", "");
				httpSession.setAttribute("to", "");
				model.addObject("pointHistory", "clicked");
				model.setViewName("thymeleaf/main");
				return model;
			}
			
			System.out.println("start");
			System.out.println(beforeFromState);
			System.out.println(beforeToState);
			System.out.println(currentFromState);
			System.out.println(currentToState);
			System.out.println("done");
			
			if (beforeFromState.equals(currentFromState) && beforeToState.equals(currentToState)) // 전의 스테이트 현재 스테이트 같으면
			{// 같은 시작일, 끝일에 페이지네이션을 사용한 경우 들어온다.
				ArrayList<Point_storeJoinStoreVO> detailTemp = null;
				detailTemp = (ArrayList<Point_storeJoinStoreVO>) httpSession.getAttribute("pointList");//세션에서 가져오기 
				// 전의 스테이트에서 받은 orders_detailList 세션을 사용한다.
				System.out.println(page + " " + range + " ");
				int listCnt = detailTemp.size();// 주문 상품 각각의 개수
				Pagination pagination = new Pagination();
				pagination.pageInfo(page, range, listCnt);
				int startList = pagination.getStartList();
				int listSize = pagination.getListSize();

				// 상품이미지를 추가하기 위한것
				ArrayList<Point_storeJoinStoreVO> List = new ArrayList<Point_storeJoinStoreVO>();
				for (int i = (page - 1) * 6; i < (page - 1) * 6 + 6; i++) {// 6개의 데이터만 전송
					if (i >= detailTemp.size()) { /// detailTemp (orders_detail ) 사이즈를 넘어가면 break ex) 1 >= 1 이면 break;
						break;
					}
					Point_storeJoinStoreVO tempVO = new Point_storeJoinStoreVO();
					tempVO.setChain_name(detailTemp.get(i).getChain_name());
					tempVO.setPoint_date(detailTemp.get(i).getPoint_date());
					tempVO.setUsed_point(detailTemp.get(i).getUsed_point());
					tempVO.setPoint_date(detailTemp.get(i).getPoint_date());
					tempVO.setStoreVO(detailTemp.get(i).getStoreVO());
					List.add(tempVO);
				}
				
				model.addObject("pagination", pagination);
				model.addObject("List", List);
				model.addObject("from", formatter.format(from));// from을 페이지의 datepicker의 ${from} value에 넣기 위함
				model.addObject("to", formatter.format(to));// to을 페이지의 datepicker의 ${from} value에 넣기 위함
			}
		}

		//user에 맞는 point_store를 뽑는다. 
		model.addObject("pointHistory", "clicked");
		model.setViewName("thymeleaf/main");
		return model;
	}
	
	//어디서 point_store를 썼는지 출력 
	@RequestMapping(value = "pointHistory.html", method = RequestMethod.POST)
	public ModelAndView pointHistoryAfter(HttpServletRequest request,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date from,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date to,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "1") int range) throws Exception  {
		//로그인 안됐으면 바로 로그인 페이지로 가야지 
		HttpSession httpSession = request.getSession();
		String users_id = (String)httpSession.getAttribute("userId");
		ModelAndView model = new ModelAndView();
		if(users_id == null) {
			model.setViewName("redirect:/index.html");
			return model;//로그인 첫 페이지로 /index.html
		}
		
		System.out.println("pointHistory에 왔습니다. post");

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// 프론트의 자바스크립트에 날짜 표시하는 것
		model.addObject("from", formatter.format(from));// from을 페이지의 datepicker의 ${from} value에 넣기 위함
		model.addObject("to", formatter.format(to));// to을 페이지의 datepicker의 ${from} value에 넣기 위함
		httpSession.setAttribute("from", from);
		httpSession.setAttribute("to", to);
		java.sql.Date sqlDateFrom = new java.sql.Date(from.getTime());//java.sql.Date 쓴 이유는 db 접근할 떄 util 보다 낫다. 
		java.sql.Date sqlDateTo = new java.sql.Date(to.getTime());
		
		
		System.out.println(sqlDateFrom  + " "  + sqlDateTo);
		//날짜별로 point_store 가져오기 
		ArrayList<Point_storeJoinStoreVO> detailTemp = point_storebiz.getByDateFromToJoin(users_id, sqlDateFrom, sqlDateTo);// 여기서 orders_id 가져온다.
		httpSession.setAttribute("pointList", detailTemp);
		
		// 페이지네이션 관련
		int listCnt = detailTemp.size();// 주문 상품 각각의 개수
		
		
		System.out.println(listCnt);
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, range, listCnt);
		int startList = pagination.getStartList();
		int listSize = pagination.getListSize();

		ArrayList<Point_storeJoinStoreVO> List = new ArrayList<Point_storeJoinStoreVO>();
		
		// 상품이미지를 추가하기 위한것
		for (int i = (page - 1) * 6; i < (page - 1) * 6 + 6; i++) {// 6개의 데이터만 전송
			if (i >= detailTemp.size()) { /// detailTemp (orders_detail ) 사이즈를 넘어가면 break ex) 1 >= 1 이면 break;
				break;
			}
			Point_storeJoinStoreVO tempVO = new Point_storeJoinStoreVO();
			tempVO.setChain_name(detailTemp.get(i).getChain_name());
			tempVO.setPoint_date(detailTemp.get(i).getPoint_date());
			tempVO.setUsed_point(detailTemp.get(i).getUsed_point());
			tempVO.setPoint_date(detailTemp.get(i).getPoint_date());
			tempVO.setStoreVO(detailTemp.get(i).getStoreVO());
			List.add(tempVO);
		}
		
		model.addObject("pagination", pagination);

		model.addObject("List", List);
		model.addObject("pointHistory", "clicked");
		model.setViewName("thymeleaf/main");
		return model;
	}
}