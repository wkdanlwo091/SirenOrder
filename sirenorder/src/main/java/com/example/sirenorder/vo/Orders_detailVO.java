package com.example.sirenorder.vo;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("orders_detail")	
public class Orders_detailVO {
	String orders_detail_id;
	int price;
	int quantity;
	Date orders_date;//개발의 간편화를 위해 역정규화 하였다. 
	String orders_id;
	String product_id;
	String product_name;//개발의 간편화를 위해 역정규화 하였다. 
	String status;
	int seq;
}
