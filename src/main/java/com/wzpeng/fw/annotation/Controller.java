package com.wzpeng.fw.annotation;

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
 * Time: 14:29
 *
 * @author wzpeng
 * @version v1.0
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    String value();
}
