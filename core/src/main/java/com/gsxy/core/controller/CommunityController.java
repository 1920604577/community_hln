package com.gsxy.core.controller;

import com.alibaba.fastjson2.JSONArray;
import com.gsxy.core.pojo.bo.*;
import com.gsxy.core.pojo.enums.CodeValues;
import com.gsxy.core.pojo.vo.ResponseVo;
import com.gsxy.core.service.ApplyService;
import com.gsxy.core.service.CommunityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static com.gsxy.core.pojo.enums.CodeValues.PARAMETER_ERROR;
import static com.gsxy.core.pojo.enums.MessageValues.PARAMETER_MESSAGE;

@CrossOrigin
@Api(value = "社团板块接口",tags = {"社团板块接口"})
@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    /**
     * @author hln 2024-8-25
     *      创建社团
     * @param communityAddBo
     * @return
     */
    @PostMapping("/addPermission")
    @ApiOperation("创建社团")
    public String addCommunity(@RequestBody CommunityAddBo communityAddBo){

        if(communityAddBo == null){
            JSONArray.toJSONString(
                    ResponseVo.builder()
                            .code(PARAMETER_ERROR)
                            .message(PARAMETER_MESSAGE)
                            .data(null)
                            .build()
            );
        }

        return JSONArray.toJSONString(communityService.addCommunity(communityAddBo));
    }

    /**
     * @author hln 2024-8-26
     *      修改社团
     * @param communityUpdateBo
     * @return
     */
    @PostMapping("/updateCommunity")
    @ApiOperation("修改社团")
    public String updateCommunity(@RequestBody CommunityUpdateBo communityUpdateBo){

        if(communityUpdateBo == null){
            JSONArray.toJSONString(
                    ResponseVo.builder()
                            .code(PARAMETER_ERROR)
                            .message(PARAMETER_MESSAGE)
                            .data(null)
                            .build()
            );
        }

        return JSONArray.toJSONString(communityService.updateCommunity(communityUpdateBo));
    }

    /**
     * @author hln 2024-8-26
     *      注销社团
     * @param id
     * @return
     */
    @GetMapping("/deleteCommunity/{id}")
    @ApiOperation("注销社团")
    public String deleteCommunity(@PathVariable Long id){

        if(id == null){
            JSONArray.toJSONString(
                    ResponseVo.builder()
                            .code(PARAMETER_ERROR)
                            .message(PARAMETER_MESSAGE)
                            .data(null)
                            .build()
            );
        }

        return JSONArray.toJSONString(communityService.deleteCommunity(id));
    }

    /**
     * @author hln 2024-8-26
     *      申请加入社团
     * @return
     */
    @GetMapping("/joinCommunity/{communityId}")
    @ApiOperation("申请加入社团")
    public String joinCommunity(@PathVariable Long communityId){
        return JSONArray.toJSONString(communityService.joinCommunity(communityId));
    }

    /**
     * @author hln 2024-8-26
     *      申请退出社团
     * @return
     */
    @GetMapping("/quitCommunity/{communityId}")
    @ApiOperation("申请退出社团")
    public String quitCommunity(@PathVariable Long communityId){
        return JSONArray.toJSONString(communityService.quitCommunity(communityId));
    }

    /**
     * @author hln 2024-8-26
     *      查看指定社团所有人员
     * @return
     */
    @GetMapping("/queryUserByCommunity/{communityId}")
    @ApiOperation("查看指定社团所有人员信息")
    public String queryUserByCommunity(@PathVariable Long communityId){
        return JSONArray.toJSONString(communityService.queryUserByCommunity(communityId));
    }

    /**
     * @author hln 2024-8-26
     *      查看所有社团
     * @return
     */
    @GetMapping("/queryCommunityAll/{page}/{limit}/{name}")
    @ApiOperation("查看所有社团")
    public String queryCommunityAll(@PathVariable Long page,@PathVariable Long limit,@PathVariable String name){
        return JSONArray.toJSONString(communityService.queryCommunityAll(page,limit,name));
    }

    /**
     * @author hln 2024-8-29
     *      导出社团成员列表
     * @return
     */
    @ApiOperation("文件下载")
    @GetMapping("/exportData/{communityId}")
    public String exportData(HttpServletResponse response, @PathVariable Long communityId) {
        return JSONArray.toJSONString(communityService.exportData(response,communityId));
    }

}
