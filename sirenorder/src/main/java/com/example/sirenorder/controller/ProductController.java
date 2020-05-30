package com.example.sirenorder.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
			System.out.println("가게에 물건이 없습니다.");
			model.addObject("List", List);
		}
		else {
			System.out.println("가게에 물건이 있습니다.");
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
		System.out.println("chain name 은 "+ chain_name + "number 은" + number);
		ArrayList<ProductVO> arrList = productbiz.getProduct(chain_name, number);//number가 1이면 1번부터 6번까지의 데이터가져오기
		if(arrList.size() == 0) {
			System.out.println("가게에 물건이 없습니다.");
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
		HttpSession httpSession = request.getSession();
		System.out.println(product_name + " " + price);
		if(httpSession.getAttribute("cartTotalNum") == null) { 
			int num = 1;
			
			CartVO cartVO = new CartVO();//작업 중인 것 
			cartVO.setPrice(Integer.parseInt(price));
			cartVO.setProduct_name(product_name);
			cartVO.setNumber(num);
			HashMap<CartVO, Integer> cartProduct = new HashMap<CartVO, Integer>();
			httpSession.setAttribute("cartProduct", cartProduct);
			
			
 			HashMap<String, Integer> cartProductName = new HashMap<String, Integer>();
			HashMap<String, Integer> cartPrice = new HashMap<String, Integer>();
			cartProductName.put(product_name, num);
			cartPrice.put(product_name, Integer.parseInt(price));
			httpSession.setAttribute("cartTotalNum", num);
			httpSession.setAttribute("cartPrice", cartPrice);
			httpSession.setAttribute("cartProductName", cartProductName);
			System.out.println("first hashmap 들어왔다.");
			Iterator<String> itr = cartProductName.keySet().iterator();
			while (itr.hasNext()) {
				System.out.println(itr.next());
			}
			
			
			
		}else {
			
			
			int num = (int)httpSession.getAttribute("cartTotalNum");
			HashMap<String, Integer> cartProductName = (HashMap<String, Integer>)httpSession.getAttribute("cartProductName");
			HashMap<String, Integer> cartPrice = (HashMap<String, Integer>)httpSession.getAttribute("cartPrice");
			// 1. using Iterator
			Iterator<String> itr = cartProductName.keySet().iterator();
			while (itr.hasNext()) {
				System.out.println(itr.next());
			}
			System.out.println(product_name +" 의 개수는 " + cartProductName.get(product_name));
			cartProductName.put(product_name ,cartProductName.get(product_name)+1);
			if(cartPrice.get(product_name) == null) {
				cartPrice.put(product_name , Integer.parseInt(price));
			}
			httpSession.setAttribute("cartProductName", cartProductName);//상품 갯수 한개 증가 
			httpSession.setAttribute("cartTotalNum", num + 1);//총 카트 개수 1증가 

			
			
			
			
			
			CartVO cartVO = new CartVO();//작업 중인 것 
			cartVO.setPrice(Integer.parseInt(price));
			cartVO.setProduct_name(product_name);
			cartVO.setNumber(1);//일단은 1로 잡는다. 나중에 db 설정할 때는 최신화 한다. 
			HashMap<CartVO, Integer> cartProduct = (HashMap<CartVO, Integer>)httpSession.getAttribute("cartProduct");
			if(cartProduct.get(cartVO) == null) {//새로운 아이템이 들어온 장바구니 
				cartProduct.put(cartVO, 1);//수량 한개 늘리기 
			}else {//이미 있던 장바구니 아이템
				cartProduct.put(cartVO, cartProduct.get(cartVO)+1);//수량 한개 늘리기 
			}
			
			
		}
		System.out.println("addTocart fhinised");
		return "cartReturned";
	}
}