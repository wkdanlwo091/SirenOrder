package com.example.sirenorder.frame;

import java.util.ArrayList;

import com.example.sirenorder.vo.StoreVO;

public interface Dao<Id, Model> {
	default public void insert(Model m) throws Exception{
	}
	default public void update(Model id) throws Exception{
	}
	default public void delete(String id) throws Exception{
	}
	default public ArrayList<Model> selectChain(String id) throws Exception{
		return null;
	}
	public Model select(Id id);
	public ArrayList<Model> selectall();
}
