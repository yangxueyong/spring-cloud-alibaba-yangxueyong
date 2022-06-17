package com.alibaba.cloud.examples.service.impl;

import com.alibaba.cloud.examples.annotation.MyTestAnnotation;
import com.alibaba.cloud.examples.service.AnnonService;
import com.alibaba.cloud.examples.service.ThreadPoolService;
import com.alibaba.cloud.examples.util.ThreadLocalUtil;
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
@MyTestAnnotation
@Slf4j
@Service("annonServiceImpl")
public class AnnonServiceImpl implements AnnonService {

    @Override
    public String core() {
        System.out.println("1231312312");
        return "123";
    }
}
