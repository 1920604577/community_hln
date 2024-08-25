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

    private User user;
    private String token;

}
