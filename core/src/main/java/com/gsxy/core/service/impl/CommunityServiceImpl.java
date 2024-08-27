package com.gsxy.core.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.gsxy.core.mapper.ApplyMapper;
import com.gsxy.core.mapper.CommunityMapper;
import com.gsxy.core.mapper.CommunityUserMapper;
import com.gsxy.core.mapper.UserMapper;
import com.gsxy.core.pojo.Apply;
import com.gsxy.core.pojo.Community;
import com.gsxy.core.pojo.CommunityUser;
import com.gsxy.core.pojo.UserRolePermission;
import com.gsxy.core.pojo.bo.CommunityAddBo;
import com.gsxy.core.pojo.bo.CommunityUpdateBo;
import com.gsxy.core.pojo.enums.CommunityTypeEnum;
import com.gsxy.core.pojo.vo.CommunityUserInfoVo;
import com.gsxy.core.pojo.vo.ResponseVo;
import com.gsxy.core.service.CommunityService;
import com.gsxy.core.util.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private CommunityUserMapper communityUserMapper;
    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional
    public ResponseVo addCommunity(CommunityAddBo communityAddBo) {

        Long loginUserId = LoginUtils.getLoginUserId();

        //创建审核申请
        Apply apply = Apply.builder()
                .applyFlow(1L)
                .message("创建社团")
                .createdBy(loginUserId)
                .communityId(null)
                .createdTime(new Date())
                .build();
        Long isSuccess = applyMapper.addApply(apply);

        if (!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L){
            return ResponseVo.builder()
                    .code("500")
                    .data(null)
                    .message("创建社团申请提交失败")
                    .build();
        }

        //若申请成功提交则，核对社团类型转化为enum
        CommunityTypeEnum communityTypeEnum = null;
        if (communityAddBo.getType().equals("ORDINARY"))
            communityTypeEnum = CommunityTypeEnum.ORDINARY;
        else if (communityAddBo.getType().equals("PROFESSIONAL"))
            communityTypeEnum = CommunityTypeEnum.PROFESSIONAL;

        //创建社团初始数据（初始状态是DISABLE禁用）
        Community community = Community.builder()
                .name(communityAddBo.getName())
                .applyId(apply.getId())
                .createdBy(loginUserId)
                .createdTime(new Date())
                .discription(communityAddBo.getDiscription())
                .teacherId(communityAddBo.getTeacherId())
                .type(communityTypeEnum)
                .build();

        Long isSuccess2 = communityMapper.addCommunity(community);

        if (!ObjectUtils.isEmpty(isSuccess2) && isSuccess2 == 0L){
            return ResponseVo.builder()
                    .code("500")
                    .data(null)
                    .message("初始化社团数据失败")
                    .build();
        }

        return ResponseVo.builder()
                .code("200")
                .data(null)
                .message("创建社团申请已提交")
                .build();
    }

    @Override
    @Transactional
    public ResponseVo updateCommunity(CommunityUpdateBo communityUpdateBo) {

        //验证当前操作用户是否是社长
        Long loginUserId = LoginUtils.getLoginUserId();
        String permissions = LoginUtils.getUserPermission(userMapper);

        if(permissions.contains("1")){

            CommunityTypeEnum communityTypeEnum = null;
            if(!ObjectUtils.isEmpty(communityUpdateBo.getType()) && communityUpdateBo.getType().equals("ORDINARY")){
                communityTypeEnum = CommunityTypeEnum.ORDINARY;
            } else if (!ObjectUtils.isEmpty(communityUpdateBo.getType()) && communityUpdateBo.getType().equals("PROFESSIONAL")) {
                communityTypeEnum = CommunityTypeEnum.PROFESSIONAL;
            }
            Community community = Community.builder()
                    .type(communityTypeEnum)
                    .name(communityUpdateBo.getName() == null ? null : communityUpdateBo.getName())
                    .teacherId(communityUpdateBo.getTeacherId() == null ? null : communityUpdateBo.getTeacherId())
                    .discription(communityUpdateBo.getDiscription() == null ? null : communityUpdateBo.getDiscription())
                    .createdBy(communityUpdateBo.getCreatedBy() == null ? null : communityUpdateBo.getCreatedBy())
                    .build();
            communityMapper.updateCommunityInfo(community);

            return ResponseVo.builder()
                    .code("200")
                    .data(null)
                    .message("修改社团信息成功")
                    .build();
        }

        if(!permissions.contains("3")){
            return ResponseVo.builder()
                    .code("411")
                    .data(null)
                    .message("只有社长才有该操作权限")
                    .build();
        }

        //创建审核申请
        Apply apply = Apply.builder()
                .applyFlow(3L)
                .message("修改社团信息")
                .createdBy(loginUserId)
                .createdTime(new Date())
                .communityId(communityUpdateBo.getId())
                .build();
        Long isSuccess = applyMapper.addApply(apply);

        if (!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L){
            return ResponseVo.builder()
                    .code("500")
                    .data(null)
                    .message("修改社团信息申请提交失败")
                    .build();
        }

        RedisTemplate<String, Object> template = redisTemplate; // 获取RedisTemplate实例
        HashOperations<String, String, Object> hashOps = template.opsForHash();
        String key = "update:community:" + communityUpdateBo.getId(); // 以社团ID作为主键
        hashOps.put(key, "name", communityUpdateBo.getName());
        hashOps.put(key, "id", communityUpdateBo.getId());
        hashOps.put(key, "description", communityUpdateBo.getDiscription());
        hashOps.put(key, "teacherId", communityUpdateBo.getTeacherId());
        hashOps.put(key, "type", communityUpdateBo.getType());
        hashOps.put(key, "createdBy", communityUpdateBo.getCreatedBy());

        return ResponseVo.builder()
                .code("200")
                .data(null)
                .message("修改社团信息申请已提交")
                .build();
    }

    @Override
    @Transactional
    public ResponseVo deleteCommunity(Long id) {

        //验证当前操作用户是否是社长
        Long loginUserId = LoginUtils.getLoginUserId();
        String permissions = LoginUtils.getUserPermission(userMapper);

        if(permissions.contains("1")){

            communityMapper.delete(id);

            return ResponseVo.builder()
                    .code("200")
                    .data(null)
                    .message("该社团注销成功")
                    .build();
        }

        if(!permissions.contains("3")){
            return ResponseVo.builder()
                    .code("412")
                    .data(null)
                    .message("只有社长才有该操作权限")
                    .build();
        }

        //注销审核申请
        Apply apply = Apply.builder()
                .applyFlow(2L)
                .message("注销社团")
                .createdBy(loginUserId)
                .createdTime(new Date())
                .communityId(id)
                .build();
        Long isSuccess = applyMapper.addApply(apply);

        if (!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L){
            return ResponseVo.builder()
                    .code("500")
                    .data(null)
                    .message("注销社团申请提交失败")
                    .build();
        }

        return ResponseVo.builder()
                .code("200")
                .data(null)
                .message("社团注销申请已提交")
                .build();
    }

    @Override
    public ResponseVo joinCommunity(Long communityId) {

        Long loginUserId = LoginUtils.getLoginUserId();

        //判断当前用户是否已经加入该社团
        CommunityUser communityUser = communityUserMapper.qeuryUserByCommunityIdAndUserId(loginUserId,communityId);

        if(!ObjectUtils.isEmpty(communityUser) && communityUser.getId() != 0L)
            return ResponseVo.builder()
                    .message("您已经加入该社团")
                    .data(null)
                    .code("414")
                    .build();

        //加入社团审核申请
        Apply apply = Apply.builder()
                .applyFlow(4L)
                .message("加入社团审核申请")
                .createdBy(loginUserId)
                .createdTime(new Date())
                .communityId(communityId)
                .build();
        Long isSuccess = applyMapper.addApply(apply);

        if (!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L){
            return ResponseVo.builder()
                    .code("500")
                    .data(null)
                    .message("加入社团审核申请提交失败")
                    .build();
        }

        return ResponseVo.builder()
                .code("200")
                .data(null)
                .message("加入社团审核申请已提交")
                .build();
    }

    @Override
    public ResponseVo quitCommunity(Long communityId) {

        Long loginUserId = LoginUtils.getLoginUserId();

        //判断当前用户是否不在该社团
        CommunityUser communityUser = communityUserMapper.qeuryUserByCommunityIdAndUserId(loginUserId,communityId);

        if(!ObjectUtils.isEmpty(communityUser) && communityUser.getId() == 0L)
            return ResponseVo.builder()
                    .message("您不在该社团无法进行此操作")
                    .data(null)
                    .code("413")
                    .build();

        //退出社团审核申请
        Apply apply = Apply.builder()
                .applyFlow(5L)
                .message("退出社团审核申请")
                .createdBy(loginUserId)
                .createdTime(new Date())
                .communityId(communityId)
                .build();
        Long isSuccess = applyMapper.addApply(apply);

        if (!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L){
            return ResponseVo.builder()
                    .code("500")
                    .data(null)
                    .message("退出社团审核申请提交失败")
                    .build();
        }

        return ResponseVo.builder()
                .code("200")
                .data(null)
                .message("退出社团审核申请已提交")
                .build();
    }

    @Override
    public ResponseVo queryUserByCommunity(Long communityId) {

        Long loginUserId = LoginUtils.getLoginUserId();
//        UserRolePermission userRolePermission = userMapper.queryUserRoleId(loginUserId);
        CommunityUser communityUser = communityUserMapper.qeuryUserByCommunityIdAndUserId(loginUserId, communityId);
        if(ObjectUtils.isEmpty(communityUser)){
            return ResponseVo.builder()
                    .code("413")
                    .message("您不在该社团")
                    .data(null)
                    .build();
        }

        // 鉴权
//        if(userRolePermission.getRoleId() != 3L){
//            return ResponseVo.builder()
//                    .code("412")
//                    .message("只有社长有权限进行此操作")
//                    .data(null)
//                    .build();
//        }

        List<CommunityUserInfoVo> communityUserInfoVoList = communityUserMapper.queryUserByCommunityId(communityId,loginUserId);

        return ResponseVo.builder()
                .code("200")
                .data(communityUserInfoVoList)
                .message("查询成功")
                .build();
    }
}
