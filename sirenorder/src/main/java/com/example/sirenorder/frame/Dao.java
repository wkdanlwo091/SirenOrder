package com.example.sirenorder.frame;

import java.util.ArrayList;

public interface Dao<Id, Model> {
	default public void insert(Model m) throws Exception{
	}
	default public void update(Model id) throws Exception{
	}
	default public void delete(String id) throws Exception{
	}
	public Model select(Id id);
	public ArrayList<Model> selectall();
}
