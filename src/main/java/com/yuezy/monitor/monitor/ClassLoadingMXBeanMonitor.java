package com.yuezy.monitor.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import java.io.IOException;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by 胡志洁 on 2016/11/6.
 */
@Component
public class ClassLoadingMXBeanMonitor {

    @Autowired
    private MBeanServerConnection mBeanServerConnection;

    private final int delay = 0;

    private final int inteval = 30;

    private final TimeUnit timeUnit = TimeUnit.SECONDS;

    ScheduledExecutorService classLoadingMXBeanMonitor = Executors.newSingleThreadScheduledExecutor();

    public ClassLoadingMXBeanMonitor(){
        this.classLoadingMXBeanMonitor.scheduleAtFixedRate(new ClassLoadingMXBeanMonitorRunnable(),this.delay,this.inteval,this.timeUnit);
    }

    private class ClassLoadingMXBeanMonitorRunnable implements Runnable{

        @Override
        public void run() {
            ClassLoadingMXBean classLoadingMXBean = null;
            try {
                classLoadingMXBean = ManagementFactory
                        .newPlatformMXBeanProxy(mBeanServerConnection,
                                ManagementFactory.CLASS_LOADING_MXBEAN_NAME,
                                ClassLoadingMXBean.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 当前加载到Java虚拟机中类的数量
            System.out.println("LoadedClassCount = " + classLoadingMXBean.getLoadedClassCount());
            // Java 虚拟机开始执行到目前已经加载的类的总数。
            System.out.println("TotalLoadedClassCount = " + classLoadingMXBean.getTotalLoadedClassCount());
            // Java 虚拟机开始执行到目前已经卸载的类的总数。
            System.out.println("UnloadedClassCount = "+ classLoadingMXBean.getUnloadedClassCount());
        }
    }
}
