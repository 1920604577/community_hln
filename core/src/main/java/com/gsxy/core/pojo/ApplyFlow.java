package com.gsxy.core.pojo;

import com.gsxy.core.pojo.enums.ApplyEnum;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 审核流程(ApplyFlow)实体类
 *
 * @author makejava
 * @since 2024-08-25 15:49:57
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ApplyFlow implements Serializable {
    private static final long serialVersionUID = -44470907932782397L;

    private Long id;
/**
     * 流程类型
     */
    private String name;
    /**
     * 流程类型
     */
    private ApplyEnum type;
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

