package com.example.sirenorder.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.mapper.PointMapper;
import com.example.sirenorder.vo.PointVO;

@Repository("pointdao")
public class PointDao implements Dao<String, PointVO>{

	
	@Autowired
	PointMapper pointmapper;

	@Override
	public PointVO select(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PointVO> selectall() {
		// TODO Auto-generated method stub
		return null;
	}

}
