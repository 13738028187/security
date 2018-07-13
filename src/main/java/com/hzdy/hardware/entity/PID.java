package com.hzdy.hardware.entity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Locale;
import java.util.StringTokenizer;

public final class PID {
	public PID() {
		super();
	}

	public static void main(String args[]) {
		final RuntimeMXBean rtb = ManagementFactory.getRuntimeMXBean();
		final String processName = rtb.getName();
		System.out.println(processName);
		System.out.println(getPID());
	}
	//获取系统当前的PID
	public static String getPID() {
		String pid = System.getProperty("pid");
		//如果为空
		if (pid == null) {
			final RuntimeMXBean rtb = ManagementFactory.getRuntimeMXBean();
			final String processName = rtb.getName();
			if (processName.indexOf('@') != 1) {
				pid = processName.substring(0, processName.indexOf('@'));
			} else {
				pid = getPIDFromOS();
			}
		}
		return pid;
	}
	//通过系统命令获取PID(Linux或则Windows)
	private static String getPIDFromOS() {
		String pid;
		final String[] cmd;
		File tempFile = null;
		Process process = null;
		try {
			try {
				//通过shell脚本
				if (!System.getProperty("os.name").toLowerCase(Locale.ENGLISH).toLowerCase().contains("windows")) {
					cmd = new String[] { "/bin/sh", "-c", "echo $$ $PPID" };
				} else {
					//自带的一个获取windows线程的文件
					//建立一个临时文件
					tempFile = File.createTempFile("getpids", ".exe");
					//存放在resuource包下
					extractGetPid(tempFile);
					cmd = new String[] { tempFile.getAbsolutePath() };
				}
				//运行命令
				process = Runtime.getRuntime().exec(cmd);
				final ByteArrayOutputStream bout = new ByteArrayOutputStream();
				//通过pump将数据存放在bout中
				pump(process.getInputStream(),bout);
				final StringTokenizer stok = new StringTokenizer(bout.toString());
				stok.nextToken();
				pid = stok.nextToken();
				//等待
				process.waitFor();
			} finally {
				if (process != null) {
					process.getInputStream().close();
					process.getOutputStream().close();
					process.getErrorStream().close();
					process.destroy();
				}
				if (tempFile != null && !tempFile.delete()) {
					tempFile.deleteOnExit();
				}
			}
		} catch (final InterruptedException e) {
			pid = e.toString();
		} catch (final IOException e) {
			pid = e.toString();
		}
		return pid;
	}

	private static void extractGetPid(File tempFile) throws IOException {
		final OutputStream output=new FileOutputStream(tempFile);
		try{
			final InputStream input=PID.class.getResourceAsStream("resource/getpids.exe");
			try{
				pump(input,output);
			}finally{
				input.close();
			}
		}finally{
			output.close();
		}

	}

	private static void pump(InputStream input, OutputStream output) throws IOException {
		final byte[] bytes=new byte[4*1024];
		int length=input.read(bytes);
		while(length!=-1){
			output.write(bytes,0,length);
			length=input.read(bytes);
		}
		
	}
}
