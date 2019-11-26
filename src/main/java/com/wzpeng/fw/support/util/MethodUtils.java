package com.wzpeng.fw.support.util;

import org.reflections.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/10
 * Time: 17:55
 *
 * @author wzpeng
 * @version v1.0
 */
@SuppressWarnings("unchecked")
public final class MethodUtils {

    public static Set<Method> findMethodsInClassWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotationType) {
        return ReflectionUtils.getMethods(clazz, method -> AnnotationUtils.checkMethodAnnotation(method, annotationType));
    }

}
