package com.demo.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by shi.lingfeng on 2018/3/2.
 */

@Entity
@DynamicUpdate
public class SuiteInfo {
    @Id
    @GeneratedValue
    Long id;
    @NotBlank(message = "用例集名称不能为空")
    @NotNull
    String suiteName;
    String caseIds;
    String suiteDesc;
    String casePassIds;
    String caseSkipIds;
    String caseFailIds;
    String casePassDesc;
    String caseSkipDesc;
    String caseFailDesc;
    String suiteResult;

    @Override
    public String toString() {
        return "SuiteInfo{" +
                "id=" + id +
                ", suiteName='" + suiteName + '\'' +
                ", caseIds='" + caseIds + '\'' +
                ", suiteDesc='" + suiteDesc + '\'' +
                ", casePassIds='" + casePassIds + '\'' +
                ", caseSkipIds='" + caseSkipIds + '\'' +
                ", caseFailIds='" + caseFailIds + '\'' +
                ", casePassDesc='" + casePassDesc + '\'' +
                ", caseSkipDesc='" + caseSkipDesc + '\'' +
                ", caseFailDesc='" + caseFailDesc + '\'' +
                ", suiteResult='" + suiteResult + '\'' +
                '}';
    }

    public SuiteInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSuiteName() {
        return suiteName;
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    public String getCaseIds() {
        return caseIds;
    }

    public void setCaseIds(String caseIds) {
        this.caseIds = caseIds;
    }

    public String getSuiteDesc() {
        return suiteDesc;
    }

    public void setSuiteDesc(String suiteDesc) {
        this.suiteDesc = suiteDesc;
    }

    public String getCasePassIds() {
        return casePassIds;
    }

    public void setCasePassIds(String casePassIds) {
        this.casePassIds = casePassIds;
    }

    public String getCaseSkipIds() {
        return caseSkipIds;
    }

    public void setCaseSkipIds(String caseSkipIds) {
        this.caseSkipIds = caseSkipIds;
    }

    public String getCaseFailIds() {
        return caseFailIds;
    }

    public void setCaseFailIds(String caseFailIds) {
        this.caseFailIds = caseFailIds;
    }

    public String getCasePassDesc() {
        return casePassDesc;
    }

    public void setCasePassDesc(String casePassDesc) {
        this.casePassDesc = casePassDesc;
    }

    public String getCaseSkipDesc() {
        return caseSkipDesc;
    }

    public void setCaseSkipDesc(String caseSkipDesc) {
        this.caseSkipDesc = caseSkipDesc;
    }

    public String getCaseFailDesc() {
        return caseFailDesc;
    }

    public void setCaseFailDesc(String caseFailDesc) {
        this.caseFailDesc = caseFailDesc;
    }

    public String getSuiteResult() {
        return suiteResult;
    }

    public void setSuiteResult(String suiteResult) {
        this.suiteResult = suiteResult;
    }
}
