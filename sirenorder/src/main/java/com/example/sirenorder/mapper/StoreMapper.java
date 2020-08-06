package com.example.sirenorder.mapper;

import java.util.ArrayList;

import com.example.sirenorder.vo.StoreVO;

public interface StoreMapper {
	public StoreVO select(String name);
	public void insert(StoreVO storeVO);
	public ArrayList<StoreVO> selectall();
	public ArrayList<StoreVO> selectchain(String name);
	public String selectStore_id(String store_name);
	public void updateAllPoint_rate(StoreVO storeVO);
}