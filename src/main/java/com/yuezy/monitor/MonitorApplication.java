package com.yuezy.monitor;

import com.yuezy.monitor.entity.CpuInfo;
import com.yuezy.monitor.entity.Memory;
import com.yuezy.monitor.entity.ThreadMemory;
import com.yuezy.monitor.entity.ThreadMonitorInfo;
import com.yuezy.monitor.service.ICpuMonitorService;
import com.yuezy.monitor.service.IMemoryMonitorService;
import com.yuezy.monitor.service.IThreadMonitorService;
import com.yuezy.monitor.service.impl.MemoryMonitorService;
import com.yuezy.monitor.service.impl.ThreadMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 胡志洁 on 2016/11/4.
 */
@SpringBootApplication
@EnableConfigurationProperties
@RestController
public class MonitorApplication {

    @Autowired
    private IMemoryMonitorService memoryMonitorService;

    @Autowired
    private IThreadMonitorService threadMonitorService;


    @Autowired
    private ICpuMonitorService cpuMonitorService;

    @RequestMapping("/monitorCpu")
    public CpuInfo monitorCpu(){
        return this.cpuMonitorService.getCpuMonitorInfo();
    }

    @RequestMapping("/monitorMemory")
    public Memory monitorMemory(){
        return this.memoryMonitorService.getHeapMemoryUsage();
    }

    @RequestMapping("/monitorThread")
    public ThreadMonitorInfo monitorThread(){
        return this.threadMonitorService.getThreadInfo();
    }

    @RequestMapping("/monitorThreadMemory")
    public List<ThreadMemory> monitorThreadMemory(){
        return this.memoryMonitorService.getThreadMemoryUsage();
    }
    public static void main(String[] args){
        SpringApplication.run(MonitorApplication.class,args);
    }
}
