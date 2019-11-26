package com.wzpeng.fw.support.data;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/10
 * Time: 19:28
 *
 * @author wzpeng
 * @version v1.0
 */
public interface RequestContext {

    String uri();

    HttpMethod method();

    FullHttpRequest request();

    MethodAndArgument methodObject();

    ChannelHandlerContext context();

    Object invoke(Object... args) throws Exception;

    HttpHeaders headers();
}
