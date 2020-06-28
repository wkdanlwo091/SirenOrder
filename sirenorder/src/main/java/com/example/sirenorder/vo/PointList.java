package com.example.sirenorder.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;
//포인트를 받아오기 위한 클래스                   --->  VO는 아니다. 
@Data
@Alias("point_list")	
public class PointList {
	
	////////////포인트 사용 관련 변수 
	String [] chain_name;//포인트 사용처의 체인이름
	String [] store_name;// 포인트 사용처의 가게 이름   --> 만약 한번에 banapresso 행신, banapresso 일산 두군데 썼어도 편의를 위해 한군데만 넣는다고 가정 
	String [] point_id;//포인트 아이디  
	int [] useOrNot;//i번째 체인의 포인트를 쓸것인지 
	int [] point;// 포인트 
	int [] totalPrice;
	/////////////////
	String [] all_chain_name;//포인트 관계 없이 모든 체인 이름 
	int [] all_chain_price;//체인당 총가격 
	
	
	/// 아래 변수가 필요 없는데 ??? 왜나면 이미 카트에 있잖아 
	String [][] productName = new String[5][5];
	//체인 점당 여러가지 물건이 있으므로 2차원 배열 ex) banapresso에 에스프레소, banapresso에 녹차 
	int [][] productPrice = new int[5][5];
	int [][] productQuantity = new int[5][5];
	int allTotalPrice;
}