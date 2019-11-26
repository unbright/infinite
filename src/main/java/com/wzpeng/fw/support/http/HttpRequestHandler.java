package com.wzpeng.fw.support.http;

import com.wzpeng.fw.support.data.RequestContext;
import io.netty.handler.codec.http.HttpMethod;

import java.util.Map;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/9
 * Time: 19:09
 *
 * @author wzpeng
 * @version v1.0
 */
public class HttpRequestHandler {

    private final Map<HttpMethod, HttpRequestExecutor> executorMap;

    public HttpRequestHandler(Map<HttpMethod, HttpRequestExecutor> executorMap) {
        this.executorMap = executorMap;
    }

    public void handle(RequestContext context) throws Exception {
        HttpRequestExecutor executor = executorMap.get(context.method());
        if (executor == null) {
            throw new NullPointerException("Method " + context.method().toString() + " Not Supported");
        }
        executor.execute(context);
    }
}
