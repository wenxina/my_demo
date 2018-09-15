package com.alibaba.dubbo.demo;
import java.util.List;

/**
 * @author wang
 *
 * 存放公共接口
 * dubbo案例第一个module
 * 环境 ：
 *      idea
 *      zookeeper安装
 *      maven
 *      jdk1.7
 * 需要先启动zookeeper后，后续dubbo demo代码运行才能使用zookeeper注册中心的功能
 */
public interface DemoService {
    List<String> getPermissions(Long id);
}