package com.gsxy.core.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限表(Permission)实体类
 *
 * @author makejava
 * @since 2024-08-25 10:12:36
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Permission implements Serializable {
    private static final long serialVersionUID = 510618777484663494L;

    private Long id;
/**
     * 权限名称
     */
    private String name;
/**
     * 创建人
     */
    private Long createdBy;
/**
     * 创建时间
     */
    private Date createdTime;
/**
     * 修改人
     */
    private Long updatedBy;
/**
     * 修改时间
     */
    private Date updatedTime;
/**
     * 逻辑删除（0：存在，1删除）
     */
    private Integer delFlag;

}

