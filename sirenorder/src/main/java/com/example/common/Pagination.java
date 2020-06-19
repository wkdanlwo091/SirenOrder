package com.example.common;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("pagination")	
public class Pagination {
	 int listSize = 6;                //페이지당 6개의 리스트
	 int rangeSize = 6;            // 페이지 번호 범위는 총 6개
	 int page;
	 int range;
	 int listCnt;
	 int pageCnt;
	 int startPage;
	 int startList;
	 int endPage;
	 boolean prev;
	 boolean next;
	public void pageInfo(int page, int range, int listCnt) {
		
		this.page = page;// 1
		this.range = range;// 1
		this.listCnt = listCnt;// 60
		this.pageCnt = (int) Math.ceil((double)listCnt/listSize);//(double)씌워줘야 한다.그래야  4/6을 1로 올림 한다. 
 		this.startPage = (range - 1) * rangeSize + 1 ;//1
		this.endPage = range * rangeSize; //6
		this.startList = (page-1) * listSize +1;//오라클 ROWNUM은 1번 부터   
		this.prev = range == 1 ? false : true; //1이면 prev 버튼 없다. 
		this.next = endPage > pageCnt ? false : true; //10 > 6 이므로 next 버튼 있다. 
		if (this.endPage > this.pageCnt) {
			this.endPage = this.pageCnt;
			this.next = false;
		}
	}
}
