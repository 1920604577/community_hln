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
public class SignInAddBo implements Serializable {

    @ApiModelProperty(value = "签到名称", example = "签到名称")
    private String title;
    @ApiModelProperty(value = "签到内容", example = "签到内容")
    private String message;
    @ApiModelProperty(value = "签到时长", example = "签到时长")
    private Integer timeLen;
    @ApiModelProperty(value = "社团id", example = "社团id")
    private Long communityId;

}
