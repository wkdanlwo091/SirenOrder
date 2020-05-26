package com.example.sirenorder.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("product")	
public class ProductVO {
	String product_id;
	String product_name;
	String store_name;
}
