package com.gsxy.core.controller;

import com.alibaba.fastjson2.JSONArray;
import com.gsxy.core.pojo.bo.ActiveAddBo;
import com.gsxy.core.pojo.bo.ChannelAddBo;
import com.gsxy.core.pojo.vo.ResponseVo;
import com.gsxy.core.service.ActiveService;
import com.gsxy.core.service.ChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.gsxy.core.pojo.enums.CodeValues.PARAMETER_ERROR;
import static com.gsxy.core.pojo.enums.MessageValues.PARAMETER_MESSAGE;

@CrossOrigin
@Api(value = "活动板块接口",tags = {"活动板块接口"})
@RestController
@RequestMapping("/active")
public class ActiveController {

    @Autowired
    private ActiveService activeService;

    /**
     * @author hln 2024-8-28
     *      创建/修改活动
     * @param activeAddBo
     * @return
     */
    @PostMapping("/addActive")
    @ApiOperation("创建/修改活动")
    public String addActive(@RequestBody ActiveAddBo activeAddBo){

        if(activeAddBo == null){
            JSONArray.toJSONString(
                    ResponseVo.builder()
                            .code(PARAMETER_ERROR)
                            .message(PARAMETER_MESSAGE)
                            .data(null)
                            .build()
            );
        }

        return JSONArray.toJSONString(activeService.addActive(activeAddBo));
    }

    /**
     * @author hln 2024-8-28
     *      删除活动
     * @param id
     * @return
     */
    @GetMapping("/deleteActive/{id}")
    @ApiOperation("删除活动")
    public String deleteActive(@PathVariable Long id){
        return JSONArray.toJSONString(activeService.deleteActive(id));
    }

    /**
     * @author hln 2024-8-28
     *      活动列表
     * @return
     */
    @GetMapping("/queryActiveByPage/{page}/{limit}/{title}/{channelId}/{type}")
    @ApiOperation("活动列表")
    public String queryActiveByPage(@PathVariable Long page ,@PathVariable Long limit
            ,@PathVariable String title,@PathVariable Long channelId,@PathVariable String type){
        return JSONArray.toJSONString(activeService.queryActiveByPage(page,limit,title,channelId,type));
    }

}
