package com.hlwd.controller;

import com.hlwd.dto.ajax.DataGridResult;
import com.hlwd.dto.ajax.JsonResult;
import com.hlwd.dto.query.DataGridParam;
import com.hlwd.dto.structure.DataGrid;
import com.hlwd.service.BaseService;
import com.hlwd.validation.annotation.ValidEntity;
import com.hlwd.validation.annotation.ValidFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class BaseController<Entity> extends PlainController {
    @Autowired
    private BaseService<Entity> baseService;

    public BaseController() {
    }

    public BaseService<Entity> getService() {
        return this.baseService;
    }

    @RequestMapping({"/insert"})
    @ResponseBody
    public JsonResult insert(@ValidEntity @ModelAttribute Entity entity) throws Exception {
        return new JsonResult(200, "操作成功", this.baseService.insert(entity));
    }

    @RequestMapping({"/getListForPager"})
    @ResponseBody
    public JsonResult getListForPage(@ModelAttribute DataGridParam params) throws Exception {
        DataGrid<Entity> datagrid = this.baseService.getListForPager(params);
        return new DataGridResult(200, "操作成功", datagrid.getTotal(), datagrid.getRows());
    }

    @RequestMapping({"/getById"})
    @ResponseBody
    public JsonResult getById(@RequestParam String id) throws Exception {
        return new JsonResult(200, "操作成功", this.baseService.getById(id));
    }

    @RequestMapping({"/update"})
    @ResponseBody
    public JsonResult update(@ValidEntity @ModelAttribute Entity entity, @ValidFields @RequestParam String fields) throws Exception {
        return new JsonResult(200, "操作成功", this.baseService.update(entity, fields));
    }

    @RequestMapping({"/batchDelete"})
    @ResponseBody
    public JsonResult batchDelete(@RequestParam String ids) throws Exception {
        return new JsonResult(200, "操作成功", this.baseService.batchDelete(ids));
    }
}
