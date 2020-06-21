package com.example.sirenorder.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.mapper.Orders_detailJoinProductMapper;
import com.example.sirenorder.vo.Orders_detailJoinProductVO;

@Repository("orders_detailjoinproductdao")
public class Orders_detailJoinProductDao implements Dao<String, Orders_detailJoinProductVO>{
	@Autowired
	Orders_detailJoinProductMapper orders_detailJoinProductMapper;

	@Override
	public ArrayList<Orders_detailJoinProductVO> selectOrdersStatus(int startList, int listSize){
		return orders_detailJoinProductMapper.selectOrdersStatus(startList, listSize);
	}

	@Override
	public int selectOrders_detailCnt(){
		return orders_detailJoinProductMapper.selectOrders_detailCnt();
	}

	@Override
	public Orders_detailJoinProductVO select(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Orders_detailJoinProductVO> selectall() {
		// TODO Auto-generated method stub
		return null;
	}

}
