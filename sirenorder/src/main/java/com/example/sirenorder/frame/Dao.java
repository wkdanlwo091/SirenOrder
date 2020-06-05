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

	public Model select(Id id);

	public ArrayList<Model> selectall();
}
