package com.gsxy.core.util;

public class LoginUtils {

    public static Long getLoginUserId(){
        return Long.valueOf((String) ThreadLocalUtil.mapThreadLocalOfJWT.get().get("userInfo").get("id"));
    }

}
