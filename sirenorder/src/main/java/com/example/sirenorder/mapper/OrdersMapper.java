package com.example.sirenorder.mapper;

import java.sql.Date;
import java.util.ArrayList;

import com.example.sirenorder.vo.OrdersVO;
public interface OrdersMapper {
	public OrdersVO select(String users_id);
	public void insert(OrdersVO ordersVO);
	public ArrayList<OrdersVO> selectByDateFromTo(String users_id, Date firstDate, Date secondDate);
}