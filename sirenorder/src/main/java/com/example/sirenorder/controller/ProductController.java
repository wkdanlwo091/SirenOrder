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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.CartVO;
import com.example.sirenorder.vo.ProductVO;

@Controller
public class ProductController {
	@Resource(name = "productbiz")	
	Biz<String, ProductVO> productbiz;

	@RequestMapping("/product.html")// 내 정보 보기 
	public ModelAndView showProduct(HttpServletRequest request) throws Exception {
		
		HttpSession httpSession = request.getSession();
		if(httpSession.getAttribute("userId") == null) {//아이디 로그인 안 했을 시 로그인 해라로 간다. 
			ModelAndView model = new ModelAndView();
			model.setViewName("redirect:/index.html");
			return model;
		}
		
		String chain_name = request.getParameter("chain_name");
		String  numberString = request.getParameter("number");
		int number = Integer.parseInt(numberString);
		System.out.println("chain name 은 "+ chain_name + "number 은" + number);
		List<ProductVO> List = productbiz.getProduct(chain_name, number);//number가 1이면 1번부터 6번까지의 데이터가져오기
		
		ModelAndView model = new ModelAndView();
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
	
	//가게의 판매 물건을 가져오는 컨트롤러이다. 
	@RequestMapping(value = "getProduct", method = RequestMethod.POST)
	@ResponseBody
	public Object getProduct(HttpServletRequest request) throws Exception {
		String chain_name = request.getParameter("chain_name");
		String  numberString = request.getParameter("number");
		int number = Integer.parseInt(numberString);
		//System.out.println("chain name 은 "+ chain_name + "number 은" + number);
		ArrayList<ProductVO> arrList = productbiz.getProduct(chain_name, number);//number가 1이면 1번부터 6번까지의 데이터가져오기
		if(arrList.size() == 0) {
			//System.out.println("가게에 물건이 없습니다.");
		}
		else {
			return arrList;
		}
		return "fail";//로그인 첫 페이지로 /index.html
	}

	//세션에 카트 정보를 저장하는 컨트롤러 
	@RequestMapping(value = "cartProduct", method = RequestMethod.POST)
	@ResponseBody
	public String carProduct(HttpServletRequest request) throws Exception {
		String product_name = request.getParameter("product_name");
		String price = request.getParameter("price");
		String sign = request.getParameter("sign");
		HttpSession httpSession = request.getSession();
		System.out.println(product_name + " " + price);
		HashMap<CartVO, Integer> cartProduct = new HashMap<CartVO, Integer>();

		if(httpSession.getAttribute("cartProduct") == null) { 
			if(sign.equals("minus")) {
				return "noData";
			}
			int num = 1;
			CartVO cartVO = new CartVO();//작업 중인 것 
			cartVO.setPrice(Integer.parseInt(price));
			cartVO.setProduct_name(product_name);
			cartVO.setNumber(num);
			cartProduct.put(cartVO, 1);
			httpSession.setAttribute("cartProduct", cartProduct);
			System.out.println("첫번 째 cart 생성하였다.");
		}else {
			CartVO cartVO = new CartVO();//작업 중인 것 
			cartVO.setPrice(Integer.parseInt(price));
			cartVO.setProduct_name(product_name);
			cartVO.setNumber(1);//일단은 1로 잡는다. 나중에 db 설정할 때는 최신화 한다. 
			cartProduct = (HashMap<CartVO, Integer>)httpSession.getAttribute("cartProduct");
			if(cartProduct.get(cartVO) == null) {//새로운 아이템이 들어온 장바구니 
				if(sign.equals("minus")) {//데이터가 없는데 - 이면 noData라고 return 
					return "noData";
				}
				cartProduct.put(cartVO, 1);//수량 한개 늘리기 
				System.out.println("새 카트다.");
			}else {//이미 있던 장바구니 아이템
				if(sign.equals("plus")) {//카트 수량 늘리기 
					System.out.println("카트의수량을 늘렸다. ");
					cartProduct.put(cartVO, cartProduct.get(cartVO)+1);//수량 한개 늘리기 
				}else {//카트 수량 줄이기 
					System.out.println("카트의 수량을 줄였다.");
					if(cartProduct.get(cartVO) == 1) {
						cartProduct.remove(cartVO);
					}else {
						cartProduct.put(cartVO, cartProduct.get(cartVO)-1);//수량 한개 늘리기 
					}
				}
			}
			httpSession.setAttribute("cartProduct", cartProduct);
		}
		
		System.out.println("addTocart finished");
		JSONArray ja = new JSONArray();
		Iterator<CartVO> itr = cartProduct.keySet().iterator();
		int index = 0;
		int totalPrice = 0;
		
		while (itr.hasNext()) {
			System.out.println("created" + index);
			CartVO tmp = itr.next();
			
			JSONObject jo = new JSONObject();
			jo.put("product_name", tmp.getProduct_name());
			jo.put("price", tmp.getPrice());
			jo.put("number", cartProduct.get(tmp));//물건 개수
			ja.add(jo);
			totalPrice += tmp.getPrice()* (cartProduct.get(tmp));
		}
		
		httpSession.setAttribute("totalPrice", totalPrice);
		System.out.println("ja size" + ja.size());
		//여기서 json으로 모든 장바구니 정보를 json으로 받는다. 

		return ja.toString();
	}
}