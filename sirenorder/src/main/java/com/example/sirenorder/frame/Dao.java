package com.example.sirenorder.frame;

import java.util.ArrayList;

public interface Dao<Id, Model> {
	default public void insert(Model m) throws Exception{
	}
	default public void update(Model m) throws Exception{
	}
	default public void delete(String id) throws Exception{
	}
	default public ArrayList<Model> selectChain(String id) throws Exception{
		return null;
	}
	default public Model selectProduct(Model m) throws Exception{
		return null;
	}
	default public void deleteProduct(Model m) throws Exception{
	}
	
	public Model select(Id id);
	public ArrayList<Model> selectall();
}
