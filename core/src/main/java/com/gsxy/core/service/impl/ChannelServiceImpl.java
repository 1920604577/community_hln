package com.gsxy.core.service.impl;

import com.gsxy.core.mapper.ChannelMapper;
import com.gsxy.core.mapper.UserMapper;
import com.gsxy.core.pojo.Channel;
import com.gsxy.core.pojo.UserInfo;
import com.gsxy.core.pojo.UserRolePermission;
import com.gsxy.core.pojo.bo.ChannelAddBo;
import com.gsxy.core.pojo.vo.ChannelVo;
import com.gsxy.core.pojo.vo.ResponseVo;
import com.gsxy.core.service.ChannelService;
import com.gsxy.core.util.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.gsxy.core.pojo.enums.CodeValues.*;
import static com.gsxy.core.pojo.enums.MessageValues.*;

@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelMapper channelMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseVo addChannel(ChannelAddBo channelAddBo) {

        Long loginUserId = LoginUtils.getLoginUserId();
        UserRolePermission userRolePermission = userMapper.queryUserRoleId(loginUserId);

        //鉴权
        if(userRolePermission.getRoleId() != 3L){
            return ResponseVo.builder()
                    .code(ERROR_ROLE_CODE)
                    .message(ERROR_ROLE_MESSAGE)
                    .data(null)
                    .build();
        }

        Long isSuccess = null;
        //看看用户信息是否已经存在
        Channel channel = channelMapper.queryIsHave(channelAddBo.getId());
        if (ObjectUtils.isEmpty(channel)) {
            isSuccess = channelMapper.add(
                    Channel.builder()
                            .name(channelAddBo.getName())
                            .createdTime(new Date())
                            .createdBy(loginUserId)
                            .communityId(channelAddBo.getCommunityId())
                            .build()
            );
        } else {
            isSuccess = channelMapper.update(
                    Channel.builder()
                            .id(channel.getId())
                            .updatedBy(loginUserId)
                            .updatedTime(new Date())
                            .name(channelAddBo.getName() == null ? null : channelAddBo.getName())
                            .build()
            );
        }

        if(!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L){
            return ResponseVo.builder()
                    .code(ERROR_CODE)
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
    public ResponseVo deleteChannel(Long id) {

        //鉴权
        Long loginUserId = LoginUtils.getLoginUserId();
        UserRolePermission userRolePermission = userMapper.queryUserRoleId(loginUserId);
        if(userRolePermission.getRoleId() != 3L){
            return ResponseVo.builder()
                    .code(ERROR_ROLE_CODE)
                    .message(ERROR_ROLE_MESSAGE)
                    .data(null)
                    .build();
        }

        Long isSuccess = channelMapper.delete(id);

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
    public ResponseVo queryChannelByPage(Long page, Long limit, Long communityId,String name) {

        page = (page - 1) * limit;
        List<ChannelVo> channelVoLists = channelMapper.queryChannelLike(page,limit,communityId,name)
                .stream().map(
                        channel -> {
                            channel.setCreatedByText(userMapper.queryUser(userMapper.queryUserById(channel.getCreatedBy()).getStudentId()).getName());
                            channel.setUpdatedByText(userMapper.queryUser(userMapper.queryUserById(channel.getUpdatedBy()).getStudentId()).getName());
                            return channel;
                        }
                ).collect(Collectors.toList());
        Long count = channelMapper.queryCommunityCount(communityId,name);

        return ResponseVo.builder()
                .data(channelVoLists)
                .count(count)
                .code(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                .build();
    }


}
