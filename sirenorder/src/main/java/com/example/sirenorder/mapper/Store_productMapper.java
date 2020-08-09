package com.example.sirenorder.mapper;

import java.util.ArrayList;
import java.util.List;

import com.example.sirenorder.vo.Store_productJoinProductVO;
import com.example.sirenorder.vo.Store_productVO;

public interface Store_productMapper {
	public ArrayList<Store_productVO> selectByStore_name(String store_name);
	public int selectListCnt(String store_name) ;
	public ArrayList<Store_productVO> selectProductList(String store_name, int startList, int listSize);
	public void insert(Store_productVO store_productVO);
	public void insertMultiple(List<Store_productVO> store_productVO);
	public ArrayList<Store_productJoinProductVO> selectProductListJoin(String store_name, int startList, int listSize);
	public void deleteStore_productByProduct_name(String product_name);

	
}