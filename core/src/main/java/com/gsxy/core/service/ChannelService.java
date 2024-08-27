package com.gsxy.core.service;

import com.gsxy.core.pojo.bo.ChannelAddBo;
import com.gsxy.core.pojo.vo.ResponseVo;

public interface ChannelService {

    ResponseVo addChannel(ChannelAddBo channelAddBo);

    ResponseVo deleteChannel(Long id);

    ResponseVo queryChannelByPage(Long page, Long limit, Long communityId,String name);
}
