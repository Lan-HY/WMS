package cn.wolfcode.wms.shiro;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.domain.Permission;
import cn.wolfcode.wms.domain.Role;
import cn.wolfcode.wms.mapper.EmployeeMapper;
import cn.wolfcode.wms.mapper.PermissionMapper;
import cn.wolfcode.wms.mapper.RoleMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RoleMapper roleMapper;

    public String getName() {
        return "UserRealm";
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Employee currentUser = (Employee) principals.getPrimaryPrincipal();
//        List<Role> roles = roleMapper.selectRolesByEmployeeId(currentUser.getId());
        if(currentUser.isAdmin()){
            List<String> exps = permissionMapper.selectAllExpressions();
            info.addStringPermissions(exps);
            return info;
        }
        List<String> expressions = permissionMapper.selectExpressionsByEmployeeId(currentUser.getId());
//        info.addRoles();
        info.addStringPermissions(expressions);
        return info;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String principal = (String) token.getPrincipal();
        Employee employee = employeeMapper.selectByName(principal);
        if(employee == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(employee,employee.getPassword(),this.getName());
    }
}
