package com.wzpeng.fw.annotation;

import com.wzpeng.fw.constant.RequestMethod;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/9
 * Time: 14:12
 *
 * @author wzpeng
 * @version v1.0
 */
@Documented
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Router {
    String value() default "";

    RequestMethod method() default RequestMethod.GET;
}
