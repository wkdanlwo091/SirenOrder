package com.example.sirenorder.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.example.common.Pagination;
import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.Orders_detailJoinProductVO;
import com.example.sirenorder.vo.Orders_detailVO;
import com.example.sirenorder.vo.Orders_detail_idList;
import com.example.sirenorder.vo.PaginationOwner;
import com.example.sirenorder.vo.PointVO;
import com.example.sirenorder.vo.ProductNames;
import com.example.sirenorder.vo.ProductVO;
import com.example.sirenorder.vo.StoreVO;
import com.example.sirenorder.vo.Store_nameAndDate;
import com.example.sirenorder.vo.Store_productVO;
import com.example.sirenorder.vo.SumAndOrders_date;
import com.example.sirenorder.vo.UserVO;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

//사업자 웹페이지 담당	
@Controller
public class OwnerController {
	@Resource(name = "userbiz")
	Biz<String, UserVO> userbiz;

	@Resource(name = "orders_detailbiz")
	Biz<String, Orders_detailVO> orders_detailbiz;

	@Resource(name = "productbiz")
	Biz<String, ProductVO> productbiz;

	@Resource(name = "orders_detailjoinproductbiz")
	Biz<String, Orders_detailJoinProductVO> orders_detailjoinproductbiz;

	@Resource(name = "storebiz")
	Biz<String, StoreVO> storebiz;

	@Resource(name = "store_productbiz")
	Biz<String, Store_productVO> store_productbiz;
	
	@Resource(name = "pointbiz")
	Biz<String, PointVO> pointbiz;


	@RequestMapping(value = "/makeConnectSession", method = RequestMethod.GET) //  connect 세션 만들어 달라고 요청
	@ResponseBody
	public void makeConnectSession(HttpServletRequest request, WebRequest request2 ) throws Exception {
		HttpSession httpSession = request.getSession();
		String connect = request.getParameter("data1");//connect
		if(connect.equals("connect")) {
			if( httpSession.getAttribute("connect") == null) {
				System.out.println("connected");
				httpSession.setAttribute(connect, "connect");
			}else {
			}
		}else if(connect.equals("disconnect")) {
			httpSession.removeAttribute("connect");
		    //request2.removeAttribute("connect", WebRequest.SCOPE_SESSION);
		}
	}

	@RequestMapping(value = "/ownermain.html", method = RequestMethod.GET) // 처음 들어 왔을 때
	public ModelAndView ownermain(HttpServletRequest request) throws Exception {
		HttpSession httpSession = request.getSession();
		ModelAndView model = new ModelAndView();
		String users_id = (String) httpSession.getAttribute("userId");
		if (users_id == null) {
			model.setViewName("redirect:/index.html");
			return model;// 로그인 첫 페이지로 /index.html
		}
		if (httpSession.getAttribute("owner").equals("owner")) {
			httpSession.setAttribute("store_name", httpSession.getAttribute("store_name"));
		}else if(httpSession.getAttribute("owner").equals("owner_first")) { 
			httpSession.setAttribute("store_name", httpSession.getAttribute("store_name"));
			httpSession.setAttribute("chain_name", ((String) httpSession.getAttribute("store_name")).split("_")[0]);
			httpSession.setAttribute("owner_first", "chain대표");
			//chain 대표면 
			
		}
		else {
			model.setViewName("redirect:/main.html");
			return model;
		}
		model.setViewName("thymeleaf/ownermain");
		return model;
	}

	@RequestMapping(value = "/addItemAndDelete.html", method = RequestMethod.GET) // 처음 들어 왔을 때 상점이 가지고 있는 아이템 list를																				// return한다.
	public ModelAndView addItem(HttpServletRequest request) throws Exception {
		HttpSession httpSession = request.getSession();
		ModelAndView model = new ModelAndView();
		String users_id = (String) httpSession.getAttribute("userId");
		System.out.println("get 왔다 .");
		
		if (users_id == null) {
			model.setViewName("redirect:/index.html");
			return model;// 로그인 첫 페이지로 /index.html
		}
		
		UserVO userVO = userbiz.get(users_id);
		System.out.println("get의 userVO는" + userVO);
		if (userVO.getRole().contains("owner")) {//owner나 owner_first 이기 때문에 
		} 
		else {
			model.setViewName("redirect:/main.html");
			return model;
		}
		
		// 스토어 이름 기반으로 상품 리스트를 가져왔다.
		String store_name = (String) httpSession.getAttribute("store_name");
		ArrayList<Store_productVO> list = store_productbiz.getByStore_name((String) httpSession.getAttribute("store_name"));
		System.out.println("list 사이즈 " + list.size());
		System.out.println(list.get(0));
		model.addObject("product_name", new ProductNames());
		model.addObject("product", list);
		PointVO pointVO = pointbiz.getByChain_name(store_name.split("_")[0]);//apple_sinchon의 apple을 가져와서 검색
		model.addObject("point_rate", pointVO.getPoint_rate());
		model.addObject("addItemAndDelete", "clicked");
		model.setViewName("thymeleaf/ownermain");
		return model;
	}
	
