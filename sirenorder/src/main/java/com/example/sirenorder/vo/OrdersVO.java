package com.example.sirenorder.vo;

import java.sql.Date;
import org.apache.ibatis.type.Alias;
import lombok.Data;

@Data
@Alias("orders")	
public class OrdersVO {
	String orders_id;
	Date orders_date;
	String payment_way;
	int total_price;
	String users_id;
	int seq;
}
