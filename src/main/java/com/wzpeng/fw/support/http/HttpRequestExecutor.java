package com.wzpeng.fw.support.http;

import com.wzpeng.fw.support.data.RequestContext;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/9
 * Time: 17:55
 *
 * @author wzpeng
 * @version v1.0
 */
public interface HttpRequestExecutor {

    void execute(RequestContext context) throws Exception;

}
