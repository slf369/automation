package com.demo.enums;

/**
 * Created by shi.lingfeng on 2018/3/16.
 */

public enum ReqMethodEnum implements CodeEnum {	
	GET(0,"GET"),
	POST(1,"POST");

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private Integer code;
	private String message;		
	
	ReqMethodEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public Integer getCode() {
		// TODO Auto-generated method stub
		return this.code;
	}
}
