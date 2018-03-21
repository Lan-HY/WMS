package cn.wolfcode.wms.util;

import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

public class PermissionUtil {

    public static String getExpression(Method m) {
        RequestMapping anno = m.getDeclaringClass().getAnnotation(RequestMapping.class);
        RequestMapping ann = m.getAnnotation(RequestMapping.class);
        String[] value = anno.value();
        String[] value1 = ann.value();
        return value[0] + ":" + value1[0];
    }

}
