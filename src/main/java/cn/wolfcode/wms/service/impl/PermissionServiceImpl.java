package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Permission;
import cn.wolfcode.wms.mapper.PermissionMapper;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.PermissionService;
import cn.wolfcode.wms.util.PermissionUtil;
import cn.wolfcode.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private ApplicationContext ctx;

    public PermissionServiceImpl() {
    }

    public void reload() {
        List<String> exps = this.permissionMapper.selectAllExpressions();
        Collection<Object> ctrls = this.ctx.getBeansWithAnnotation(Controller.class).values();
        Iterator it = ctrls.iterator();

        while(it.hasNext()) {
            Object ctrl = it.next();
            Method[] ms = ctrl.getClass().getDeclaredMethods();

            for(int i = 0; i < ms.length; ++i) {
                Method m = ms[i];
                RequiredPermission anno = (RequiredPermission)m.getAnnotation(RequiredPermission.class);
                if (anno != null) {
                    String exp = PermissionUtil.getExpression(m);
                    if (!exps.contains(exp)) {
                        Permission p = new Permission();
                        p.setName(anno.value());
                        p.setExpression(exp);
                        this.permissionMapper.insert(p);
                    }
                }
            }
        }

    }

    public void delete(Long id) {
        this.permissionMapper.deleteByPrimaryKey(id);
    }

    public List<Permission> list() {
        return this.permissionMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        Integer totalCount = this.permissionMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.EMPTY_PAGE;
        } else {
            List<?> data = this.permissionMapper.queryForList(qo);
            return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
        }
    }
}
