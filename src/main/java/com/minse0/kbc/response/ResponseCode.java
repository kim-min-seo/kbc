package com.minse0.kbc.response;

public enum ResponseCode {
	
	SUCCESS(1000,"success"),
	
	DUPLICATE_ID(2001, "duplicate id"),
	USER_JOIN_FAIL(2002, "user join fail");
	
	private int code;
	private String message;
	
	 ResponseCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	
	
}
