package cn.wolfcode.wms.web.interceptor;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.util.PermissionUtil;
import cn.wolfcode.wms.util.RequiredPermission;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class SecurityInterceptor extends HandlerInterceptorAdapter {

    public SecurityInterceptor() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Employee user = UserContext.getCurrentUser();
        if (user.isAdmin()) {
            return true;
        } else {
            HandlerMethod hm = (HandlerMethod)handler;
            Method m = hm.getMethod();
            if (!m.isAnnotationPresent(RequiredPermission.class)) {
                return true;
            } else {
                String exp = PermissionUtil.getExpression(m);
                if (UserContext.getExpressions().contains(exp)) {
                    return true;
                } else {
                    throw new SecurityException();
                }
            }
        }
    }

}
