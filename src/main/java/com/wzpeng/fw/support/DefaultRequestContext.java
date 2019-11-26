package com.wzpeng.fw.support;

import com.wzpeng.fw.support.data.MethodAndArgument;
import com.wzpeng.fw.support.data.RequestContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/10
 * Time: 19:48
 *
 * @author wzpeng
 * @version v1.0
 */
public class DefaultRequestContext implements RequestContext {

    private FullHttpRequest request;
    private ChannelHandlerContext context;
    private MethodAndArgument method;

    public DefaultRequestContext(FullHttpRequest request, ChannelHandlerContext context) {
        this.request = request;
        this.context = context;
    }

    public DefaultRequestContext(FullHttpRequest request, ChannelHandlerContext context, MethodAndArgument method) {
        this.request = request;
        this.context = context;
        this.method = method;
    }

    @Override
    public String uri() {
        return this.request.uri();
    }

    @Override
    public HttpMethod method() {
        return this.request.method();
    }

    @Override
    public FullHttpRequest request() {
        return this.request;
    }

    @Override
    public MethodAndArgument methodObject() {
        return this.method;
    }

    @Override
    public ChannelHandlerContext context() {
        return this.context;
    }

    @Override
    public Object invoke(Object... args) throws Exception {
        return this.methodObject().getMethod().invoke(this.methodObject().getClazz().newInstance(), args);
    }

    @Override
    public HttpHeaders headers() {
        return this.request.headers();
    }

    public RequestContext setMethodObject(MethodAndArgument methodObject) {
        this.method = methodObject;
        return this;
    }
}
