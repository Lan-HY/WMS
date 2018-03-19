package cn.wolfcode.wms.util;

import cn.wolfcode.wms.domain.Employee;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

public abstract class UserContext {
    public static final String USER_IN_SESSION = "USER_IN_SESSION";
    public static final String EXPS_IN_SESSION = "EXPS_IN_SESSION";

    public UserContext() {
    }

    private static HttpSession getSession() {
        return ((ServletRequestAttributes)((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())).getRequest().getSession();
    }

    public static void setCurrentUser(Employee emp) {
        getSession().setAttribute("USER_IN_SESSION", emp);
    }

    public static Employee getCurrentUser() {
        return (Employee)getSession().getAttribute("USER_IN_SESSION");
    }

    public static void setExpressions(List<String> exps) {
        getSession().setAttribute("EXPS_IN_SESSION", exps);
    }

    public static List<String> getExpressions() {
        return (List)getSession().getAttribute("EXPS_IN_SESSION");
    }
}
