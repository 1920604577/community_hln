package com.gsxy.core.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.gsxy.core.pojo.bo.*;
import com.gsxy.core.pojo.vo.ResponseVo;

public interface UserService {

    ResponseVo userReg(UserRegBo user);

    ResponseVo userLogin(UserLoginBo userLoginBo);

    ResponseVo addPermission(PermissionAddBo permissionAddBo);

    ResponseVo addRole(RoleAddBo roleAddBo);

    ResponseVo addUserRolePermission(UserRolePermissionAddBo userRolePermissionAddBo);

    ResponseVo deletePermission(Long id);

    ResponseVo queryPagePermission(Long page, Long limit);

    ResponseVo deleteRole(Long id);

    ResponseVo queryPageRole(Long page, Long limit);

    ResponseVo updateRole(RoleUpdateBo roleUpdateBo);

    ResponseVo deleteUserRolePermission(Long id);

    ResponseVo queryUser(String number);

    ResponseVo userInfo(UserInfoBo userInfoBo);

    ResponseVo queryPageUser(Long page, Long limit);
}
