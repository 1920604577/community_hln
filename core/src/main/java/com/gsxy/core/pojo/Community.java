package com.gsxy.core.pojo;

import com.gsxy.core.pojo.enums.CommunityStatusEnum;
import com.gsxy.core.pojo.enums.CommunityTypeEnum;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 社团表(Community)实体类
 *
 * @author makejava
 * @since 2024-08-25 16:14:19
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Community implements Serializable {
    private static final long serialVersionUID = -96369906739051157L;

    private Long id;
    /**
     * 绑定的apply_id
     */
    private Long applyId;
/**
     * 社团名称
     */
    private String name;
/**
     * 社团简介
     */
    private String discription;
/**
     * 指导教师id
     */
    private Long teacherId;
/**
     * 预留字段
     */
    private String remark;
    /**
     * 类型
     */
    private CommunityTypeEnum type;
    /**
     * 状态（启用/禁用）
     */
    private CommunityStatusEnum status;
/**
     * 社长id
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

