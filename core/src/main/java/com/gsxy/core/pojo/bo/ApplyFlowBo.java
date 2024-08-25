package com.gsxy.core.pojo.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class ApplyFlowBo implements Serializable {

    @ApiModelProperty(value = "审核内容", example = "审核内容")
    private String message;
    @ApiModelProperty(value = "审核流程id", example = "审核流程id")
    private Long applyFlow;

}
