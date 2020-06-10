package com.example.sirenorder.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.mapper.Orders_detailMapper;
import com.example.sirenorder.vo.OrdersVO;
import com.example.sirenorder.vo.Orders_detailVO;

@Repository("orders_detaildao")
public class Orders_detailDao  implements Dao<String, Orders_detailVO>{
	@Autowired
	Orders_detailMapper orders_detailMapper;

	@Override
	public Orders_detailVO select(String users_id) {
		return orders_detailMapper.select(users_id);
	}

	@Override
	public ArrayList<Orders_detailVO> selectall() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void insert(Orders_detailVO orders_detailVO) {
		// TODO Auto-generated method stub
		orders_detailMapper.insert(orders_detailVO);
	}
	

}
