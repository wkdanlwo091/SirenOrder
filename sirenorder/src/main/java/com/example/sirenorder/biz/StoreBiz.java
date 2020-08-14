package com.example.sirenorder.biz;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.vo.StoreVO;

@Service("storebiz")
public class StoreBiz implements Biz<String, StoreVO> {
	
	@Resource(name = "storedao")
	Dao<String, StoreVO> dao;
	
	@Override
	public StoreVO get(String name) {
		// TODO Auto-generated method stub
		return dao.select(name);
	}
	
	@Override
	public void changeLatLong(StoreVO storeVO) { 
		dao.changeLatLong(storeVO);
	}


	@Override
	public void register(StoreVO storeVO) throws Exception {
		// TODO Auto-generated method stub
		dao.insert(storeVO);
	}
	@Override
	public ArrayList<StoreVO> get() {
		// TODO Auto-generated method stub
		return dao.selectall();
	}
	@Override
	public String getStore_id(String store_name) {
		return dao.selectStore_id(store_name);
	}
	@Override
	public ArrayList<StoreVO> getChain(String name) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectChain(name);
	}
	
	@Override
	public void updateAllPoint_rate(StoreVO storeVO) {
		dao.updateAllPoint_rate(storeVO);
	}

}
