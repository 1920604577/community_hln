package com.gsxy.core.service.impl;

import com.gsxy.core.mapper.ApplyFlowMapper;
import com.gsxy.core.mapper.ApplyMapper;
import com.gsxy.core.mapper.CommunityMapper;
import com.gsxy.core.mapper.UserMapper;
import com.gsxy.core.pojo.Apply;
import com.gsxy.core.pojo.ApplyFlow;
import com.gsxy.core.pojo.bo.ApplyFlowAddBo;
import com.gsxy.core.pojo.bo.ApplyFlowBo;
import com.gsxy.core.pojo.enums.ApplyEnum;
import com.gsxy.core.pojo.enums.CommunityStatusEnum;
import com.gsxy.core.pojo.vo.ApplyFlowVo;
import com.gsxy.core.pojo.vo.ApplyVo;
import com.gsxy.core.pojo.vo.ResponseVo;
import com.gsxy.core.service.ApplyService;
import com.gsxy.core.util.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public ResponseVo addApplyFlow(ApplyFlowAddBo applyFlowAddBo) {

        Long loginUserId = LoginUtils.getLoginUserId();
        ApplyFlow applyFlow = ApplyFlow.builder()
                .name(applyFlowAddBo.getName())
                .createdBy(loginUserId)
                .createdTime(new Date())
                .build();

        Long isSuccess = applyFlowMapper.addApplyFlow(applyFlow);

        if(!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L){
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

        if(!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L){
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

        if(!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L){
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
    public ResponseVo queryApply(String type, Long page, Long limit) {

        page = (page - 1) * limit;
        ApplyEnum applyEnum = null;
        if(type.equals("COMMUNITY_APPLY"))
            applyEnum = ApplyEnum.COMMUNITY_APPLY;

        List<ApplyVo> applyVoList = applyMapper.queryApply(type,page,limit)
                .stream()
                .map(applyVo -> {
                    applyVo.setCreatedByText(userMapper.queryUserById(applyVo.getCreatedBy()).getName());
                    applyVo.setUpdatedByText(applyVo.getUpdatedBy() == null ? "" : userMapper.queryUserById(applyVo.getUpdatedBy()).getName());
                    return applyVo;
                }).collect(Collectors.toList());

        return ResponseVo.builder()
                .data(applyVoList)
                .code("200")
                .message("查询成功")
                .build();
    }

    @Override
    @Transactional
    public ResponseVo apply(String type, Long id) {

        //查看当前登录用户是否有审核的权限（目前只有admin有）
        Long loginUserId = LoginUtils.getLoginUserId();
        String userPermission = userMapper.queryPermissions(loginUserId);//当前用户附加权限
        String userRolePermission = userMapper.queryRolePermission(loginUserId);//当前用户绑定角色所拥有的权限集合
        String permissions = userPermission + userRolePermission;

        if(!permissions.contains("1")){
            return ResponseVo.builder()
                    .code("401")
                    .data(null)
                    .message("没有权限")
                    .build();
        }

        //进入审核
        ApplyEnum applyEnum = null;
        if(type.equals("COMMUNITY_APPLY")) {
            applyEnum = ApplyEnum.COMMUNITY_APPLY;
            applyMapper.updateApply(id,ApplyEnum.PASS);
            communityMapper.updateCommunity(id, CommunityStatusEnum.ENABLE);
        }

        return ResponseVo.builder()
                .data(null)
                .code("200")
                .message("已审核")
                .build();
    }
}
