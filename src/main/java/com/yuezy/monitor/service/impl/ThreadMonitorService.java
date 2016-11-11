package com.yuezy.monitor.service.impl;

import com.yuezy.monitor.entity.BasicThreadInfo;
import com.yuezy.monitor.entity.ThreadMonitorInfo;
import com.yuezy.monitor.entity.Time;
import com.yuezy.monitor.service.IThreadMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.management.MBeanServerConnection;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kfzx-liuyz1 on 2016/11/9.
 */
@Service
public class ThreadMonitorService implements IThreadMonitorService{

    @Autowired
    @Qualifier("mBeanServerConnection")
    private MBeanServerConnection mBeanServerConnection;

    @Override
    public ThreadMonitorInfo getThreadInfo() {
        ThreadMonitorInfo threadMonitorInfo = new ThreadMonitorInfo();
        threadMonitorInfo.setNotRunnable(0);
        threadMonitorInfo.setRunnable(0);
        //List<BasicThreadInfo> threadInfos = new ArrayList<BasicThreadInfo>();
        ThreadMXBean threadMXBean = null;
        try {
            threadMXBean = ManagementFactory.newPlatformMXBeanProxy(this.mBeanServerConnection, ManagementFactory.THREAD_MXBEAN_NAME, ThreadMXBean.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long[] ct = threadMXBean.getAllThreadIds();
        for (int i = 0; i < ct.length; i++) {
            ThreadInfo ti = threadMXBean.getThreadInfo(ct[i], Integer.MAX_VALUE);
            //threadInfos.add(new BasicThreadInfo(ti));
            if (ti != null) {
                if(ti.getThreadState().equals(Thread.State.RUNNABLE)){
                    threadMonitorInfo.setRunnable(threadMonitorInfo.getRunnable() + 1);
                }
                else{
                    threadMonitorInfo.setNotRunnable(threadMonitorInfo.getNotRunnable() + 1);

                }
//                System.out.println("Thread name and ID:........" + ti.getThreadName() + " (" + ct[i] + ")");
//                System.out.println("Thread state:.............." + ti.getThreadState().toString());
//                System.out.print("Lock:......................Name=" + ti.getLockName());
//                System.out.print(" OwnerID=" + ti.getLockOwnerId());
//                System.out.println(" OwnerName=" + ti.getLockOwnerName());
//                System.out.println("Thread blocked:............" + ti.getBlockedCount() + " times / " + ti.getBlockedTime());
//                System.out.println("Thread waited:............." + ti.getWaitedCount() + " times / " + ti.getWaitedTime());
//
//                StackTraceElement[] ste = ti.getStackTrace();
//                if (ste.length > 0) {
//                    for (int j = 0; j < ste.length; j++) {
//                        System.out.println("\t" + ste[j]);
//                    }
//                }
//                System.out.println("- - - - - -");
            } else {
                System.out.println("Thread name and ID:........(" + ct[i] + ") (Thread has died.)");
            }
        }

        //threadMonitorInfo.setThreadInfoList(threadInfos);
        //threadMonitorInfo.setCount(threadInfos.size());
        threadMonitorInfo.setTime(new Time(new Date()));

        return threadMonitorInfo;
    }
}
