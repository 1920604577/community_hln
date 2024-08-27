package com.gsxy.core.mapper;

import com.gsxy.core.pojo.Community;
import com.gsxy.core.pojo.enums.CommunityStatusEnum;
import com.gsxy.core.pojo.vo.CommunityVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommunityMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long addCommunity(Community community);

    void updateCommunity(Long id, CommunityStatusEnum communityStatusEnum);

    @Select("select * from community where apply_id = #{id} limit 1")
    Community queryCommunityById(Long id);

    void delete(Long id);

    Long updateCommunityInfo(Community community);

    @Select("select * from community where id = #{communityId} limit 1")
    Community queryCommunityByCommunityId(Long communityId);

    void deleteCommunityUser(Long communityId, Long userId);

    List<CommunityVo> queryCommunityLike(Long page, Long limit, String name, CommunityStatusEnum communityStatusEnum);

    Long queryCommunityCount(CommunityStatusEnum communityStatusEnum, String name);
}
