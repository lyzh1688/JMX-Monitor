package com.yuezy.monitor.monitor;
import com.sun.management.ThreadMXBean;
import com.yuezy.monitor.entity.ThreadMonitorInfo;
import com.yuezy.monitor.entity.Time;
import com.yuezy.monitor.service.IThreadMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.management.MBeanServerConnection;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by 胡志洁 on 2016/11/6.
 */
//@Component
public class ThreadMonitor {

    @Autowired
    @Qualifier("mBeanServerConnection")
    private MBeanServerConnection mBeanServerConnection;

    private final int delay = 0;

    private final int inteval = 2;

    private final TimeUnit timeUnit = TimeUnit.SECONDS;

    ScheduledExecutorService threadMonitor = Executors.newSingleThreadScheduledExecutor();

    public ThreadMonitor(){
        this.threadMonitor.scheduleAtFixedRate(new ThreadMonitorRunnable(),this.delay,this.inteval,this.timeUnit);


    }

    private class ThreadMonitorRunnable implements Runnable{

        //private final List<Long> threadIds = Collections.synchronizedList(new ArrayList<Long>());

        @Override
        public void run() {

            if(mBeanServerConnection == null){
                return ;
            }

            ThreadMXBean threadMXBean = null;
            try {

                threadMXBean =  ManagementFactory
                        .newPlatformMXBeanProxy(mBeanServerConnection,
                                ManagementFactory.THREAD_MXBEAN_NAME,
                                ThreadMXBean.class);

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " : Monitor thread started");
            threadMXBean.setThreadAllocatedMemoryEnabled(true);
            long[] threadIds = threadMXBean.getAllThreadIds();
            for (Long threadId : threadIds) {
                long allocateBytes = threadMXBean.getThreadAllocatedBytes(threadId);
                System.out.println("Thread Name : " + Thread.currentThread().getName() + " : Thread ID : " + threadId + " : memory = " + allocateBytes + " bytes");
                if(allocateBytes > 150 * 1024 * 1024){
                    ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId, Integer.MAX_VALUE);
                    StackTraceElement[] stackTraceElements = threadInfo.getStackTrace();
                    for(StackTraceElement stackTraceElement : stackTraceElements ){
                        String outputMsg = "ClassName : " + stackTraceElement.getClassName()  + " |FileName : " + stackTraceElement.getFileName()
                                + " |Methods : " + stackTraceElement.getMethodName() + " |LineNumber : " + stackTraceElement.getLineNumber();
                        System.out.println(outputMsg);
                    }
                }



            }



        }
    }
}
