package com.hzdy.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 
 * 这个类主要是用于管理文件的增删改查
 * 
 * @author kirohuji
 * @version 0.1
 */
public  class FileUtils {
	private static String path = "C:\\Users\\70767\\git\\security\\src\\main\\webapp\\resources\\json\\";

	/**
	 * 这个方法用来读取文件中的数据
	 * 
	 * @param fileName
	 *            文件名
	 * @return 返回读取的数据
	 */
	public static String read(String fileName) {
		StringBuilder sb=new StringBuilder();
		try {
			String read;
			File file = new File(path + fileName);
			BufferedReader br = new BufferedReader(new FileReader(file));
			while((read=br.readLine())!=null) {
				sb.append(read);
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 这个方法用来创建文件
	 * 
	 * @param fileName
	 *            文件名
	 * @return true表示创建成功
	 */
	public static boolean create(String fileName) {
		File file = new File(path + fileName);
		try {
			if (!isExists(file)) {
				file.createNewFile();
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 这个方法用来删除文件
	 * 
	 * @param fileName
	 *            文件名
	 * @return true表示成功,false表示不成功
	 */
	public static boolean delete(String fileName) {
		File file = new File(path + fileName);
		if(isExists(file)&&file.isFile()) {
			return file.delete();
		}else {
			return false;
		}
	}

	/**
	 * 这个方法用来清除文件中的内容
	 * 
	 * @param fileName
	 *            文件名
	 * @return true表示成功,false表示不成功
	 */
	public static boolean clear(String fileName) {
		return write(fileName,"");
	}

	/**
	 * 这个方法用来写入内容
	 * 
	 * @param fileName
	 *            文件名
	 * @param Content
	 *            写入的内容
	 * @return true表示成功,false表示不成功
	 */
	public static boolean write(String fileName, String content) {
		File file = new File(path + fileName);
		try {
			if(!isExists(file)) {
				file.createNewFile();
			}
			FileWriter fw=new FileWriter(file,true);
			BufferedWriter bw=new BufferedWriter(fw);
			bw.write(content);
			bw.flush();
			bw.close();
			return true;
		}catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 这个方法用来覆盖文件中的内容
	 * 
	 * @param fileName
	 *            文件名
	 * @param Content
	 *            新的内容
	 * @return true表示成功,false表示不成功
	 */
	public static boolean cover(String fileName, String content) {
		if(clear(fileName)) {
			return write(fileName,content);
		}else {
			return false;
		}
	}

	/**
	 * 这个方法用来追加文件中的内容
	 * 
	 * @param fileName
	 *            文件名
	 * @param Content
	 *            追加的内容
	 * @return true表示成功,false表示不成功
	 */
	public static boolean append(String fileName, String content) {
		try {
			RandomAccessFile randomfile=new RandomAccessFile(path + fileName,"rw");
		    long fileLength=randomfile.length();
		    randomfile.seek(fileLength);
		    randomfile.writeBytes(content);
		    randomfile.close();
		    return true;
		}catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 这个方法用来判断此文件是否存在
	 * 
	 * @param file
	 *            文件名
	 * @return true表示存在,false表示不存在
	 */
	public static boolean isExists(File file) {
		if(file.exists()) {
			return true;
		}else {
			return false;
		}
	}
}
