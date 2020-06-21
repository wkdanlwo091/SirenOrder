package com.example.sirenorder.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("Orders_detailJoinProduct")	
public class Orders_detailJoinProductVO{
	String orders_detail_id;
	int price;
	int quantity;
	String orders_id;
	String product_id;
	String status;
	int seq;
	ProductVO productVO;
}