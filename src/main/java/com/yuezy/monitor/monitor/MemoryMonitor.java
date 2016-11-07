package com.yuezy.monitor.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by 胡志洁 on 2016/11/6.
 */
@Component
public class MemoryMonitor {

    @Autowired
    private MBeanServerConnection mBeanServerConnection;

    private final int delay = 0;

    private final int inteval = 30;

    private final TimeUnit timeUnit = TimeUnit.SECONDS;

    ScheduledExecutorService memoryMonitor = Executors.newSingleThreadScheduledExecutor();

    public MemoryMonitor(){
        this.memoryMonitor.scheduleAtFixedRate(new MemoryMonitorRunnable(),this.delay,this.inteval,this.timeUnit);
    }

    private static String convertKB(long src) {

        if (src <= 0L) {
            return "0KB";
        }
        return (double) src / 1024 + "KB";
    }

    private class MemoryMonitorRunnable implements Runnable {

        @Override
        public void run() {
            try {
                MemoryMXBean memoryMXBean = ManagementFactory
                        .newPlatformMXBeanProxy(mBeanServerConnection,
                                ManagementFactory.MEMORY_MXBEAN_NAME,
                                MemoryMXBean.class);

                MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
                System.out.println("heapMemoryUsage :");
                System.out.println("committed = "
                        + convertKB(heapMemoryUsage.getCommitted()));
                System.out
                        .println("init = " + convertKB(heapMemoryUsage.getInit()));
                System.out.println("max = " + convertKB(heapMemoryUsage.getMax()));
                System.out
                        .println("used = " + convertKB(heapMemoryUsage.getUsed()));

                MemoryUsage nonHeapMemoryUsage = memoryMXBean
                        .getNonHeapMemoryUsage();
                System.out.println("\nnonHeapMemoryUsage :");
                System.out.println("committed = "
                        + convertKB(nonHeapMemoryUsage.getCommitted()));
                System.out.println("init = "
                        + convertKB(nonHeapMemoryUsage.getInit()));
                System.out.println("max = "
                        + convertKB(nonHeapMemoryUsage.getMax()));
                System.out.println("used = "
                        + convertKB(nonHeapMemoryUsage.getUsed()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
