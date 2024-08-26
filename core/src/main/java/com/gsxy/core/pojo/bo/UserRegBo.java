package com.gsxy.core.pojo.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class UserRegBo implements Serializable {

    @ApiModelProperty(value = "用户名", example = "用户名")
    private String username;
    @ApiModelProperty(value = "密码", example = "密码")
    private String passwd;
    @ApiModelProperty(value = "学号", example = "学号")
    private String studentId;
    @ApiModelProperty(value = "注册类型（学生/老师）", example = "注册类型（学生/老师）")
    private String regType;
    @ApiModelProperty(value = "确认密码", example = "确认密码")
    private String confirmPassword;

}
