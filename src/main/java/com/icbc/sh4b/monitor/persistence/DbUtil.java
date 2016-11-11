package com.icbc.sh4b.monitor.persistence;

import com.yuezy.monitor.entity.Memory;
import com.yuezy.monitor.util.FileUtil;

public class DbUtil {
	public static void saveToDb(Memory memory) {
		try {
			FileUtil.generateEmptyFile("output/memory.json");
			FileUtil.appendFileAddMode("output/memory.json", memory.toString() + "\r\n", "UTF-8");
		} catch(Exception e) {
			e.printStackTrace();
			
		}
	}
}
