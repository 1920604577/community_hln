package com.gsxy.core.mapper;

import com.gsxy.core.pojo.SignIn;
import com.gsxy.core.pojo.SignInUser;
import com.gsxy.core.pojo.vo.SignInUserVo;
import com.gsxy.core.pojo.vo.SignInVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.Arrays;
import java.util.List;

@Mapper
public interface SignInMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long add(SignIn signIn);

    List<SignInVo> queryAllByPage(Long page, Long limit, Long communityId);

    Long queryAllByPageCount(Long communityId);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long signIn(SignInUser signInUser);

    List<SignInUserVo> querySignInUserBySignInId(Long signInId);

    Long querySignInUserBySignInIdCount(Long signInId);

    @Select("select * from sign_in where id = #{signInId} limit 1")
    SignIn qeuryById(Long signInId);
}
