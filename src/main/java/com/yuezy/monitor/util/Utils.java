package com.yuezy.monitor.util;

/**
 * Created by kfzx-liuyz1 on 2016/11/7.
 */
public class Utils {
    public static double convertKB(long src) {

        if (src <= 0L) {
            return 0;
        }
        return (double) src / 1024;
    }
}
