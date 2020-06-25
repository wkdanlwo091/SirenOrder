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
	int [] useOrNot;//i번째 체인의 포인트를 쓸것인지 
	int [] point;
	int [] totalPrice;
	String [][] productName = new String[5][5];
	//체인 점당 여러가지 물건이 있으므로 2차원 배열 ex) banapresso에 에스프레소, 녹차, 행신감자탕에 감자탕, 뼈다귀해장국
	int [][] productPrice = new int[5][5];
	int [][] productQuantity = new int[5][5];
	int allTotalPrice;
}