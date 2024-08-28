package com.gsxy.core.controller;

import com.alibaba.fastjson2.JSONArray;
import com.gsxy.core.pojo.bo.ChannelAddBo;
import com.gsxy.core.pojo.bo.NoticeAddAllBo;
import com.gsxy.core.pojo.bo.NoticeAddBo;
import com.gsxy.core.pojo.vo.ResponseVo;
import com.gsxy.core.service.ChannelService;
import com.gsxy.core.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.gsxy.core.pojo.enums.CodeValues.PARAMETER_ERROR;
import static com.gsxy.core.pojo.enums.MessageValues.PARAMETER_MESSAGE;

@CrossOrigin
@Api(value = "通知板块接口",tags = {"通知板块接口"})
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * @author hln 2024-8-27
     *      创建通知
     * @param noticeAddBo
     * @return
     */
    @PostMapping("/addNotice")
    @ApiOperation("创建通知")
    public String addNotice(@RequestBody NoticeAddBo noticeAddBo){

        if(noticeAddBo == null){
            JSONArray.toJSONString(
                    ResponseVo.builder()
                            .code(PARAMETER_ERROR)
                            .message(PARAMETER_MESSAGE)
                            .data(null)
                            .build()
            );
        }

        return JSONArray.toJSONString(noticeService.addNotice(noticeAddBo));
    }

    /**
     * @author hln 2024-8-27
     *      通知列表（指定收件人）
     * @return
     */
    @GetMapping("/queryNoticeByPageLike/{page}/{limit}/{communityId}/{type}")
    @ApiOperation("通知列表")
    public String queryNoticeByPageLike(@PathVariable Long page,@PathVariable Long limit,@PathVariable Long communityId,@PathVariable String type){
        return JSONArray.toJSONString(noticeService.queryNoticeByPageLike(page,limit,communityId,type));
    }

    /**
     * @author hln 2024-8-28
     *      创建全体通知
     * @param noticeAddAllBo
     * @return
     */
    @PostMapping("/addAllNotice")
    @ApiOperation("创建全体通知")
    public String addAllNotice(@RequestBody NoticeAddAllBo noticeAddAllBo){

        if(noticeAddAllBo == null){
            JSONArray.toJSONString(
                    ResponseVo.builder()
                            .code(PARAMETER_ERROR)
                            .message(PARAMETER_MESSAGE)
                            .data(null)
                            .build()
            );
        }

        return JSONArray.toJSONString(noticeService.addAllNotice(noticeAddAllBo));
    }

}
