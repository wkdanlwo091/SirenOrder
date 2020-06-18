package com.example.sirenorder.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("orders_detail")	
public class Orders_detailVO {
	String orders_detail_id;
	int price;
	int quantity;
	String orders_id;
	String product_id;
	int seq;
}
