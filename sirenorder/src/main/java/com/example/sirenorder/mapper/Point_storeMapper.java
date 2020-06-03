package com.example.sirenorder.mapper;

import com.example.sirenorder.vo.Point_storeVO;

public interface Point_storeMapper {
	public Point_storeVO select(String users_id, String chain_name);
	public void insert(Point_storeVO point_storeVO);
}
