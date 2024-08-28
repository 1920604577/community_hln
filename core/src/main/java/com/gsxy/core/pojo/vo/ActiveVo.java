package com.gsxy.core.pojo.vo;

import com.gsxy.core.pojo.enums.ActiveTypeEnum;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class ActiveVo implements Serializable {

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
     * 创建人name
     */
    private Long createdByText;
    /**
     * 修改时间
     */
    private Date updatedTime;
    /**
     * 修改人
     */
    private Long updatedBy;
    /**
     * 修改人name
     */
    private Long updatedByText;
    /**
     * 类型（预留字段）
     */
    private ActiveTypeEnum type;

}
