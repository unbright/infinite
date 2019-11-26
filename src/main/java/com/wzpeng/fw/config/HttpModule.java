package com.wzpeng.fw.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.MapBinder;
import com.wzpeng.fw.support.http.DeleteRequestExecutor;
import com.wzpeng.fw.support.http.GetRequestExecutor;
import com.wzpeng.fw.support.http.HttpRequestExecutor;
import com.wzpeng.fw.support.http.HttpRequestHandler;
import com.wzpeng.fw.support.http.PostRequestExecutor;
import io.netty.handler.codec.http.HttpMethod;

import java.util.Map;


/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/9
 * Time: 18:28
 *
 * @author wzpeng
 * @version v1.0
 */
class HttpModule extends AbstractModule {

    @Override
    protected void configure() {
        MapBinder<HttpMethod, HttpRequestExecutor> binders = MapBinder.newMapBinder(binder(), HttpMethod.class, HttpRequestExecutor.class);
        binders.addBinding(HttpMethod.GET).to(GetRequestExecutor.class).in(Singleton.class);
        binders.addBinding(HttpMethod.POST).to(PostRequestExecutor.class).in(Singleton.class);
        binders.addBinding(HttpMethod.DELETE).to(DeleteRequestExecutor.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    public HttpRequestHandler serviceManager(Map<HttpMethod, HttpRequestExecutor> executorMap) {
        return new HttpRequestHandler(executorMap);
    }
}
