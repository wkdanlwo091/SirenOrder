package com.example.sirenorder.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("store")	
public class StoreVO{
	String store_id;
	String store_name;
	String chain_id;
	String chain_name;
	double gps_latitude;
	double gps_longtitude;
	double point_rate;
	
	
	//유저의 위도 경도와 store의  위도 경도 차이 값
	double Difference;
	
	int seq;
}