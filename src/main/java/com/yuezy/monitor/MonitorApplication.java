package com.yuezy.monitor;

import com.yuezy.monitor.entity.Memory;
import com.yuezy.monitor.service.impl.MemoryMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 胡志洁 on 2016/11/4.
 */
@SpringBootApplication
@EnableConfigurationProperties
@RestController
public class MonitorApplication {

    @Autowired
    private MemoryMonitorService memoryMonitorService;

    @RequestMapping("/monitorMemory")
    public Memory monitorMemory(){
        return this.memoryMonitorService.getHeapMemoryUsage();
    }

    public static void main(String[] args){
        SpringApplication.run(MonitorApplication.class,args);
    }
}
