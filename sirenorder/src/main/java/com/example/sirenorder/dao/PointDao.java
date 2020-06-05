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
	public ArrayList<PointVO> selectall() {
		return null;
	}

}
