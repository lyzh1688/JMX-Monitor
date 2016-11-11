package com.yuezy.monitor.service;

import com.yuezy.monitor.entity.ThreadMonitorInfo;

import java.lang.management.ThreadInfo;
import java.util.List;

/**
 * Created by kfzx-liuyz1 on 2016/11/9.
 */
public interface IThreadMonitorService {
    public ThreadMonitorInfo getThreadInfo();

}
