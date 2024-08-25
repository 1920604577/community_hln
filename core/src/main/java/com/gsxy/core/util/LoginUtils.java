package com.gsxy.core.util;

import com.gsxy.core.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginUtils {

    public static Long getLoginUserId(){
        return Long.valueOf((String) ThreadLocalUtil.mapThreadLocalOfJWT.get().get("userInfo").get("id"));
    }

}
