package com.example.sirenorder.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.mapper.StoreMapper;
import com.example.sirenorder.vo.StoreVO;

@Repository("storedao")
public class StoreDao implements Dao<String,StoreVO>{
	
	@Autowired
	StoreMapper storemapper;

	@Override
	public StoreVO select(String name) {
		return storemapper.select(name);
	}
	
	@Override
	public void updateAllPoint_rate(StoreVO storeVO) {
		storemapper.updateAllPoint_rate(storeVO);
	}

	@Override
	public void insert(StoreVO storeVO) {
		// TODO Auto-generated method stub
		storemapper.insert(storeVO);
	}

	@Override
	public ArrayList<StoreVO> selectall() {
		return storemapper.selectall();
	}
	@Override
	public String selectStore_id(String store_name) {
		return storemapper.selectStore_id(store_name);
	}

	
	@Override
	public ArrayList<StoreVO> selectChain(String name) {
		return storemapper.selectchain(name);
	}
}
