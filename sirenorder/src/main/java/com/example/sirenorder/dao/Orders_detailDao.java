package com.example.sirenorder.dao;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.mapper.Orders_detailMapper;
import com.example.sirenorder.vo.OrdersVO;
import com.example.sirenorder.vo.Orders_detailVO;
import com.example.sirenorder.vo.Store_nameAndDate;

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
	@Override
	public int selectOrders_seq() {
		return orders_detailMapper.selectOrders_seq();
	}
	@Override
	public int selectOrders_detailCnt( ){
		return orders_detailMapper.selectOrders_detailCnt();
	}
	@Override
	public ArrayList<Orders_detailVO> selectOrders_detailByOrdersId(String orders_id) {
		return orders_detailMapper.selectOrders_detailByOrdersId(orders_id);
	}
	@Override
	public ArrayList<Orders_detailVO> selectOrders_detailByStore_name(String store_name) {
		return orders_detailMapper.selectOrders_detailByStore_name(store_name);
	}
	@Override
	public int selectOrders_detailCntByStore_name(String store_name) { 
		return orders_detailMapper.selectOrders_detailCntByStore_name(store_name);
	}
	@Override
	public void update(Orders_detailVO m) { 
		orders_detailMapper.update(m);
	}
	@Override
	public ArrayList<Integer> selectIncomeBystore_nameDayRange(Store_nameAndDate a) { 
		return orders_detailMapper.selectIncomeBystore_nameDayRange(a);
	}
	@Override
	public ArrayList<Integer> selectIncomeByStore_nameDay(Store_nameAndDate a) { 
		return orders_detailMapper.selectIncomeBystore_nameDayRange(a);
	}

	
}
