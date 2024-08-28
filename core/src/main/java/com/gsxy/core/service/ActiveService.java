package com.gsxy.core.service;

import com.gsxy.core.pojo.bo.ActiveAddBo;
import com.gsxy.core.pojo.vo.ResponseVo;

public interface ActiveService {

    ResponseVo addActive(ActiveAddBo activeAddBo);

    ResponseVo deleteActive(Long id);

    ResponseVo queryActiveByPage(Long page, Long limit, String title,Long channelId,String type);
}
