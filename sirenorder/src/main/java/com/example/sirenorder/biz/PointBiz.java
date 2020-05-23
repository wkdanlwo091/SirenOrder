package com.example.sirenorder.biz;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.vo.PointVO;
import com.example.sirenorder.vo.UserVO;

@Service("pointbiz")
public class PointBiz implements Biz<String, PointVO>  {

	
	@Resource(name = "userdao")
	Dao<String, UserVO> dao;

	@Override
	public PointVO get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PointVO> get() {
		// TODO Auto-generated method stub
		return null;
	}

}
