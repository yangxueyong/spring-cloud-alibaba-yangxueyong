package com.alibaba.cloud.examples.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.util.StringUtils;

/**
 * cron表达式工具类
 * @author zhangsy
 * @date   2019年8月19日
 */
public class CronUtils {
	
	/**
	 * 按时间计算下次执行时间
	 * @Title: getExcuteTime
	 * @data:2019年8月19日上午11:34:13
	 * @author:zhangsy
	 *
	 * @param cron
	 * @return
	 */
	public static Date getExcuteTime(String cron, Date date) {
        if (StringUtils.isEmpty(cron))
            throw new IllegalArgumentException("cron表达式不可为空");
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);
        return cronSequenceGenerator.next(date);
    }
	
	/**
	 * 根据当前计算下次执行时间
	 * @Title: getExcuteTimeByNow
	 * @data:2019年8月19日上午11:34:13
	 * @author:zhangsy
	 *
	 * @param cron
	 * @return
	 */
	public static Date getExcuteTimeByNow(String cron) {
        return getExcuteTime(cron, new Date());
    }
	
	/**
	 * 计算多个执行时间
	 * @Title: getExcuteTimes
	 * @data:2019年8月19日上午11:25:21
	 * @author:zhangsy
	 *
	 * @param cron 表达式
	 * @param count 执行时间个数
	 * @return
	 */
	public static List<Date> getExcuteTimes(String cron, Integer count) {
        if (StringUtils.isEmpty(cron))
            throw new IllegalArgumentException("cron表达式不可为空");
        count = count==null||count<1?1:count;
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);
        List<Date> list = new ArrayList<Date>(count);
        Date nextTimePoint = new Date();
        for (int i = 0; i < count; i++) {
            // 计算下次时间点的开始时间
            nextTimePoint = cronSequenceGenerator.next(nextTimePoint);
            list.add(nextTimePoint);
        }
        return list;
    }

    
}
