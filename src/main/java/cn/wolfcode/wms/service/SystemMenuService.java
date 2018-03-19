package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.SystemMenuQueryObject;

import java.util.List;
import java.util.Map;

public interface SystemMenuService {

    void saveOrUpdate(SystemMenu entity);

    void delete(Long id);

    SystemMenu get(Long id);

    List<SystemMenu> list();

    List<SystemMenu> getParentMenus(SystemMenu entity);

    PageResult query(SystemMenuQueryObject qo);

    List<Map<String, Object>> loadMenusBySn(String parentSn);

}