	@RequestMapping(value = "/addItemAndDelete.html", method = RequestMethod.POST) // 상점에 아이템을 추가한다. 																				// return한다.
	public ModelAndView addItemPost(HttpServletRequest request, ProductVO productVO) throws Exception {
		HttpSession httpSession = request.getSession();
		ModelAndView model = new ModelAndView();
		String users_id = (String) httpSession.getAttribute("userId");
		if (users_id == null) {
			model.setViewName("redirect:/index.html");
			return model;// 로그인 첫 페이지로 /index.html
		}
		if (userbiz.get(users_id).getRole().equals("owner")) {
		} else {
			model.setViewName("redirect:/main.html");
			return model;
		}
		//만들 아이템을 넣는다. 
		System.out.println(productVO);

		// 스토어 이름 기반으로 상품 리스트를 가져왔다.
		ArrayList<Store_productVO> list = store_productbiz
				.getByStore_name((String) httpSession.getAttribute("store_name"));
		System.out.println(list.size());
		System.out.println(list.get(0));
		model.addObject("product_name", new ProductNames());
		model.addObject("product", list);
		model.addObject("addItemAndDelete", "clicked");
		model.setViewName("thymeleaf/ownermain");
		return model;
	}
	
	@RequestMapping(value = "pointUpdate", method = RequestMethod.POST) // 상점에 아이템을 추가한다. 																				// return한다.
	public ModelAndView pointUpdate(HttpServletRequest request, ProductVO productVO) throws Exception {
		HttpSession httpSession = request.getSession();
		ModelAndView model = new ModelAndView();
		String users_id = (String) httpSession.getAttribute("userId");
		if (users_id == null) {
			model.setViewName("redirect:/index.html");
			return model;// 로그인 첫 페이지로 /index.html
		}
		if (httpSession.getAttribute("owner") != null) {// owner로 접속했을 때 생기는 session
		} else {
			model.setViewName("redirect:/main.html");
			return model;
		}
		
		pointbiz.
		return model;
	}

