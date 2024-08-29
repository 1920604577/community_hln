package com.gsxy.core.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class CommunityUserVo implements Serializable {

    private String name;//姓名
    private String org;//这里是拼接后的 即年级 + 专业 + 班级
    private String stuId;//学号

}
