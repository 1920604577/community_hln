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
public class SignInUserVo implements Serializable {

    private Long id;
    /**
     * 关联sign表id
     */
    private Long signInId;
    /**
     * 创建人
     */
    private Long createdBy;
    /**
     * 创建人name
     */
    private String createdByText;
    /**
     * 创建时间
     */
    private Date createdTime;

}
