package com.gsxy.core.service;

import com.gsxy.core.pojo.bo.CommunityAddBo;
import com.gsxy.core.pojo.bo.CommunityUpdateBo;
import com.gsxy.core.pojo.vo.ResponseVo;

public interface CommunityService {
    ResponseVo addCommunity(CommunityAddBo communityAddBo);

    ResponseVo updateCommunity(CommunityUpdateBo communityUpdateBo);

    ResponseVo deleteCommunity(Long id);

    ResponseVo joinCommunity(Long communityId);

    ResponseVo quitCommunity(Long communityId);

    ResponseVo queryUserByCommunity(Long communityId);
}
