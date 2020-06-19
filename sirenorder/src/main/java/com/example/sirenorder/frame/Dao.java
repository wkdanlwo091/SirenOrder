package com.example.sirenorder.frame;

import java.util.ArrayList;

public interface Dao<Id, Model> {
	default public void insert(Model m) throws Exception {
	}

	default public void update(Model m) throws Exception {
	}

	default public void delete(String id) throws Exception {
	}

	default public ArrayList<Model> selectChain(String name) throws Exception {
		return null;
	}

	default public ArrayList<Model> selectProduct(String  name, int num) throws Exception {
		return null;
	}

	default public Model select(String  name, String name2) throws Exception {
		return null;
	}

	default public void deleteProduct(Model m) throws Exception {
	}
	
	default public Model selectByChain_name(Id id) throws Exception {
		return null;
	}
	default public int selectOrders_seq() throws Exception {
		return (Integer) null;
	}
	default public String selectProduct_id(String product_name) {
		return null;
	}
	default public int selectListCnt(String chain_name) {
		return (Integer) null;
	}
	default public ArrayList<Model> selectProductList(String chain_name, int startList, int listSize) {
		return null;
	}
	
	public Model select(Id id);

	public ArrayList<Model> selectall();
}
