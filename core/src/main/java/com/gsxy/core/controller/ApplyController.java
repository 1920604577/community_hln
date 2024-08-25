package com.gsxy.core.controller;

import com.alibaba.fastjson2.JSONArray;
import com.gsxy.core.pojo.bo.ApplyFlowAddBo;
import com.gsxy.core.pojo.bo.ApplyFlowBo;
import com.gsxy.core.pojo.bo.UserInfoBo;
import com.gsxy.core.service.ApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Api(value = "审核板块接口",tags = {"审核板块接口"})
@RestController
@RequestMapping("/apply")
public class ApplyController {

    @Autowired
    private ApplyService applyService;

    /**
     * @author hln 2024-8-25
     *      审批申请
     * @param applyFlowBo
     * @return
     */
    @PostMapping("/addApply")
    @ApiOperation("审批申请")
    public String addApply(@RequestBody ApplyFlowBo applyFlowBo){
        return JSONArray.toJSONString(applyService.addApply(applyFlowBo));
    }

    /**
     * @author hln 2024-8-25
     *      创建审批流程
     * @param applyFlowAddBo
     * @return
     */
    @PostMapping("/addApplyFlow")
    @ApiOperation("创建审批流程")
    public String addApplyFlow(@RequestBody ApplyFlowAddBo applyFlowAddBo){
        return JSONArray.toJSONString(applyService.addApplyFlow(applyFlowAddBo));
    }

    /**
     * @author hln 2024-8-25
     *      删除审批流程（逻辑删除）
     * @param id
     * @return
     */
    @GetMapping("/deleteApplyFlow/{id}")
    @ApiOperation("删除审批流程（逻辑删除）")
    public String deleteApplyFlow(@PathVariable Long id){
        return JSONArray.toJSONString(applyService.deleteApplyFlow(id));
    }

}
