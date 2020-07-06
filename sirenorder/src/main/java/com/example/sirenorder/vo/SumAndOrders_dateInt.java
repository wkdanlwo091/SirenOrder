package com.example.sirenorder.vo;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("sumandorders_dateint")	
public class SumAndOrders_dateInt {
	int Sum;
	int orders_date;
}