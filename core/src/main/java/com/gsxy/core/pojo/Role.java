package com.gsxy.core.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表(Role)实体类
 *
 * @author makejava
 * @since 2024-08-25 10:12:42
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Role implements Serializable {
    private static final long serialVersionUID = -76502138342512417L;

    private Long id;
/**
     * 角色名称
     */
    private String name;
/**
     * 权限
     */
    private String permission;
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

