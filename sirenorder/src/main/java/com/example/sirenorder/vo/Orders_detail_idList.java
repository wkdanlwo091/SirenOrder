package com.example.sirenorder.vo;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("Orders_detailList")	
public class Orders_detail_idList {// owner가 주문 완료되었다고 넣을 때 사용하는 것 
	String [] orders_detail_id = new String[6];
	String [] orders_id = new String[6];
	String [] store_name = new String[6];
}