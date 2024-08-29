package com.gsxy.core.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 签到表(SignIn)实体类
 *
 * @author makejava
 * @since 2024-08-29 09:33:00
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class SignIn implements Serializable {
    private static final long serialVersionUID = 718004188301608572L;

    private Long id;
/**
     * 签到标题
     */
    private String title;
/**
     * 签到内容
     */
    private String message;
    /**
     * 社团id
     */
    private Long communityId;
/**
     * 持续时长
     */
    private Integer timeLen;
/**
     * 创建时间
     */
    private Date createdTime;
/**
     * 创建人
     */
    private Long createdBy;
/**
     * 逻辑删除（0：存在，1：删除）
     */
    private Integer delFlag;

}

