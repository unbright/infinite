package com.wzpeng.fw.config;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import com.wzpeng.fw.support.UrlPathBuilder;
import com.wzpeng.fw.support.data.MethodAndArgument;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/9
 * Time: 14:45
 *
 * @author wzpeng
 * @version v1.0
 */
@Slf4j
class RouteModule extends AbstractModule {

    private final Class<?> mainClass;

    RouteModule(Class<?> mainClass) {
        this.mainClass = mainClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void configure() {
        MapBinder<String, MethodAndArgument> binders = MapBinder.newMapBinder(binder(), String.class, MethodAndArgument.class);
        UrlPathBuilder.scanUrl(mainClass.getPackage().getName())
                .parallelStream()
                .forEach(methodObject -> {
                    log.info("Scan request method {} route {}", methodObject.getRequestMethod(), methodObject.getRoute());
                    binders.addBinding(methodObject.getRoute()).toInstance(methodObject);
                });
    }
}
