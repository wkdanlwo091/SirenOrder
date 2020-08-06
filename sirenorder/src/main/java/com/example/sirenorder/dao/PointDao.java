package com.example.sirenorder.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.mapper.PointMapper;
import com.example.sirenorder.vo.PointVO;

@Repository("pointdao")
public class PointDao implements Dao<String, PointVO> {
	@Autowired
	PointMapper pointmapper;
	@Override
	public PointVO select(String point_id) {
		return pointmapper.select(point_id);
	}
	@Override
	public PointVO selectByChain_name(String chain_name) {
		return pointmapper.selectByChain_name(chain_name);
	}
	
	@Override
	public PointVO selectByChain_nameWithusers_id(String chain_name, String users_id) {
		return pointmapper.selectByChain_nameWithusers_id(chain_name, users_id);
	}
	
	@Override
	public ArrayList<PointVO> selectall() {
		return null;
	}
	@Override
	public void update(PointVO pointVO) {
		pointmapper.update(pointVO);
	}
	@Override
	public void updatePoint_rate(PointVO m) throws Exception {
		// TODO Auto-generated method stub
		pointmapper.updatePoint_rate(m);
	}
	@Override
	public void insert(PointVO pointVO) {
		pointmapper.insert(pointVO);
	}

}
