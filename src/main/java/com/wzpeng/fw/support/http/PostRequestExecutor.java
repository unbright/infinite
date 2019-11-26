package com.wzpeng.fw.support.http;

import com.wzpeng.fw.support.data.RequestContext;
import com.wzpeng.fw.support.util.Responses;
import com.wzpeng.fw.support.util.Tools;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.util.CharsetUtil;

import java.lang.reflect.Parameter;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/9
 * Time: 18:03
 *
 * @author wzpeng
 * @version v1.0
 */
public class PostRequestExecutor implements HttpRequestExecutor {
    @Override
    public void execute(RequestContext context) throws Exception {
        String contentType = context.headers().get(HttpHeaderNames.CONTENT_TYPE);
        Parameter[] params = context.methodObject().getParams();
        if (contentType.contains(HttpHeaderValues.APPLICATION_JSON)) {
            // json
            String json = context.request().content().toString(CharsetUtil.UTF_8);
            Object data = Tools.fromJson(json, params[0].getType());
            Object result = context.invoke(data);
            Responses.ok(context.context(), context.request(), result);
        }
    }
}
