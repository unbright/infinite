package com.wzpeng.fw.support.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created with IDEA
 * ProjectName: consoler
 * Date: 2019/6/18
 * Time: 18:40
 *
 * @author wzpeng
 * @version v1.0
 */
public class Tools {

    public final static Gson GSON = new GsonBuilder().serializeNulls().create();

    /**
     * json字符转对象.
     *
     * @param json  json string
     * @param clazz obj class
     * @param <T>   obj
     * @return result
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    public static String toJson(Object source) {
        return GSON.toJson(source);
    }
}
