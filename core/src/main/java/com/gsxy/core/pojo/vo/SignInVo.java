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
public class SignInVo implements Serializable {

    private Long id;
    /**
     * 签到标题
     */
    private String title;
    /**
     * 签到内容
     */
    private String message;
    /**
     * 社团id
     */
    private Long communityId;
    /**
     * 持续时长
     */
    private Integer timeLen;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 创建人
     */
    private Long creaetdBy;
    /**
     * 创建人name
     */
    private String creaetdByText;

}
