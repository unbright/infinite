package com.wzpeng.fw.support.http;

import com.wzpeng.fw.support.data.RequestContext;
import com.wzpeng.fw.support.util.Responses;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/9
 * Time: 18:02
 *
 * @author wzpeng
 * @version v1.0
 */
public class GetRequestExecutor implements HttpRequestExecutor {

    @Override
    public void execute(RequestContext context) throws Exception {
        QueryStringDecoder decoder = new QueryStringDecoder(context.uri());
        Map<String, List<String>> parameters = decoder.parameters();
        Parameter[] params = context.methodObject().getParams();
        Object[] args = context.methodObject().getArgs();
        for (int i = 0; i < params.length; i++) {
            Parameter pa = params[i];
            if (parameters.containsKey(pa.getDeclaringExecutable().getName())) {
                List<String> value = parameters.get(pa.getDeclaringExecutable().getName());
                args[i] = value.get(0);
            }
        }
        Object result = context.invoke(args);
        Responses.ok(context.context(), context.request(), result);
    }
}
