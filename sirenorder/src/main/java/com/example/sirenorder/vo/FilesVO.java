package com.example.sirenorder.vo;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("files")	
public class FilesVO {
	String files_name;
	long files_size;
	Date regdate;
	int seq;
}
