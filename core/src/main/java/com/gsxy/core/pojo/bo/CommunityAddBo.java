package com.gsxy.core.pojo.bo;

import com.gsxy.core.pojo.enums.CommunityTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class CommunityAddBo implements Serializable {

    @ApiModelProperty(value = "社团名称", example = "社团名称")
    private String name;
    @ApiModelProperty(value = "社团简介", example = "社团简介")
    private String discription;
    @ApiModelProperty(value = "指导教师", example = "指导教师")
    private Long teacherId;
    @ApiModelProperty(value = "社团类型", example = "社团类型")
    private String type;
//    @ApiModelProperty(value = "审核内容", example = "审核内容")
//    private String message;
//    @ApiModelProperty(value = "流程id", example = "流程id")
//    private Long applyFlow;

}
