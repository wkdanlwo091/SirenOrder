package com.example.sirenorder.vo;


import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("user")	
public class UserVO {
	String users_id;
	String users_password;
	String users_name;
	String sex;
	String orders_id;
	String point_id;
}
