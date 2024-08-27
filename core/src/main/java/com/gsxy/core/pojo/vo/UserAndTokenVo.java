package com.gsxy.core.pojo.vo;

import com.gsxy.core.pojo.User;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class UserAndTokenVo implements Serializable {

    private User user;//当前登录用户的所有信息
    private boolean isHaveInfo;//是否完善了个人信息
    private String token;

}
