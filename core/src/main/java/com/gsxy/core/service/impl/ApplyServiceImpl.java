package com.gsxy.core.service.impl;

import com.gsxy.core.mapper.*;
import com.gsxy.core.pojo.*;
import com.gsxy.core.pojo.bo.ApplyFlowAddBo;
import com.gsxy.core.pojo.bo.ApplyFlowBo;
import com.gsxy.core.pojo.bo.UserRolePermissionAddBo;
import com.gsxy.core.pojo.enums.ApplyEnum;
import com.gsxy.core.pojo.enums.CommunityStatusEnum;
import com.gsxy.core.pojo.enums.CommunityTypeEnum;
import com.gsxy.core.pojo.vo.ApplyFlowVo;
import com.gsxy.core.pojo.vo.ApplyVo;
import com.gsxy.core.pojo.vo.ResponseVo;
import com.gsxy.core.service.ApplyService;
import com.gsxy.core.util.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gsxy.core.pojo.enums.ApplyEnum.QUIT_COMMUNITY;

@Service
public class ApplyServiceImpl implements ApplyService {

    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private ApplyFlowMapper applyFlowMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CommunityUserMapper communityUserMapper;

    @Override
    public ResponseVo addApplyFlow(ApplyFlowAddBo applyFlowAddBo) {

        Long loginUserId = LoginUtils.getLoginUserId();
        ApplyFlow applyFlow = ApplyFlow.builder()
                .name(applyFlowAddBo.getName())
                .createdBy(loginUserId)
                .createdTime(new Date())
                .build();

        Long isSuccess = applyFlowMapper.addApplyFlow(applyFlow);

        if (!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L) {
            return ResponseVo.builder()
                    .code("500")
                    .data(null)
                    .message("添加审批流程失败")
                    .build();
        }

        return ResponseVo.builder()
                .data(null)
                .code("200")
                .message("添加审批流程成功")
                .build();
    }

    @Override
    public ResponseVo deleteApplyFlow(Long id) {

        Long isSuccess = applyFlowMapper.deleteApplyFlow(id);

        if (!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L) {
            return ResponseVo.builder()
                    .code("500")
                    .data(null)
                    .message("删除审批流程失败")
                    .build();
        }

        return ResponseVo.builder()
                .data(null)
                .code("200")
                .message("删除审批流程成功")
                .build();
    }

    @Override
    public ResponseVo addApply(ApplyFlowBo applyFlowBo) {

        Apply apply = Apply.builder()
                .applyFlow(applyFlowBo.getApplyFlow())
                .message(applyFlowBo.getMessage())
                .createdBy(LoginUtils.getLoginUserId())
                .createdTime(new Date())
                .build();

        Long isSuccess = applyMapper.addApply(apply);

        if (!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L) {
            return ResponseVo.builder()
                    .code("500")
                    .data(null)
                    .message("提交审核申请失败")
                    .build();
        }

        return ResponseVo.builder()
                .data(null)
                .code("200")
                .message("提交审核申请成功")
                .build();
    }

    @Override
    public ResponseVo queryApplyFlow() {

        List<ApplyFlowVo> applyFlowVoList = applyFlowMapper.queryApplyFlow();

        return ResponseVo.builder()
                .data(applyFlowVoList)
                .code("200")
                .message("查询成功")
                .build();
    }

