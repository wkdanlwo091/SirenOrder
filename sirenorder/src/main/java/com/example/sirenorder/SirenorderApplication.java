package com.example.sirenorder;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.example.sirenorder.mapper")
public class SirenorderApplication {
	public static void main(String[] args) {
		SpringApplication.run(SirenorderApplication.class, args);
	}
}