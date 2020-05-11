package com.example.sirenorder.frame;

import java.util.ArrayList;

public interface Dao<Id, Model> {
	
	default public void insert(Model m) throws Exception{
		
	}
	default public void update(Model id) throws Exception{
		
	}
	
	default public ArrayList<Model> selectuser(Id id) throws Exception{
		return null;//
	}
	default public ArrayList<Model> selecthq(Id id) throws Exception{
		return null;
	}
	
	public Model select(Id id);
	public ArrayList<Model> selectall();
}
