package com.alibaba.cloud.examples.service.impl;

import com.alibaba.cloud.examples.service.ThreadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ：29520
 * @date ：Created in 2022/1/24 17:33
 * @description：
 * @modified By：
 * @version:
 */
@Slf4j
@Service
public class ThreadServiceImpl implements ThreadService {

    @Override
    public String core(int n) {
        boolean b1 = getSum(n);
        boolean b2 = getSub(n);
        if(b1 && b2) {
            return "success";
        }else{
            return "fail";
        }
    }

    public boolean getSum(int n){
        int sum = n + 5;
        log.info("[{}] + 5 = {}",n,sum);
        return true;
    }

    public boolean getSub(int n){
        int sub = n - 2;
        log.info("[{}] - 2 = {}",n,sub);
        if(sub < 0){
            return false;
        }
        return true;
    }

}
