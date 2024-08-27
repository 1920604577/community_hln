package com.gsxy.core.pojo.vo;

import com.gsxy.core.pojo.enums.CommunityTypeEnum;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class CommunityVo implements Serializable {

    private Long id;//社团id
    private String name;//社团名称
    private Long teacherId;//指导老师id
    private String teacherName;//指导老师name
    private CommunityTypeEnum type;//社团类型enum
    private String typeText;//社团类型
    private String discription;//社团介绍
    private String phone;//社团负责人联系方式
    private Long createdBy;//社长id
    private String createdByName;//社长name

}
