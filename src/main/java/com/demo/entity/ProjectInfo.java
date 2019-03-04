package com.demo.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by shi.lingfeng on 2018/3/23.
 */
@Entity
@DynamicUpdate
public class ProjectInfo {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "项目名称不能为空")
    private String projectName;
    private String projectDesc;

    @Override
    public String toString() {
        return "ProjectInfo{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", projectDesc='" + projectDesc + '\'' +
                '}';
    }

    public ProjectInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }
}
