package com.example.sirenorder.mapper;

import java.util.ArrayList;

import com.example.sirenorder.vo.Orders_detailJoinProductVO;
import com.example.sirenorder.vo.Orders_detailVO;

public interface Orders_detailMapper {
	public Orders_detailVO select(String orders_id);
	public void insert(Orders_detailVO orders_detailVO);
	public int selectOrders_seq();
	public int selectOrders_detailCnt();
	public ArrayList<Orders_detailVO> selectOrders_detailByOrdersId(String orders_id);
}