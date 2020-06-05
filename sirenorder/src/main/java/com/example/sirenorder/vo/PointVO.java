package com.example.sirenorder.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("point")	
public class PointVO {
	String point_id;
	String users_id;
	int point; 
	String chain_name;
}
