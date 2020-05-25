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
	
	public Model get(Id id);
	public ArrayList<Model> get();
}
