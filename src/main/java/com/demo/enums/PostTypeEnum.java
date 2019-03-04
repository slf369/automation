package com.demo.enums;

/**
 * Created by shi.lingfeng on 2018/3/16.
 */

public enum PostTypeEnum implements CodeEnum {
	//Enum构造
//	BLANK(99,"BLACK"),
	JSON(0,"JSON"),
	RAW(1,"RawStSring"),
	FROM(2,"Form表单"),
	XML(3,"XML")	;

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
	 
	PostTypeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public Integer getCode() {
		// TODO Auto-generated method stub
		return this.code;
	}
}
