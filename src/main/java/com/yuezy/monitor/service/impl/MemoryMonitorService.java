package com.yuezy.monitor.service.impl;

import com.sun.management.ThreadMXBean;
import com.yuezy.monitor.entity.Memory;
import com.yuezy.monitor.entity.ThreadMemory;
import com.yuezy.monitor.entity.ThreadMonitorInfo;
import com.yuezy.monitor.entity.Time;
import com.yuezy.monitor.service.IMemoryMonitorService;
import com.yuezy.monitor.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.management.MBeanServerConnection;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kfzx-liuyz1 on 2016/11/7.
 */
@Service
public class MemoryMonitorService implements IMemoryMonitorService {
    @Autowired
    @Qualifier("mBeanServerConnection")
    private MBeanServerConnection mBeanServerConnection;


    @Override
    public List<ThreadMemory> getThreadMemoryUsage(){
        ThreadMXBean threadMXBean = null;
        try {
            threadMXBean = ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection,
                    ManagementFactory.THREAD_MXBEAN_NAME,
                    ThreadMXBean.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

        threadMXBean.setThreadAllocatedMemoryEnabled(true);
        long[] threadIds = threadMXBean.getAllThreadIds();
        List<ThreadMemory> threadMonitorInfoList = new ArrayList<ThreadMemory>();
        for (Long threadId : threadIds) {
            long allocateBytes = threadMXBean.getThreadAllocatedBytes(threadId);
            //System.out.println("Thread Name : " + Thread.currentThread().getName() + " : Thread ID : " + threadId + " : memory = " + allocateBytes + " bytes");
            ThreadMemory threadMemory = new ThreadMemory();
            threadMemory.setMemory(allocateBytes / 1024) ;
            threadMemory.setThreadName(threadId.toString());
            threadMemory.setTime(new Time(new Date()));

            threadMonitorInfoList.add(threadMemory);

        }
        return threadMonitorInfoList;
    }

    @Override
    public Memory getHeapMemoryUsage() {
        MemoryMXBean memoryMXBean = null;
        try {
            memoryMXBean = ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection,
                            ManagementFactory.MEMORY_MXBEAN_NAME,
                            MemoryMXBean.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Memory memory = new Memory();

      /*  MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        memory.setCommitted(Utils.convertKB(heapMemoryUsage.getCommitted()));
        memory.setInit(Utils.convertKB(heapMemoryUsage.getInit()));
        memory.setMax(Utils.convertKB(heapMemoryUsage.getMax()));
        memory.setUsed(Utils.convertKB(heapMemoryUsage.getUsed()));*/
        
        
        MemoryUsage heapMs = memoryMXBean.getHeapMemoryUsage();
		MemoryUsage nonHeapMs = memoryMXBean.getNonHeapMemoryUsage();
		memory.setCommitted(Utils.convertKB(heapMs.getCommitted() + nonHeapMs.getCommitted()));
		memory.setMax(Utils.convertKB(heapMs.getMax() + nonHeapMs.getMax()));
		memory.setUsed(Utils.convertKB(heapMs.getUsed() + nonHeapMs.getUsed()));
		
        memory.setTime(new Time(new Date()));
        return memory;
    }
    
    
}
