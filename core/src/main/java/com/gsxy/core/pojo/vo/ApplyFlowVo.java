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
public class ApplyFlowVo implements Serializable {

    private String name;//流程类型（名称）
    private Long id;//流程id
    private Long createdBy;//创建人
    private Date createdTime;//创建时间
    private Long updatedBy;//更新人
    private Date updatedTime;//更新时间

}
