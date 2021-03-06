package com.example.sirenorder.biz;

import java.sql.Date;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.vo.OrdersVO;

@Service("ordersbiz")
public class OrdersBiz implements Biz<String, OrdersVO>{
	
	@Resource(name = "ordersdao")
	Dao<String, OrdersVO> dao;
	@Override
	public OrdersVO get(String id) {
		return dao.select(id);
	}
	@Override
	public ArrayList<OrdersVO> get() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<OrdersVO> getByDateFromTo(String users_id, Date firstDate, Date secondDate) {
		return dao.selectByDateFromTo(users_id, firstDate, secondDate);
	}
	@Override
	public void register(OrdersVO ordersVO) throws Exception {
		dao.insert(ordersVO);
	}
}
