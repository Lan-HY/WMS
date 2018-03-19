package cn.wolfcode.wms.util;

import java.lang.reflect.Method;

public class PermissionUtil {

    public static String getExpression(Method m) {
        String className = m.getDeclaringClass().getName();
        return className + ":" + m.getName();
    }

}
