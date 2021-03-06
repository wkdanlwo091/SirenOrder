package com.example.sirenorder.biz;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.vo.Store_productJoinProductVO;
import com.example.sirenorder.vo.Store_productVO;
@Service("store_productbiz")
public class Store_productBiz implements Biz<String, Store_productVO>{
	@Resource(name = "store_productdao")
	Dao<String, Store_productVO> dao;
	@Override
	public Store_productVO get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Store_productVO> get() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getLastId() {
		return dao.selectLastId();
	}
	@Override
	public void register(Store_productVO store_productVO) throws Exception {
		// TODO Auto-generated method stub
		dao.insert(store_productVO);
	}
	
	@Override
	public void deleteMultiple(List<String> lists) throws Exception {
		// TODO Auto-generated method stub
		dao.deleteMultiple(lists);
	}
	
	@Override
	public void registerMultiple(List<Store_productVO> lists) throws Exception {
		// TODO Auto-generated method stub
		dao.insertMultiple(lists);
	}
	@Override
	public ArrayList<Store_productVO> getByStore_name(String store_name) {
		// TODO Auto-generated method stub
		return dao.selectByStore_name(store_name);
	}
	@Override
	public int getListCnt(String store_name) {
		// TODO Auto-generated method stub
		return dao.selectListCnt(store_name);
	}
	@Override
	public void deleteStore_productByProduct_name(String product_name) {
		// TODO Auto-generated method stub
		dao.deleteStore_productByProduct_name(product_name);
	}
	@Override
	public ArrayList<Store_productJoinProductVO> getProductListJoin(String store_name, int startList, int listSize) {
		// TODO Auto-generated method stub
		return dao.selectProductListJoin(store_name, startList, listSize);
	}
}