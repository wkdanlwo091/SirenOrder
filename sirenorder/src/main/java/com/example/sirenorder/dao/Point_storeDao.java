package com.example.sirenorder.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.mapper.Point_storeMapper;
import com.example.sirenorder.vo.Point_storeVO;

@Repository("point_storedao")
public class Point_storeDao  implements Dao<String, Point_storeVO>{
	@Autowired
	Point_storeMapper point_storemapper;
	@Override
	public Point_storeVO selectPoint_store(String users_id, String chain_name) {
		return point_storemapper.select(users_id, chain_name);
	}
	@Override
	public void insert(Point_storeVO point_storeVO) {
		point_storemapper.insert(point_storeVO);
	}
	@Override
	public Point_storeVO select(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Point_storeVO> selectall() {
		// TODO Auto-generated method stub
		return null;
	}

}
