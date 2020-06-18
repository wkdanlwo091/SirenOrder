package com.example.sirenorder.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;
//포인트를 받아오기 위한 클래스                   --->  VO는 아니다. 
@Data
@Alias("point_list")	
public class PointList {
	String [] chain_name;
	String [] store_name;
	String [] point_id;
	int [] useOrNot;
	int [] point;
	int [] totalPrice;
	
	String [][] productName = new String[5][5];
	int [][] productPrice = new int[5][5];
	int [][] productQuantity = new int[5][5];
}