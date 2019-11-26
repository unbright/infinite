package com.wzpeng.fw.support;

import com.wzpeng.fw.annotation.Controller;
import com.wzpeng.fw.annotation.Router;
import com.wzpeng.fw.support.data.MethodAndArgument;
import com.wzpeng.fw.support.util.AnnotationUtils;
import com.wzpeng.fw.support.util.ClassUtils;
import com.wzpeng.fw.support.util.MethodUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/10
 * Time: 19:00
 *
 * @author wzpeng
 * @version v1.0
 */
@Slf4j
public final class UrlPathBuilder {

    public static List<MethodAndArgument> scanUrl(String backPackage) {
        Set<Class<?>> classes = ClassUtils.scanWithAnnotation(backPackage, Controller.class);
        return classes.parallelStream()
                .flatMap(clazz -> MethodUtils.findMethodsInClassWithAnnotation(clazz, Router.class).parallelStream())
                .map(UrlPathBuilder::build)
                .collect(Collectors.toList());
    }

    private static MethodAndArgument build(Method method) {
        Router annotation = method.getDeclaringClass().getAnnotation(Router.class);
        String prefix = "/";
        if (annotation != null) {
            prefix = "/" + annotation.value().replace("/", "");
        }
        Router router = AnnotationUtils.findAnnotation(method, Router.class);
        String path = router.value().replaceFirst("/", "");
        return new MethodAndArgument(method, router.method(), prefix + path);
    }
}
