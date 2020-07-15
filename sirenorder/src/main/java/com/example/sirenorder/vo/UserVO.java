package com.example.sirenorder.vo;


import org.apache.ibatis.type.Alias;

import lombok.*;


@Data
@Alias("users")	
public class UserVO {//대소
	String users_id;
	String users_password;
	String users_name;
	String users_address;
	String sex;
	String role;
	String store_name;
	String token;
}
