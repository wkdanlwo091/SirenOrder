package com.example.sirenorder.frame;

import java.util.ArrayList;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

public interface Biz<Id, Model> {
	@Transactional 
	default public void register(Model m) throws Exception{
	}
	@Transactional 
	default public void update(Model id) throws Exception{
	}
	@Transactional 
	default public ArrayList<Model> getuser(Id id) throws Exception{
		return null;
	}
	@Transactional 
	default public ArrayList<Model> gethq(Id id) throws Exception{
		return null;
	}
	public Model get(Id id);
	public ArrayList<Model> get();
}
