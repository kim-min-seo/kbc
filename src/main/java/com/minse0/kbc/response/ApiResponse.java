package com.minse0.kbc.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 응답의 형태를 규격화
@AllArgsConstructor
@Getter
public class ApiResponse<T> {
	
	// 성공실패 여부, 코드, 메시지
	// 성공시 추가 데이터
	private String result;
	private int code;
	private String message;
	private T data;
	
	public static <T> ApiResponse<T> success(T data){
		return new ApiResponse<>("success", ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
		
	}
	
	public static <T> ApiResponse<T> fail(ResponseCode code){
		return new ApiResponse<>("fail", code.getCode(), code.getMessage(), null);
		
	}
	 
}
