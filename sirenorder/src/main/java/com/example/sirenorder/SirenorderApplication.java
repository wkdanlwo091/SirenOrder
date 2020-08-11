package com.example.sirenorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.example.sirenorder.fileupload.FileUploadProperties;

@SpringBootApplication
@MapperScan("com.example.sirenorder.mapper")


//아래의 것은 파일 업로드 클래스를 불러온다. 
@EnableConfigurationProperties({
    FileUploadProperties.class
})
 

public class SirenorderApplication  extends SpringBootServletInitializer{// extends 한 것은 aws에서 돌리려면 필요해서
	public static void main(String[] args) {
		SpringApplication.run(SirenorderApplication.class, args);
	}
	@Override//이것도 aws에서 돌리려면 필요하다. 
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {	
		return builder.sources(SirenorderApplication.class);
	}
}

//여기서 class 가 없어서 에러가 났다고 한다 따라서 주석 처리 하겠다. 