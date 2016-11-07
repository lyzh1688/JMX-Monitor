package com.yuezy.monitor.service;

import com.yuezy.monitor.entity.Memory;

/**
 * Created by kfzx-liuyz1 on 2016/11/7.
 */
public interface IMemoryMonitorService {
    public Memory getHeapMemoryUsage();
}
