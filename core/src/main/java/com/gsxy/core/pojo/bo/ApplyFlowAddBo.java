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
public class ApplyFlowAddBo implements Serializable {

    @ApiModelProperty(value = "流程名称", example = "流程名称")
    private String name;

}
