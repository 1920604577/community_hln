package com.gsxy.core.service;

import com.gsxy.core.pojo.bo.CommunityAddBo;
import com.gsxy.core.pojo.vo.ResponseVo;

public interface CommunityService {
    ResponseVo addCommunity(CommunityAddBo communityAddBo);
}
