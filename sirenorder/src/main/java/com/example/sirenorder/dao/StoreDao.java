package com.example.sirenorder.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	public ArrayList<StoreVO> selectall() {
		return storemapper.selectall();
	}
	@Override
	public ArrayList<StoreVO> selectChain(String name) {
		return storemapper.selectchain(name);
	}
}
