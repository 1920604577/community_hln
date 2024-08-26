package com.gsxy.core.mapper;

import com.gsxy.core.pojo.User;
import com.gsxy.core.pojo.UserInfo;
import com.gsxy.core.pojo.UserRolePermission;
import com.gsxy.core.pojo.bo.UserLoginBo;
import com.gsxy.core.pojo.bo.UserRegBo;
import com.gsxy.core.pojo.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select id from user where student_id = #{studentId}")
    Long isUserReg(UserRegBo userRegBo);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long userReg(User user);

    @Select("select * from user where username = #{username}")
    User userLogin(UserLoginBo userLoginBo);

    UserVo queryUser(String number);

    /**
     * 获取user基础信息
     * @param loginUserId
     * @return
     */
    @Select("select * from user where id = #{loginUserId}")
    User queryUserById(Long loginUserId);

    @Select("select * from user_info where id = #{studentId}")
    UserInfo queryIsHave(String studentId);

    Long addUserInfo(UserInfo userInfo);

    Long updateUserInfo(UserInfo userInfo);

    String queryPermissions(Long loginUserId);

    String queryRolePermission(Long loginUserId);

    @Select("select * from user_role_permission where user_id = #{loginUserId} limit 1")
    UserRolePermission queryUserRoleId(Long loginUserId);
}
