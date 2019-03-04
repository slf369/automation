package com.demo.entity;

import com.demo.enums.ExtractTypeEnum;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by shi.lingfeng on 2018/3/28 0028.
 */
@Entity
@DynamicUpdate
public class ExtractInfo implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @NotBlank(message = "表达式名称不能为空")
    private String extractName;
    private Integer extractType= ExtractTypeEnum.BLANK.getCode();
    private String jsonpathExpress;
    private String regexExpress;
    private String leftExpress;
    private String rightExpress;
    private String databaseUrl;
    private String databaseUsername;
    private String databasePassword;
    private String sqlExpress;

    @Override
    public String toString() {
        return "ExtractInfo{" +
                "id=" + id +
                ", extractName='" + extractName + '\'' +
                ", extractType=" + extractType +
                ", jsonpathExpress='" + jsonpathExpress + '\'' +
                ", regexExpress='" + regexExpress + '\'' +
                ", leftExpress='" + leftExpress + '\'' +
                ", rightExpress='" + rightExpress + '\'' +
                ", databaseUrl='" + databaseUrl + '\'' +
                ", databaseUsername='" + databaseUsername + '\'' +
                ", databasePassword='" + databasePassword + '\'' +
                ", sqlExpress='" + sqlExpress + '\'' +
                ", extractValue='" + extractValue + '\'' +
                '}';
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public void setDatabaseUsername(String databaseUsername) {
        this.databaseUsername = databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    private String extractValue;

    public String getSqlExpress() {
        return sqlExpress;
    }

    public void setSqlExpress(String sqlExpress) {
        this.sqlExpress = sqlExpress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExtractName() {
        return extractName;
    }

    public void setExtractName(String extractName) {
        this.extractName = extractName;
    }

    public Integer getExtractType() {
        return extractType;
    }

    public void setExtractType(Integer extractType) {
        this.extractType = extractType;
    }

    public String getJsonpathExpress() {
        return jsonpathExpress;
    }

    public void setJsonpathExpress(String jsonpathExpress) {
        this.jsonpathExpress = jsonpathExpress;
    }

    public String getRegexExpress() {
        return regexExpress;
    }

    public void setRegexExpress(String regexExpress) {
        this.regexExpress = regexExpress;
    }

    public String getLeftExpress() {
        return leftExpress;
    }

    public void setLeftExpress(String leftExpress) {
        this.leftExpress = leftExpress;
    }

    public String getRightExpress() {
        return rightExpress;
    }

    public void setRightExpress(String rightExpress) {
        this.rightExpress = rightExpress;
    }

    public String getExtractValue() {
        return extractValue;
    }

    public void setExtractValue(String extractValue) {
        this.extractValue = extractValue;
    }

    public ExtractInfo() {
    }
}
