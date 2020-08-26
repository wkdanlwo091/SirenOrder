package com.example.sirenorder.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.mapper.OrdersJoinOrders_detailMapper;
import com.example.sirenorder.vo.OrdersJoinOrders_detailVO;

@Repository("ordersjoinorders_detaildao")
public class OrdersJoinOrders_detailDao implements Dao<String, OrdersJoinOrders_detailVO>{
	@Autowired
	OrdersJoinOrders_detailMapper ordersJoinOrders_detailMapper;

	@Override
	public OrdersJoinOrders_detailVO select(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<OrdersJoinOrders_detailVO> selectall() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<OrdersJoinOrders_detailVO> selectOrdersJoinOrders_detailByOrders_id(String store_name){
		return ordersJoinOrders_detailMapper.selectOrdersJoinOrders_detailByOrders_id(store_name);
	}
}
