package com.gsxy.core.pojo.vo;

import com.gsxy.core.pojo.enums.ApplyEnum;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class ApplyVo implements Serializable {

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
     * 审核状态
     */
    private ApplyEnum status;
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
    /**
     * 修改人
     */
    private Long updatedBy;
    /**
     * 修改人name
     */
    private String updatedByText;
    /**
     * 社团id
     */
    private Long communityId;
    /**
     * 修改时间
     */
    private Date updatedTime;

}
