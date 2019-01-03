package com.plasticlove.dao;

import com.plasticlove.pojo.Permission;

import java.util.Set;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    Set<String> selectPermissionsByUsername(String username);
}