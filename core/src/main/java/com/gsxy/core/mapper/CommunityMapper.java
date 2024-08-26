package com.gsxy.core.mapper;

import com.gsxy.core.pojo.Community;
import com.gsxy.core.pojo.enums.CommunityStatusEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommunityMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long addCommunity(Community community);

    void updateCommunity(Long id, CommunityStatusEnum communityStatusEnum);

    @Select("select * from community where apply_id = #{id} limit 1")
    Community queryCommunityById(Long id);

    void delete(Long id);

    void updateCommunityInfo(Community community);

    @Select("select * from community where id = #{communityId} limit 1")
    Community queryCommunityByCommunityId(Long communityId);

    void deleteCommunityUser(Long communityId, Long userId);
}
