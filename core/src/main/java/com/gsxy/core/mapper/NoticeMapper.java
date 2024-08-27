package com.gsxy.core.mapper;

import com.gsxy.core.pojo.Notice;
import com.gsxy.core.pojo.enums.NoticeTypeEnum;
import com.gsxy.core.pojo.vo.NoticeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;
import java.util.List;

@Mapper
public interface NoticeMapper {
    Long addNotice(Notice build);

    List<NoticeVo> queryNoticeByPageLike(Long page, Long limit, Long communityId, Long receiveUserId, NoticeTypeEnum typeEnum);

    List<NoticeVo> queryNoticeByPageLikeCommunity(Long page, Long limit, Long communityId, Long receiveUserId, NoticeTypeEnum typeEnum);

    Long queryNoticeByPageLikeCount(Long communityId, Long receiveUserId, NoticeTypeEnum typeEnum);

    Long queryNoticeByPageLikeCommunityCount(Long communityId, Long receiveUserId, NoticeTypeEnum typeEnum);
}
