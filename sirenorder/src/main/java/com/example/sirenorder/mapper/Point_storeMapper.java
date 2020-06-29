package com.example.sirenorder.mapper;
import java.sql.Date;
import java.util.ArrayList;

import com.example.sirenorder.vo.Point_storeJoinStoreVO;
import com.example.sirenorder.vo.Point_storeVO;

public interface Point_storeMapper {
	public Point_storeVO select(String users_id, String chain_name);
	public ArrayList<Point_storeJoinStoreVO> selectByDateFromToJoin(String users_id, Date firstDate, Date secondDate);
	public ArrayList<Point_storeJoinStoreVO> selectByUsers_id(String users_id );
	public void insert(Point_storeVO point_storeVO);
}