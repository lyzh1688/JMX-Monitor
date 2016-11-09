package com.yuezy.monitor.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by 胡志洁 on 2016/11/6.
 */

public class ThreadMonitor {

    @Autowired
    private MBeanServerConnection mBeanServerConnection;

    private final int delay = 0;

    private final int inteval = 30;

    private final TimeUnit timeUnit = TimeUnit.SECONDS;

    ScheduledExecutorService threadMonitor = Executors.newSingleThreadScheduledExecutor();

    public ThreadMonitor(){
        this.threadMonitor.scheduleAtFixedRate(new ThreadMonitorRunnable(),this.delay,this.inteval,this.timeUnit);
    }

    private class ThreadMonitorRunnable implements Runnable{

        @Override
        public void run() {
            try {
                ThreadMXBean threadMXBean = ManagementFactory
                        .newPlatformMXBeanProxy(mBeanServerConnection,
                                ManagementFactory.THREAD_MXBEAN_NAME,
                                ThreadMXBean.class);

                System.out.println("ThreadCount = " + threadMXBean.getThreadCount());
                System.out.println("DaemonThreadCount = "
                        + threadMXBean.getDaemonThreadCount());
                System.out.println("PeakThreadCount = "
                        + threadMXBean.getPeakThreadCount());
                System.out.println("CurrentThreadCpuTime = "
                        + threadMXBean.getCurrentThreadCpuTime());
                System.out.println("CurrentThreadUserTime = "
                        + threadMXBean.getCurrentThreadUserTime());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
