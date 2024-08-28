package com.gsxy.core.service.impl;

import com.gsxy.core.mapper.CommunityUserMapper;
import com.gsxy.core.mapper.NoticeMapper;
import com.gsxy.core.mapper.UserMapper;
import com.gsxy.core.pojo.CommunityUser;
import com.gsxy.core.pojo.Notice;
import com.gsxy.core.pojo.User;
import com.gsxy.core.pojo.UserRolePermission;
import com.gsxy.core.pojo.bo.NoticeAddAllBo;
import com.gsxy.core.pojo.bo.NoticeAddBo;
import com.gsxy.core.pojo.enums.NoticeTypeEnum;
import com.gsxy.core.pojo.vo.NoticeVo;
import com.gsxy.core.pojo.vo.ResponseVo;
import com.gsxy.core.service.NoticeService;
import com.gsxy.core.util.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.gsxy.core.pojo.enums.CodeValues.SUCCESS_CODE;
import static com.gsxy.core.pojo.enums.MessageValues.ERROR_MESSAGE;
import static com.gsxy.core.pojo.enums.MessageValues.SUCCESS_MESSAGE;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommunityUserMapper communityUserMapper;

    @Override
    public ResponseVo addNotice(NoticeAddBo noticeAddBo) {

        Long loginUserId = LoginUtils.getLoginUserId();

        Long isSuccess = noticeMapper.addNotice(
                Notice.builder()
                        .title(noticeAddBo.getTitle())
                        .createdTime(new Date())
                        .createdBy(loginUserId)
                        .sendUserId(loginUserId)
                        .communityId(noticeAddBo.getCommunityId())
                        .message(noticeAddBo.getMessage() == null ? null : noticeAddBo.getMessage())
                        .type(noticeAddBo.getType())
                        .receiveUserId(noticeAddBo.getReceiveUserId())
                        .build()
        );

        if(!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L){
            return ResponseVo.builder()
                    .code(SUCCESS_MESSAGE)
                    .data(null)
                    .message(ERROR_MESSAGE)
                    .build();
        }

        return ResponseVo.builder()
                .data(null)
                .code(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    @Override
    public ResponseVo queryNoticeByPageLike(Long page, Long limit, Long communityId,String type) {

        page = (page - 1) * limit;
        Long loginUserId = LoginUtils.getLoginUserId();
        List<NoticeVo> noticeVoLists = null;
        UserRolePermission userRolePermission = userMapper.queryUserRoleId(loginUserId);
        Long count = null;

        if(type.equals("ALL")){
            noticeVoLists = noticeMapper.queryNoticeByPageLike(page,limit,communityId,loginUserId, NoticeTypeEnum.ALL);
        } else {
            if (userRolePermission.getRoleId() == 1L) {
                noticeVoLists = noticeMapper.queryNoticeByPageLike(page,limit,communityId,loginUserId, NoticeTypeEnum.ADMIN)
                        .stream().map(notice -> {
                            String sendUserIdText = userMapper.queryInfoById(userMapper.queryUserById(notice.getCreatedBy()).getStudentId()).getName();
                            notice.setSendUserIdText(sendUserIdText);
                            notice.setCreatedByText(sendUserIdText);
                            notice.setUpdatedByText(notice.getUpdatedBy() == null ? "" : userMapper.queryInfoById(userMapper.queryUserById(notice.getUpdatedBy()).getStudentId()).getName());
                            return notice;
                        }).collect(Collectors.toList());
                count = noticeMapper.queryNoticeByPageLikeCount(communityId,loginUserId, NoticeTypeEnum.ADMIN);
            } else if (userRolePermission.getRoleId() == 3L) {
                noticeVoLists = noticeMapper.queryNoticeByPageLikeCommunity(page,limit,communityId,loginUserId, NoticeTypeEnum.COMMUNITY)
                        .stream().map(notice -> {
                            String sendUserIdText = userMapper.queryInfoById(userMapper.queryUserById(notice.getCreatedBy()).getStudentId()).getName();
                            notice.setSendUserIdText(sendUserIdText);
                            notice.setCreatedByText(sendUserIdText);
                            notice.setUpdatedByText(notice.getUpdatedBy() == null ? "" : userMapper.queryInfoById(userMapper.queryUserById(notice.getUpdatedBy()).getStudentId()).getName());
                            return notice;
                        }).collect(Collectors.toList());
                count = noticeMapper.queryNoticeByPageLikeCommunityCount(communityId,loginUserId, NoticeTypeEnum.COMMUNITY);
            }
        }

        return ResponseVo.builder()
                .data(noticeVoLists)
                .code(SUCCESS_CODE)
                .count(count)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    @Override
    @Transactional
    public ResponseVo addAllNotice(NoticeAddAllBo noticeAddAllBo) {

        Long loginUserId = LoginUtils.getLoginUserId();
        UserRolePermission userRolePermission = userMapper.queryUserRoleId(loginUserId);

        if(userRolePermission.getRoleId() == 1L){
            List<User> userList = userMapper.queryAll(loginUserId);
            userList.forEach(user -> {
                Long isSuccess = noticeMapper.addNotice(
                        Notice.builder()
                                .title(noticeAddAllBo.getTitle())
                                .createdTime(new Date())
                                .createdBy(loginUserId)
                                .sendUserId(loginUserId)
                                .receiveUserId(user.getId())
                                .message(noticeAddAllBo.getMessage() == null ? null : noticeAddAllBo.getMessage())
                                .type(NoticeTypeEnum.ALL)
                                .build()
                );
            });
        } else if (userRolePermission.getRoleId() == 3L) {
            List<CommunityUser> communityUserList = communityUserMapper.queryUserIdByCommunityId(noticeAddAllBo.getCommunityId(),loginUserId);
            communityUserList.forEach(user -> {
                Long isSuccess = noticeMapper.addNotice(
                        Notice.builder()
                                .title(noticeAddAllBo.getTitle())
                                .createdTime(new Date())
                                .createdBy(loginUserId)
                                .sendUserId(loginUserId)
                                .communityId(noticeAddAllBo.getCommunityId())
                                .receiveUserId(user.getId())
                                .message(noticeAddAllBo.getMessage() == null ? null : noticeAddAllBo.getMessage())
                                .type(NoticeTypeEnum.COMMUNITY)
                                .build()
                );
            });
        }

        return ResponseVo.builder()
                .data(null)
                .code(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                .build();
    }


}
