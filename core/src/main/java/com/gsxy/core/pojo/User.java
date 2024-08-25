package com.gsxy.core.pojo;

import lombok.*;

import java.util.Date;
import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2024-08-24 16:18:18
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class User implements Serializable {
    private static final long serialVersionUID = 370899512347857117L;

    private Long id;
/**
     * 用户名
     */
    private String username;
/**
     * 密码
     */
    private String password;
/**
     * 姓名
     */
    private String name;
/**
     * 学院
     */
    private Integer college;
/**
     * 学号
     */
    private String studentId;
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
     * 登录时间
     */
    private Date loginTime;
/**
     * 创建人
     */
    private Long createBy;
/**
     * 创建时间
     */
    private Date createTime;
/**
     * 修改人
     */
    private Long updateBy;
/**
     * 修改时间
     */
    private Date updateTime;
/**
     * 状态
     */
    private Integer status;
/**
     * 逻辑删除（0：没有删除，1：已删除）
     */
    private Integer delFlag;


}

