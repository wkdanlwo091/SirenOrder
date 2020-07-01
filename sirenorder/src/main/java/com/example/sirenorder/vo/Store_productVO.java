package com.example.sirenorder.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("store_product")	
public class Store_productVO {
	String store_product_id;
	String store_id;
	String product_id;
	String store_name;
	String chain_name;
	String product_name;
	int seq;
}
