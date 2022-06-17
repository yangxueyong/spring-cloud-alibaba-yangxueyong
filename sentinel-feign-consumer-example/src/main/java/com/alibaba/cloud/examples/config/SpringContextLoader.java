package com.alibaba.cloud.examples.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebListener
@Configuration
public class SpringContextLoader implements ApplicationContextAware, ServletContextListener {
 
    private final static Logger logger = LoggerFactory.getLogger(SpringContextLoader.class);
 
    private static ApplicationContext applicationContext = null;
 
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }
 
    @Override
    public void contextInitialized(ServletContextEvent sce) {
 
        // 装载Spring的Context
        try {
            if(applicationContext==null) {
                applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext());
            }
            logger.info("系统spring配置装载成功~");
        } catch (Exception e) {
            logger.error("系统spring配置装载失败", e);
        }
    }
 
    /**
     * 通过注解得到类型
     * 
     * @param clazz
     * @return
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> clazz) {
        return applicationContext.getBeansWithAnnotation(clazz);
 
    }
 
 
    /**
     * 得到Class中包含有传入Annotation类型的方法
     * 
     * @param clz
     *            Class类型
     * @param annoClz
     *            Annotation类型
     * @return 传入Annotation类型标记的方法
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<Method> getClassMethodByAnnotation(Class clz, Class annoClz) throws Exception {
 
        clz = Class.forName(clz.getName(), true, clz.getClassLoader());
 
        List<Method> result = new ArrayList<Method>();
 
        for (Method method : clz.getMethods()) {
 
            if (method.getAnnotation(annoClz) != null) {
                result.add(clz.getMethod(method.getName(), method.getParameterTypes()));
            }
        }
 
        return result;
    }
 
//    /**
//     * 打印注解对应的方法名
//     * @throws Exception
//     */
//    public static void printMethodName() throws Exception{
//        Map<String, Object> openClz = SpringContextLoader.getBeansWithAnnotation(OpenAPI.class);
//
//        if (openClz != null) {
//            for (Object clzObj : openClz.values()) {
//
//                List<Method> methodList = getClassMethodByAnnotation(clzObj.getClass(), OpenAPIMethod.class);
//
//                for (Method method : methodList) {
//
//                    String methodName = method.getDeclaringClass().getSimpleName() + "." + method.getName();
//
//                    System.out.println(methodName);
//                }
//            }
//        }
//    }
}