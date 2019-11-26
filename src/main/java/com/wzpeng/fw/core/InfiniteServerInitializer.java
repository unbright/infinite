package com.wzpeng.fw.core;

import com.google.inject.Inject;
import com.wzpeng.fw.support.data.MethodAndArgument;
import com.wzpeng.fw.support.http.HttpRequestHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.Map;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/9
 * Time: 11:37
 *
 * @author wzpeng
 * @version v1.0
 */
public class InfiniteServerInitializer extends ChannelInitializer<SocketChannel> {

    @Inject
    private Map<String, MethodAndArgument> routers;
    @Inject
    private HttpRequestHandler handler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(65535));
        pipeline.addLast("chunkedWriteHandler", new ChunkedWriteHandler());
        pipeline.addLast("httpServerInboundHandler", new HttpServerInboundHandler(routers, handler));
    }
}
