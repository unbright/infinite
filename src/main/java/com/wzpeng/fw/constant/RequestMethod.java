package com.wzpeng.fw.constant;

import io.netty.handler.codec.http.HttpMethod;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/10
 * Time: 11:25
 *
 * @author wzpeng
 * @version v1.0
 */
public enum RequestMethod {
    GET(HttpMethod.GET),

    POST(HttpMethod.POST);

    private HttpMethod httpMethod;

    RequestMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }
}
