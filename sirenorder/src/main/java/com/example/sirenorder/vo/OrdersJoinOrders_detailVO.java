package com.example.sirenorder.vo;

import java.sql.Date;
import java.util.List;
import org.apache.ibatis.type.Alias;
import lombok.Data;

//1대 N 관계를 위한 것
@Data
@Alias("OrdersJoinOrders_detailVO")
public class OrdersJoinOrders_detailVO {
	String orders_id;
	Date orders_date;
	String payment_way;
	int total_price;
	String users_id;
	int seq;
	List<Orders_detailVO> orders_detailVO;
}
