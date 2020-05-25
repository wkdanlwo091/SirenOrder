package com.example.sirenorder.mapper;

import java.util.ArrayList;

import com.example.sirenorder.vo.StoreVO;

public interface StoreMapper {
	public StoreVO select(String name);
	public ArrayList<StoreVO> selectall();
	public ArrayList<StoreVO> selectchain(String name);
}
