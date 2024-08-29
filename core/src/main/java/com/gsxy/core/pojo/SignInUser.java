package com.gsxy.core.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户签到表（注意：该表与sign_in表建立了外键，如果sign_in的数据被删除那sign_in_user对应的数据也会被删除）(SignInUser)实体类
 *
 * @author makejava
 * @since 2024-08-29 09:33:07
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class SignInUser implements Serializable {
    private static final long serialVersionUID = -47774826978229434L;

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
     * 创建时间
     */
    private Date createdTime;
/**
     * 逻辑删除（0：存在，1：删除）
     */
    private Integer delFlag;

}

