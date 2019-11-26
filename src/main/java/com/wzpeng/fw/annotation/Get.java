package com.wzpeng.fw.annotation;

import com.wzpeng.fw.constant.RequestMethod;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/10
 * Time: 11:26
 *
 * @author wzpeng
 * @version v1.0
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Router(method = RequestMethod.GET)
public @interface Get {

    String value() default "";

}
