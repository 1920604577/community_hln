package com.gsxy.core.pojo;

import com.gsxy.core.pojo.enums.ApplyEnum;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 审核表(Apply)实体类
 *
 * @author makejava
 * @since 2024-08-25 15:50:20
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Apply implements Serializable {
    private static final long serialVersionUID = -85603374904177298L;

    private Long id;
/**
     * 内容
     */
    private String message;
/**
     * 审核流程
     */
    private Long applyFlow;
    /**
     * 社团id
     */
    private Long communityId;
/**
     * 审核状态
     */
    private ApplyEnum status;
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

