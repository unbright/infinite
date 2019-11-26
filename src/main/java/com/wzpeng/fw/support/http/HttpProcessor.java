package com.wzpeng.fw.support.http;

import com.wzpeng.fw.support.DefaultRequestContext;
import com.wzpeng.fw.support.data.MethodAndArgument;
import com.wzpeng.fw.support.util.Responses;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/10
 * Time: 19:40
 *
 * @author wzpeng
 * @version v1.0
 */
@Slf4j
public class HttpProcessor extends DefaultRequestContext implements Runnable {

    private final Map<String, MethodAndArgument> routers;
    private final ChannelHandlerContext context;
    private final FullHttpRequest request;
    private final HttpRequestHandler handler;
    private boolean retain;

    public HttpProcessor(Map<String, MethodAndArgument> routers, ChannelHandlerContext context, FullHttpRequest request, HttpRequestHandler handler) {
        super(request, context);
        this.routers = routers;
        this.context = context;
        this.request = request;
        this.handler = handler;
        request.retain();
        this.retain = true;
    }

    @Override
    public void run() {
        try {
            QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
            MethodAndArgument methodObject = routers.get(decoder.path());
            if (methodObject == null) {
                Responses.notFound(context);
                return;
            }
            if (!methodObject.getRequestMethod().name().equals(request.method().name())) {
                Responses.responseFail(context, HttpResponseStatus.INTERNAL_SERVER_ERROR, "Method [" + request.method().toString() + "]" + " not allowed");
                return;
            }
            handler.handle(this.setMethodObject(methodObject));
        } catch (Exception ex) {
            log.error("System Error ====> ", ex);
            Responses.systemError(context);
        } finally {
            if (retain) {
                ReferenceCountUtil.release(request);
                retain = false;
            }
        }
    }
}
