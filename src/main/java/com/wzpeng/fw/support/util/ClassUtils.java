package com.wzpeng.fw.support.util;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/10
 * Time: 19:12
 *
 * @author wzpeng
 * @version v1.0
 */
public class ClassUtils {

    public static Set<Class<?>> scanWithAnnotation(String backPackage, Class<? extends Annotation> annotationClass) {
        Reflections reflections = new Reflections(backPackage);
        return reflections.getTypesAnnotatedWith(annotationClass);
    }

}
