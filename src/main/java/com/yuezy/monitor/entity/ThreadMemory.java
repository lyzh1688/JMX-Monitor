package com.yuezy.monitor.entity;

/**
 * Created by kfzx-liuyz1 on 2016/11/10.
 */
public class ThreadMemory {
    public long getMemory() {
        return memory;
    }

    public void setMemory(long memory) {
        this.memory = memory;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public long memory;

    public String threadName;

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    private Time time;
}
