package com.gsxy.core.mapper;

import com.gsxy.core.pojo.User;
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
}
