package com.example.sirenorder.frame;

import java.util.ArrayList;

import org.springframework.transaction.annotation.Transactional;

import com.example.sirenorder.vo.StoreVO;

public interface Biz<Id, Model> {
	@Transactional 
	default public void register(Model m) throws Exception{
	}
	@Transactional 
	default public void update(Model id) throws Exception{
	}
	@Transactional 
	default public void delete(Id id) throws Exception{
	}
	@Transactional 
	default public ArrayList<Model> getChain(Id id) throws Exception{
		return null;
	}
	@Transactional 
	default public ArrayList<Model> getProduct(String name, int num) throws Exception{
		return null;
	}
	
	@Transactional 
	default public Model get(String name, String name2) throws Exception{
		return null;
	}
	
	@Transactional 
	default public Model getByChain_name(String chain_name) throws Exception{
		return null;
	}
	
	@Transactional 
	default public void deleteProduct(Model m) throws Exception{
	}
	
	@Transactional
	default public int getOrders_seq() throws Exception{
		return (Integer) null;
	}
	
	@Transactional
	default public String getProduct_id(String product_name) throws Exception{
		return  null;
	}
	
	public Model get(Id id);
	public ArrayList<Model> get();
}
