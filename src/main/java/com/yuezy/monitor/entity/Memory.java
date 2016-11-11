package com.yuezy.monitor.entity;

/**
 * Created by kfzx-liuyz1 on 2016/11/7.
 */
public class Memory {
    public double getCommitted() {
        return committed;
    }
    
    public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public void setCommitted(double committed) {
        this.committed = committed;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getInit() {
        return init;
    }

    public void setInit(double init) {
        this.init = init;
    }

    public double getUsed() {
        return used;
    }

    public void setUsed(double used) {
        this.used = used;
    }
    
    

    @Override
	public String toString() {
		return "Memory [committed=" + committed + ", init=" + init + ", max=" + max + ", used=" + used + ", time="
				+ time.toString() + "]";
	}



	private double committed;
    private double init;
    private double max;
    private double used;
    private Time time;
}
