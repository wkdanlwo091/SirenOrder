package com.example.sirenorder.biz;

import java.util.ArrayList;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.vo.Orders_detailJoinProductVO;
import com.example.sirenorder.vo.Orders_detailVO;

@Service("orders_detailbiz")
public class Orders_detailBiz implements Biz<String, Orders_detailVO>{
	@Resource(name = "orders_detaildao")
	Dao<String, Orders_detailVO> dao;
	@Override
	public Orders_detailVO get(String id) {
		return dao.select(id);
	}
	@Override
	public ArrayList<Orders_detailVO> get() {
		return null;
	}
	@Override
	public void register(Orders_detailVO orders_detailVO) throws Exception {
		dao.insert(orders_detailVO);
	}
	@Override
	public int getOrders_seq() throws Exception {
		return dao.selectOrders_seq();
	}
	@Override
	public int getOrders_detailCnt(){
		return  dao.selectOrders_detailCnt();
	}	
	@Override
	public ArrayList<Orders_detailVO> getOrders_detailByOrdersId(String orders_id) {
		return dao.selectOrders_detailByOrdersId(orders_id);
	}
}
