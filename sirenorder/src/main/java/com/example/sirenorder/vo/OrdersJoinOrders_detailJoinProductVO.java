package com.example.sirenorder.vo;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("ordersjoinorders_detailjoinproduct")//실제로 join은 하지 않는다. 세 옵젝트 합치기 위해서 만듬
public class OrdersJoinOrders_detailJoinProductVO {
	String product_name;
	String chain_name;
	String image;
	int price;
	Date orders_date;
	int quantity;
}