package com.example.sirenorder.mapper;

import com.example.sirenorder.vo.Orders_detailVO;

public interface Orders_detailMapper {
	public Orders_detailVO select(String orders_id);
	public void insert(Orders_detailVO orders_detailVO);

}
