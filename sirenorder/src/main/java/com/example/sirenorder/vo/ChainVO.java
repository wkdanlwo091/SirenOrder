package com.example.sirenorder.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("chain")	
public class ChainVO {
	String chain_id;
	String chain_name;
	double point_rate;
	int seq;
}
