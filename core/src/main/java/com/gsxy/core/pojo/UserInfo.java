package com.gsxy.core.pojo;

import java.io.Serializable;

/**
 * 用户信息(UserInfo)实体类
 *
 * @author makejava
 * @since 2024-08-25 14:41:55
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 108181105025273468L;
/**
     * 关联user学号id
     */
    private String id;
/**
     * 姓名
     */
    private String name;
/**
     * 学院
     */
    private Integer college;
/**
     * 班级
     */
    private Integer org;
/**
     * 年级
     */
    private String grade;
/**
     * 专业
     */
    private String professional;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCollege() {
        return college;
    }

    public void setCollege(Integer college) {
        this.college = college;
    }

    public Integer getOrg() {
        return org;
    }

    public void setOrg(Integer org) {
        this.org = org;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

}

