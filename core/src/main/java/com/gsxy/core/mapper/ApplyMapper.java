package com.gsxy.core.mapper;

import com.gsxy.core.pojo.Apply;
import com.gsxy.core.pojo.enums.ApplyEnum;
import com.gsxy.core.pojo.vo.ApplyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface ApplyMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long addApply(Apply apply);

    List<ApplyVo> queryApply(String type, Long page, Long limit);

    void updateApply(Long id, ApplyEnum applyEnum);
}
