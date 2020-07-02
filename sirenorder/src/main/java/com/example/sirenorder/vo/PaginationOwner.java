package com.example.sirenorder.vo;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;


//owner에서 Orders_detailJoinProduct를 페이지네이션 범위 검색하기 위해서 만들어진 것 
@Data
@Alias("paginationowner")	
public class PaginationOwner {
	 int listSize = 6;                //페이지당 6개의 리스트
	 int startList;
	 String store_name;
}
