# infinite
一个基于Netty的简易Http服务器

# 使用方法

#### 新建一个Controller
```java
@Slf4j
@Controller("test")
public class TestController {

    @Get("/get")
    public Map<String, String> get(String test) {
        log.info("请求参数: {}", test);
        ImmutableMap<String, String> map = ImmutableMap.of("a", "123", "b", "456");
        return map;
    }

    @Post("/user")
    public User post(User user) {
        log.info("请求参数: {}", user.toString());
        return user;
    }
}
```
#### 启动方法
```java
    //主类
    public static void main(String[] args) {
        //main class
        InfiniteApplication.run(AppStater.class);
    }
```
