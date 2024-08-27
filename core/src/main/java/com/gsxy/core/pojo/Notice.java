package com.gsxy.core.pojo;

import com.gsxy.core.pojo.enums.NoticeTypeEnum;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 通知表(Notice)实体类
 *
 * @author makejava
 * @since 2024-08-27 14:40:45
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Notice implements Serializable {
    private static final long serialVersionUID = 228639351053966692L;

    private Long id;
/**
     * 标题
     */
    private String title;
/**
     * 内容
     */
    private String message;
/**
     * 枚举类型（预留）
     */
    private NoticeTypeEnum type;
/**
     * 发起人id
     */
    private Long sendUserId;
    /**
     * 社团id
     */
    private Long communityId;
/**
     * 接收人id
     */
    private Long receiveUserId;
/**
     * 创建者
     */
    private Long createdBy;
/**
     * 创建时间
     */
    private Date createdTime;
/**
     * 修改者
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

