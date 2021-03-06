package com.alibaba.cloud.examples.service.impl;

import com.alibaba.cloud.examples.service.ThreadPoolService;
import com.alibaba.cloud.examples.service.ThreadTtlPoolService;
import com.alibaba.cloud.examples.util.InheritableThreadLocalUtil;
import com.alibaba.cloud.examples.util.ThreadLocalUtil;
import com.alibaba.cloud.examples.util.TtlThreadLocalUtil;
import com.alibaba.ttl.TtlRunnable;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ：29520
 * @date ：Created in 2022/1/24 17:33
 * @description：
 * @modified By：
 * @version:
 */
@Slf4j
@Service
public class ThreadTtlPoolServiceImpl implements ThreadTtlPoolService {

    ThreadFactory th = new ThreadFactoryBuilder().setNameFormat("yxy002").build();
    ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 5, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10000),th, new ThreadPoolExecutor.AbortPolicy());

    @Override
    public String core(int userNo) {
        ThreadLocalUtil.put("userNo", userNo);

        log.info("当前用户1->{}",ThreadLocalUtil.get("userNo"));

//        createPool();

        ThreadLocalUtil.remove();
        return "success";
    }

//    public boolean createPool(){
//        poolExecutor.execute(TtlRunnable.get(()->{
//            log.info("当前用户2->{}", ThreadLocalUtil.get("userNo"));
//        }));
//
//
//        return true;
//    }

    public boolean createPool(){

        poolExecutor.execute(TtlRunnable.get(()->{
            int j = 0;
            while(++j < 100) {
                log.info("当前用户2->{}", ThreadLocalUtil.get("userNo"));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }));

        int i = 0;
        while(++i < 100){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int userNo = (int) (Math.random() * 100000);
            log.info("随机产生的userNo->{}", userNo);
            TtlThreadLocalUtil.put("userNo", userNo);
        }

        return true;
    }

}
