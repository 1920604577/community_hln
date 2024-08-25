package com.gsxy.core.service.impl;

import com.gsxy.core.mapper.ApplyFlowMapper;
import com.gsxy.core.mapper.ApplyMapper;
import com.gsxy.core.pojo.Apply;
import com.gsxy.core.pojo.ApplyFlow;
import com.gsxy.core.pojo.bo.ApplyFlowAddBo;
import com.gsxy.core.pojo.bo.ApplyFlowBo;
import com.gsxy.core.pojo.vo.ResponseVo;
import com.gsxy.core.service.ApplyService;
import com.gsxy.core.util.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Service
public class ApplyServiceImpl implements ApplyService {

    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private ApplyFlowMapper applyFlowMapper;

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
}
