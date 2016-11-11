package com.yuezy.monitor.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import sun.applet.Main;

public class Time {
	private Date date;
	private String hour;
	private String min;
	private String sec;
	private String mill;
	public Time(Date date) {
		this.date = date;
	}
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String toString() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");	
		return f.format(this.date);
	}
	
	//private SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");	
	public String getHour() {
		SimpleDateFormat f = new SimpleDateFormat("HH");
		return f.format(this.date);
	}
	
	public String getMin() {
		SimpleDateFormat f = new SimpleDateFormat("mm");
		return f.format(this.date);
	}
	
	public String getSec() {
		SimpleDateFormat f = new SimpleDateFormat("ss");
		return f.format(this.date);
	}
	
	public String getMill() {
		SimpleDateFormat f = new SimpleDateFormat("SSS");
		return f.format(this.date);
	}
	public static void main(String[] args) {
		Time time = new Time(new Date());
		System.out.println(time.getHour());
		System.out.println(time.getMin());
		System.out.println(time.getSec());
		System.out.println(time.getMill());
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public void setSec(String sec) {
		this.sec = sec;
	}
	public void setMill(String mill) {
		this.mill = mill;
	}
}
