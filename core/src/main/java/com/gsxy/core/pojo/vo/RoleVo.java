package com.gsxy.core.pojo.vo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class RoleVo implements Serializable {

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
     * 创建人name
     */
    private String createdByText;
    /**
     * 创建时间
     */
    private Date createdTime;

}
