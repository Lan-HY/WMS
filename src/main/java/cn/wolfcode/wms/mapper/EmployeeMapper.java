package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.query.EmployeeQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee entity);

    Employee selectByPrimaryKey(Long id);

    Employee selectByName(String name);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee entity);

    List<Employee> queryForList(EmployeeQueryObject qo);

    int queryForCount(EmployeeQueryObject qo);

    void deleteRelation(Long id);

    void insertRelation(@Param("employeeId") Long employeeId, @Param("roleId") Long roleId);

    Employee selectByInfo(@Param("username") String username, @Param("password") String password);

    void batchDelete(Long[] ids);
}