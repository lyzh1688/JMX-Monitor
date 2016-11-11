package com.yuezy.monitor.util;
/**
 * 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件管理功能类
 * <pre>
 *	用到什么功能追加什么功能。
 * </pre>
 *
 * <pre>
 * modify by kfzx-jinjf on 2014-11-4
 *    fix->1.
 *         2.
 * </pre> 
 */
public class FileUtil {
	public static List<String> readFile(String fileName, String encode)  {
		List<String> listFile = new ArrayList<String>();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(fileName);
			isr = new InputStreamReader(fis, encode);
			br = new BufferedReader(isr);
			String rowData = null;// 一行
			while ((rowData = br.readLine()) != null) {
				listFile.add(rowData);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					listFile = new ArrayList<String>();
					e.printStackTrace();
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					listFile = new ArrayList<String>();
					e.printStackTrace();
				}
			}
		}
		return listFile;
	}
	/**
	 * 生成空文件
	 * @author kfzx-jinjf
	 * @param fileName 需要生成的文件全路径名
	 * @return 生成的文件File对象，若生成失败，返回null;若创建前对应文件已经存在，则返回该文件
	 * */
	public static File generateEmptyFile(String fileName) {
		File f = null;
		try {
			f = new File(fileName);	
			if(f.exists())
				return f;
			return f.createNewFile() ? f : null;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static File generateNewEmptyFile(String fileName) {
		File f = null;
		try {
			f = new File(fileName);	
			f.delete();
			f.createNewFile();
			return f;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 追加文件
	 * @author kfzx-jinjf
	 * @param fileName 需要追加的文件全路径名
	 * @param strArr 追加的String数组
	 * @param encoding 编码格式
	 * @return 文件File对象，若生成失败，返回null;
	 * */
	public static File appendFile(String fileName, String[] strArr, String encoding) {
		File f = generateEmptyFile(fileName);		
		if(f == null)
			return null;	
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {			
			fos = new FileOutputStream(f); 
			osw = new OutputStreamWriter(fos, encoding);
			for(int i=0; i<strArr.length; i++) {
				osw.write(strArr[i]);
			}
	        osw.flush(); 
	        osw.close();
	        fos.close();
		}catch(Exception e) {
			try {
				if(osw != null)
					osw.close();
				if(fos != null)
					fos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return null;
		}
		return f;
	}
	
	public static File appendFileAddMode(String fileName, String fileContent, String encoding) {
		return appendFile(fileName, new String[]{fileContent});
	}
	/**
	 * 追加文件
	 * @author kfzx-jinjf
	 * @param fileName 需要追加的文件全路径名
	 * @param strArr 追加的String数组
	 * @param encoding 编码格式
	 * @return 文件File对象，若生成失败，返回null;
	 * */
	public static File appendFile(String fileName, String fileContent, String encoding) {
		File f = generateEmptyFile(fileName);		
		if(f == null)
			return null;	
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {			
			fos = new FileOutputStream(f); 
			osw = new OutputStreamWriter(fos, encoding); 
	        osw.write(fileContent);
	        osw.flush(); 
	        osw.close();
	        fos.close();
		}catch(Exception e) {
			try {
				if(osw != null)
					osw.close();
				if(fos != null)
					fos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return null;
		}
		return f;
	}
	
	/**
	 * 追加文件
	 * @author kfzx-jinjf
	 * @param fileName 需要追加的文件全路径名
	 * @param strArr 追加的String数组
	 * @return 文件File对象，若生成失败，返回null;
	 * */
	public static File appendFile(String fileName, String[] strArr) {
		File f = generateEmptyFile(fileName);
		if(f == null)
			return null;
		FileWriter w = null;
		try {
			w = new FileWriter(f, true);
			for(int i=0; i<strArr.length; i++) {
				w.write(strArr[i]);				
			}
			w.flush();
			w.close();
		}catch(Exception e) {
			try {
				if(w != null)
					w.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return null;
		}
		return f;
	}
	
	/**
	 * 追加文件一行一行追加
	 * @author kfzx-jinjf
	 * @param fileName 需要追加的文件全路径名
	 * @param strArr 追加的String数组
	 * @return 文件File对象，若生成失败，返回null;
	 * */
	public static File appendFileLineByLine(String fileName, String[] strArr) {
		File f = generateEmptyFile(fileName);
		if(f == null)
			return null;
		FileWriter w = null;
		try {
			w = new FileWriter(f, true);
			for(int i=0; i<strArr.length; i++) {
				w.write(strArr[i] + "\r\n");
			}
			w.flush();
			w.close();
		}catch(Exception e) {
			try {
				if(w != null)
					w.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return null;
		}
		return f;
	}
	
	/**
	 * 追加文件一行一行追加
	 * @author kfzx-jinjf
	 * @param fileName 需要追加的文件全路径名
	 * @param strArr 追加的String数组
	 * @return 文件File对象，若生成失败，返回null;
	 * */
	public static File appendFileLineByLine(String fileName, String[] strArr, String encoding) {
		File f = generateEmptyFile(fileName);
		if(f == null)
			return null;
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {			
			fos = new FileOutputStream(f); 
			osw = new OutputStreamWriter(fos, encoding);
			for(int i=0; i<strArr.length; i++) {
				osw.write(strArr[i] + "\r\n");
			}
	        osw.flush(); 
	        osw.close();
	        fos.close();			
		}catch(Exception e) {
			try {
				if(osw != null)
					osw.close();
				if(fos != null)
					fos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return null;
		}
		return f;
	}
	
	/**
	 * 用count个ch左补齐num
	 * @author kfzx-jinjf
	 * */
	public static String leftPadding(int count, char ch, int num) {
		String srcStr = Integer.toString(num);
		String paddingStr = "";
		int remainCount = count - srcStr.length();
		if(remainCount < 0) {
			return srcStr.substring(srcStr.length() - count);
		}
		for(int i=0; i<remainCount; i++) {
			paddingStr += ch;
		}
		return paddingStr + srcStr;
	}
	
	/**
	 * 追加文件一行一行追加
	 * e.g. 如果strArr的长度为20，filename为HelloWorld.TXT maxLineCount为6，seqLength为5
	 * 则会生成如下文件： HelloWorld00001.TXT HelloWorld00002.TXT HelloWorld00003.TXT HelloWorld00004.TXT
	 * 其中前3个文件为6行，最后一个文件为2行（3*6+2 = 20）
	 * @author kfzx-jinjf
	 * @param fileName 需要追加的文件全路径名
	 * @param strArr 追加的String数组
	 * @param maxLineCount 每个文件最大行数
	 * @param seqLength 文件名流水号长度
	 * @return 文件File List对象，若生成失败，返回null;
	 * */
	public static List<File> appendFileLineByLine(String fileName, String[] strArr, int maxLineCount, int seqLength) {
		List<File> fileList = new ArrayList<File>();
		if(strArr.length == 0)
			return fileList;
		FileWriter w = null;
		File f = null;
		try {
			for(int i=0; i<strArr.length; i++) {
				if(i%maxLineCount == 0 && i != 0) {
					w.flush();
					w.close();
					fileList.add(f);
				}
				
				if(i%maxLineCount == 0) {
					String seq = leftPadding(seqLength, '0', i/maxLineCount + 1);
					String[] tmpArr = fileName.split("[.]");
					String suffix = tmpArr[tmpArr.length-1];
					String tmpFileName = tmpArr[0];
					for(int j=1; j<tmpArr.length-1; j++) {
						tmpFileName += ("." + tmpArr[j]);
					}
					tmpFileName += (seq + "." + suffix);
					f = generateEmptyFile(tmpFileName);
					if(f == null)
						return null;
					w = new FileWriter(f, true);
				}
				
				if ((i % maxLineCount != maxLineCount - 1 || 0 == i)
						&& i != strArr.length - 1) {// 不是最后一行都要换行，最后一行不需要换行
					w.write(strArr[i] + "\r\n");
				} 
				else {
					w.write(strArr[i]);
				}
					
			}	
			w.flush();
			w.close();
			fileList.add(f);
			
		}catch(Exception e) {
			try {
				if(w != null)
					w.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return null;
		}
		return fileList;
	}
	
	/**
	 * 追加文件一行一行追加
	 * e.g. 如果strArr的长度为20，filename为HelloWorld.TXT maxLineCount为6，seqLength为5
	 * 则会生成如下文件： HelloWorld00001.TXT HelloWorld00002.TXT HelloWorld00003.TXT HelloWorld00004.TXT
	 * 其中前3个文件为6行，最后一个文件为2行（3*6+2 = 20）
	 * @author kfzx-jinjf
	 * @param fileName 需要追加的文件全路径名
	 * @param strArr 追加的String数组
	 * @param maxLineCount 每个文件最大行数
	 * @param seqLength 文件名流水号长度
	 * @return 文件File List对象，若生成失败，返回null;
	 * */
	public static List<File> appendFileLineByLine(String fileName, String[] strArr, int maxLineCount, int seqLength, String encoding) {
		List<File> fileList = new ArrayList<File>();
		if(strArr.length == 0)
			return fileList;
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		File f = null;
		try {
			for(int i=0; i<strArr.length; i++) {
				if(i%maxLineCount == 0 && i != 0) {
					osw.flush();
					osw.close();
					fos.close();
					fileList.add(f);
				}
				
				if(i%maxLineCount == 0) {
					String seq = leftPadding(seqLength, '0', i/maxLineCount + 1);
					String[] tmpArr = fileName.split("[.]");
					String suffix = tmpArr[tmpArr.length-1];
					String tmpFileName = tmpArr[0];
					for(int j=1; j<tmpArr.length-1; j++) {
						tmpFileName += ("." + tmpArr[j]);
					}
					tmpFileName += (seq + "." + suffix);
					f = generateEmptyFile(tmpFileName);
					if(f == null)
						return null;
					fos = new FileOutputStream(f); 
					osw = new OutputStreamWriter(fos, encoding);			        
				}
				
				if ((i % maxLineCount != maxLineCount - 1 || 0 == i)
						&& i != strArr.length - 1) {// 不是最后一行都要换行，最后一行不需要换行
					osw.write(strArr[i] + "\r\n");
				} 
				else {
					osw.write(strArr[i]);
				}
					
			}	
			osw.flush();
			osw.close();
			fos.close();
			fileList.add(f);
			
		}catch(Exception e) {
			try {
				if(osw != null)
					osw.close();
				if(fos != null)
					fos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return null;
		}
		return fileList;
	}
	
	public static void main(String[] args) {
		System.out.println();
		String fileName = "D:\\HelloWorldGBK.TXT";
		String fileName2 = "D:\\HelloWorldUTF8.TXT";
		String[] strArr = new String[20];
		for(int i=0; i<strArr.length; i++) {
			strArr[i] = i + "你好你好你好你好你好你好啦啦啦你好你好你好你好你好你好啦啦啦你好你好你好你好你好你好";
		}
		long s = System.currentTimeMillis();
		List<File> fileList = FileUtil.appendFileLineByLine(fileName, strArr, 6, 5, "GBK");
		long e = System.currentTimeMillis();
		System.out.println("filename:" + fileList.toString() +" time used: " + (e-s)/1000 + "s");
		List<File> fileList2 = FileUtil.appendFileLineByLine(fileName2, strArr, 6, 5, "UTF-8");
		e = System.currentTimeMillis();
		System.out.println("filename:" + fileList2.toString() +" time used: " + (e-s)/1000 + "s");
		
		fileName = "D:\\TEST2.TXT";
		strArr = new String[20];
		for(int i=0; i<strArr.length; i++) {
			strArr[i] = i + "hfjkds中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国hfsdkj<img src='sasa' /> fjldsajflkdsjaflkdsjalf <img src='sada' ait=''/>sfdsfadas\r\n";
		}
		s = System.currentTimeMillis();
		FileUtil.appendFile(fileName, strArr, "GBK");
		e = System.currentTimeMillis();
		System.out.println("filename:" + fileName.toString() +" time used: " + (e-s)/1000 + "s");
		
	}


}
