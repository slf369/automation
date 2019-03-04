package com.demo.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by shi.lingfeng on 2018/3/30.
 */
@Entity
@DynamicUpdate
public class FuncInfo {
    @Id
    @GeneratedValue
    Long id;
    @NotNull
    @NotBlank(message = "函数名不能为空")
    String funcName;
    @NotNull
    @NotBlank(message = "函数值不能为空")
    String funcValue;
    String funcDesc;
    String funcParams;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getFuncValue() {
        return funcValue;
    }

    public void setFuncValue(String funcValue) {
        this.funcValue = funcValue;
    }

    public String getFuncDesc() {
        return funcDesc;
    }

    public void setFuncDesc(String funcDesc) {
        this.funcDesc = funcDesc;
    }

    public String getFuncParams() {
        return funcParams;
    }

    public void setFuncParams(String funcParams) {
        this.funcParams = funcParams;
    }

    @Override
    public String toString() {
        return "FuncInfo{" +
                "id=" + id +
                ", funcName='" + funcName + '\'' +
                ", funcValue='" + funcValue + '\'' +
                ", funcDesc='" + funcDesc + '\'' +
                ", funcParams='" + funcParams + '\'' +
                '}';
    }

    public FuncInfo() {
    }

    public FuncInfo(String funcName, String funcValue, String funcDesc, String funcParams) {
        this.funcName = funcName;
        this.funcValue = funcValue;
        this.funcDesc = funcDesc;
        this.funcParams = funcParams;
    }
}
