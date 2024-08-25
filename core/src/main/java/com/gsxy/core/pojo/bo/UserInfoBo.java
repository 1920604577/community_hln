package com.gsxy.core.pojo.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class UserInfoBo implements Serializable {

    @ApiModelProperty(value = "姓名", example = "姓名")
    private String name;
    @ApiModelProperty(value = "学院", example = "学院")
    private Integer college;
    @ApiModelProperty(value = "班级", example = "班级")
    private Integer org;
    @ApiModelProperty(value = "年级", example = "年级")
    private String grade;
    @ApiModelProperty(value = "专业", example = "专业")
    private String professional;
    @ApiModelProperty(value = "年龄", example = "年龄")
    private Integer age;
    @ApiModelProperty(value = "生日", example = "生日")
    private Date birthday;

}
