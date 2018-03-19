package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Role;
import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface RoleService {

    void saveOrUpdate(Role entity, Long[] ids, Long[] menuIds);

    void delete(Long id);

    Role get(Long id);

    List<Role> list();

    PageResult query(QueryObject qo);


}
