package com.example.sirenorder.biz;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.vo.PointVO;

@Service("pointbiz")
public class PointBiz implements Biz<String, PointVO>{
	@Resource(name = "pointdao")
	Dao<String, PointVO> dao;
	
	@Override
	public PointVO get(String id) {
		// TODO Auto-generated method stub
		return dao.select(id);
	}
	
	@Override
	public PointVO getByChain_name(String chain_name) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectByChain_name(chain_name);
	}
	@Override
	public PointVO getByChain_nameWithusers_id(String chain_name, String users_id) {
		// TODO Auto-generated method stub
		return dao.selectByChain_nameWithusers_id(chain_name, users_id);
	}
	
	@Override
	public void update(PointVO m) throws Exception {
		// TODO Auto-generated method stub
		dao.update(m);
	}
	@Override
	public ArrayList<PointVO> get() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void register(PointVO pointVO) throws Exception {
		dao.insert(pointVO);
	}
}