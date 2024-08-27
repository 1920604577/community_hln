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
public class PermissionVo implements Serializable {

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
     * 创建人name
     */
    private String createdByText;
    /**
     * 创建时间
     */
    private Date createdTime;

}
