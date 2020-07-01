package com.example.sirenorder.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.common.Pagination;
import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.CartVO;
import com.example.sirenorder.vo.ProductVO;
import com.example.sirenorder.vo.Store_productJoinProductVO;
import com.example.sirenorder.vo.Store_productVO;
@Controller
public class ProductController {
	@Resource(name = "productbiz")	
	Biz<String, ProductVO> productbiz;
	@Resource(name = "store_productbiz")	
	Biz<String, Store_productVO> store_productbiz;
	
	@RequestMapping("/product.html")// 체인의 상점의 물품들을 ajax로 띄운다.   . pagination 구현
	public ModelAndView showProduct(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int page , 
			@RequestParam(required = false, defaultValue = "1") int range ) throws Exception {
		ModelAndView model = new ModelAndView();
		HttpSession httpSession = request.getSession();
		if(httpSession.getAttribute("userId") == null) {//아이디 로그인 안 했을 시 로그인 해라로 간다. 
			model.setViewName("redirect:/index.html");
			return model;
		}

		String chain_name = request.getParameter("chain_name");
		String store_name = request.getParameter("store_name");
		int listCnt;
		int startList;
		int listSize;
		//스토어에 해당하는 상품을 가져와야 한다. 
		//banapresso 신촌과 홍대의 판매 item이 다를 수 있다. 
		//따라서 다대다 관계를 위해 store_product를 만들었다.  
		listCnt = store_productbiz.getListCnt(store_name);
		//listCnt = productbiz.getListCnt(chain_name);//상품 갯수 가져오기
		Pagination pagination = new Pagination();
		pagination.pageInfo(page, range, listCnt);
		startList = pagination.getStartList();
		listSize =  pagination.getListSize();
		
		List<Store_productJoinProductVO> List = store_productbiz.getProductListJoin(store_name, startList, listSize);
		model.addObject("pagination", pagination);
		model.addObject("chain_name", chain_name);
		model.addObject("store_name", store_name);

		model.addObject("product", "clicked");
		model.setViewName("thymeleaf/main");
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
	//가게의 판매 물건을 가져오는 컨트롤러이다
	@RequestMapping(value = "getProduct", method = RequestMethod.POST)
	@ResponseBody
	public Object getProduct(HttpServletRequest request) throws Exception {
		String chain_name = request.getParameter("chain_name");
		String  numberString = request.getParameter("number");
		int number = Integer.parseInt(numberString);
		
		ArrayList<ProductVO> arrList = productbiz.getProduct(chain_name, number);//number가 1이면 1번부터 6번까지의 데이터가져오기
		if(arrList.size() == 0) {
			//System.out.println("가게에 물건이 없습니다.");
		}
		else {
			return arrList;
		}
		return "fail";//로그인 첫 페이지로 /index.html
	}
	public CartVO makeCart(String product_name,String store_name, String chain_name, String price) {
		int num = 1;
		CartVO cartVO = new CartVO();//작업 중인 것 
		cartVO.setPrice(Integer.parseInt(price));
		cartVO.setProduct_name(product_name);
		cartVO.setChain_name(chain_name);
		cartVO.setNumber(num);
		cartVO.setStore_name(store_name);
		return cartVO;
	}
	public JSONObject putJSONObject(CartVO tmp, HashMap<CartVO, Integer> cartProduct) {
		JSONObject jo = new JSONObject();
		jo.put("product_name", tmp.getProduct_name());
		jo.put("store_name", tmp.getStore_name());
		jo.put("chain_name", tmp.getChain_name());
		jo.put("price", tmp.getPrice());
		jo.put("number", cartProduct.get(tmp));//물건 개수
		return jo;
	}
	
	//세션에 카트 정보를 저장하는 컨트롤러  plus minus 둘다 담당 --> 이 둘 나눠야지
	@RequestMapping(value = "cartProductAdd", method = RequestMethod.POST)
	@ResponseBody
	public String carProductAdd(HttpServletRequest request) throws Exception {
		String product_name = request.getParameter("product_name");
		String store_name = request.getParameter("store_name");
		String chain_name = request.getParameter("chain_name");
		String price = request.getParameter("price");
		String sign = request.getParameter("sign");
		HttpSession httpSession = request.getSession();
		System.out.println(product_name + " and price  " + price + " " + store_name + " " + chain_name + " " + price);
		HashMap<CartVO, Integer> cartProduct = new HashMap<CartVO, Integer>();

		if(httpSession.getAttribute("cartProduct") == null) { 
			CartVO cartVO = makeCart(product_name, store_name, chain_name, price);
			cartProduct.put(cartVO, 1);
			httpSession.setAttribute("cartProduct", cartProduct);
		}else {
			CartVO cartVO = makeCart(product_name, store_name, chain_name, price);
			cartProduct = (HashMap<CartVO, Integer>)httpSession.getAttribute("cartProduct");
			if(cartProduct.get(cartVO) == null) {//새로운 아이템이 들어온 장바구니 
				cartProduct.put(cartVO, 1);//수량 한개 늘리기 
			}else {//이미 있던 장바구니 아이템
				cartProduct.put(cartVO, cartProduct.get(cartVO)+1);//수량 한개 늘리기 
			}
			httpSession.setAttribute("cartProduct", cartProduct);
		}
		JSONArray ja = new JSONArray();
		Iterator<CartVO> itr = cartProduct.keySet().iterator();
		int index = 0;
		int totalPrice = 0;
		int totalIndex = 0;
		while (itr.hasNext()) {
			System.out.println("created" + index++);
			CartVO tmp = itr.next();
			JSONObject jo = new JSONObject();
			jo = putJSONObject(tmp, cartProduct);
			ja.add(jo);
			totalIndex += (cartProduct.get(tmp));
			totalPrice += tmp.getPrice()* (cartProduct.get(tmp));
		}
		httpSession.setAttribute("totalPrice", totalPrice);
		httpSession.setAttribute("totalIndex", totalIndex);
		if(index == 0) {
			System.out.println("noData");
			return "noData";
		}
		System.out.println("ja size" + ja.size());//여기서 json으로 모든 장바구니 정보를 json으로 받는다. 

		return ja.toString();
	}

	@RequestMapping(value = "cartProductMinus", method = RequestMethod.POST)
	@ResponseBody
	public String carProductMinus(HttpServletRequest request) throws Exception {
		String product_name = request.getParameter("product_name");
		String store_name = request.getParameter("store_name");
		String chain_name = request.getParameter("chain_name");
		String price = request.getParameter("price");
		String sign = request.getParameter("sign");
		HttpSession httpSession = request.getSession();
		System.out.println(product_name + " " + price + " " + chain_name);
		HashMap<CartVO, Integer> cartProduct = new HashMap<CartVO, Integer>();

		if(httpSession.getAttribute("cartProduct") == null) { 
			System.out.println("null cartProduct");
			if(sign.equals("minus")) {
				return "noData";
			}
		}else {
			CartVO cartVO = new CartVO();//작업 중인 것 
			cartVO = makeCart(product_name, store_name, chain_name, price);
			cartProduct = (HashMap<CartVO, Integer>)httpSession.getAttribute("cartProduct");
			System.out.println(cartProduct.size() + " haha");
			if(cartProduct.get(cartVO) == 1) {//새로운 아이템이 들어온 장바구니 
				cartProduct.remove(cartVO);
				System.out.println("카트 파괴");
			}else {//이미 있던 장바구니 아이템
				cartProduct.put(cartVO, cartProduct.get(cartVO)-1);//수량 한개 줄이기
			}
			httpSession.setAttribute("cartProduct", cartProduct);
		}
		
		
		JSONArray ja = new JSONArray();
		Iterator<CartVO> itr = cartProduct.keySet().iterator();
		int index = 0;
		int totalPrice = 0;
		int totalIndex = 0;
		while (itr.hasNext()) {
			System.out.println("created" + index++);
			CartVO tmp = itr.next();
			JSONObject jo = new JSONObject();
			jo = putJSONObject(tmp, cartProduct);
			ja.add(jo);
			totalIndex += (cartProduct.get(tmp));
			totalPrice += tmp.getPrice()* (cartProduct.get(tmp));
		}
		httpSession.setAttribute("totalPrice", totalPrice);
		httpSession.setAttribute("totalIndex", totalIndex);

		if(index == 0) {
			System.out.println("noData");
			return "noData";
		}
		System.out.println("ja size" + ja.size());//여기서 json으로 모든 장바구니 정보를 json으로 받는다. 

		return ja.toString();
	}
	
	@RequestMapping(value = "cartProductBring", method = RequestMethod.POST)
	@ResponseBody
	public String carProductBring(HttpServletRequest request) throws Exception {
		HttpSession httpSession = request.getSession();

		if(httpSession.getAttribute("cartProduct") == null) { 
			return "noCart";
		}
		HashMap<CartVO, Integer> cartProduct = (HashMap<CartVO, Integer>) httpSession.getAttribute("cartProduct");
		JSONArray ja = new JSONArray();
		Iterator<CartVO> itr = cartProduct.keySet().iterator();
		int index = 0;
		int totalPrice = 0;
		int totalIndex = 0;
		while (itr.hasNext()) {
			System.out.println("created" + index++);
			CartVO tmp = itr.next();
			
			JSONObject jo = new JSONObject();
			jo.put("product_name", tmp.getProduct_name());
			jo.put("store_name", tmp.getStore_name());
			jo.put("chain_name", tmp.getChain_name());
			jo.put("price", tmp.getPrice());
			jo.put("number", cartProduct.get(tmp));//물건 개수
			ja.add(jo);
			totalIndex += (cartProduct.get(tmp));
			totalPrice += tmp.getPrice()* (cartProduct.get(tmp));
		}
		httpSession.setAttribute("totalPrice", totalPrice);
		httpSession.setAttribute("totalIndex", totalIndex);

		if(index == 0) {
			System.out.println("noData");
			return "noData";
		}
		System.out.println("ja size" + ja.size());
		//여기서 json으로 모든 장바구니 정보를 json으로 받는다. 

		return ja.toString();
	}

	
}