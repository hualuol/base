package com.hlwd.service;

import com.hlwd.dao.BaseDao;
import com.hlwd.dto.query.DataGridParam;
import com.hlwd.dto.structure.DataGrid;

import java.util.List;

public interface BaseService<Entity> {
    Object insert(Entity var1) throws Exception;

    int update(Entity var1, String var2) throws Exception;

    Entity getById(String var1) throws Exception;

    int batchDelete(String var1) throws Exception;

    int getCount(DataGridParam var1) throws Exception;

    List<Entity> getList(DataGridParam var1) throws Exception;

    DataGrid<Entity> getListForPager(DataGridParam var1) throws Exception;

    BaseDao<Entity> getDao();
}

