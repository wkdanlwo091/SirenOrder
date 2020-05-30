package com.example.sirenorder.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("cart")	
public class CartVO {//일단은 디비 연결은 안할거다.
	String product_name;
	int number;
	int price;
}
