package com.gsxy.core.util;

import com.gsxy.core.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginUtils {

    /**
     * @author hln - 2024-8-26
     * 该方法用来获取当前登录用户的id
     * @return
     */
    public static Long getLoginUserId(){
        return Long.valueOf((String) ThreadLocalUtil.mapThreadLocalOfJWT.get().get("userInfo").get("id"));
    }

    /**
     * @author hln - 2024-8-26
     * 该方法用来获取当前登录用户所有权限
     * @return
     */
    public static String getUserPermission(UserMapper userMapper){
        Long loginUserId = getLoginUserId();
        String userPermission = userMapper.queryPermissions(loginUserId);//当前用户附加权限
        String userRolePermission = userMapper.queryRolePermission(loginUserId);//当前用户绑定角色所拥有的权限集合
        return userPermission + userRolePermission;
    }

}
