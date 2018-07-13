package com.hzdy.hardware.entity;

import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.Locale;

import com.hzdy.hardware.Parameters;

public class SystemInfoFactory {
	public static SystemInfoFactory systemInfoFactory;
	public static SystemInfoFactory getInstance(){
		if(systemInfoFactory==null){
			return systemInfoFactory=new SystemInfoFactory();
		}else{
			return systemInfoFactory;
		}
	}
	public void build(SystemInfo systemInfo){
		//获取系统的主机名
		systemInfo.setHost(Parameters.getHostName() + '@' + Parameters.getHostAddress());
		//获取系统名
		systemInfo.setOs( buildOS());
		// 通过Runtime.getRuntime()获取当前运行时,调用方法获取可用的CPU个数
		systemInfo.setAvailableProcessors(Runtime.getRuntime().availableProcessors());
		//获取Java版本
		systemInfo.setJavaVersion( System.getProperty("java.runtime.name") + ", " + System.getProperty("java.runtime.version"));
		//获取JVM版本
		systemInfo.setJvmVersion(System.getProperty("java.vm.name") + ", " + System.getProperty("java.vm.version") + ", "
				+ System.getProperty("java.vm.info"));
		//获取应用程序开始时间
		systemInfo.setStartDate(new Date());
		//获取JVM参数
		systemInfo.setJvmArguments(buildJvmArguments());
		//获取当前的PID
		systemInfo.setPid( PID.getPID());
	}
	private String buildOS() {
		final String name = System.getProperty("os.name");
		final String version = System.getProperty("os.version");
		final String patchLevel = System.getProperty("sun.os.patch.level");
		final String arch = System.getProperty("os.arch");
		final String bits = System.getProperty("sun.arch.data.model");
		final StringBuilder sb = new StringBuilder();
		sb.append(name).append(", ");
		if (!name.toLowerCase(Locale.ENGLISH).contains("windows")) {
			sb.append(version).append(' ');
		}
		if (!"unknown".equals(patchLevel)) {
			sb.append(patchLevel);
		}
		sb.append(", ").append(arch).append('/').append(bits);
		return sb.toString();
	}

	private String buildJvmArguments() {
		final StringBuilder jvmArgs = new StringBuilder();
		// Returns the managed bean for the runtime system of the Java virtual
		// machine.
		// ManagementFactory.getRuntimeMXBean():返回Java虚拟机的运行系统的管理Bean
		// getInputArguments:返回传递给Java虚拟机的输入参数，该虚拟机不包括main方法的参数。
		for (final String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
			jvmArgs.append(jvmArg).append('\n');
		}
		if (jvmArgs.length() > 0) {
			jvmArgs.deleteCharAt(jvmArgs.length() - 1);
		}

		return jvmArgs.toString();
	}

}
