package com.hzdy.hardware.entity;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.hzdy.hardware.Parameters;

public class SystemActualInfoFactory {
	private static SystemActualInfoFactory systemActualInfoFactory;

	public static void main(String args[]) {
		SystemActualInfo sai = new SystemActualInfo();
		SystemActualInfoFactory.getInstance().build(sai);
		System.out.println(sai);
	}

	public static SystemActualInfoFactory getInstance() {
		if (systemActualInfoFactory == null) {
			return systemActualInfoFactory = new SystemActualInfoFactory();
		} else {
			return systemActualInfoFactory;
		}
	}

	private static final boolean SYSTEM_CPU_LOAD_ENABLED = "1.7".compareTo(Parameters.JAVA_VERSION) < 0;
	// cpu负载情况
	private double systemCpuLoad;

	private double buildSystemCpuLoad() {
		final OperatingSystemMXBean operatingSystem = ManagementFactory.getOperatingSystemMXBean();
		if (SYSTEM_CPU_LOAD_ENABLED && isSunOsMBean(operatingSystem)) {
			return MemoryInformations.getDoubleFromOperatingSystem(operatingSystem, "getSystemCpuLoad") * 100;
		}
		return -1;
	}

	private boolean isSunOsMBean(OperatingSystemMXBean operatingSystem) {
		final String className = operatingSystem.getClass().getName();
		return "com.sun.management.OperatingSystem".equals(className)
				|| "com.sun.management.UnixOperatingSystem".equals(className)
				// sun.management.OperatingSystemImpl pour java 8
				|| "sun.management.OperatingSystemImpl".equals(className);
	}

	private void buildMemory() {

	}

	public void build(SystemActualInfo systemActualInfo) {
		OperatingSystemMXBean operatingSystem = ManagementFactory.getOperatingSystemMXBean();
		if (isSunOsMBean(operatingSystem)) {
			systemActualInfo.setMaxPhysicalMemorySize((int) (getLongFromOperatingSystem(operatingSystem, "getTotalPhysicalMemorySize")/1024/1024));
			systemActualInfo.setMaxSwapSpaceSize((int) (getLongFromOperatingSystem(operatingSystem, "getTotalSwapSpaceSize")/1024/1024));
			systemActualInfo.setUsedPhysicalMemorySize((int) ((getLongFromOperatingSystem(operatingSystem, "getTotalPhysicalMemorySize")- getLongFromOperatingSystem(operatingSystem, "getFreePhysicalMemorySize"))/1024/1024));
			systemActualInfo.setUsedSwapSpaceSize((int) ((getLongFromOperatingSystem(operatingSystem, "getTotalSwapSpaceSize")- getLongFromOperatingSystem(operatingSystem, "getFreeSwapSpaceSize"))/1024/1024));
		} else {
			systemActualInfo.setMaxPhysicalMemorySize(-1);
			systemActualInfo.setMaxSwapSpaceSize(-1);
			systemActualInfo.setUsedPhysicalMemorySize(-1);
			systemActualInfo.setUsedSwapSpaceSize(-1);
		}
		systemActualInfo.setSystemCpuLoad(buildSystemCpuLoad());
		systemActualInfo.setStartDate((new SimpleDateFormat("HH:mm:ss")).format(new Date()));
		systemActualInfo.setMaxMemory((int) (Runtime.getRuntime().maxMemory()/1024/1024));
		systemActualInfo.setUsedMemory((int) ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1024/1024));
	}

	static Object getFromOperatingSystem(OperatingSystemMXBean operatingSystem, String methodName) {
		try {
			// 得到operatingSystem中的一个方法
			final Method method = operatingSystem.getClass().getMethod(methodName, (Class<?>[]) null);
			// 设置为可使用
			method.setAccessible(true);
			// 调用方法
			return method.invoke(operatingSystem, (Object[]) null);
		} catch (final InvocationTargetException e) {
			if (e.getCause() instanceof Error) {
				throw (Error) e.getCause();
			} else if (e.getCause() instanceof RuntimeException) {
				throw (RuntimeException) e.getCause();
			}
			throw new IllegalStateException(e.getCause());
		} catch (final NoSuchMethodException e) {
			throw new IllegalArgumentException(e);
		} catch (final IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	public static long getLongFromOperatingSystem(OperatingSystemMXBean operatingSystem, String methodName) {
		return (Long) getFromOperatingSystem(operatingSystem, methodName);
	}

	public static double getDoubleFromOperatingSystem(OperatingSystemMXBean operatingSystem, String methodName) {
		return (Double) getFromOperatingSystem(operatingSystem, methodName);
	}
}
