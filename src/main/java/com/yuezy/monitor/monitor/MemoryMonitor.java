package com.yuezy.monitor.monitor;

import com.yuezy.monitor.entity.Memory;
import com.yuezy.monitor.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("mBeanServerConnection")
    private MBeanServerConnection mBeanServerConnection;

    private final int delay = 0;

    private final int inteval = 3;

    private final TimeUnit timeUnit = TimeUnit.SECONDS;

    ScheduledExecutorService memoryMonitor = Executors.newSingleThreadScheduledExecutor();

    public MemoryMonitor(){
        this.memoryMonitor.scheduleAtFixedRate(new MemoryMonitorRunnable(),this.delay,this.inteval,this.timeUnit);
    }

    private class MemoryMonitorRunnable implements Runnable {

        private MemoryMXBean memoryMXBean = null;

        @Override
        public void run() {
            //MemoryMXBean memoryMXBean = null;
            if(mBeanServerConnection == null){
                return ;
            }
            try {
                if(this.memoryMXBean == null){
                    this.memoryMXBean = ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection,
                            ManagementFactory.MEMORY_MXBEAN_NAME,
                            MemoryMXBean.class);
                }

            } catch (Exception e) {
                System.out.println(e.toString());
            }
                MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
                Memory memory = new Memory();
                memory.setCommitted(Utils.convertKB(heapMemoryUsage.getCommitted()));
                memory.setInit(Utils.convertKB(heapMemoryUsage.getInit()));
                memory.setMax(Utils.convertKB(heapMemoryUsage.getMax()));
                memory.setUsed(Utils.convertKB(heapMemoryUsage.getUsed()));
                System.out.println(Utils.convertKB(heapMemoryUsage.getUsed()));

        }
    }

}
