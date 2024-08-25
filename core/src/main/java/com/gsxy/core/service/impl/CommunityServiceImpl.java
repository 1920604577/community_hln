package com.gsxy.core.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.gsxy.core.mapper.ApplyMapper;
import com.gsxy.core.mapper.CommunityMapper;
import com.gsxy.core.pojo.Apply;
import com.gsxy.core.pojo.Community;
import com.gsxy.core.pojo.bo.CommunityAddBo;
import com.gsxy.core.pojo.enums.CommunityTypeEnum;
import com.gsxy.core.pojo.vo.ResponseVo;
import com.gsxy.core.service.CommunityService;
import com.gsxy.core.util.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private ApplyMapper applyMapper;

    @Override
    @Transactional
    public ResponseVo addCommunity(CommunityAddBo communityAddBo) {

        Long loginUserId = LoginUtils.getLoginUserId();

        //创建审核申请
        Apply apply = Apply.builder()
                .applyFlow(communityAddBo.getApplyFlow())
                .message(communityAddBo.getMessage())
                .createdBy(loginUserId)
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
}
