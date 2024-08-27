package com.gsxy.core.controller;

import com.alibaba.fastjson2.JSONArray;
import com.gsxy.core.pojo.bo.ChannelAddBo;
import com.gsxy.core.pojo.bo.CommunityAddBo;
import com.gsxy.core.pojo.vo.ResponseVo;
import com.gsxy.core.service.ApplyService;
import com.gsxy.core.service.ChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.gsxy.core.pojo.enums.CodeValues.PARAMETER_ERROR;
import static com.gsxy.core.pojo.enums.MessageValues.PARAMETER_MESSAGE;

@CrossOrigin
@Api(value = "通道板块接口",tags = {"通道板块接口"})
@RestController
@RequestMapping("/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    /**
     * @author hln 2024-8-27
     *      创建/修改通道
     * @param channelAddBo
     * @return
     */
    @PostMapping("/addChannel")
    @ApiOperation("创建/修改通道")
    public String addChannel(@RequestBody ChannelAddBo channelAddBo){

        if(channelAddBo == null){
            JSONArray.toJSONString(
                    ResponseVo.builder()
                            .code(PARAMETER_ERROR)
                            .message(PARAMETER_MESSAGE)
                            .data(null)
                            .build()
            );
        }

        return JSONArray.toJSONString(channelService.addChannel(channelAddBo));
    }

    /**
     * @author hln 2024-8-27
     *      删除通道
     * @return
     */
    @GetMapping("/deleteChannel/{id}")
    @ApiOperation("删除通道")
    public String deleteChannel(@PathVariable Long id){
        return JSONArray.toJSONString(channelService.deleteChannel(id));
    }

    /**
     * @author hln 2024-8-27
     *      通道列表（指定社团）
     * @return
     */
    @GetMapping("/queryChannelByPage/{page}/{limit}/{communityId}/{name}")
    @ApiOperation("删除通道")
    public String queryChannelByPage(@PathVariable Long page,@PathVariable Long limit,@PathVariable Long communityId,@PathVariable String name){
        return JSONArray.toJSONString(channelService.queryChannelByPage(page,limit,communityId,name));
    }

}
