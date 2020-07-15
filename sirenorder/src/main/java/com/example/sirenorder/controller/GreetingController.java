package com.example.sirenorder.controller;

import java.util.Scanner;

import javax.annotation.Resource;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.Greeting;
import com.example.sirenorder.vo.Orders_detailIdMessage;
import com.example.sirenorder.vo.Orders_detailVO;
import com.example.sirenorder.vo.StoreVO;

@Controller
public class GreetingController {
	@Resource(name = "orders_detailbiz")
	Biz<String, Orders_detailVO> orders_detailbiz;

	@MessageMapping("/orders_detailId") // 자바스크립트 front에서 /app/hello로 송신하면 여기로 들어온다.
	@SendTo("/topic/greetings") //
	public String greeting(Orders_detailIdMessage message) throws Exception {
		Thread.sleep(1000); // simulated delay
		System.out.println(message.getOrders_detail_id().get(0));
		Orders_detailVO orders_detailVO = new Orders_detailVO();
		for(int i = 0 ;i < message.getOrders_detail_id().size();i++) {
			orders_detailVO.setOrders_detail_id(message.getOrders_detail_id().get(i));
			orders_detailbiz.update(orders_detailVO);
		}
		
		//fcm  날려 
		Scanner scan = new Scanner(System.in);
		scan.next();
		//return 할 때 주문번호 몇번 전달하였습니다.
		String temp = "";
		for(int i = 0 ;i < message.getOrders_detail_id().size(); i++) {
			temp += message.getOrders_detail_id().get(i) + " 주문 완료하였습니다. ";
		}
		
		return temp;
	}
}