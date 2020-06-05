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
	public ArrayList<StoreVO> get() {
		// TODO Auto-generated method stub
		return dao.selectall();
	}
	@Override
	public ArrayList<StoreVO> getChain(String name) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectChain(name);
	}

}
