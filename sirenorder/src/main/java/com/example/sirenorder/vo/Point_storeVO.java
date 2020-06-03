package com.example.sirenorder.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("point_store")	
public class Point_storeVO {
	String point_store_id;
	String point_id;
	String store_id;
	String users_id;
	String chain_name;
}