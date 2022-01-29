package com.alibaba.cloud.examples.service.impl;

import com.alibaba.cloud.examples.entity.MyThreadEntity;
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
public class ThreadService2Impl implements ThreadService {

    @Override
    public String core(int n) {
        MyThreadEntity b1 = getSum(n);
        MyThreadEntity b2 = getSub(n);

        log.info("和为->{}",b1.getResult());
        log.info("差为->{}",b2.getResult());

        if(b1.isFlag() && b2.isFlag()) {
            return "success";
        }else{
            return "fail";
        }
    }

    public MyThreadEntity getSum(int n){
        int sum = n + 5;
        log.info("[{}] + 5 = {}",n,sum);
        MyThreadEntity myThreadEntity = new MyThreadEntity();
        myThreadEntity.setFlag(true);
        return myThreadEntity;
    }

    public MyThreadEntity getSub(int n){
        MyThreadEntity myThreadEntity = new MyThreadEntity();
        int sub = n - 2;
        log.info("[{}] - 2 = {}",n,sub);
        myThreadEntity.setResult(sub);
        if(sub < 0){
            myThreadEntity.setFlag(false);
        }else{
            myThreadEntity.setFlag(true);
        }
        return myThreadEntity;
    }

}
