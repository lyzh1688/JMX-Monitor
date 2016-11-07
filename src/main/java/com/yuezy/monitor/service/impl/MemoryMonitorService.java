package com.yuezy.monitor.service.impl;

import com.yuezy.monitor.entity.Memory;
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

/**
 * Created by kfzx-liuyz1 on 2016/11/7.
 */
@Service
public class MemoryMonitorService implements IMemoryMonitorService {
    @Autowired
    @Qualifier("mBeanServerConnection")
    private MBeanServerConnection mBeanServerConnection;

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

        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        Memory memory = new Memory();
        memory.setCommitted(Utils.convertKB(heapMemoryUsage.getCommitted()));
        memory.setInit(Utils.convertKB(heapMemoryUsage.getInit()));
        memory.setMax(Utils.convertKB(heapMemoryUsage.getMax()));
        memory.setUsed(Utils.convertKB(heapMemoryUsage.getUsed()));
        return memory;
    }
}
