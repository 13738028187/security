package com.hzdy.hardware.entity;

import java.io.Serializable;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import javax.management.JMException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import com.hzdy.hardware.I18N;

public final class MemoryInformations implements Serializable {
	private static final long serialVersionUID = 3281861236369720876L;
	private static final String NEXT = ",\n";
	private static final String MO = " Mo";
	// ObjectName 表示一个Mbean的对象名称
	private static final Set<ObjectName> NIO_BUFFER_POOLS = new HashSet<ObjectName>();
	static {
		try {
			NIO_BUFFER_POOLS.addAll(new MBeans().getNioBufferPools());
		} catch (final MalformedObjectNameException e) {
			e.toString();
		}

	}
	// 已使用的内存
	private final long usedMemory;
	// 最大内存
	private final long maxMemory;
	/*
	 * // 已使用的永久带内存 private final long usedPermGen; // 最大永久带内存 private final
	 * long maxPermGen;
	 */
	// 被使用的非堆内存
	private final long usedNonHeapMemory;
	// 被使用的缓存内存
	/* private final long usedBufferedMemory; */
	// 测量GC时间
	/* private final long garbageCollectionTimeMillis; */
	// 被使用的物理内存大小
	private final long usedPhysicalMemorySize;
	// 被使用的交换内存大小
	private final long usedSwapSpaceSize;
	// 加载类的数量
	private final int loadedClassesCount;
	// 内存细节
	private final MemoryDetails memoryDetails;

	public MemoryInformations() {
		super();
		// 被使用的内存:总内存-空内存
		usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		maxMemory = Runtime.getRuntime().maxMemory();

		// start
		/*
		 * 内存池的管理界面。内存池代表Java虚拟机管理的内存资源，由一个或多个内存管理器管理
		 * Java虚拟机:用于对象分配的堆(Heap)，维护方法区域(Method Area,)和Java虚拟机执行的非堆内存(NoHeap)
		 * 
		 * 内存使用监控 内存池具有以下几种属性 1.内存使用率 2.内存使用高峰 3.使用阈值 4.集合使用阈值（仅由一些垃圾回收内存池支持）
		 * 
		 * 
		 */
		/*
		 * final MemoryPoolMXBean permGenMemoryPool = getPermGenMemoryPool(); if
		 * (permGenMemoryPool != null) { // 通过MemoryUsage可以查看Java
		 * 虚拟机的内存池的内存使用情况。 final MemoryUsage usage =
		 * permGenMemoryPool.getUsage(); // 被使用的永久代 usedPermGen =
		 * usage.getUsed(); // 最大永久代 maxPermGen = usage.getMax(); } else {
		 * usedPermGen = -1; maxPermGen = -1; }
		 */

		// end

		// MemoryUsage.getUsed():返回以字节为单位的已用内存量。
		usedNonHeapMemory = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed();
		// getClassLoadingMXBean:返回Java虚拟机的类加载系统的托管bean。
		// getLoadedClassCount:返回当前在Java虚拟机中加载的类的数量。
		loadedClassesCount = ManagementFactory.getClassLoadingMXBean().getLoadedClassCount();
		final OperatingSystemMXBean operatingSystem = ManagementFactory.getOperatingSystemMXBean();
		// 判断操作系统
		if (isSunOsMBean(operatingSystem)) {
			// 获取被使用的物理内存大小,通过反射
			// 计算:总物理内存大小-空物理内存大小
			// 交换内存同理
			usedPhysicalMemorySize = (getLongFromOperatingSystem(operatingSystem, "getTotalPhysicalMemorySize")
					- getLongFromOperatingSystem(operatingSystem, "getFreePhysicalMemorySize"));
			usedSwapSpaceSize = getLongFromOperatingSystem(operatingSystem, "getTotalSwapSpaceSize")
					- getLongFromOperatingSystem(operatingSystem, "getFreeSwapSpaceSize");
		} else {
			usedPhysicalMemorySize = -1;
			usedSwapSpaceSize = -1;
		}
		// 获取内存详细信息
		memoryDetails = buildMemoryDetails();
	}

