package com.gsxy.core.pojo.vo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class UserVo implements Serializable {

    private Long id;//userId
    private String username;//用户名
    private String name;//姓名
    private Integer college;//学院
    private Integer org;//班级
    private String grade;//年级
    private String professional;//专业
    private Long createdBy;//创建人
    private String createdByText;//创建人name
    private Date createdTime;//创建时间
    private Long updateBy;//修改人
    private String updateByText;//修改人name
    private Date updateTime;//修改时间
    private String cardCode;//身份证号

}
