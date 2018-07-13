package com.hzdy.manager.utils;

/**
* 
* what
* 
* @author 
* @version 0.1
*/
public class DeviceResourcesOid {
	/**
	 * 获取系统基本信息 sysDesc GET
	 */
	public static final String sysDesc="1.3.6.1.2.1.1.1.0";
	
	/**
	 * 监控时间 sysUptime GET
	 */
	public static final String sysUptime="1.3.6.1.2.1.1.3.0";
	
	/**
	 * 机器名 sysName GET
	 */
	public static final String sysName="1.3.6.1.2.1.1.5.0";

	/**
	 * 系统联系人 sysContact GET
	 */
	public static final String sysContact="1.3.6.1.2.1.1.4.0";
	
	/**
	 * 机器提供的服务 sysService GET
	 */
	public static final String sysService="1.3.6.1.2.1.1.7.0";
	
	/**
	 * 机器坐在位置 sysLocation GET
	 */
	public static final String sysLocation="1.3.6.1.2.1.1.6.0";
	
	/**
	 * 系统安装的软件列表 hrSWInstalledName WALK
	 */
	public static final String hrSWInstalledName="1.3.6.1.2.1.25.6.3.1.2";
	
	/**
	 * 系统运行的进程列表 hrSWRunName WALK
	 */
	public static final String hrSWRunName="1.3.6.1.2.1.25.4.2.1.2";
	
	/**
	 * 获取内存大小
	 * hrMemorySize GET
	 */
	public static final String hrMemorySize="1.3.6.1.2.1.25.2.2.0";
	/**
	 * Total RAM used
	 * memAvailReal
	 */
	public static final String memTotalReal="1.3.6.1.4.1.2021.4.5.0";
	/**
	 * Total RAM used
	 * memAvailReal
	 */
	public static final String memAvailReal="1.3.6.1.4.1.2021.4.6.0";
	/**
	 * CPU的当前负载，N个核就有N个负载
	 * hrProcessorLoadAvg WALK
	 */
	public static final String hrProcessorLoadAvg="1.3.6.1.2.1.25.3.3.1.2";
	
	/**
	 * 网络接口的数目 GET
	 * ifNumber
	 */
	public static final String ifNumber="1.3.6.1.2.1.2.1.0";
	
	/**
	 * 网络接口信息描述 WALK
	 * ifDescr
	 */
	public static final String ifDescr="1.3.6.1.2.1.2.2.1.2";
	
	/**
	 * 网络接口类型 WALK
	 * ifType
	 */
	public static final String ifType="1.3.6.1.2.1.2.2.1.3";
	
	/**
	 * 接口发送和接收的最大IP数据报[BYTE] WALK
	 * ifMTU
	 */
	public static final String ifMTU="1.3.6.1.2.1.2.2.1.4";
	
	/**
	 * 接口当前带宽[bps] WALK
	 * ifSpeed
	 */
	public static final String ifSpeed="1.3.6.1.2.1.2.2.1.5";
	
	/**
	 * 接口的物理地址 WALK
	 * ifPhysAddress
	 */
	public static final String ifPhysAddress="1.3.6.1.2.1.2.2.1.6";
	
	/**
	 * 接口当前操作状态[up|down] WALK
	 * ifOperStatus
	 */
	public static final String ifOperStatus="1.3.6.1.2.1.2.2.1.8";
	
	/**
	 * 接口收到的字节数 WALK
	 * ifInOctet
	 */
	public static final String ifInOctet="1.3.6.1.2.1.2.2.1.10";
	
	/**
	 * 接口发送的字节数 WALK
	 * ifOutOctet
	 */
	public static final String ifOutOctet="1.3.6.1.2.1.2.2.1.16";
	
	/**
	 * 接口收到的数据包个数 WALK
	 * ifInUcastPkts
	 */
	public static final String ifInUcastPkts="1.3.6.1.2.1.2.2.1.11";
	
	/**
	 * 	接口发送的数据包个数 WALK
	 * ifOutUcastPkts
	 */
	public static final String ifOutUcastPkts="1.3.6.1.2.1.2.2.1.17";

}
