package com.wzpeng.fw.support.data;

import com.wzpeng.fw.constant.RequestMethod;
import lombok.Data;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/10
 * Time: 19:05
 *
 * @author wzpeng
 * @version v1.0
 */
@Data
public class MethodAndArgument {

    private Method method;
    private Class<?> clazz;
    private Object[] args;
    private Parameter[] params;
    private RequestMethod requestMethod;
    private String route;

    private MethodAndArgument() {
    }

    public MethodAndArgument(Method method, Class<?> clazz, Parameter[] params, RequestMethod requestMethod, String route) {
        this.method = method;
        this.clazz = clazz;
        this.params = params;
        this.args = new Object[this.params.length];
        this.requestMethod = requestMethod;
        this.route = route;
    }

    public MethodAndArgument(Method method, RequestMethod requestMethod, String route) {
        this(method, method.getDeclaringClass(), method.getParameters(), requestMethod, route);
    }
}
