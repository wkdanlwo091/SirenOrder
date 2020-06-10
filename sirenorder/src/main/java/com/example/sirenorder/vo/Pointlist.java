package com.example.sirenorder.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

//포인트를 받아오기 위한 클래스
@Data
@Alias("point_store")	
public class Pointlist {
	String [] chain_name;
}
