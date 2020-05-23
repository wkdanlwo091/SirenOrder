package com.example.sirenorder.mapper;

import java.util.ArrayList;
import com.example.sirenorder.vo.PointVO;

public interface PointMapper {
	public PointVO select(String point_id);
	public ArrayList<PointVO> selectall();
	public void update(PointVO point);
	public void delete(String point_id);
	public void insert(PointVO point);
}