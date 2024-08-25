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
public class RoleUpdateBo implements Serializable {

    @ApiModelProperty(value = "角色名称", example = "角色名称")
    private String name;
    @ApiModelProperty(value = "权限id（集合 -》 拼成一个字符串给我）", example = "权限id（集合 -》 拼成一个字符串给我）")
    private String permission;

}
