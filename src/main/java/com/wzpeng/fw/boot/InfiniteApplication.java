package com.wzpeng.fw.boot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.wzpeng.fw.config.ApplicationModule;
import com.wzpeng.fw.core.InfiniteServer;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/9
 * Time: 11:51
 *
 * @author wzpeng
 * @version v1.0
 */
public class InfiniteApplication {

    public static void run(Class<?> mainClass) {
        Injector injector = Guice.createInjector(new ApplicationModule(mainClass));
        InfiniteServer server = injector.getInstance(InfiniteServer.class);
        server.start();
    }
}
