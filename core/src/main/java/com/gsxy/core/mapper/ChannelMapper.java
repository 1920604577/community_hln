package com.gsxy.core.mapper;

import com.gsxy.core.pojo.Channel;
import com.gsxy.core.pojo.vo.ChannelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChannelMapper {

    @Select("select * from channel where id = #{id} and del_flag = 0 limit 1")
    Channel queryIsHave(Long id);

    Long add(Channel build);

    Long update(Channel build);

    Long delete(Long id);

    List<ChannelVo> queryChannelLike(Long page, Long limit, Long communityId, String name);

    Long queryCommunityCount(Long communityId, String name);
}
