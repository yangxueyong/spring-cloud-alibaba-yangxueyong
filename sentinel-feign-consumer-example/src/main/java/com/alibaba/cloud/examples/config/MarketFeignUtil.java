package com.alibaba.cloud.examples.config;

import feign.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author ：29520
 * @date ：Created in 2022/1/19 13:52
 * @description：
 * @modified By：
 * @version:
 */
@Component
public class MarketFeignUtil {
    @Autowired
    private MarketFeignConfig marketFeignConfig;

    @Value("${ribbon.ReadTimeout:5000}")
    private long readTimeout;

    @Value("${ribbon.ConnectTimeout:5000}")
    private long connectTimeout;

    /**
     * 请求的时间控制
     */
    private Map<String,Request.Options> reqOptionMap = new ConcurrentHashMap<>();
    private Map<String,String> urlReplaceMap = new ConcurrentHashMap<>();


    public void clearMap(){
        reqOptionMap.clear();
        urlReplaceMap.clear();
    }


    public String getNewUrlReplace(String url){
        if(!CollectionUtils.isEmpty(urlReplaceMap)){
            String newUrl = urlReplaceMap.get(url);
            if(newUrl != null) {
                return newUrl;
            }
        }
        List<Map<String, Object>> urlReplaceList = marketFeignConfig.getUrlreplace();
        if(!CollectionUtils.isEmpty(urlReplaceList)){
            for (Map<String, Object> newUrlMap : urlReplaceList) {
                if(!CollectionUtils.isEmpty(newUrlMap)){
                    Object oldUrlObj = newUrlMap.get("old-url");
                    Object newUrlObj = newUrlMap.get("new-url");

                    if(oldUrlObj != null && !"".equals(oldUrlObj)
                            && newUrlObj != null && !"".equals(newUrlObj)){
                        urlReplaceMap.put(oldUrlObj.toString(), newUrlObj.toString());
                    }
                }
            }
        }
        if(!CollectionUtils.isEmpty(urlReplaceMap)){
            String newUrl = urlReplaceMap.get(url);
            if(newUrl != null) {
                return newUrl;
            }
        }
        return url;
    }

    /**
     * 获取req的超时时间
     * @param name
     * @return
     */
    public Request.Options getReqOption(String name){
        if(!CollectionUtils.isEmpty(reqOptionMap)){
            Request.Options options = reqOptionMap.get(name);
            if(options != null) {
                return options;
            }
        }
        Map<String, Object> timeouts = marketFeignConfig.getTimeout();
        long locReadTimeout = readTimeout;
        long locConnectTimeout = connectTimeout;

        if(timeouts != null){
            Object o = timeouts.get(name);
            if(o != null && o instanceof Map){
                Object ctime = ((Map) o).get("connect-timeout");
                Object rtime = ((Map) o).get("read-timeout");
                if(ctime != null){
                    try {
                        locConnectTimeout = Long.valueOf((String)ctime);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(rtime != null){
                    try {
                        locReadTimeout = Long.valueOf((String)rtime);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        Request.Options options = new Request.Options(locConnectTimeout, TimeUnit.MILLISECONDS, locReadTimeout, TimeUnit.MILLISECONDS, true);
        reqOptionMap.putIfAbsent(name, options);
        return options;
    }
}
