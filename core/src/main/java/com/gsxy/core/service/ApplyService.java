package com.gsxy.core.service;

import com.gsxy.core.pojo.bo.ApplyFlowAddBo;
import com.gsxy.core.pojo.bo.ApplyFlowBo;
import com.gsxy.core.pojo.vo.ResponseVo;

public interface ApplyService {
    ResponseVo addApplyFlow(ApplyFlowAddBo applyFlowAddBo);

    ResponseVo deleteApplyFlow(Long id);

    ResponseVo addApply(ApplyFlowBo applyFlowBo);
}
