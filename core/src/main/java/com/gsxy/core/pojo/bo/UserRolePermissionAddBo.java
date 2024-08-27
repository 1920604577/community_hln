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
public class UserRolePermissionAddBo implements Serializable {

    @ApiModelProperty(value = "用户id", example = "用户id")
    private Long userId;
    @ApiModelProperty(value = "角色id", example = "角色id")
    private Long roleId;
    @ApiModelProperty(value = "社团id", example = "社团id")
    private Long communityId;
    @ApiModelProperty(value = "权限id集合", example = "权限id集合")
    private String permission;

}
