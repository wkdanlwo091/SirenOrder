package com.example.sirenorder.mapper;

import com.example.sirenorder.vo.OrdersVO;
public interface OrdersMapper {
	public OrdersVO select(String users_id);
	public void insert(OrdersVO ordersVO);
}