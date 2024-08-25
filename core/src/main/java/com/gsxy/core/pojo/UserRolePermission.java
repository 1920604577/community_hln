package com.gsxy.core.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色权限表(UserRolePermission)实体类
 *
 * @author makejava
 * @since 2024-08-25 10:12:47
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class UserRolePermission implements Serializable {
    private static final long serialVersionUID = -66839404076269740L;

    private Long id;
/**
     * 用户id
     */
    private Long userId;
/**
     * 绑定的角色id
     */
    private Long roleId;
/**
     * 附加的权限id（注意此处是拼接成了一个字符串）
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