	@RequestMapping(value = "/ownerOrderStatus.html", method = RequestMethod.GET) //
	public ModelAndView ownerOrderStatus(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "1") int range,
			@RequestParam(required = false, defaultValue = "nothing") String orders_detail_id) throws Exception {
		ModelAndView model = new ModelAndView();
		HttpSession httpSession = request.getSession();
		String store_name = (String) httpSession.getAttribute("store_name");
		if (httpSession.getAttribute("userId") == null) {// 아이디 로그인 안 했을 시 로그인 해라로 간다.
			model.setViewName("redirect:/index.html");
			return model;
		}
		
		
		int listCnt;
		int startList;
		int listSize;
		listCnt = orders_detailbiz.getOrders_detailCntByStore_name(store_name);
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, range, listCnt);
		startList = pagination.getStartList();
		listSize = pagination.getListSize();

		System.out.println("page cnt는 " + pagination.getPageCnt() + " page end는 " +  pagination.getEndPage());
		
		PaginationOwner paginationOwner = new PaginationOwner();
		paginationOwner.setStartList(startList);
		paginationOwner.setStore_name(store_name);
		List<Orders_detailJoinProductVO> List = orders_detailjoinproductbiz
				.getOrders_detailJoinProductByStore_name(paginationOwner);
		model.addObject("pagination", pagination);
		model.addObject("store_name", store_name);

		model.addObject("ownerOrderStatus", "clicked");
		model.setViewName("thymeleaf/ownermain");
		model.addObject("store_name", store_name);// 체인점 중 가게를 구분하기 위한 변수
		if (List.size() == 0) {
			// System.out.println("가게에 물건이 없습니다.");
			model.addObject("List", List);
		} else {
			// System.out.println("가게에 물건이 있습니다.");
			model.addObject("List", List);
		}
		return model;
	}
	
	//파이어베이스로 안드로이드 기기에 notification 보내기 
	public void firebaseSend(Map<String, String> orders_id) throws IOException, FirebaseMessagingException {
		//fcm 설정 관련 부분 fcm json파일의 절대 경로  ---> 상대경로로 처리해야한다. 
		System.out.println(new File("").getAbsolutePath());
		FileInputStream refreshToken  = new FileInputStream("src\\main\\resources\\fcm\\sirenorderclient-firebase-adminsdk-dpxxu-0ee3d63b4f.json");//구글 파이어베이스에서 받아온 json 데이터의 위치 
		//FileInputStream refreshToken = new FileInputStream("..\\resources\\fcm\\sirenorderclient-firebase-adminsdk-dpxxu-0ee3d63b4f.json");//구글 파이어베이스에서 받아온 json 데이터의 위치 
		 FirebaseOptions options = new FirebaseOptions.Builder()
				 .setCredentials(GoogleCredentials.fromStream(refreshToken))
				 .setDatabaseUrl("https://sirenorderclient.firebaseio.com")
				 .build();
				 //Firebase 처음 호출시에만 initializing 처리
		if(FirebaseApp.getApps().isEmpty()) {
				 FirebaseApp.initializeApp(options);
		}
		
		///ordesr_id 기준으로 users_id 조회 후 users_id의 token에 데이터 전달 
		for (Entry<String, String> entry : orders_id.entrySet()) {
			String myToken = userbiz.getToken(entry.getKey());// orders_id를 가지고 검색 --> orders와 users join 한다. 
			System.out.println(myToken);
			// See documentation on defining a message payload.
			///여기다가  주문 상품 번호를 넣고 싶어 
			Message message = Message.builder().setNotification(new Notification(entry.getValue() + " +주문번호 : "+ entry.getKey(), "주문한 상품 완료"))
					.setToken(myToken).build();

			// Send a message to the device corresponding to the provided
			// registration token.
			String response = FirebaseMessaging.getInstance().send(message);
			// Response is a message ID string.
			System.out.println("Successfully sent message: " + response);
			/// firebase token으로 데이터 전송
		}
	}
	
	//여기서 파이어베이스 전송함수를 호출한다. 
	@RequestMapping(value = "/ownerOrderStatus.html", method = RequestMethod.POST) //
	public ModelAndView ownerOrderFinsh(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "1") int range, 
			Orders_detail_idList orders_detail_idList//여기서 RequestParam 쓰면 안되더라 
	// 주문완료 되었다고 변경하는 정보를 담은 배열 클래스
	) throws Exception {
		ModelAndView model = new ModelAndView();
		HttpSession httpSession = request.getSession();
		String store_name = (String) httpSession.getAttribute("store_name");

		if (httpSession.getAttribute("userId") == null) {// 아이디 로그인 안 했을 시 로그인 해라로 간다.
			model.setViewName("redirect:/index.html");
			return model;
		}
		System.out.println("post came");
		System.out.println(orders_detail_idList);
		
		// orders_id 중복되는 것 제거하고 기준으로 orders_id의 user 조회 후
		Map<String, String> orders_id = new HashMap<String, String>();
		for (int i = 0; i < orders_detail_idList.getOrders_detail_id().length; i++) {
			if (orders_detail_idList.getOrders_detail_id()[i] != null) {
				Orders_detailVO temp = new Orders_detailVO();
				temp.setOrders_detail_id(orders_detail_idList.getOrders_detail_id()[i]);
				//orders_detailbiz.update(temp);// 완료되었다고 orders_detail 변경 잠시 실험을 위해서 주석 
				if (orders_id.get(orders_detail_idList.getOrders_id()[i]) == null) {
					orders_id.put(orders_detail_idList.getOrders_id()[i], orders_detail_idList.getStore_name()[i]);//hashmap 에 orders_id를 넣는다. 
				}
			}
		}
		
		
		
		//파이어베이스 메시지 보내기 
		firebaseSend(orders_id);

		int listCnt;
		int startList;
		int listSize;
		listCnt = orders_detailbiz.getOrders_detailCntByStore_name(store_name);
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, range, listCnt);
		startList = pagination.getStartList();
		listSize = pagination.getListSize();

		PaginationOwner paginationOwner = new PaginationOwner();
		paginationOwner.setStartList(startList);
		paginationOwner.setStore_name(store_name);
		List<Orders_detailJoinProductVO> List = orders_detailjoinproductbiz
				.getOrders_detailJoinProductByStore_name(paginationOwner);
		model.addObject("pagination", pagination);
		model.addObject("store_name", store_name);

		model.addObject("ownerOrderStatus", "clicked");
		model.setViewName("thymeleaf/ownermain");
		model.addObject("store_name", store_name);// 체인점 중 가게를 구분하기 위한 변수
		if (List.size() == 0) {
			// System.out.println("가게에 물건이 없습니다.");
			model.addObject("List", List);
		} else {
			// System.out.println("가게에 물건이 있습니다.");
			model.addObject("List", List);
		}
		return model;
	}

	// 매출액 보기
	@RequestMapping(value = "/incomeChart.html", method = RequestMethod.GET) //
	public ModelAndView ownerIncome(HttpServletRequest request,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date from,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date to
	// 주문완료 되었다고 변경하는 정보를 담은 배열 클래스
	) throws Exception {
		ModelAndView model = new ModelAndView();
		HttpSession httpSession = request.getSession();
		String store_name = (String) httpSession.getAttribute("store_name");
		if (httpSession.getAttribute("userId") == null) {// 아이디 로그인 안 했을 시 로그인 해라로 간다.
			model.setViewName("redirect:/index.html");
			return model;
		}
		model.addObject("incomeChart", "clicked");
		model.setViewName("thymeleaf/ownermain");
		return model;
	}

	// 연도 월을 지정한 form 을 받아서 돌려주는 함수
	@RequestMapping(value = "/incomeChart.html", method = RequestMethod.POST) //
	public ModelAndView ownerIncomePost(HttpServletRequest request,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date from,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date to,
			@RequestParam(required = false) String option
	// 주문완료 되었다고 변경하는 정보를 담은 배열 클래스
	) throws Exception {
		ModelAndView model = new ModelAndView();
		HttpSession httpSession = request.getSession();
		String store_name = (String) httpSession.getAttribute("store_name");
		if (httpSession.getAttribute("userId") == null) {// 아이디 로그인 안 했을 시 로그인 해라로 간다.
			model.setViewName("redirect:/index.html");
			return model;
		}
		
		
		LocalDate dateFrom = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dateTo = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	    LocalDate myObj = LocalDate.now();  // Create a date object

		System.out.println(from  + "  " + myObj);
		
		Store_nameAndDate temp = new Store_nameAndDate();
		temp.setStore_name(store_name);
		temp.setFrom(new java.sql.Date(from.getTime()));
		temp.setTo(new java.sql.Date(to.getTime()));
		JSONArray ja = new JSONArray();
		ArrayList<SumAndOrders_date> list = orders_detailbiz.getIncomeBystore_nameDayRange(temp);// 디비에서 데이터 가져오는 것
		if (option.equals("년")) {
			int currentYear = 0;
			int currentSum = 0;

			for (int j = 0; j < list.size(); j++) {
				if (j == 0) {// 첫번 째 년도
					currentYear = list.get(j).getOrders_date().getYear() + 1900;// date가 -1900한 것을 return 하므로 1900 더한다.
				}
				if (list.get(j).getOrders_date().getYear() + 1900 != currentYear) {// 년도 바뀔 때 전의 sum, year 더한다. 바뀐 연도로
																					// 교환, sum 초기화
					JSONObject jo = new JSONObject();
					jo.put("currentDate", currentYear);
					jo.put("currentSum", currentSum);
					ja.add(jo);
					currentYear = list.get(j).getOrders_date().getYear() + 1900;
					currentSum = 0;
				}

				currentSum += list.get(j).getSum();
				if (j == list.size() - 1) {// 마지막에 json object 더하기
					JSONObject jo = new JSONObject();

					jo.put("currentDate", currentYear);
					jo.put("currentSum", currentSum);
					ja.add(jo);
				}
			}
			model.addObject("selected", "year");
		} else if (option.equals("월")) {/// 2017 2018 2019 년 이면 3년치 데이터 다 가져와서 365개의 데이터 평균 내보네
			int currentMonth = 0;
			int currentSum = 0;

			for (int j = 0; j < list.size(); j++) {
				if (j == 0) {
					currentMonth = list.get(j).getOrders_date().getMonth() + 1;// 6월이면 5월을 return해서 1을 더해주었다.
				}
				if (list.get(j).getOrders_date().getMonth() + 1 != currentMonth) {// 월이 바뀔 때마다 month와 sum을 바꾼다.
					JSONObject jo = new JSONObject();
					jo.put("currentDate", currentMonth);
					jo.put("currentSum", currentSum);
					ja.add(jo);
					currentMonth = list.get(j).getOrders_date().getMonth() + 1;
					currentSum = 0;
				}
				currentSum += list.get(j).getSum();

				if (j == list.size() - 1) {// 마지막에 json object 더하기
					JSONObject jo = new JSONObject();
					jo.put("currentDate", currentMonth);
					jo.put("currentSum", currentSum);
					ja.add(jo);
				}
			}
			model.addObject("selected", "month");

			/// 1월 이면 31개의 평균 2월 30개의 평균 치를 데이터로 내보네
		} else if (option.equals("일")) {

			for (int j = 0; j < list.size(); j++) {
				JSONObject jo = new JSONObject();
				jo.put("currentDate", list.get(j).getOrders_date());
				jo.put("currentSum", list.get(j).getSum());
				ja.add(jo);
			}

			model.addObject("selected", "day");
		}
		
		model.addObject("from", dateFrom);//시작일
		model.addObject("to", dateTo);// 마침일을 incomeChart의 datepicker 입력창에 넣어준다. 
		
		
		model.addObject("ja", ja);// javascript에서 string으로 받는다.
		model.addObject("message", "exist");
		model.addObject("incomeChart", "clicked");
		model.setViewName("thymeleaf/ownermain");
		return model;
	}
}