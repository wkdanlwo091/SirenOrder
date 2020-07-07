package com.example.sirenorder.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("productnames")	
public class ProductNames {//front에서 spring controller로 전달할 때 받아오는 모델 
	String [] product_names;
}
