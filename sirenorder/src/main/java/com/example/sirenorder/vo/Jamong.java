package com.example.sirenorder.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("jamong")	
public class Jamong {
	int age;
	String name;
}
