package com.wzpeng.fw.example;

import com.google.common.collect.ImmutableMap;
import com.wzpeng.fw.annotation.Controller;
import com.wzpeng.fw.annotation.Get;
import com.wzpeng.fw.annotation.Post;

import java.util.Map;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/9
 * Time: 14:37
 *
 * @author wzpeng
 * @version v1.0
 */
@Controller("test")
public class TestController {

    @Get("/te")
    public Map<String, String> test(String test) {
        System.out.println(test);
        ImmutableMap<String, String> map = ImmutableMap.of("asd", "123", "a", "asddddd");
        return map;
    }

    @Post("/user")
    public User test(User user) {
        System.out.println(user.toString());
        return user;
    }
}
