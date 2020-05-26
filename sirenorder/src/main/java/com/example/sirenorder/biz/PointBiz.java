package com.example.sirenorder.biz;

import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.vo.PointVO;

@Service("pointbiz")
public class PointBiz implements Biz<String, PointVO>  {
	
	@Resource(name = "pointdao")
	Dao<String, PointVO> dao;
	
	@Override
	public PointVO get(String id) {
		// TODO Auto-generated method stub
		return dao.select(id);
	}
	@Override
	public ArrayList<PointVO> get() {
		// TODO Auto-generated method stub
		return dao.selectall();
	}
}