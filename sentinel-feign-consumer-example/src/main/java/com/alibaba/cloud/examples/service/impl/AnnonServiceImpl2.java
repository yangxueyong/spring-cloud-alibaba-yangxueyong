package com.alibaba.cloud.examples.service.impl;

import com.alibaba.cloud.examples.annotation.MyTestAnnotation;
import com.alibaba.cloud.examples.service.AnnonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ：29520
 * @date ：Created in 2022/1/24 17:33
 * @description：
 * @modified By：
 * @version:
 */
@MyTestAnnotation
@Slf4j
@Service("annonServiceImpl2")
public class AnnonServiceImpl2 implements AnnonService {

    @Override
    public String core() {
        System.out.println("5565656");
        return "123";
    }
}
