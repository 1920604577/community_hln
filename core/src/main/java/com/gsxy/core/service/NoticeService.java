package com.gsxy.core.service;

import com.gsxy.core.pojo.bo.NoticeAddBo;
import com.gsxy.core.pojo.vo.ResponseVo;

public interface NoticeService {
    ResponseVo addNotice(NoticeAddBo noticeAddBo);

    ResponseVo queryNoticeByPageLike(Long page, Long limit, Long communityId);
}
