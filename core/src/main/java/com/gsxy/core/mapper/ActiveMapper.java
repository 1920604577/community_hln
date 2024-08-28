package com.gsxy.core.mapper;

import com.gsxy.core.pojo.Active;
import com.gsxy.core.pojo.enums.ActiveTypeEnum;
import com.gsxy.core.pojo.vo.ActiveVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ActiveMapper {

    @Select("select * from active where id = #{id}")
    Active queryIsHave(Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long add(Active active);

    Long update(Active active);

    Long deleteActive(Long id);

    List<ActiveVo> queryActiveByPage(Long page, Long limit, String title,Long channelId ,ActiveTypeEnum typeEnum);

    Long queryActiveByPageCount(String title, Long channelId, ActiveTypeEnum typeEnum);
}
