package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Role;
import cn.wolfcode.wms.mapper.RoleMapper;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.RoleService;
import org.aspectj.bridge.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public RoleServiceImpl() {
    }

    public void saveOrUpdate(Role entity, Long[] ids, Long[] menuIds) {

        if (entity.getId() == null) {
            roleMapper.insert(entity);
        } else {
            roleMapper.deleteRelation(entity.getId());
            roleMapper.deleteMenuRelation(entity.getId());
            roleMapper.updateByPrimaryKey(entity);
        }

        if (ids != null) {
            for(Long id : ids) {
                roleMapper.insertRelation(entity.getId(), id);
            }
        }

        if (menuIds != null) {
            for(Long menuId : menuIds) {
                roleMapper.insertMenuRelation(entity.getId(), menuId);
            }
        }

    }

    public void delete(Long id) {
        roleMapper.deleteRelation(id);
        roleMapper.deleteMenuRelation(id);
        roleMapper.deleteByPrimaryKey(id);
    }

    public Role get(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public List<Role> list() {
        return roleMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        Integer totalCount = roleMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.EMPTY_PAGE;
        } else {
            List<?> data = roleMapper.queryForList(qo);
            return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
        }
    }
}
