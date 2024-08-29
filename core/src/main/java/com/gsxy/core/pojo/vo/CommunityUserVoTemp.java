package com.gsxy.core.pojo.vo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class CommunityUserVoTemp {

    private String name;//姓名
    private Integer org;//班级
    private String grade;//年级
    private String professional;//专业
    private String stuId;//学号

}
