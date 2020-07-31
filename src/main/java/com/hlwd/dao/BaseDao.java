package com.hlwd.dao;

import com.hlwd.dto.query.DataGridParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BaseDao<Entity> {
    int insert(Entity var1);

    int update(@Param("entity") Entity var1, @Param("fields") String[] var2);

    Entity getById(String var1);

    int delete(@Param("id") String var1);

    int batchDelete(@Param("ids") String[] var1);

    int getCount(@Param("params") DataGridParam var1);

    List<Entity> getList(@Param("params") DataGridParam var1);
}

