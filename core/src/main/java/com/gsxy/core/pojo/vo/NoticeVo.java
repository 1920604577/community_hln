package com.gsxy.core.pojo.vo;

import com.gsxy.core.pojo.enums.NoticeTypeEnum;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class NoticeVo implements Serializable {

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
     * 发起人name
     */
    private String sendUserIdText;
    /**
     * 接收人id
     */
    private Long receiveUserId;
    /**
     * 接收人name
     */
    private String receiveUserIdText;
    /**
     * 创建者
     */
    private Long createdBy;
    /**
     * 创建者name
     */
    private String createdByText;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 修改者
     */
    private Long updatedBy;
    /**
     * 修改者
     */
    private String updatedByText;
    /**
     * 修改时间
     */
    private Date updatedTime;

}
