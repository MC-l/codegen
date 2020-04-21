package com.mcl.tool.codegen.common.util;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * properties 工具类
 * @auth caiguowei
 * @date 2020/4/21
 */
public class PropertiesUtil {
    public static final Properties properties = new Properties();

    static {
        try {
            InputStream stream = PropertiesUtil.class.getResourceAsStream("/application.properties");
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean contains(String key){
        return properties.get(key) != null;
    }

    public static String getVal(String key){
        checkKey(key);
        Object val = properties.get(key);
        return val == null ? null : val.toString();
    }

    public static List<String> getVals(String key){
        checkKey(key);
        Object valstr = properties.get(key);
        if (valstr == null){
            return null;
        }
        String[] split = valstr.toString().split(",");
        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].trim();
        }
        return Stream.of(split).collect(Collectors.toList());
    }

    private static void checkKey(String key){
        if (StringUtils.isEmpty(key)){
            throw new RuntimeException("key 不能为空");
        }
    }

    public static void main(String[] args) {
        System.out.println(getVal("entity.base.entity.class"));
        System.out.println(getVals("entity.base.entity.class.fields"));
    }

}
