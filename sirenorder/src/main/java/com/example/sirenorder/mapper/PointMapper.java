package com.example.sirenorder.mapper;

import java.sql.Date;

import com.example.sirenorder.vo.PointVO;

public interface PointMapper {
	public PointVO select(String point_id);
	public PointVO selectByChain_name(String chain_name);
	public PointVO selectByChain_nameWithusers_id(String chain_name, String users_id);
	public void updatePoint_rate(PointVO pointVO);
	public void insert(PointVO pointVO);
	public void update(PointVO pointVO);
}