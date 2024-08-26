package com.gsxy.core.mapper;

import com.gsxy.core.pojo.Apply;
import com.gsxy.core.pojo.enums.ApplyEnum;
import com.gsxy.core.pojo.vo.ApplyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ApplyMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long addApply(Apply apply);

    List<ApplyVo> queryApply(ApplyEnum type, Long page, Long limit);

    void updateApply(Long id, ApplyEnum applyEnum);

    List<ApplyVo> queryApplyByCommunityId(ApplyEnum applyEnum, Long page, Long limit, Long communityId);

    @Select("select * from apply where id = #{id}")
    Apply queryApplyById(Long id);

    Long queryApplyCount(ApplyEnum applyEnum, Long page, Long limit);

    Long queryApplyByCommunityIdCount(ApplyEnum applyEnum, Long page, Long limit, Long communityId);
}
