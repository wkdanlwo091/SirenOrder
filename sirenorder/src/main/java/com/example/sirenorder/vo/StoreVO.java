package com.example.sirenorder.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("store")	
public class StoreVO {
	String store_id;
	String store_name;
	String chain_id;
	String chain_name;
}
