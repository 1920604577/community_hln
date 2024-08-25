package com.gsxy.core.mapper;

import com.gsxy.core.pojo.ApplyFlow;
import com.gsxy.core.pojo.vo.ApplyFlowVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ApplyFlowMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long addApplyFlow(ApplyFlow applyFlow);

    Long deleteApplyFlow(Long id);

    @Select("select * from apply_flow where del_flag = 0")
    List<ApplyFlowVo> queryApplyFlow();
}
