package com.example.sirenorder.vo;
import java.sql.Date;//util 보다 oracle에 넣기 편하다 .but 둘다 사장된 api
import org.apache.ibatis.type.Alias;
import lombok.Data;

@Data
@Alias("Orders_detailJoinProduct")	
public class Orders_detailJoinProductVO{
	String orders_detail_id;
	int price;
	int quantity;
	String orders_id;
	String product_id;
	String product_name;
	String status;
	Date orders_date;//개발의 간편화를 위해 역정규화를 하였다. 
	String store_id;//owner가 orders_detail 볼 수 있게 
	String store_name;
	int seq;
	ProductVO productVO;
}