    @Override
    public ResponseVo queryApply(String type, Long page, Long limit,Long communityId) {

        page = (page - 1) * limit;
        List<ApplyVo> applyVoList = null;
        Long loginUserId = LoginUtils.getLoginUserId();
        String permissions = LoginUtils.getUserPermission(userMapper);
        UserRolePermission userRolePermission = userMapper.queryUserRoleId(loginUserId);
        Long count = null;

        //管理员走的审核列表
        if (userRolePermission.getRoleId() == 1L) {
            ApplyEnum applyEnum = null;
            if (type.equals("COMMUNITY_APPLY"))
                applyEnum = ApplyEnum.COMMUNITY_APPLY;
            else if (type.equals("CHANGE_COMMUNITY"))
                applyEnum = ApplyEnum.CHANGE_COMMUNITY;
            else if (type.equals("DESTROY_COMMUNITY"))
                applyEnum = ApplyEnum.DESTROY_COMMUNITY;

            applyVoList = applyMapper.queryApply(applyEnum, page, limit)
                    .stream()
                    .map(applyVo -> {
                        applyVo.setCreatedByText(userMapper.queryUserById(applyVo.getCreatedBy()).getName());
                        applyVo.setUpdatedByText(applyVo.getUpdatedBy() == null ? "" : userMapper.queryUserById(applyVo.getUpdatedBy()).getName());
                        return applyVo;
                    }).collect(Collectors.toList());
            count = applyMapper.queryApplyCount(applyEnum, page, limit);
        } else if (userRolePermission.getRoleId() == 3L) {
            ApplyEnum applyEnum = null;
            if (type.equals("JOIN_COMMUNITY"))
                applyEnum = ApplyEnum.JOIN_COMMUNITY;
            else if (type.equals("QUIT_COMMUNITY"))
                applyEnum = ApplyEnum.QUIT_COMMUNITY;

            applyVoList = applyMapper.queryApplyByCommunityId(applyEnum, page, limit,communityId)
                    .stream()
                    .map(applyVo -> {
                        applyVo.setCreatedByText(userMapper.queryUserById(applyVo.getCreatedBy()).getName());
                        applyVo.setUpdatedByText(applyVo.getUpdatedBy() == null ? "" : userMapper.queryUserById(applyVo.getUpdatedBy()).getName());
                        return applyVo;
                    }).collect(Collectors.toList());
            count = applyMapper.queryApplyByCommunityIdCount(applyEnum, page, limit,communityId);
        }

        return ResponseVo.builder()
                .data(applyVoList)
                .code("200")
                .message("查询成功")
                .count(count)
                .build();
    }

