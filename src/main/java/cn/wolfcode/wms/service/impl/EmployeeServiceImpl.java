package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.mapper.EmployeeMapper;
import cn.wolfcode.wms.mapper.PermissionMapper;
import cn.wolfcode.wms.query.EmployeeQueryObject;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.service.EmployeeService;
import cn.wolfcode.wms.util.MD5;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    public void login(String username, String password) {
        Employee user = this.employeeMapper.selectByInfo(username, MD5.encoder(password));
        if (user == null) {
            throw new RuntimeException("账号或密码错误");
        } else {
            UserContext.setCurrentUser(user);
            if (!user.isAdmin()) {
                List<String> exps = this.permissionMapper.selectExpressionsByEmployeeId(user.getId());
                UserContext.setExpressions(exps);
            }

        }
    }

    public void saveOrUpdate(Employee entity, Long[] ids) {
        if (entity.getId() == null) {
            entity.setPassword(MD5.encoder(entity.getPassword()));
            this.employeeMapper.insert(entity);
        } else {
            this.employeeMapper.deleteRelation(entity.getId());
            this.employeeMapper.updateByPrimaryKey(entity);
        }

        if (ids != null) {
            for(Long id : ids) {
                this.employeeMapper.insertRelation(entity.getId(), id);
            }
        }

    }

    public void delete(Long id) {
        if (id != null) {
            employeeMapper.deleteByPrimaryKey(id);
        }
    }

    public void batchDelete(Long[] ids) {
        if (ids != null) {
            employeeMapper.batchDelete(ids);
        }
    }

    public Employee get(Long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    public List<Employee> list() {
        return employeeMapper.selectAll();
    }

    public PageResult query(EmployeeQueryObject qo) {
        int totalCount = employeeMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<Employee> data = employeeMapper.queryForList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

}
