package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface DepartmentService {

    void saveOrUpdate(Department entity);

    void delete(Long id);

    Department get(Long id);

    List<Department> list();

    PageResult query(QueryObject qo);

}
