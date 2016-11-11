package com.yuezy.monitor.entity;

/**
 * Created by kfzx-liuyz1 on 2016/11/9.
 */
public class CpuInfo {

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    private double ratio;
    private Time time;
}
