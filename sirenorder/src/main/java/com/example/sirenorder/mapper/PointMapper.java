package com.example.sirenorder.mapper;

import java.sql.Date;

import com.example.sirenorder.vo.PointVO;

public interface PointMapper {
	public PointVO select(String point_id);
	public PointVO selectByChain_name(String chain_name);
	public void insert(PointVO pointVO);
	public void update(PointVO pointVO);
}