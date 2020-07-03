package com.example.sirenorder.vo;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("Orders_detailList")	
public class Orders_detail_idList {
	String [] orders_detail_id = new String[6];
}