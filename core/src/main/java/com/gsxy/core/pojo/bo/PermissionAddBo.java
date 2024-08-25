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
public class PermissionAddBo implements Serializable {

    @ApiModelProperty(value = "权限名称", example = "权限名称")
    private String name;

}
