package com.wzpeng.fw.support.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/10
 * Time: 16:42
 *
 * @author wzpeng
 * @version v1.0
 */
public class AnnotationInvocationHandler implements InvocationHandler {

    private final Annotation source;
    private final String attrName;
    private final Object value;

    public AnnotationInvocationHandler(Annotation source, String attrName, Object value) {
        this.source = source;
        this.attrName = attrName;
        this.value = value;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals(attrName) && args == null)
            return value;
        else {
            return source.getClass().getMethod(method.getName()).invoke(source);
        }
    }
}
