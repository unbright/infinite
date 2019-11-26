package com.wzpeng.fw.support.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/10
 * Time: 15:40
 *
 * @author wzpeng
 * @version v1.0
 */
@Slf4j
@SuppressWarnings("unchecked")
public final class AnnotationUtils {

    static boolean checkMethodAnnotation(Method method, Class<? extends Annotation> annotationType) {
        if (method == null)
            throw new NullPointerException("Method can not be null");
        return Arrays.asList(method.getDeclaredAnnotations())
                .parallelStream()
                .anyMatch(annotation -> checkAnnotationInherited(annotation, annotationType));
    }

    public static <A extends Annotation> A findAnnotation(AnnotatedElement element, Class<A> annotationType) {
        try {
            Annotation source = Arrays.stream(element.getDeclaredAnnotations()).filter(annotation -> checkAnnotationInherited(annotation, annotationType))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("No Annotation In Method"));
            if (source.annotationType().equals(annotationType))
                return (A) source;
            for (Method method : annotationType.getDeclaredMethods()) {
                boolean hasMethod = Stream.of(source.annotationType().getMethods()).anyMatch(me -> me.getName().equals(method.getName()));
                Object value;
                if (hasMethod) {
                    value = MethodUtils.invokeMethod(source, method.getName());
                } else {
                    Annotation head = Stream.of(source.annotationType().getDeclaredAnnotations())
                            .filter(annotation -> annotation.annotationType().equals(annotationType))
                            .findFirst()
                            .orElseThrow(() -> new NoSuchElementException("No expected Annotation [" + annotationType.getName() + "]"));
                    value = MethodUtils.invokeMethod(head, method.getName());
                }
                source = setAttrValue(source, annotationType, method.getName(), value);
            }
            return (A) source;
        } catch (Exception ex) {
            log.error("Find annotation error ====> ", ex);
            throw new RuntimeException("Find annotation error");
        }
    }

    private static Annotation setAttrValue(Annotation annotation, Class<? extends Annotation> type, String attrName, Object newValue) {
        InvocationHandler handler = new AnnotationInvocationHandler(annotation, attrName, newValue);
        return (Annotation) Proxy.newProxyInstance(annotation.getClass().getClassLoader(), new Class[]{type}, handler);
    }

    private static boolean checkAnnotationInherited(Annotation annotation, Class<? extends Annotation> target) {
        return annotation.annotationType().equals(target) || annotation.annotationType().isAnnotationPresent(target);
    }
}
