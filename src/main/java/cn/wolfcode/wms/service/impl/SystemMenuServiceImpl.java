package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.mapper.SystemMenuMapper;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.query.SystemMenuQueryObject;
import cn.wolfcode.wms.service.SystemMenuService;
import cn.wolfcode.wms.util.UserContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class SystemMenuServiceImpl implements SystemMenuService {

    @Autowired
    private SystemMenuMapper systemMenuMapper;

    public void saveOrUpdate(SystemMenu entity) {
        if (entity.getId() == null) {
            systemMenuMapper.insert(entity);
        } else {
            systemMenuMapper.updateByPrimaryKey(entity);
        }
    }

    public void delete(Long id) {
        if (id != null) {
            systemMenuMapper.deleteByPrimaryKey(id);
        }
    }

    public SystemMenu get(Long id) {
        return systemMenuMapper.selectByPrimaryKey(id);
    }

    public List<SystemMenu> list() {
        return systemMenuMapper.selectAll();
    }

    public List<SystemMenu> getParentMenus(SystemMenu entity) {
        List<SystemMenu> menus = new ArrayList<>();
        menus.add(entity);
        while (entity.getParent() != null) {
            entity = entity.getParent();
            menus.add(entity);
        }
        Collections.reverse(menus);
        return menus;
    }

    public List<Map<String, Object>> loadMenusBySn(String parentSn) {
        Subject subject = SecurityUtils.getSubject();
        Employee user = (Employee) subject.getPrincipal();
        return user.isAdmin() ? systemMenuMapper.loadMenusBySn(parentSn) : systemMenuMapper.loadMenusBySnAndUser(parentSn, user.getId());
    }

    public PageResult query(SystemMenuQueryObject qo) {
        List<SystemMenu> data = systemMenuMapper.queryForList(qo);
        return new PageResult(data, 0, 1, 1);
    }
}
