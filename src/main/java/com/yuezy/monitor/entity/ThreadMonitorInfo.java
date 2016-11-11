package com.yuezy.monitor.entity;

import java.lang.management.ThreadInfo;
import java.util.List;

/**
 * Created by kfzx-liuyz1 on 2016/11/9.
 */
public class ThreadMonitorInfo {


    //private List<BasicThreadInfo> threadInfoList;

    private int runnable;

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    private Time time;

    public int getNotRunnable() {
        return notRunnable;
    }

    public void setNotRunnable(int notRunnable) {
        this.notRunnable = notRunnable;
    }

    public int getRunnable() {
        return runnable;
    }

    public void setRunnable(int runnable) {
        this.runnable = runnable;
    }

    private int notRunnable;

}
