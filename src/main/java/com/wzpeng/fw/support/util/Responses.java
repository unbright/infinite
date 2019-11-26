package com.wzpeng.fw.support.util;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/9
 * Time: 15:28
 *
 * @author wzpeng
 * @version v1.0
 */
public class Responses {

    public static void responseFail(ChannelHandlerContext context, HttpResponseStatus status, String message) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status);
        response.content().writeBytes(message.getBytes());
        HttpUtil.setContentLength(response, response.content().readableBytes());
        response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
        context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    public static void notFound(ChannelHandlerContext context) {
        responseFail(context, HttpResponseStatus.NOT_FOUND, "404 Not Found");
    }

    public static void ok(ChannelHandlerContext context, FullHttpRequest request, Object body) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK);
        response.content().writeBytes(Tools.toJson(body).getBytes());
        response.headers().set(CONTENT_TYPE, "application/json;charset=utf-8");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
        boolean keepAlive = HttpUtil.isKeepAlive(request);
        if (!keepAlive) {
            context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            response.headers().set(CONNECTION, KEEP_ALIVE);
            context.writeAndFlush(response);
        }
    }

    public static void systemError(ChannelHandlerContext context) {
        responseFail(context, HttpResponseStatus.INTERNAL_SERVER_ERROR, "System Error");
    }
}
