package com.example.sirenorder.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("cart")	
public class CartVO {//일단은 디비 연결은 안할거다. 시간이 부족해서
	String product_name;
	int number;//수량 quantitty
	int price;
	String store_name;//banapresso 신촌점과 banapresso 홍대점을 구분하기 위해서
	String chain_name;
}
