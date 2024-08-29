package com.gsxy.core.pojo.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class FunctionExcelBo {

    @ExcelProperty(value = "姓名" ,index = 0)
    private String name;

    @ExcelProperty(value = "班级" ,index = 1)
    private String org;

    @ExcelProperty(value = "学号" ,index = 2)
    private String stuId;

}
