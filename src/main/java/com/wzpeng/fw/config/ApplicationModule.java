package com.wzpeng.fw.config;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.wzpeng.fw.core.InfiniteServerInitializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/9
 * Time: 11:17
 *
 * @author wzpeng
 * @version v1.0
 */
public class ApplicationModule extends AbstractModule {

    private final Class<?> mainClass;

    public ApplicationModule(Class<?> mainClass) {
        this.mainClass = mainClass;
    }

    @Override
    protected void configure() {
        bind(EventLoopGroup.class).annotatedWith(Names.named("boss")).to(NioEventLoopGroup.class).asEagerSingleton();
        bind(EventLoopGroup.class).annotatedWith(Names.named("worker")).to(NioEventLoopGroup.class).asEagerSingleton();
        bind(SystemConfigs.class).toProvider(SystemConfigs.ConfigProvider.class).asEagerSingleton();
        bind(ChannelInitializer.class).to(InfiniteServerInitializer.class).in(Singleton.class);
        install(new HttpModule());
        install(new RouteModule(mainClass));
    }
}
