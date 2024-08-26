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
public class CommunityUserInfoVo implements Serializable {

    private Long id;
    /**
     * 社团id（关联）
     */
    private Long communityId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户name
     */
    private String name;
    /**
     * 用户phone
     */
    private String phone;

}
