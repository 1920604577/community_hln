package com.gsxy.core.controller;

import com.alibaba.fastjson2.JSONArray;
import com.gsxy.core.pojo.bo.*;
import com.gsxy.core.pojo.vo.ResponseVo;
import com.gsxy.core.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.gsxy.core.pojo.enums.CodeValues.PARAMETER_ERROR;
import static com.gsxy.core.pojo.enums.MessageValues.PARAMETER_MESSAGE;

@CrossOrigin
@Api(value = "用户板块接口",tags = {"用户板块接口"})
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @author hln 2024-8-24
     *      用户注册
     * @param userRegBo
     * @return
     */
    @PostMapping("/userReg")
    @ApiOperation("用户注册")
    public String userReg(@RequestBody UserRegBo userRegBo){
        return JSONArray.toJSONString(userService.userReg(userRegBo));
    }

    /**
     * @author hln 2024-8-24
     *      用户登录
     * @param userLoginBo
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public String userLogin(@RequestBody UserLoginBo userLoginBo){

        if(userLoginBo == null){
            JSONArray.toJSONString(
                    ResponseVo.builder()
                            .code(PARAMETER_ERROR)
                            .message(PARAMETER_MESSAGE)
                            .data(null)
                            .build()
            );
        }

        return JSONArray.toJSONString(userService.userLogin(userLoginBo));
    }

    /**
     * @author hln 2024-8-24
     *      填写个人信息
     * @param userInfoBo
     * @return
     */
    @PostMapping("/userInfo")
    @ApiOperation("填写个人信息")
    public String userInfo(@RequestBody UserInfoBo userInfoBo){
        return JSONArray.toJSONString(userService.userInfo(userInfoBo));
    }

    /**
     * @author hln 2024-8-25
     *      查询用户
     * @param number
     * @return
     */
    @GetMapping("/queryUser/{number}")
    @ApiOperation("查询用户")
    public String queryUser(@PathVariable String number){
        return JSONArray.toJSONString(userService.queryUser(number));
    }

    /**
     * @author hln 2024-8-25
     *      添加权限
     * @param permissionAddBo
     * @return
     */
    @PostMapping("/addPermission")
    @ApiOperation("添加权限")
    public String addPermission(@RequestBody PermissionAddBo permissionAddBo){

        if(permissionAddBo == null){
            JSONArray.toJSONString(
                    ResponseVo.builder()
                            .code(PARAMETER_ERROR)
                            .message(PARAMETER_MESSAGE)
                            .data(null)
                            .build()
            );
        }

        return JSONArray.toJSONString(userService.addPermission(permissionAddBo));
    }

    /**
     * @author hln 2024-8-25
     *      删除权限
     * @param id
     * @return
     */
    @GetMapping("/deletePermission/{id}")
    @ApiOperation("删除权限（逻辑删除）")
    public String deletePermission(@PathVariable Long id){

        if(id == null && id != 0L){
            JSONArray.toJSONString(
                    ResponseVo.builder()
                            .code(PARAMETER_ERROR)
                            .message(PARAMETER_MESSAGE)
                            .data(null)
                            .build()
            );
        }

        return JSONArray.toJSONString(userService.deletePermission(id));
    }

    /**
     * @author hln 2024-8-25
     *      分页查询权限
     * @return
     */
    @GetMapping("/queryPagePermission/{page}/{limit}")
    @ApiOperation("分页查询权限")
    public String queryPagePermission(@PathVariable Long page,@PathVariable Long limit){
        return JSONArray.toJSONString(userService.queryPagePermission(page,limit));
    }

    /**
     * @author hln 2024-8-25
     *      添加角色
     * @param roleAddBo
     * @return
     */
    @PostMapping("/addRole")
    @ApiOperation("添加角色")
    public String addRole(@RequestBody RoleAddBo roleAddBo){

        if(roleAddBo == null){
            JSONArray.toJSONString(
                    ResponseVo.builder()
                            .code(PARAMETER_ERROR)
                            .message(PARAMETER_MESSAGE)
                            .data(null)
                            .build()
            );
        }

        return JSONArray.toJSONString(userService.addRole(roleAddBo));
    }

    /**
     * @author hln 2024-8-25
     *      添加角色
     * @param roleUpdateBo
     * @return
     */
    @PostMapping("/updateRole")
    @ApiOperation("添加角色")
    public String updateRole(@RequestBody RoleUpdateBo roleUpdateBo){

        if(roleUpdateBo == null){
            JSONArray.toJSONString(
                    ResponseVo.builder()
                            .code(PARAMETER_ERROR)
                            .message(PARAMETER_MESSAGE)
                            .data(null)
                            .build()
            );
        }

        return JSONArray.toJSONString(userService.updateRole(roleUpdateBo));
    }

    /**
     * @author hln 2024-8-25
     *      分页查询角色
     * @return
     */
    @GetMapping("/queryPageRole/{page}/{limit}")
    @ApiOperation("分页查询角色")
    public String queryPageRole(@PathVariable Long page,@PathVariable Long limit){
        return JSONArray.toJSONString(userService.queryPageRole(page,limit));
    }

    /**
     * @author hln 2024-8-25
     *      分页查询用户列表
     * @return
     */
    @GetMapping("/queryPageUser/{page}/{limit}")
    @ApiOperation("分页查询用户列表")
    public String queryPageUser(@PathVariable Long page,@PathVariable Long limit){
        return JSONArray.toJSONString(userService.queryPageUser(page,limit));
    }

    /**
     * @author hln 2024-8-25
     *      删除角色
     * @param id
     * @return
     */
    @GetMapping("/deleteRole/{id}")
    @ApiOperation("删除角色（逻辑删除）")
    public String deleteRole(@PathVariable Long id){

        if(id == null && id != 0L){
            JSONArray.toJSONString(
                    ResponseVo.builder()
                            .code(PARAMETER_ERROR)
                            .message(PARAMETER_MESSAGE)
                            .data(null)
                            .build()
            );
        }

        return JSONArray.toJSONString(userService.deleteRole(id));
    }

    /**
     * @author hln 2024-8-25
     *      为用户绑定角色以及附加权限
     * @param userRolePermissionAddBo
     * @return
     */
    @PostMapping("/addUserRolePermission")
    @ApiOperation("为用户绑定角色以及附加权限")
    public String addUserRolePermission(@RequestBody UserRolePermissionAddBo userRolePermissionAddBo){

        if(userRolePermissionAddBo == null){
            JSONArray.toJSONString(
                    ResponseVo.builder()
                            .code(PARAMETER_ERROR)
                            .message(PARAMETER_MESSAGE)
                            .data(null)
                            .build()
            );
        }

        return JSONArray.toJSONString(userService.addUserRolePermission(userRolePermissionAddBo));
    }

    /**
     * @author hln 2024-8-25
     *      删除指定用户绑定角色以及附加权限
     * @param id
     * @return
     */
    @GetMapping("/deleteUserRolePermission/{id}")
    @ApiOperation("删除指定用户绑定角色以及附加权限")
    public String deleteUserRolePermission(@PathVariable Long id){
        return JSONArray.toJSONString(userService.deleteUserRolePermission(id));
    }

}