	/**
	 * 构建内存详细信息
	 * 
	 * @return
	 */
	private MemoryDetails buildMemoryDetails() {
		/*
		 * 内存使用 MemoryUsage对象表示内存使用的快照
		 * MemoryUsage类的实例通常由用于获取关于Java虚拟机的单个内存池或整个Java虚拟机的堆或非堆内存的内存使用信息的方法构建
		 * MemoryUsage对象包含四个值： 1.init表示Java虚拟机在启动期间从操作系统请求进行内存管理的初始内存量（以字节为单位）
		 * 2.used表示当前使用的内存量（以字节为单位） 3.committed表示Java虚拟机保证可供使用的内存量（以字节为单位）
		 * 4.max表示可用于内存管理的最大内存量（以字节为单位）
		 */

		MemoryDetails md = new MemoryDetails();
		final DecimalFormat integerFormat = I18N.createIntegerFormat();
		// GC测量时间
		float garbageCollectionTimeMillis = buildGarbageCollectionTimeMillis();
		md.setGarbageCollectionTime(garbageCollectionTimeMillis + " ms");
		/*
		 * final String gc = "Garbage collection time = " +
		 * integerFormat.format(garbageCollectionTimeMillis) + " ms";
		 */
		final OperatingSystemMXBean operatingSystem = ManagementFactory.getOperatingSystemMXBean();
		MemoryUsage hmu = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
		MemoryUsage nhmu = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
		if (isSunOsMBean(operatingSystem)) {

			md.setUsedNoHeapMemory(nhmu.getUsed() / 1024 / 1024 + " MO (MmetaSpace, Code Cache)");
			md.setPrcessCpuTime(
					integerFormat.format(getLongFromOperatingSystem(operatingSystem, "getProcessCpuTime") / 1000000)
							+ "ms");
			md.setUsedHeapMemory(hmu.getUsed() / 1024 / 1024 + MO + "(Eden Space, Survivor Space,Old Gen)");
			md.setTotalJVMMemory((nhmu.getMax() + hmu.getMax())/1024/1024 + MO);
			/*
			 * md.setCommittedVirtualMemory(integerFormat.format(
			 * getLongFromOperatingSystem(operatingSystem,
			 * "getCommittedVirtualMemorySize") / 1024 / 1024) + MO);
			 */
			md.setFreePhysicalMemory(integerFormat.format(
					getLongFromOperatingSystem(operatingSystem, "getFreePhysicalMemorySize") / 1024 / 1024) + MO);
			md.setTotalPhysicalMemory(integerFormat.format(
					getLongFromOperatingSystem(operatingSystem, "getTotalPhysicalMemorySize") / 1024 / 1024) + MO);
			md.setFreeSwapSpace(integerFormat
					.format(getLongFromOperatingSystem(operatingSystem, "getFreeSwapSpaceSize") / 1024 / 1024) + MO);
			md.setTotalSwapScpace(integerFormat
					.format(getLongFromOperatingSystem(operatingSystem, "getTotalSwapSpaceSize") / 1024 / 1024) + MO);
			/*
			 * osInfo = "Process cpu time = " +
			 * integerFormat.format(getLongFromOperatingSystem(operatingSystem,
			 * "getProcessCpuTime") / 1000000) +
			 * " ms,\nCommitted virtual memory = " + integerFormat.format(
			 * getLongFromOperatingSystem(operatingSystem,
			 * "getCommittedVirtualMemorySize") / 1024 / 1024) + MO +
			 * ",\nFree physical memory = " + integerFormat.format(
			 * getLongFromOperatingSystem(operatingSystem,
			 * "getFreePhysicalMemorySize") / 1024 / 1024) + MO +
			 * ",\nTotal physical memory = " + integerFormat.format(
			 * getLongFromOperatingSystem(operatingSystem,
			 * "getTotalPhysicalMemorySize") / 1024 / 1024) + MO +
			 * ",\nFree swap space = " + integerFormat
			 * .format(getLongFromOperatingSystem(operatingSystem,
			 * "getFreeSwapSpaceSize") / 1024 / 1024) + MO +
			 * ",\nTotal swap space = " + integerFormat.format(
			 * getLongFromOperatingSystem(operatingSystem,
			 * "getTotalSwapSpaceSize") / 1024 / 1024) + MO;
			 */ }
		long usedBufferedMemory = getUsedBufferMemory();
		md.setBufferedMemory(integerFormat.format(usedBufferedMemory / 1024 / 1024) + MO);
		if (usedBufferedMemory < 0) {
			return md;
		}
		return md;
	}

