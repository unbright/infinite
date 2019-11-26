package com.wzpeng.fw.core;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wzpeng.fw.support.data.MethodAndArgument;
import com.wzpeng.fw.support.http.HttpProcessor;
import com.wzpeng.fw.support.http.HttpRequestHandler;
import com.wzpeng.fw.support.util.Responses;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/9
 * Time: 11:47
 *
 * @author wzpeng
 * @version v1.0
 */
@Slf4j
public class HttpServerInboundHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final Map<String, MethodAndArgument> routers;
    private final HttpRequestHandler handler;

    private static final String FAVICON_ICO = "/favicon.ico";
    private static ThreadPoolExecutor EXECUTOR;

    static {
        ThreadFactory factory = new ThreadFactoryBuilder().setDaemon(true)
                .setNameFormat("service-task-pool-%d").build();
        EXECUTOR = new ThreadPoolExecutor(4, 4, 0,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
                factory, new ThreadPoolExecutor.AbortPolicy());
    }

    public HttpServerInboundHandler(Map<String, MethodAndArgument> routers, HttpRequestHandler handler) {
        this.routers = routers;
        this.handler = handler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (request.decoderResult().isFailure()) {
            Responses.responseFail(ctx, BAD_REQUEST, "400 Bad Request");
            return;
        }
        if (request.uri().equals(FAVICON_ICO)) {
            return;
        }
        EXECUTOR.execute(new HttpProcessor(routers, ctx, request, handler));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Channel connection error ====> ", cause);
        ctx.close();
    }
}
