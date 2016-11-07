package com.yuezy.monitor.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.*;
import javax.management.remote.JMXConnector;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by 胡志洁 on 2016/11/6.
 */
@Component
public class OSMXMonitor {

    @Autowired
    private MBeanServerConnection mBeanServerConnection;

    private final int delay = 0;

    private final int inteval = 30;

    private final TimeUnit timeUnit = TimeUnit.SECONDS;

    ScheduledExecutorService operSysMXMonitor = Executors.newSingleThreadScheduledExecutor();

    public OSMXMonitor(){
        this.operSysMXMonitor.scheduleAtFixedRate(new OSMXMonitorRunnable(),this.delay,this.inteval,this.timeUnit);
    }

    private class OSMXMonitorRunnable implements Runnable{

        private long lastC = 0;
        private long last = 0;
        @Override
        public void run() {
            OperatingSystemMXBean operatingSystemMXBean = null;
            try {
                operatingSystemMXBean = ManagementFactory
                        .newPlatformMXBeanProxy(mBeanServerConnection,
                                ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
                                OperatingSystemMXBean.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("AvailableProcessors = "
                    + operatingSystemMXBean.getAvailableProcessors());
            double ratio = 0.0;
            long start = System.currentTimeMillis();
            try {
                //long startC = (Long) mBeanServerConnection.getAttribute(operatingSystemMXBean.getObjectName(), "ProcessCpuTime");
                long current = System.currentTimeMillis();
                long currentC = (Long) mBeanServerConnection.getAttribute(operatingSystemMXBean.getObjectName(), "ProcessCpuTime");
                int availableProcessors = operatingSystemMXBean.getAvailableProcessors();
                ratio = (currentC - lastC) / 1000000.0 / (current - last) / availableProcessors;
                lastC = currentC;
                last = current;
            } catch (MBeanException e) {
                e.printStackTrace();
            } catch (AttributeNotFoundException e) {
                e.printStackTrace();
            } catch (InstanceNotFoundException e) {
                e.printStackTrace();
            } catch (ReflectionException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
