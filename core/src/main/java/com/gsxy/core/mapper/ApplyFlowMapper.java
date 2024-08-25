package com.gsxy.core.mapper;

import com.gsxy.core.pojo.ApplyFlow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface ApplyFlowMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long addApplyFlow(ApplyFlow applyFlow);

    Long deleteApplyFlow(Long id);
}
