package com.example.sirenorder.biz;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.vo.Point_storeVO;

@Service("point_storebiz")
public class Point_storeBiz implements Biz<String, Point_storeVO>{
	
	@Resource(name = "point_storedao")
	Dao<String, Point_storeVO> dao;
	
	@Override
	public Point_storeVO get(String users_id, String chain_name) throws Exception {
		return dao.select(users_id, chain_name);
	}
	
	@Override
	public void register(Point_storeVO point_storeVO) throws Exception {
		dao.insert(point_storeVO);
	}
	
	@Override
	public Point_storeVO get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Point_storeVO> get() {
		// TODO Auto-generated method stub
		return null;
	}
}