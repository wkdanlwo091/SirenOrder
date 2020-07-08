package com.example.messagingstompwebsocket;

public class OwnerMessage {
	private String content;//product_name을 전달 

	public OwnerMessage() {
	}

	public OwnerMessage(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

}
