package com.gsxy.core.service.impl;

import com.gsxy.core.mapper.SignInMapper;
import com.gsxy.core.mapper.UserMapper;
import com.gsxy.core.pojo.SignIn;
import com.gsxy.core.pojo.SignInUser;
import com.gsxy.core.pojo.bo.NoticeAddAllBo;
import com.gsxy.core.pojo.bo.SignInAddBo;
import com.gsxy.core.pojo.vo.ResponseVo;
import com.gsxy.core.pojo.vo.SignInUserVo;
import com.gsxy.core.pojo.vo.SignInVo;
import com.gsxy.core.service.SignInService;
import com.gsxy.core.util.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.gsxy.core.pojo.enums.CodeValues.*;
import static com.gsxy.core.pojo.enums.MessageValues.*;
import static com.gsxy.core.pojo.enums.RedisKeys.SIGN_IN_KEY;
import static org.springframework.beans.MethodInvocationException.ERROR_CODE;

@Service
public class SignInServiceImpl implements SignInService {

    @Autowired
    private SignInMapper signInMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private NoticeServiceImpl noticeServiceImpl;

    @Override
    @Transactional
    public ResponseVo add(SignInAddBo signInAddBo) {

        Long loginUserId = LoginUtils.getLoginUserId();
        SignIn signIn = SignIn.builder()
                .timeLen(signInAddBo.getTimeLen())
                .title(signInAddBo.getTitle())
                .communityId(signInAddBo.getCommunityId())
                .message(signInAddBo.getMessage())
                .createdBy(loginUserId)
                .createdTime(new Date())
                .build();
        Long isSuccess = signInMapper.add(signIn);

        if (!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L) {
            return ResponseVo.builder()
                    .code(ERROR_CODE)
                    .data(null)
                    .message(ERROR_MESSAGE)
                    .build();
        }

        //流程都没有问题，发通知以及存入redis sign_in_id -> 注意：我的这个方法是群发
        noticeServiceImpl.addAllNotice(
                NoticeAddAllBo.builder()
                        .title("签到")
                        .message("您有新的签到信息请在签到列表中查看~")
                        .communityId(signIn.getCommunityId())
                        .build()
        );

        //存入redis 这里我选择 str类型作为存储类型 因为有时间倒计时业务（到达指定时长后自动删除）
        // 注意：此处代码不要动
        String key = SIGN_IN_KEY + signIn.getId();
        redisTemplate.opsForValue().set(key,signIn.getId(),signInAddBo.getTimeLen(), TimeUnit.MINUTES);

        return ResponseVo.builder()
                .data(null)
                .code(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    @Override
    public ResponseVo queryAllByPage(Long page, Long limit, Long communityId) {

        Long loginUserId = LoginUtils.getLoginUserId();
        List<SignInVo> signInVoLists = signInMapper.queryAllByPage(page, limit, communityId)
                .stream().map(vo -> {
                    vo.setCreaetdByText(userMapper.queryInfoById(userMapper.queryUserById(loginUserId).getStudentId()).getName());
                    return vo;
                }).collect(Collectors.toList());
        Long count = signInMapper.queryAllByPageCount(communityId);

        return ResponseVo.builder()
                .data(signInVoLists)
                .count(count)
                .code(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    @Override
    @Transactional
    public ResponseVo signIn(Long signInId, String message) {

        Long loginUserId = LoginUtils.getLoginUserId();
        //签到
        String key = SIGN_IN_KEY + signInId;
        Object val = redisTemplate.opsForValue().get(key);

        //判断是否过期
        if(ObjectUtils.isEmpty(val))
            return ResponseVo.builder()
                    .data(null)
                    .code(REDIS_TIME_OUT_ERROR_CODE)
                    .message(REDIS_TIME_OUT_ERROR_MESSAGE)
                    .build();

        //判断内容是否匹配
        if(!message.equals(signInMapper.qeuryById(signInId).getMessage()))
            return ResponseVo.builder()
                    .data(null)
                    .message(SIGN_IN_MESSAGE_ERROR_MESSAGE)
                    .code(SIGN_IN_MESSAGE_ERROR_CODE)
                    .build();

        //如果上述流程都成功，注入信息到sign_in_user表中
        SignInUser signInUser = SignInUser.builder()
                .signInId(signInId)
                .createdBy(loginUserId)
                .createdTime(new Date())
                .signInId(Long.valueOf(String.valueOf(val)))
                .build();

        Long isSuccess = signInMapper.signIn(signInUser);

        if (!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L) {
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
    public ResponseVo querySignInUserBySignInId(Long signInId) {

        Long loginUserId = LoginUtils.getLoginUserId();
        List<SignInUserVo> signInUserVoList = signInMapper.querySignInUserBySignInId(signInId)
                .stream().map(vo -> {
                    vo.setCreatedByText(userMapper.queryInfoById(userMapper.queryUserById(loginUserId).getStudentId()).getName());
                    return vo;
                }).collect(Collectors.toList());
        Long count = signInMapper.querySignInUserBySignInIdCount(signInId);

        return ResponseVo.builder()
                .data(signInUserVoList)
                .count(count)
                .code(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                .build();
    }
}
