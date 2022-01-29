package com.alibaba.cloud.examples.service.impl;

import com.alibaba.cloud.examples.service.ThreadPoolService;
import com.alibaba.cloud.examples.service.ThreadTtlPoolService;
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
        TtlThreadLocalUtil.put("userNo", userNo);

        log.info("当前用户1->{}",TtlThreadLocalUtil.get("userNo"));

        createPool();

        TtlThreadLocalUtil.remove();
        return "success";
    }

    public boolean createPool(){

        poolExecutor.execute(TtlRunnable.get(()->{
            log.info("当前用户2->{}",TtlThreadLocalUtil.get("userNo"));
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        return true;
    }

}
