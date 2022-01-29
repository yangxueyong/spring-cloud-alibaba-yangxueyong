package com.alibaba.cloud.examples.service.impl;

import com.alibaba.cloud.examples.service.ThreadService;
import com.alibaba.cloud.examples.util.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author ：29520
 * @date ：Created in 2022/1/24 17:33
 * @description：
 * @modified By：
 * @version:
 */
@Slf4j
@Service
public class ThreadService3Impl implements ThreadService {

    static ThreadLocal<Map<String,Object>> myThreadLocal = new ThreadLocal<>();

    @Override
    public String core(int n) {
        boolean b1 = getSum(n);
        boolean b2 = getSub(n);
        Integer sum = ThreadLocalUtil.get("sum");
        Integer sub = ThreadLocalUtil.get("sub");
        log.info("和为->{}",sum);
        log.info("差为->{}",sub);
        if(b1 && b2) {
            return "success";
        }else{
            return "fail";
        }
    }

    public boolean getSum(int n){
        int sum = n + 5;
        log.info("[{}] + 5 = {}",n,sum);
        ThreadLocalUtil.put("sum", sum);
        return true;
    }

    public boolean getSub(int n){
        int sub = n - 2;
        log.info("[{}] - 2 = {}",n,sub);
        ThreadLocalUtil.put("sub", sub);
        if(sub < 0){
            return false;
        }
        return true;
    }

}
