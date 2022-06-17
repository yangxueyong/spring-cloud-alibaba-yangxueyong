package com.alibaba.cloud.examples.service.impl;

import com.alibaba.cloud.examples.service.ThreadTtlPoolService;
import com.alibaba.cloud.examples.util.TtlThreadLocalUtil;
import com.alibaba.ttl.TtlRunnable;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ：29520
 * @date ：Created in 2022/1/24 17:33
 * @description：
 * @modified By：
 * @version:
 */
@Slf4j
@Service
public class ThreadTtlPoolService2Impl implements ThreadTtlPoolService {

    ThreadFactory th = new ThreadFactoryBuilder().setNameFormat("yxy002").build();
    ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 5, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10000),th, new ThreadPoolExecutor.AbortPolicy());

    final Lock lock = new ReentrantLock();

    @Override
    public String core(int userNo) {
        TtlThreadLocalUtil.put("userNo", userNo);

        log.info("当前用户1->{}",TtlThreadLocalUtil.get("userNo"));

        try {
            createPool();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        TtlThreadLocalUtil.remove();
        return "success";
    }

    public boolean createPool() throws InterruptedException {

        poolExecutor.execute(TtlRunnable.get(()->{
            int j = 0;
            while(++j < 100) {
                lock.lock();
                try {
                    log.info("当前用户2->{}", TtlThreadLocalUtil.get("userNo"));
                }finally {
                    lock.unlock();
                }
            }
        }));

        int i = 0;
        while(++i < 100){
            lock.lock();
            try {
                int userNo = (int) (Math.random() * 100000);
                log.info("随机产生的userNo->{}", userNo);
                TtlThreadLocalUtil.put("userNo", userNo);
            }finally {
                lock.unlock();
            }
            Thread.sleep(1000);
        }

        return true;
    }

}
