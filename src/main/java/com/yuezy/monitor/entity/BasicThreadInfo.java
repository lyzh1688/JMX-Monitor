package com.yuezy.monitor.entity;

import java.lang.management.LockInfo;
import java.lang.management.ThreadInfo;

/**
 * Created by kfzx-liuyz1 on 2016/11/9.
 */
public class BasicThreadInfo {

    public BasicThreadInfo(ThreadInfo threadInfo){
        this.threadId = threadInfo.getThreadId();
        this.threadName = threadInfo.getThreadName();
        this.threadState = threadInfo.getThreadState();
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public Thread.State getThreadState() {
        return threadState;
    }

    public void setThreadState(Thread.State threadState) {
        this.threadState = threadState;
    }

    private String       threadName;
    private long         threadId;
//    private long         blockedTime;
//    private long         blockedCount;
//    private long         waitedTime;
//    private long         waitedCount;
//    private LockInfo lock;
//    private String       lockName;
//    private long         lockOwnerId;
//    private String       lockOwnerName;
//    private boolean      inNative;
//    private boolean      suspended;
    private Thread.State threadState;
}
