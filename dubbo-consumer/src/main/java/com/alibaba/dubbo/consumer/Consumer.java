package com.alibaba.dubbo.consumer;

import com.alibaba.dubbo.demo.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wang
 * 消费者 服务的调用者
 * 配置consumer.xml启动此服务
 */
public class Consumer {
    public static void main(String[] args) {
        //测试常规服务
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        System.out.println("即将开始调用...");
        DemoService demoService = context.getBean(DemoService.class);
        System.out.println(demoService.getPermissions(1L));
    }
}