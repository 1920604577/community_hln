package com.gsxy.core.pojo;

import com.gsxy.core.pojo.enums.ActiveTypeEnum;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动表（该表依附于channel表）(Active)实体类
 *
 * @author makejava
 * @since 2024-08-28 16:49:23
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Active implements Serializable {
    private static final long serialVersionUID = -77549215594873410L;

    private Long id;
/**
     * 活动标题
     */
    private String title;
/**
     * 活动内容
     */
    private String message;
/**
     * 通道id
     */
    private Long channelId;
/**
     * 开始时间
     */
    private Date startTime;
/**
     * 活动时间
     */
    private Date endTime;
    /**
     * 附件文件路径
     */
    private String filePath;
/**
     * 创建时间
     */
    private Date createdTime;
/**
     * 创建人
     */
    private Long createdBy;
/**
     * 修改时间
     */
    private Date updatedTime;
/**
     * 修改人
     */
    private Long updatedBy;
/**
     * 类型（预留字段）
     */
    private ActiveTypeEnum type;
/**
     * 逻辑删除（0：存在，1：删除）
     */
    private Integer delFlag;

}

