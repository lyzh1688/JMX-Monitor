package com.yuezy.monitor.service.impl;

import com.sun.management.OperatingSystemMXBean;
import com.yuezy.monitor.entity.CpuInfo;
import com.yuezy.monitor.entity.Time;
import com.yuezy.monitor.service.ICpuMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;

import java.util.Date;

/**
 * Created by kfzx-liuyz1 on 2016/11/9.
 */
@Service
public class CpuMonitorService implements ICpuMonitorService {

    @Autowired
    @Qualifier("mBeanServerConnection")
    private MBeanServerConnection mBeanServerConnection;

    private long lastC = 0;
    private long last = 0;

    @Override
    public CpuInfo getCpuMonitorInfo() {
        OperatingSystemMXBean operatingSystemMXBean = null;
        try {
            operatingSystemMXBean = ManagementFactory
                    .newPlatformMXBeanProxy(mBeanServerConnection,
                            ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
                            OperatingSystemMXBean.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        double ratio = 0.0;
        long start = System.currentTimeMillis();
        long current = System.currentTimeMillis();
        long currentC = 0;
        try {
            currentC = operatingSystemMXBean.getProcessCpuTime();
            //(Long) mBeanServerConnection.getAttribute(operatingSystemMXBean.getObjectName(), "ProcessCpuTime");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int availableProcessors = operatingSystemMXBean.getAvailableProcessors();
        ratio = (currentC - lastC) / 1000000.0 / (current - last) / availableProcessors;
        this.lastC = currentC;
        this.last = current;

        CpuInfo cpuInfo = new CpuInfo();
        cpuInfo.setRatio(ratio * 100);
        cpuInfo.setTime(new Time(new Date()));

        return cpuInfo;

    }
}
