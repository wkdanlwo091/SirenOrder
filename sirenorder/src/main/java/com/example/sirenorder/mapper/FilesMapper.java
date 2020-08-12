package com.example.sirenorder.mapper;

import com.example.sirenorder.vo.FilesVO;

public interface FilesMapper {
	public void insert(FilesVO filesVO);
	public FilesVO selectLastByPk();// 가장 최근 것 가져와야지 by primary key로써   
	//apk 파일은 
}
