package com.example.sirenorder.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.mapper.ProductMapper;
import com.example.sirenorder.vo.ProductVO;

@Repository("productdao")
public class ProductDao implements Dao<String,ProductVO>{
	@Autowired
	ProductMapper productmapper;
	@Override
	public ArrayList<ProductVO> selectProduct(String name, int num) {
		return productmapper.selectProduct(name, num);
	}
	
	@Override 
	public String selectProduct_id(String product_name) {
		return productmapper.selectProduct_id(product_name);
	}
	@Override
	public void deleteProduct(ProductVO product) {
	}
	
	@Override
	public void insert(ProductVO product) {
	}

	@Override
	public int selectListCnt(String chain_name) {
		return productmapper.selectListCnt(chain_name);
	}
	
	@Override
	public ArrayList<ProductVO> selectProductList(String chain_name, int startList, int listSize) {
		return productmapper.selectProductList(chain_name, startList, listSize);
	}
	
	
	//아래는 구현 안함 
	@Override
	public ProductVO select(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ProductVO> selectall() {
		// TODO Auto-generated method stub
		return null;
	}



}
