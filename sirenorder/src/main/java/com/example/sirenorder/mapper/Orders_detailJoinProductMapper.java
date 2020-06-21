package com.example.sirenorder.mapper;

import java.util.ArrayList;

import com.example.sirenorder.vo.Orders_detailJoinProductVO;

public interface Orders_detailJoinProductMapper {
	public ArrayList<Orders_detailJoinProductVO> selectOrdersStatus(int startList,int listSize);
	public int selectOrders_detailCnt();
}
