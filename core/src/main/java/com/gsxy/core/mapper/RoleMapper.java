package com.gsxy.core.mapper;

import com.gsxy.core.pojo.Role;
import com.gsxy.core.pojo.vo.RoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface RoleMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long addRole(Role role);

    Long deleteRole(Long id);

    List<RoleVo> queryPageRole(Long page, Long limit);

    Long updateRole(Role role);
}
