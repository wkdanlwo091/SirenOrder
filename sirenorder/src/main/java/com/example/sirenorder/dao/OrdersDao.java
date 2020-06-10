package com.example.sirenorder.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.mapper.OrdersMapper;
import com.example.sirenorder.vo.OrdersVO;

@Repository("ordersdao")
public class OrdersDao  implements Dao<String, OrdersVO>{
	@Autowired
	OrdersMapper ordersMapper;

	@Override
	public OrdersVO select(String users_id) {
		return ordersMapper.select(users_id);
	}

	@Override
	public ArrayList<OrdersVO> selectall() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void insert(OrdersVO ordersVO) {
		// TODO Auto-generated method stub
		ordersMapper.insert(ordersVO);;
	}
	

}
