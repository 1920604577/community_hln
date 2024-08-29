package com.gsxy.core.service;

import com.gsxy.core.pojo.bo.SignInAddBo;
import com.gsxy.core.pojo.vo.ResponseVo;

public interface SignInService {

    ResponseVo add(SignInAddBo signInAddBo);

    ResponseVo queryAllByPage(Long page, Long limit, Long communityId);

    ResponseVo signIn(Long signInId, String message);

    ResponseVo querySignInUserBySignInId(Long signInId);
}
