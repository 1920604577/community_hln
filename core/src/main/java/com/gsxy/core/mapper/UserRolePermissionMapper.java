package com.gsxy.core.mapper;

import com.gsxy.core.pojo.UserRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRolePermissionMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long addUserRolePermission(UserRolePermission userRolePermission);

    @Select("select id from user_role_permission where user_id = #{userId} limit 1")
    Long queryIsHave(Long userId);

    Long updateUserRolePermission(UserRolePermission userRolePermission);

    Long deleteUserRolePermission(Long id);

    void deleteUserRolePermissionByCUId(Long createdBy, Long communityId);
}
