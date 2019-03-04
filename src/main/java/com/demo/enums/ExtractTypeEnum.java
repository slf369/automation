package com.demo.enums;

/**
 * Created by shi.lingfeng on 2018/3/28 0028.
 */
public enum ExtractTypeEnum implements CodeEnum  {

    BLANK(99,"BLACK"),
    JSONPATH(0,"JSONPATH"),
    REGEX(1,"REGEX"),
    PREFIX_SUFFIX(2,"PREFIX_SUFFIX"),
    SQL(3,"SQL");

    private Integer code;
    private String message;

    ExtractTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
