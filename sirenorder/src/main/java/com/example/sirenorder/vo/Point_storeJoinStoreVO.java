package com.example.sirenorder.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("Point_storeJoinStore")	
public class Point_storeJoinStoreVO {
	
	String point_store_id;
	String point_id;
	String store_id;
	String users_id;
	String chain_name;
	Date point_date;
	int used_point;
	int seq;
	
	StoreVO storeVO;
}