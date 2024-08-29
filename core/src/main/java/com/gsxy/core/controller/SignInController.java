package com.gsxy.core.controller;

import com.alibaba.fastjson2.JSONArray;
import com.gsxy.core.pojo.bo.SignInAddBo;
import com.gsxy.core.pojo.vo.ResponseVo;
import com.gsxy.core.service.SignInService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.gsxy.core.pojo.enums.CodeValues.PARAMETER_ERROR;
import static com.gsxy.core.pojo.enums.MessageValues.PARAMETER_MESSAGE;

@CrossOrigin
@Api(value = "签到板块接口",tags = {"签到板块接口"})
@RestController
@RequestMapping("/signIn")
public class SignInController {

    @Autowired
    private SignInService signInService;

    /**
     * @author hln 2024-8-29
     *      发起签到
     * @param signInAddBo
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("发起签到")
    public String add(@RequestBody SignInAddBo signInAddBo){

        if(signInAddBo == null){
            JSONArray.toJSONString(
                    ResponseVo.builder()
                            .code(PARAMETER_ERROR)
                            .message(PARAMETER_MESSAGE)
                            .data(null)
                            .build()
            );
        }

        return JSONArray.toJSONString(signInService.add(signInAddBo));
    }

    /**
     * @author hln 2024-8-29
     *      签到
     * @param signInId
     * @return
     */
    @GetMapping("/signIn/{signInId}/{message}")
    @ApiOperation("签到")
    public String signIn(@PathVariable Long signInId,@PathVariable String message){
        return JSONArray.toJSONString(signInService.signIn(signInId,message));
    }

    /**
     * @author hln 2024-8-29
     *      签到列表
     * @return
     */
    @GetMapping("/queryAllByPage/{page}/{limit}/{communityId}")
    @ApiOperation("签到列表")
    public String queryAllByPage(@PathVariable Long page,@PathVariable Long limit,@PathVariable Long communityId){
        return JSONArray.toJSONString(signInService.queryAllByPage(page, limit, communityId));
    }

    /**
     * @author hln 2024-8-29
     *      签到人员列表 -> 根据传入签到id区分
     * @return
     */
    @GetMapping("/querySignInUserBySignInId/{signInId}")
    @ApiOperation("签到人员列表")
    public String querySignInUserBySignInId(@PathVariable Long signInId){
        return JSONArray.toJSONString(signInService.querySignInUserBySignInId(signInId));
    }

}
