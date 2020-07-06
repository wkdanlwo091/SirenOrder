package com.example.sirenorder.vo;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("sumandorders_date")	
public class SumAndOrders_date {
	int sum;
	Date orders_date;
}