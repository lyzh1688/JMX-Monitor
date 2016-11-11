package com.yuezy.monitor.service;

import com.yuezy.monitor.entity.Memory;
import com.yuezy.monitor.entity.ThreadMemory;

import java.util.List;

/**
 * Created by kfzx-liuyz1 on 2016/11/7.
 */
public interface IMemoryMonitorService {
    public Memory getHeapMemoryUsage();
    public List<ThreadMemory> getThreadMemoryUsage();

}
