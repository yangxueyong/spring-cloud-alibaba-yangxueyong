//package com.alibaba.cloud.examples.util;
//
//import com.alibaba.fastjson.JSON;
////import org.quartz.CronExpression;
//import org.quartz.CronExpression;
//import org.quartz.TriggerUtils;
//import org.quartz.impl.calendar.BaseCalendar;
//import org.quartz.impl.triggers.CronTriggerImpl;
//import org.springframework.scheduling.support.CronSequenceGenerator;
//import org.springframework.util.Assert;
////import org.springframework.scheduling.support.CronExpression;
//
//import java.text.ParseException;
//import java.time.*;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
///**
// * @author ：29520
// * @date ：Created in 2022/4/20 8:39
// * @description：
// * @modified By：
// * @version:
// */
//public class DateUtils {
//
//    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
////System.out.println(friday.format(formatter));
//    public static List<Date> latest(String cron, int count) throws ParseException {
//        CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
//        // 设置cron表达式
//        cronTriggerImpl.setCronExpression(cron);
//        // 根据count获取最近执行时间
//        List<Date> dateList = TriggerUtils.computeFireTimes(cronTriggerImpl, null, count);
//        return dateList;
//    }
//
//    /**
//     * Nearby local date time.
//     * <p>
//     * 获取cron当前次执行时间和前后次执行时间
//     * <p>
//     * String cron = "0 0/2 * * * ?";
//     *
//     * @param cron the cron
//     * @return local date time
//     */
//    public static void nearby(String cron) {
//        // 转换成cron表达式
//        org.springframework.scheduling.support.CronExpression cronExpression = org.springframework.scheduling.support.CronExpression.parse(cron);
//        // 下一次执行时间
//        LocalDateTime next = cronExpression.next(LocalDateTime.now());
//
//        Objects.requireNonNull(next);
//
//        // 两次间隔时间
//        long between = Duration.between(next, cronExpression.next(next)).getSeconds();
//
//        // 当前次执行时间
//        LocalDateTime current = next.minusSeconds(between);
//
//        // 上一次执行时间
//        LocalDateTime previous = current.minusSeconds(between);
//
////        log.info("now:{} previous:{} current:{} next:{}", Java8DateTimeUtils.nowDateTime(), previous, current, next);
////
////        return Tuple.of(previous, current, next);
//
//        System.out.println("previous->" + previous);
//        System.out.println("current->" + current);
//        System.out.println("next->" + next);
//
//    }
//
//    public static void getPeriodByCron() {
//        //30s执行一次
//        String cron = "0/30 * * * * ?";
//        //spring @since 5.3
//        org.springframework.scheduling.support.CronExpression cronExpression = org.springframework.scheduling.support.CronExpression.parse(cron);
//        //下次预计的执行时间
//        LocalDateTime nextFirst = cronExpression.next(LocalDateTime.now());
//        //下下次预计的执行时间
//        LocalDateTime nextSecond = cronExpression.next(nextFirst);
//        //计算周期1
//        long between1 = ChronoUnit.SECONDS.between(nextFirst, nextSecond);
//        //计算周期2
//        long between2 = Duration.between(nextFirst, nextSecond).getSeconds();
//
//        System.out.println("nextFirst->" + nextFirst);
//        System.out.println("nextSecond->" + nextSecond);
//
//    }
//
//    /**
//     * 将 Date 转为 LocalDateTime
//     *
//     * @param date
//     * @return java.time.LocalDateTime;
//     */
//    public static LocalDateTime dateToLocalDateTime(Date date) {
//        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//    }
//
//    public static Date localDateTimeToDate(final LocalDateTime localDateTime) {
//        if (null == localDateTime) {
//            return null;
//        }
//        final ZoneId zoneId = ZoneId.systemDefault();
//        final ZonedDateTime zdt = localDateTime.atZone(zoneId);
//        final Date date = Date.from(zdt.toInstant());
//        return date;
//    }
//
//    public static void getPeriodByCron(String cron,Date beginTime,Date endTime) {
//        //spring @since 5.3
//        org.springframework.scheduling.support.CronExpression cronExpression = org.springframework.scheduling.support.CronExpression.parse(cron);
//        //下次预计的执行时间
//        LocalDateTime nextFirst = cronExpression.next(dateToLocalDateTime(beginTime));
//        LocalDateTime localEndDateTime = dateToLocalDateTime(endTime);
//        List<LocalDateTime> datas = new ArrayList<>();
//        if(localEndDateTime.isAfter(nextFirst)){
//            datas.add(nextFirst);
//        }
//        while(true){
//            //下次预计的执行时间
//            nextFirst = cronExpression.next(nextFirst);
//            if(localEndDateTime.isBefore(nextFirst)){
//                break;
//            }
//            datas.add(nextFirst);
//        }
//        for (LocalDateTime date : datas) {
//            System.out.println("date->" + date);
//        }
//    }
//
//
//    public static void main(String[] args) throws ParseException {
////        List<Date> datas = latest("0 0/2 * * * ?", 5);
////        for (Date date : datas) {
////            Instant instant = date.toInstant();
////            ZoneId zoneId = ZoneId.systemDefault();
////            LocalDateTime friday = instant.atZone(zoneId).toLocalDateTime();
////            String format = friday.format(formatter);
////            System.out.println(format);
////        }
////        System.out.println(JSON.toJSONString(datas));
//
////        nearby("0 0/2 * * * ?");
//
////        getPeriodByCron();
//
//
//        getPeriodByCron("0 0 1 * * ?",
//                localDateTimeToDate(LocalDateTime.of(2017, 10, 22, 10, 10, 10)),
//                localDateTimeToDate(LocalDateTime.of(2017, 10, 22, 10, 10, 10)));
//
////        //获取当前时间
////        LocalDateTime nowTime= LocalDateTime.now();
////        //自定义时间
////        LocalDateTime endTime = LocalDateTime.of(2017, 10, 22, 10, 10, 10);
////
////        System.out.println(nowTime.isBefore(endTime));
//    }
//}
