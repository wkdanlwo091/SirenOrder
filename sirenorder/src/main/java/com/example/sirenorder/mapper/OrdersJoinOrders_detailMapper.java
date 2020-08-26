package com.example.sirenorder.mapper;

import java.util.List;
import com.example.sirenorder.vo.OrdersJoinOrders_detailVO;
public interface OrdersJoinOrders_detailMapper {
	public List<OrdersJoinOrders_detailVO> selectOrdersJoinOrders_detailByOrders_id(String store_name);
}
