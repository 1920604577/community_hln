package com.gsxy.core.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息(UserInfo)实体类
 *
 * @author makejava
 * @since 2024-08-25 14:41:55
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
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
     * 身份证号
     */
    private String cardCode;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 手机号
     */
    private String phone;
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
    /**
     * 生日
     */
    private Date birthday;

}

