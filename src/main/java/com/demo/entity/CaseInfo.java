package com.demo.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import com.demo.enums.PostTypeEnum;
import com.demo.enums.ProtocolTypeEnum;
import com.demo.enums.ReqMethodEnum;
import org.hibernate.validator.constraints.NotBlank;


/**
 * Created by shi.lingfeng on 2018/3/15.
 */
@Entity
@DynamicUpdate
public class CaseInfo implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
//	@NotNull
//	@NotBlank(message="项目名不能为空")
	private Integer projectId;
	private String testEnv;
	@NotNull
	@NotBlank(message="用例名不能为空")
	private String caseName;
	private String caseDesc;
	private Integer protocolType=ProtocolTypeEnum.HTTP.getCode();
	private Integer reqMethod=ReqMethodEnum.POST.getCode();
	@NotNull
	@NotBlank(message="服务器名称/IP不能为空")
	private String reqHostUrl;
	private String reqPath;
	private String reqHeader;
	private String getParams;
	private Integer postType=PostTypeEnum.JSON.getCode();
	private String postBody;
	private String suiteIds;
	private String preProcessorId;
	private String postProcessorId;
	private String assertIds;
	private String userId;
	private Date createTime;
	private Date updateTime;
	private String assertResult;

	@Override
	public String toString() {
		return "CaseInfo{" +
				"id=" + id +
				", projectId=" + projectId +
				", testEnv='" + testEnv + '\'' +
				", caseName='" + caseName + '\'' +
				", caseDesc='" + caseDesc + '\'' +
				", protocolType=" + protocolType +
				", reqMethod=" + reqMethod +
				", reqHostUrl='" + reqHostUrl + '\'' +
				", reqPath='" + reqPath + '\'' +
				", reqHeader='" + reqHeader + '\'' +
				", getParams='" + getParams + '\'' +
				", postType=" + postType +
				", postBody='" + postBody + '\'' +
				", suiteIds='" + suiteIds + '\'' +
				", preProcessorId='" + preProcessorId + '\'' +
				", postProcessorId='" + postProcessorId + '\'' +
				", assertIds='" + assertIds + '\'' +
				", userId='" + userId + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", assertResult='" + assertResult + '\'' +
				'}';
	}

	public CaseInfo() {
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getTestEnv() {
		return testEnv;
	}

	public void setTestEnv(String testEnv) {
		this.testEnv = testEnv;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getCaseDesc() {
		return caseDesc;
	}

	public void setCaseDesc(String caseDesc) {
		this.caseDesc = caseDesc;
	}

	public Integer getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(Integer protocolType) {
		this.protocolType = protocolType;
	}

	public Integer getReqMethod() {
		return reqMethod;
	}

	public void setReqMethod(Integer reqMethod) {
		this.reqMethod = reqMethod;
	}

	public String getReqHostUrl() {
		return reqHostUrl;
	}

	public void setReqHostUrl(String reqHostUrl) {
		this.reqHostUrl = reqHostUrl;
	}

	public String getReqPath() {
		return reqPath;
	}

	public void setReqPath(String reqPath) {
		this.reqPath = reqPath;
	}

	public String getReqHeader() {
		return reqHeader;
	}

	public void setReqHeader(String reqHeader) {
		this.reqHeader = reqHeader;
	}

	public String getGetParams() {
		return getParams;
	}

	public void setGetParams(String getParams) {
		this.getParams = getParams;
	}

	public Integer getPostType() {
		return postType;
	}

	public void setPostType(Integer postType) {
		this.postType = postType;
	}

	public String getPostBody() {
		return postBody;
	}

	public void setPostBody(String postBody) {
		this.postBody = postBody;
	}

	public String getSuiteIds() {
		return suiteIds;
	}

	public void setSuiteIds(String suiteIds) {
		this.suiteIds = suiteIds;
	}

	public String getPreProcessorId() {
		return preProcessorId;
	}

	public void setPreProcessorId(String preProcessorId) {
		this.preProcessorId = preProcessorId;
	}

	public String getPostProcessorId() {
		return postProcessorId;
	}

	public void setPostProcessorId(String postProcessorId) {
		this.postProcessorId = postProcessorId;
	}

	public String getAssertIds() {
		return assertIds;
	}

	public void setAssertIds(String assertIds) {
		this.assertIds = assertIds;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getAssertResult() {
		return assertResult;
	}

	public void setAssertResult(String assertResult) {
		this.assertResult = assertResult;
	}
}