    @Override
    @Transactional
    public ResponseVo apply(String type, Long id, Long communityId) {

        //查看当前登录用户是否有审核的权限（目前只有admin有），注意 1也就是admin权限只有最高管理员可以拥有
        String permissions = LoginUtils.getUserPermission(userMapper);
        Long loginUserId = LoginUtils.getLoginUserId();
        UserRolePermission userRolePermission = userMapper.queryUserRoleId(loginUserId);

        if (userRolePermission.getRoleId() == 1L) {
            //进入审核
            ApplyEnum applyEnum = null; // 该字段为预留字段暂时没用上
            if (type.equals("COMMUNITY_APPLY")) { //创建社团
                applyEnum = ApplyEnum.COMMUNITY_APPLY;
                applyMapper.updateApply(id, ApplyEnum.PASS);
                communityMapper.updateCommunity(id, CommunityStatusEnum.ENABLE);
                //都成功之后给该社团创建者绑定一个身份
                Community community = communityMapper.queryCommunityById(id);
                UserRolePermissionAddBo userRolePermissionAddBo = UserRolePermissionAddBo.builder()
                        .userId(community.getCreatedBy())
                        .roleId(3L)
                        .build();
                userServiceImpl.addUserRolePermission(userRolePermissionAddBo);
                //为指导教师绑定角色权限
                UserRolePermissionAddBo userRolePermissionTeacher = UserRolePermissionAddBo.builder()
                        .userId(community.getTeacherId())
                        .roleId(4L)
                        .build();
                userServiceImpl.addUserRolePermission(userRolePermissionTeacher);
                CommunityUser communityUser = CommunityUser.builder()
                        .createdTime(new Date())
                        .communityId(communityId)
                        .userId(community.getCreatedBy())
                        .createdBy(community.getCreatedBy())
                        .build();
                communityUserMapper.add(communityUser);
            } else if (type.equals("CHANGE_COMMUNITY")) {//修改社团信息
                applyEnum = ApplyEnum.CHANGE_COMMUNITY;
                applyMapper.updateApply(id, ApplyEnum.PASS);

                //此时从redis中获取数据
                Community communityTemp = communityMapper.queryCommunityByCommunityId(communityId);
                HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
                String key = "update:community:" + communityId;
                Map<String, Object> entries = hashOps.entries(key);

                CommunityTypeEnum communityTypeEnum = null;
                if (!ObjectUtils.isEmpty(entries.get("type")) && entries.get("type").equals("ORDINARY")) {
                    communityTypeEnum = CommunityTypeEnum.ORDINARY;
                } else if (!ObjectUtils.isEmpty(entries.get("type")) && entries.get("type").equals("PROFESSIONAL")) {
                    communityTypeEnum = CommunityTypeEnum.PROFESSIONAL;
                }

                Community community = Community.builder()
                        .id(communityTemp.getId())
                        .name((String) entries.get("name") == null ? null : (String) entries.get("name"))
                        .discription((String) entries.get("description") == null ? null : (String) entries.get("description"))
                        .teacherId((Long) entries.get("teacherId") == null ? null : (Long) entries.get("teacherId"))
                        .type(communityTypeEnum)
                        .createdBy((Long) entries.get("createdBy") == null ? null : (Long) entries.get("createdBy"))
                        .build();

                communityMapper.updateCommunityInfo(community);
                //以上流程都结束后开始清理redis
                redisTemplate.delete(key);

                //都成功之后如果社长被变更给新的社团创建者绑定一个身份
                if (!ObjectUtils.isEmpty(community.getCreatedBy()) && community.getCreatedBy() != 0L) {
                    UserRolePermissionAddBo userRolePermissionAddBo = UserRolePermissionAddBo.builder()
                            .userId(community.getCreatedBy())
                            .roleId(3L)
                            .build();
                    userServiceImpl.addUserRolePermission(userRolePermissionAddBo);
                    userServiceImpl.deleteUserRolePermission(communityTemp.getCreatedBy());
                }
                //都成功之后如果指导教师被变更给新的指导教师绑定一个身份
                if (!ObjectUtils.isEmpty(community.getTeacherId()) && community.getTeacherId() != 0L) {
                    UserRolePermissionAddBo userRolePermissionAddBo = UserRolePermissionAddBo.builder()
                            .userId(community.getTeacherId())
                            .roleId(4L)
                            .build();
                    userServiceImpl.addUserRolePermission(userRolePermissionAddBo);
                    userServiceImpl.deleteUserRolePermission(communityTemp.getTeacherId());
                }
            } else {
                return ResponseVo.builder()
                        .message("您无权限操作查看该类型的数据")
                        .code("412")
                        .data(null)
                        .build();
            }

            return ResponseVo.builder()
                    .data(null)
                    .code("200")
                    .message("已审核")
                    .build();
        } else if (userRolePermission.getRoleId() == 3L) {
            //进入审核
            ApplyEnum applyEnum = null;
            if (type.equals("JOIN_COMMUNITY")){ //加入社团的审批逻辑
                applyEnum = ApplyEnum.JOIN_COMMUNITY;
                applyMapper.updateApply(id, ApplyEnum.PASS);
                Apply apply = applyMapper.queryApplyById(id);
                Community community = communityMapper.queryCommunityByCommunityId(communityId);

                CommunityUser communityUser = CommunityUser.builder()
                        .userId(apply.getCreatedBy())
                        .communityId(communityId)
                        .createdBy(community.getCreatedBy())
                        .createdTime(new Date())
                        .build();

                communityUserMapper.add(communityUser);
                userServiceImpl.addUserRolePermission(
                        UserRolePermissionAddBo.builder()
                                .userId(apply.getCreatedBy())
                                .roleId(5L)
                                .build()
                );
            } else if (type.equals("QUIT_COMMUNITY")) {
                applyEnum = ApplyEnum.QUIT_COMMUNITY;
                applyMapper.updateApply(id, ApplyEnum.PASS);
                Apply apply = applyMapper.queryApplyById(id);
                communityMapper.deleteCommunityUser(communityId,apply.getCreatedBy());
                userServiceImpl.deleteUserRolePermission(apply.getCreatedBy());
            }

            return ResponseVo.builder()
                    .data(null)
                    .code("200")
                    .message("已审核")
                    .build();
        }

        return ResponseVo.builder()
                .code("401")
                .data(null)
                .message("没有权限")
                .build();
    }
}
