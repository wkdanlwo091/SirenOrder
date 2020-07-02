package com.example.sirenorder.mapper;

import java.util.ArrayList;

import com.example.common.Pagination;
import com.example.sirenorder.vo.Orders_detailJoinProductVO;
import com.example.sirenorder.vo.PaginationOwner;

public interface Orders_detailJoinProductMapper {
	public ArrayList<Orders_detailJoinProductVO> selectOrdersStatus(Pagination pagination);
	public ArrayList<Orders_detailJoinProductVO> selectOrders_detailJoinProductByStore_name(PaginationOwner pagination);
	public int selectOrders_detailCnt();
	
}
