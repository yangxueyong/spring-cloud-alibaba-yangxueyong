package com.alibaba.cloud.examples.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：29520
 * @date ：Created in 2022/1/24 17:50
 * @description：
 * @modified By：
 * @version:
 */
public class InheritableThreadLocalUtil {
    static ThreadLocal<Map<String,Integer>> myThreadLocal = new InheritableThreadLocal<>();

    public static void put(String key, Integer value){
        Map<String, Integer> stringIntegerMap = myThreadLocal.get();
        if(stringIntegerMap == null){
            stringIntegerMap = new HashMap<>();
            myThreadLocal.set(stringIntegerMap);
        }
        stringIntegerMap.put(key, value);
    }

    public static Integer get(String key){
        Map<String, Integer> stringIntegerMap = myThreadLocal.get();
        if(stringIntegerMap != null) {
            return stringIntegerMap.get(key);
        }else{
            return null;
        }
    }

    public static void remove(){
        myThreadLocal.remove();
    }
}
