package com.example.sirenorder.mapper;

import java.util.ArrayList;

import com.example.common.Pagination;
import com.example.sirenorder.vo.Orders_detailJoinProductVO;

public interface Orders_detailJoinProductMapper {
	public ArrayList<Orders_detailJoinProductVO> selectOrdersStatus(Pagination pagination);
	public int selectOrders_detailCnt();
}
