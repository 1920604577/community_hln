package com.gsxy.core.mapper;

import com.gsxy.core.pojo.Permission;
import com.gsxy.core.pojo.vo.PermissionVo;
import com.gsxy.core.pojo.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.Arrays;
import java.util.List;

@Mapper
public interface PermissionMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long addPermission(Permission build);

    Long deletePermission(Long id);

    List<PermissionVo> queryPagePermission(Long page, Long limit);

    Long queryPagePermissionCount(Long page, Long limit);

}
