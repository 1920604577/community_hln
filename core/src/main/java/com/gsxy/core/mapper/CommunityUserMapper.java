package com.gsxy.core.mapper;

import com.gsxy.core.pojo.CommunityUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommunityUserMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void add(CommunityUser communityUser);

    @Select("select * from community_user where user_id = #{userId} and community_id = #{communityId} and del_flag = 0 limit 1")
    CommunityUser qeuryUserByCommunityIdAndUserId(Long userId, Long communityId);
}