	private boolean isSunOsMBean(OperatingSystemMXBean operatingSystem) {
		final String className = operatingSystem.getClass().getName();
		return "com.sun.management.OperatingSystem".equals(className)
				|| "com.sun.management.UnixOperatingSystem".equals(className)
				// sun.management.OperatingSystemImpl pour java 8
				|| "sun.management.OperatingSystemImpl".equals(className);
	}

	// 构建GC测量时间
	private float buildGarbageCollectionTimeMillis() {
		float garbageCollectionTime = 0;
		for (final GarbageCollectorMXBean garbageCollector : ManagementFactory.getGarbageCollectorMXBeans()) {
			garbageCollectionTime += garbageCollector.getCollectionTime();
		}
		return garbageCollectionTime;
	}

	// 获取被使用的缓存内存
	private long getUsedBufferMemory() {
		if (NIO_BUFFER_POOLS.isEmpty()) {
			return -1;
		}
		long result = 0;
		// MBeans里有个管理MBean的ObjectName的List
		final MBeans mBeans = new MBeans();
		try {
			for (final ObjectName objectName : NIO_BUFFER_POOLS) {
				result += (Long) mBeans.getAttribute(objectName, "MemoryUsed");
			}
		} catch (final JMException e) {
			throw new IllegalStateException(e);
		}
		return result;
	}

	// 获取永久代(元空间)内存池
	private MemoryPoolMXBean getPermGenMemoryPool() {
		for (final MemoryPoolMXBean memoryPool : ManagementFactory.getMemoryPoolMXBeans()) {
			if (memoryPool.getName().endsWith("Metaspace")) {
				return memoryPool;
			}
		}
		return null;
	}

	public static long getLongFromOperatingSystem(OperatingSystemMXBean operatingSystem, String methodName) {
		return (Long) getFromOperatingSystem(operatingSystem, methodName);
	}

	public static double getDoubleFromOperatingSystem(OperatingSystemMXBean operatingSystem, String methodName) {
		return (Double) getFromOperatingSystem(operatingSystem, methodName);
	}

	/**
	 * 通过反射机制进行方法的调用
	 * 
	 * @param operatingSystem
	 * @param methodName
	 * @return
	 */
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

	/*
	 * // 获取被使用的永久代百分比 double getUsedPermGenPercentage() { if (usedPermGen > 0
	 * && maxPermGen > 0) { return 100d * usedPermGen / maxPermGen; } return
	 * -1d; }
	 */

	// 获取被使用的内存百分比
	double getUsedMemoryPercentage() {
		return 100d * usedMemory / maxMemory;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getUsedMemory() {
		return usedMemory;
	}

	public long getMaxMemory() {
		return maxMemory;
	}

	public long getUsedSwapSpaceSize() {
		return usedSwapSpaceSize;
	}

	public int getLoadedClassesCount() {
		return loadedClassesCount;
	}

	public MemoryDetails getMemoryDetails() {
		return memoryDetails;
	}

}
