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

        Long isReg = userMapper.isUserReg(userRegBo);

        if(!ObjectUtils.isEmpty(isReg)){
           return new ResponseVo("用户名已存在",null,"400");
        }

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
                .code("200")
                .data(null)
                .message("注册成功")
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

        UserAndTokenVo userAndTokenVo = UserAndTokenVo.builder()
                .token(token)
                .user(user)
                .build();

        return ResponseVo.builder()
                .code("200")
                .data(userAndTokenVo)
                .message("登录成功")
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
                .code("200")
                .data(null)
                .message("权限添加成功")
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
                .code("200")
                .data(null)
                .message("角色添加成功")
                .build();
    }

    @Override
    public ResponseVo addUserRolePermission(UserRolePermissionAddBo userRolePermissionAddBo) {

        Long loginUserId = LoginUtils.getLoginUserId();
        UserRolePermission userRolePermission = UserRolePermission.builder()
                .createdBy(loginUserId)
                .createdTime(new Date())
                .roleId(userRolePermissionAddBo.getRoleId())
                .userId(userRolePermissionAddBo.getUserId())
                .permission(userRolePermissionAddBo.getPermission())
                .build();

        //先确认该用户有无绑定记录 有的话走修改接口 ，无就走添加接口
        Long isHaveId = userRolePermissionMapper.queryIsHave(userRolePermission.getUserId());

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
                .code("200")
                .data(null)
                .message("角色或附加权限绑定成功")
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
                .code("200")
                .data(null)
                .message("权限删除成功")
                .build();
    }

    @Override
    public ResponseVo queryPagePermission(Long page, Long limit) {

        page = (page - 1) * limit;
        List<PermissionVo> permissionVoList = permissionMapper.queryPagePermission(page,limit);

        return ResponseVo.builder()
                .code("200")
                .data(permissionVoList)
                .message("权限查询成功")
                .build();
    }

    @Override
    public ResponseVo deleteRole(Long id) {

        Long isSuccess = roleMapper.deleteRole(id);

        if(!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L){
            return ResponseVo.builder()
                    .code("500")
                    .data(null)
                    .message("角色删除失败")
                    .build();
        }

        return ResponseVo.builder()
                .code("200")
                .data(null)
                .message("角色删除成功")
                .build();
    }

    @Override
    public ResponseVo queryPageRole(Long page, Long limit) {
        page = (page - 1) * limit;
        List<RoleVo> roleVo = roleMapper.queryPageRole(page,limit);

        return ResponseVo.builder()
                .code("200")
                .data(roleVo)
                .message("权限查询成功")
                .build();
    }

    @Override
    public ResponseVo updateRole(RoleUpdateBo roleUpdateBo) {

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
                .code("200")
                .data(null)
                .message("角色修改成功")
                .build();
    }

    @Override
    public ResponseVo deleteUserRolePermission(Long id) {

        Long aLong = userRolePermissionMapper.deleteUserRolePermission(id);

        return ResponseVo.builder()
                .message(aLong == 0L ? "删除失败" : "删除成功")
                .code(aLong == 0L ? "500" : "200")
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
                .message("查询成功")
                .code("200")
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
                .message("添加成功")
                .code("200")
                .data(null)
                .build();
    }
}
