package com.example.sirenorder.biz;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.common.Pagination;
import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.vo.Orders_detailJoinProductVO;
import com.example.sirenorder.vo.PaginationOwner;
@Service("orders_detailjoinproductbiz")
public class Orders_detailJoinProductBiz implements Biz<String, Orders_detailJoinProductVO>{
	@Resource(name = "orders_detailjoinproductdao")
	Dao<String, Orders_detailJoinProductVO> dao;

	@Override
	public ArrayList<Orders_detailJoinProductVO> getOrdersStatus(Pagination pagination){
		return dao.selectOrdersStatus(pagination);
	}
	
	@Override//owner 전용 
	public ArrayList<Orders_detailJoinProductVO> getOrders_detailJoinProductByStore_name(PaginationOwner pagination)
	{
		return dao.selectOrders_detailJoinProductByStore_name(pagination);
	}

	@Override
	public int getOrders_detailCnt(){
		return dao.selectOrders_detailCnt();
	}
	@Override
	public Orders_detailJoinProductVO get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Orders_detailJoinProductVO> get() {
		// TODO Auto-generated method stub
		return null;
	}
}