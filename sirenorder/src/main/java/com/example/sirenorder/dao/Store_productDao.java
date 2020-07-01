package com.example.sirenorder.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.mapper.ProductMapper;
import com.example.sirenorder.mapper.Store_productMapper;
import com.example.sirenorder.vo.ProductVO;
import com.example.sirenorder.vo.Store_productJoinProductVO;
import com.example.sirenorder.vo.Store_productVO;

@Repository("store_productdao")
public class Store_productDao implements Dao<String, Store_productVO>{
	@Autowired
	Store_productMapper store_productMapper;
	@Override
	public Store_productVO select(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Store_productVO> selectall() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Store_productVO> selectByStore_name(String store_name){
		// TODO Auto-generated method stub
		return store_productMapper.selectByStore_name(store_name);
	}
	@Override
	public int selectListCnt(String store_name){
		// TODO Auto-generated method stub
		return store_productMapper.selectListCnt(store_name);
	}
	@Override
	public ArrayList<Store_productVO> selectProductList(String store_name, int startList, int listSize){
		// TODO Auto-generated method stub
		return store_productMapper.selectProductList(store_name,startList,listSize);
	}
	@Override
	public ArrayList<Store_productJoinProductVO> selectProductListJoin(String store_name, int startList, int listSize) {
		// TODO Auto-generated method stub
		return store_productMapper.selectProductListJoin(store_name,startList,listSize);
	}

}