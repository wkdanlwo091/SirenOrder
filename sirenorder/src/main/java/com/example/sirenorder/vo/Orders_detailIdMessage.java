package com.example.sirenorder.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Orders_detailIdMessage {
	
    private List<String> orders_detail_id;
    public List<String> getOrders_detail_id() {
        return orders_detail_id;
    }
}