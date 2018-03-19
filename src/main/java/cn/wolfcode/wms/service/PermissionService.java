package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.domain.Permission;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface PermissionService {

    void delete(Long var1);

    List<Permission> list();

    PageResult query(QueryObject qo);

    void reload();

}
