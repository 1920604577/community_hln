package com.gsxy.core.pojo.vo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class ChannelVo implements Serializable {

    private Long id;//通道id
    private String name;//通道名称
    private Long createdBy;//创建者id
    private String createdByText;//创建者姓名
    private Long updatedBy;//更新者id
    private String updatedByText;//更新者姓名
    private Date createdTime;//创建时间
    private Date updatedTime;//更新时间

}
