package com.alibaba.cloud.examples.service.impl;

import com.alibaba.cloud.examples.service.ThreadService;
import com.alibaba.cloud.examples.service.ThreadUserService;
import com.alibaba.cloud.examples.util.ThreadLocalUtil;
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
public class ThreadUserServiceImpl implements ThreadUserService {

    @Override
    public String core(int userNo) {
        if(userNo != 0){
            ThreadLocalUtil.put("userNo", userNo);
        }
        createProd();
        pushProd();

        ThreadLocalUtil.remove();
        return "success";
    }

    public boolean createProd(){
        Integer userNo = ThreadLocalUtil.get("userNo");
        log.info("用户[{}]正在创建产品",userNo);
        return true;
    }

    public boolean pushProd(){
        Integer userNo = ThreadLocalUtil.get("userNo");
        log.info("用户[{}]正在推送产品",userNo);
        return true;
    }
}
