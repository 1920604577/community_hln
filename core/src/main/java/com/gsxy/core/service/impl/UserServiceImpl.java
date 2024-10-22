package com.gsxy.core.service.impl;

import com.gsxy.core.mapper.PermissionMapper;
import com.gsxy.core.mapper.RoleMapper;
import com.gsxy.core.mapper.UserMapper;
import com.gsxy.core.mapper.UserRolePermissionMapper;
import com.gsxy.core.pojo.*;
import com.gsxy.core.pojo.bo.*;
import com.gsxy.core.pojo.vo.*;
import com.gsxy.core.service.UserService;
import com.gsxy.core.util.JwtUtil;
import com.gsxy.core.util.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.gsxy.core.pojo.enums.CodeValues.SUCCESS_CODE;
import static com.gsxy.core.pojo.enums.MessageValues.SUCCESS_MESSAGE;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRolePermissionMapper userRolePermissionMapper;

    @Override
    public ResponseVo userReg(UserRegBo userRegBo) {

        //此处判断一下注册类型是老师还是学生（咱们学校老师工号是4位,学生学号是10位,但是考虑到后期开源所以这里写的通用一些）
        if (userRegBo.getRegType().equals("STUDENT") && userRegBo.getStudentId().length() != 10)
            return ResponseVo.builder()
                    .message("学号输入有误")
                    .data(null)
                    .code("409")
                    .build();
        if (userRegBo.getRegType().equals("TEACHER") && userRegBo.getStudentId().length() != 4)
            return ResponseVo.builder()
                    .message("工号输入有误")
                    .data(null)
                    .code("409")
                    .build();

        User user = User.builder()
                .username(userRegBo.getUsername())
                .password(userRegBo.getPasswd())
                .studentId(userRegBo.getRegType().equals("STUDENT") ? "S" + userRegBo.getStudentId() : "T" + userRegBo.getStudentId())
                .createTime(new Date())
                .build();

        userRegBo.setStudentId(user.getStudentId());
        Long isReg = userMapper.isUserReg(userRegBo);

        if(!ObjectUtils.isEmpty(isReg)){
            return ResponseVo.builder()
                    .message("该学号已经被注册过账号")
                    .data(null)
                    .code("400")
                    .build();
        }

        //注意这里已经直接通过mybatis返回了id主键
        Long isRegSuccess = userMapper.userReg(user);

        if(!ObjectUtils.isEmpty(isRegSuccess) && isRegSuccess == 0L){
            return ResponseVo.builder()
                    .code("500")
                    .data(null)
                    .message("注册失败")
                    .build();
        }

        return ResponseVo.builder()
                .code(SUCCESS_CODE)
                .data(null)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    @Override
    public ResponseVo userLogin(UserLoginBo userLoginBo) {

        //通过username去获取用户
        User user = userMapper.userLogin(userLoginBo);

        //比较用户密码和数据库中密码是否一致
        if(user == null || !user.getPassword().equals(userLoginBo.getPasswd())){
            return ResponseVo.builder()
                    .code("500")
                    .data(null)
                    .message("登录失败")
                    .build();
        }

        String token = JwtUtil.createJWT(user);

        UserInfo userInfo = userMapper.queryInfoById(user.getStudentId());
        user.setStudentId(user.getStudentId().substring(1));
        UserAndTokenVo userAndTokenVo = UserAndTokenVo.builder()
                .token(token)
                .user(user)
                .isHaveInfo(userInfo == null ? false : true)
                .build();

        return ResponseVo.builder()
                .code(SUCCESS_CODE)
                .data(userAndTokenVo)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    @Override
    public ResponseVo addPermission(PermissionAddBo permissionAddBo) {

        Long loginUserId = LoginUtils.getLoginUserId();
        Permission permission = Permission.builder()
                .createdBy(loginUserId)
                .createdTime(new Date())
                .name(permissionAddBo.getName())
                .build();

        Long isSuccess = permissionMapper.addPermission(permission);

        if(!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L){
            return ResponseVo.builder()
                    .code("500")
                    .data(null)
                    .message("权限添加失败")
                    .build();
        }

        return ResponseVo.builder()
                .code(SUCCESS_CODE)
                .data(null)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    @Override
    public ResponseVo addRole(RoleAddBo roleAddBo) {

        Long loginUserId = LoginUtils.getLoginUserId();
        Role role = Role.builder()
                .createdBy(loginUserId)
                .createdTime(new Date())
                .name(roleAddBo.getName())
                .permission(roleAddBo.getPermission())
                .build();

        Long isSuccess = roleMapper.addRole(role);

        if(!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L){
            return ResponseVo.builder()
                    .code("500")
                    .data(null)
                    .message("角色添加失败")
                    .build();
        }

        return ResponseVo.builder()
                .code(SUCCESS_CODE)
                .data(null)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    @Override
    public ResponseVo addUserRolePermission(UserRolePermissionAddBo userRolePermissionAddBo) {

        Long loginUserId = LoginUtils.getLoginUserId();
        UserRolePermission userRolePermission = UserRolePermission.builder()
                .createdBy(loginUserId)
                .createdTime(new Date())
                .communityId(userRolePermissionAddBo.getCommunityId() == null ? null : userRolePermissionAddBo.getCommunityId())
                .roleId(userRolePermissionAddBo.getRoleId())
                .userId(userRolePermissionAddBo.getUserId())
                .permission(userRolePermissionAddBo.getPermission())
                .build();

        //先确认该用户有无绑定记录 有的话走修改接口 ，无就走添加接口
        Long isHaveId = userRolePermissionMapper.queryIsHave(userRolePermission);

        Long isSuccess = null;
        if (ObjectUtils.isEmpty(isHaveId) || isHaveId == 0L) {
            isSuccess = userRolePermissionMapper.addUserRolePermission(userRolePermission);
        } else if (!ObjectUtils.isEmpty(isHaveId) && isHaveId != 0L) {
            userRolePermission.setId(isHaveId);
            isSuccess = userRolePermissionMapper.updateUserRolePermission(userRolePermission);
        }

        if(!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L){
            return ResponseVo.builder()
                    .code("500")
                    .data(null)
                    .message("角色或附加权限绑定失败")
                    .build();
        }

        return ResponseVo.builder()
                .code(SUCCESS_CODE)
                .data(null)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    @Override
    public ResponseVo deletePermission(Long id) {



        Long isSuccess = permissionMapper.deletePermission(id);

        if(!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L){
            return ResponseVo.builder()
                    .code("500")
                    .data(null)
                    .message("权限删除失败")
                    .build();
        }

        return ResponseVo.builder()
                .code(SUCCESS_CODE)
                .data(null)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    @Override
    public ResponseVo queryPagePermission(Long page, Long limit) {

        boolean haveRole = LoginUtils.isHaveRole(userMapper);

        if(!haveRole){
            return ResponseVo.builder()
                    .data(null)
                    .message("只有最高权限管理员才有此权限")
                    .code("412")
                    .build();
        }

        page = (page - 1) * limit;
        List<PermissionVo> permissionVoList = permissionMapper.queryPagePermission(page,limit)
                .stream().map(vo -> {
                    vo.setCreatedByText(userMapper.queryInfoById(userMapper.queryUserById(vo.getCreatedBy()).getStudentId()).getName());
                    return vo;
                }).collect(Collectors.toList());
        Long count = permissionMapper.queryPagePermissionCount(page,limit);

        return ResponseVo.builder()
                .code(SUCCESS_CODE)
                .count(count)
                .data(permissionVoList)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    @Override
    public ResponseVo deleteRole(Long id) {

        boolean haveRole = LoginUtils.isHaveRole(userMapper);

        if(!haveRole){
            return ResponseVo.builder()
                    .data(null)
                    .message("只有最高权限管理员才有此权限")
                    .code("412")
                    .build();
        }

        Long isSuccess = roleMapper.deleteRole(id);

        if(!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L){
            return ResponseVo.builder()
                    .code("500")
                    .data(null)
                    .message("角色删除失败")
                    .build();
        }

        return ResponseVo.builder()
                .code(SUCCESS_CODE)
                .data(null)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    @Override
    public ResponseVo queryPageRole(Long page, Long limit) {

        boolean haveRole = LoginUtils.isHaveRole(userMapper);

        if(!haveRole){
            return ResponseVo.builder()
                    .data(null)
                    .message("只有最高权限管理员才有此权限")
                    .code("412")
                    .build();
        }

        page = (page - 1) * limit;
        List<RoleVo> roleVo = roleMapper.queryPageRole(page,limit)
                .stream().map(vo -> {
                    vo.setCreatedByText(userMapper.queryInfoById(userMapper.queryUserById(vo.getCreatedBy()).getStudentId()).getName());
                    return vo;
                }).collect(Collectors.toList());
        Long count = roleMapper.queryPageRoleCount(page,limit);

        return ResponseVo.builder()
                .code(SUCCESS_CODE)
                .data(roleVo)
                .count(count)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    @Override
    public ResponseVo updateRole(RoleUpdateBo roleUpdateBo) {

        boolean haveRole = LoginUtils.isHaveRole(userMapper);

        if(!haveRole){
            return ResponseVo.builder()
                    .data(null)
                    .message("只有最高权限管理员才有此权限")
                    .code("412")
                    .build();
        }

        Long loginUserId = LoginUtils.getLoginUserId();
        Role role = Role.builder()
                .updatedBy(loginUserId)
                .updatedTime(new Date())
                .name(roleUpdateBo.getName())
                .permission(roleUpdateBo.getPermission())
                .build();

        Long isSuccess = roleMapper.updateRole(role);

        if(!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L){
            return ResponseVo.builder()
                    .code("500")
                    .data(null)
                    .message("角色修改失败")
                    .build();
        }

        return ResponseVo.builder()
                .code(SUCCESS_CODE)
                .data(null)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    @Override
    public ResponseVo deleteUserRolePermission(Long id) {

        Long aLong = userRolePermissionMapper.deleteUserRolePermission(id);

        return ResponseVo.builder()
                .message(aLong == 0L ? "删除失败" : SUCCESS_MESSAGE)
                .code(aLong == 0L ? "500" : SUCCESS_CODE)
                .data(null)
                .build();
    }

    @Override
    public ResponseVo queryUser(String number) {

        if (number.length() == 4)
            number = "T" + number;
        else if (number.length() == 10)
            number = "S" + number;

        UserVo userVo = userMapper.queryUser(number);

        if (ObjectUtils.isEmpty(userVo))
            return ResponseVo.builder()
                    .message("查询无此用户")
                    .code("410")
                    .data(null)
                    .build();

        return ResponseVo.builder()
                .message(SUCCESS_MESSAGE)
                .code(SUCCESS_CODE)
                .data(userVo)
                .build();
    }

    @Override
    public ResponseVo userInfo(UserInfoBo userInfoBo) {

        if(userInfoBo.getCardCode().length() != 18){
            return ResponseVo.builder()
                    .code("411")
                    .data(null)
                    .message("身份证号长度应为18位")
                    .build();
        }

        Long loginUserId = LoginUtils.getLoginUserId();
        User user = userMapper.queryUserById(loginUserId);

        UserInfo userInfo = UserInfo.builder()
                .id(user.getStudentId())
                .professional(userInfoBo.getProfessional())
                .grade(userInfoBo.getGrade())
                .cardCode(userInfoBo.getCardCode())
                .college(userInfoBo.getCollege())
                .org(userInfoBo.getOrg())
                .name(userInfoBo.getName())
                .age(userInfoBo.getAge())
                .phone(userInfoBo.getPhone())
                .birthday(userInfoBo.getBirthday())
                .build();

        Long isSuccess = null;
        //看看用户信息是否已经存在
        UserInfo info = userMapper.queryIsHave(user.getStudentId());
        if (ObjectUtils.isEmpty(info)) {
            isSuccess = userMapper.addUserInfo(userInfo);
        } else {
            isSuccess = userMapper.updateUserInfo(userInfo);
        }

        if(!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L){
            return ResponseVo.builder()
                    .code("500")
                    .data(null)
                    .message("角色修改失败")
                    .build();
        }

        return ResponseVo.builder()
                .message(SUCCESS_MESSAGE)
                .code(SUCCESS_CODE)
                .data(null)
                .build();
    }

    @Override
    public ResponseVo queryPageUser(Long page, Long limit) {

        page = (page - 1) * limit;
        List<UserVo> userVoList = userMapper.queryPageUser(page,limit)
                .stream().map(vo -> {
                    if(userMapper.queryUserById(vo.getCreatedBy()) != null)
                        vo.setCreatedByText(userMapper.queryInfoById(userMapper.queryUserById(vo.getCreatedBy()).getStudentId()).getName());
                    vo.setUpdateByText(vo.getUpdateBy() == null ? null : userMapper.queryInfoById(userMapper.queryUserById(vo.getUpdateBy()).getStudentId()).getName());
                    return vo;
                }).collect(Collectors.toList());
        Long count = userMapper.queryPageUserCount(page,limit);

        return ResponseVo.builder()
                .message(SUCCESS_MESSAGE)
                .code(SUCCESS_CODE)
                .data(userVoList)
                .count(count)
                .build();
    }
}
