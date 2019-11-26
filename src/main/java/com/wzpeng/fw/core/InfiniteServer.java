package com.wzpeng.fw.core;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.wzpeng.fw.config.SystemConfigs;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/9
 * Time: 11:07
 *
 * @author wzpeng
 * @version v1.0
 */
@Slf4j
@Singleton
public class InfiniteServer {

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private final ChannelInitializer channelInitializer;
    private final SystemConfigs configs;

    @Inject
    public InfiniteServer(@Named("boss") EventLoopGroup bossGroup, @Named("worker") EventLoopGroup workerGroup, ChannelInitializer channelInitializer, SystemConfigs configs) {
        this.bossGroup = bossGroup;
        this.workerGroup = workerGroup;
        this.channelInitializer = channelInitializer;
        this.configs = configs;
    }

    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            ChannelFuture future = bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(channelInitializer)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .bind(configs.getPort()).sync();
            log.info("Netty started on port: {} (HTTP)", configs.getPort());
            future.channel().closeFuture().sync();
        } catch (Exception ex) {
            log.error("Server start on error =====> ", ex);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
