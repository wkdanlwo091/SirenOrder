package com.example.sirenorder.vo;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("store_nameanddate")	
public class Store_nameAndDate {
	String store_name;
	Date from;
	Date to;
}
