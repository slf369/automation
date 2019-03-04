package com.demo.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@DynamicUpdate
public class ParamInfo {
	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	@NotBlank(message = "参数名不能为空")
	private String paramName;
	@NotNull
	@NotBlank(message = "参数值不能为空")
	private String paramValue;
	private String paramDesc;

	@Override
	public String toString() {
		return "ParamInfo{" +
				"id=" + id +
				", paramName='" + paramName + '\'' +
				", paramValue='" + paramValue + '\'' +
				", paramDesc='" + paramDesc + '\'' +
				'}';
	}

	public ParamInfo() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}
}
