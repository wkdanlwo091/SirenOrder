package com.example.sirenorder.biz;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.vo.OrdersJoinOrders_detailVO;
import com.example.sirenorder.vo.OrdersVO;
import com.example.sirenorder.vo.Orders_detailVO;
import com.example.sirenorder.vo.Point_storeVO;

@Service("ordersjoinorders_detailbiz")
public class OrdersJoinOrders_detailBiz implements Biz<String, OrdersJoinOrders_detailVO>{

	
	@Resource(name = "ordersjoinorders_detaildao")
	Dao<String, OrdersJoinOrders_detailVO> dao;

	@Override
	public OrdersJoinOrders_detailVO get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<OrdersJoinOrders_detailVO> get() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<OrdersJoinOrders_detailVO> getOrdersJoinOrders_detailByOrders_id(){
		return dao.selectOrdersJoinOrders_detailByOrders_id();
	}

}
