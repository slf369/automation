package com.demo.enums;


/**
 * Created by shi.lingfeng on 2018/3/16.
 */

public enum ProtocolTypeEnum implements CodeEnum{
	
	HTTP(0,"HTTP"),
	HTTPS(1,"HTTPS");
	
	private Integer code;
	private String message;

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	ProtocolTypeEnum(int code, String message) {

		this.code = code;
		this.message = message;
	}

	@Override
	public Integer getCode() {
		// TODO Auto-generated method stub
		return this.code;
	}	
}
