package com.example.sirenorder.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("users")	
public class PointVO {
	String point_id;
	String users_id;
}
