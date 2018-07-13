package com.hzdy.manager.utils;
/**
* 
* what
* 
* @author 
* @version 0.1
*/
/**
 * @author kirohuji
 *
 */
public class OidGet {

	/**
	 * 系统基本信息 SysDesc
	 */
	public static final String SysDesc = ".1.3.6.1.2.1.1.1.0";

	/**
	 * 系统标识Id SysObjectID
	 */
	public static final String SysObjectID = ".1.3.6.1.2.1.1.2.0";

	/**
	 * 监控时间 SysUptime
	 */
	public static final String SysUptime = ".1.3.6.1.2.1.1.3.0";

	/**
	 * 系统联系人 SysContact
	 */
	public static final String SysContact = ".1.3.6.1.2.1.1.4.0";

	/**
	 * 机器名称 SysName
	 */
	public static final String SysName = ".1.3.6.1.2.1.1.5.0";

	/**
	 * 机器所在位置 SysLocation
	 */
	public static final String SysLocation = ".1.3.6.1.2.1.1.6";

	/**
	 * 机器提供的服务 SysServIce
	 */
	public static final String SysServIce = ".1.3.6.1.2.1.1.7.0";

	/**
	 * 机器网络接口的数量 IfNumber
	 */
	public static final String IfNumber = ".1.3.6.1.2.1.2.1.0";

	/**
	 * Unique value for each interface IfIndex
	 */
	public static final String IfIndex = ".1.3.6.1.2.1.2.2.1.1";

	public static final String IfDescr = ".1.3.6.1.2.1.2.2.1.2";

	public static final String IfType = ".1.3.6.1.2.1.2.2.1.3";

	public static final String IfMtu = ".1.3.6.1.2.1.2.2.1.4";

	public static final String IfSpeed = ".1.3.6.1.2.1.2.2.1.5";

	public static final String IfPhysAddress = ".1.3.6.1.2.1.2.2.1.6";

	public static final String IfAdminStatus = ".1.3.6.1.2.1.2.2.1.7";

	public static final String IfOperStatus = ".1.3.6.1.2.1.2.2.1.8";

	public static final String IfLastChange = ".1.3.6.1.2.1.2.2.1.9";
	public static final String IfInOctets = ".1.3.6.1.2.1.2.2.1.10";
	public static final String IfInUcastPkts = ".1.3.6.1.2.1.2.2.1.11";
	public static final String IfInNUcastPkts = ".1.3.6.1.2.1.2.2.1.12";
	public static final String IfInDiscards = ".1.3.6.1.2.1.2.2.1.13";
	public static final String IfInErrors = ".1.3.6.1.2.1.2.2.1.14";
	public static final String IfInUnknownProtos = ".1.3.6.1.2.1.2.2.1.15";
	public static final String IfOutOctets = ".1.3.6.1.2.1.2.2.1.16";
	public static final String IfOutUcastPkts = ".1.3.6.1.2.1.2.2.1.17";
	public static final String IfOutNUcastPkts = ".1.3.6.1.2.1.2.2.1.18";
	public static final String IfOutDiscards = ".1.3.6.1.2.1.2.2.1.19";
	public static final String IfOutErrors = ".1.3.6.1.2.1.2.2.1.20";
	public static final String IfOutQLen = ".1.3.6.1.2.1.2.2.1.21";
	public static final String IfSpecifIc = ".1.3.6.1.2.1.2.2.1.22";

	/**
	 * 用户CPU百分比 SsCpuUser
	 */
	public static final String SsCpuUser = ".1.3.6.1.4.1.2021.11.9.0";

	/**
	 * 系统CPU百分比 SsCpuSystem
	 */
	public static final String SsCpuSystem = ".1.3.6.1.4.1.2021.11.10.0";

	/**
	 * 空闲CPU百分比 SsCpuIdle
	 */
	public static final String SsCpuIdle = "1.3.6.1.4.1.2021.11.11.0";

	/**
	 * 原始用户CPU使用时间 SsCpuRawUser
	 */
	public static final String SsCpuRawUser = ".1.3.6.1.4.2021.11.50.0";
	/**
	 * 原始nIce占用时间 SsCpuRawNIce
	 */
	public static final String SsCpuRawNIce = ".1.3.6.1.4.1.2021.11.51.0";

	/**
	 * 原始系统CPU使用时间 SsCpuRawSystem
	 */
	public static final String SsCpuRawSystem = ".1.3.6.1.4.2021.11.52,0";

	/**
	 * 原始CPU空闲时间 SsCpuRawIdle
	 */
	public static final String SsCpuRawIdle = ".1.3.6.1.4.2021.11.53.0";

	public static final String SsSwapIn = ".1.3.6.1.4.2021.11.3.0";

	public static final String SsSwapOut = ".1.3.6.1.4.1.2021.11.4.0";

	public static final String SsIOSent = ".1.3.6.1.4.1.2021.11.5.0";

	public static final String SsIOReceive = ".1.3.6.1.4.1.2021.11.6.0";

	public static final String SsSysInterrupts = ".1.3.6.1.4.1.2021.11.7.0";

	public static final String SsSysContext = ".1.3.6.1.4.1.2021.11.8.0";

	public static final String SsCpuRawWait = ".1.3.6.1.4.1.2021.11.54.0";

	public static final String SsCpuRawInterrupt = ".1.3.6.1.4.1.2021.11.56.0";

	public static final String SsIORawSent = ".1.3.6.1.4.1.2021.11.57.0";

	public static final String SsIORawReceived = ".1.3.6.1.4.1.2021.11.58.0";

	public static final String SsRawInterrupts = ".1.3.6.1.4.1.2021.11.59.0";

	public static final String SsRawContexts = ".1.3.6.1.4.1.2021.11.60.0";

	public static final String SsCpuRawSoftIRQ = ".1.3.6.1.4.1.2021.11.61.0";

	public static final String SsRawSwapIn = ".1.3.6.1.4.1.2021.11.62.0";

	public static final String SsRawSwapOut = ".1.3.6.1.4.1.2021.11.63.0";

	public static final String Load5 = ".1.3.6.1.4.1.2021.10.1.3.1";

	public static final String Load10 = ".1.3.6.1.4.1.2021.10.1.3.2";

	public static final String Load15 = ".1.3.6.1.4.1.2021.10.1.3.3";

	/**
	 * 内存大小 HrMemorySize
	 */
	public static final String HrMemorySize = "1.3.6.1.2.1.25.2.2.0";

	/**
	 * 虚拟内存大小 MemTotalSwap
	 */
	public static final String MemTotalSwap = "1.3.6.1.4.1.2021.4.3.0";

	/**
	 * 可用交换内存空间 AvailableSwapSpace
	 */
	public static final String MemAvailSwap = ".1.3.6.1.4.1.2021.4.4.0";

	/**
	 * 机器的总RAM TotalRAMInMachine
	 */
	public static final String MemTotalReal = ".1.3.6.1.4.1.2021.4.5.0";

	/**
	 * RAM被使用的总量 TotalRAMUsed
	 */
	public static final String MemAvailReal = ".1.3.6.1.4.1.2021.4.6.0";

	/**
	 * RAM闲置总量 TotalRAMFree
	 */
	public static final String MemTotalFree = ".1.3.6.1.4.1.2021.4.11.0";

	/**
	 * RAM被共享总量 TotalRAMShared
	 */
	public static final String MemShared = ".1.3.6.1.4.1.2021.4.13.0";

	/**
	 * RAM缓存总量 TotalRAMBuffered
	 */
	public static final String MemBuffer = ".1.3.6.1.4.1.2021.4.14.0";

	public static final String MemCached = ".1.3.6.1.4.1.2021.4.15.0";

	public static final String IpForwarding = ".1.3.6.1.2.1.4.1";
	public static final String IpDefaultTTL = ".1.3.6.1.2.1.4.2";
	public static final String IpInReceives = ".1.3.6.1.2.1.4.3";
	public static final String IpInHdrErrors = ".1.3.6.1.2.1.4.4";
	public static final String IpInAddrErrors = ".1.3.6.1.2.1.4.5";
	public static final String IpForwDatagrams = ".1.3.6.1.2.1.4.6";
	public static final String IpInUnknownProtos = ".1.3.6.1.2.1.4.7";
	public static final String IpInDiscards = ".1.3.6.1.2.1.4.8";
	public static final String IpInDelivers = ".1.3.6.1.2.1.4.9";
	public static final String IpOutRequests = ".1.3.6.1.2.1.4.10";
	public static final String IpOutDiscards = ".1.3.6.1.2.1.4.11";
	public static final String IpOutNoRoutes = ".1.3.6.1.2.1.4.12";
	public static final String IpReasmTimeout = ".1.3.6.1.2.1.4.13";
	public static final String IpReasmReqds = ".1.3.6.1.2.1.4.14";
	public static final String IpReasmOKs = ".1.3.6.1.2.1.4.15";
	public static final String IpReasmFails = ".1.3.6.1.2.1.4.16";
	public static final String IpFragOKs = ".1.3.6.1.2.1.4.17";
	public static final String IpFragFails = ".1.3.6.1.2.1.4.18";
	public static final String IpFragCreates = ".1.3.6.1.2.1.4.19";
	public static final String IpAddrTable = ".1.3.6.1.2.1.4.20";
	public static final String ipAdEntAddr = ".1.3.6.1.2.1.4.20.1.1";
	public static final String ipAdEntIfIndex = ".1.3.6.1.2.1.4.20.1.2";
	public static final String ipAdEntNetMask = ".1.3.6.1.2.1.4.20.1.3";
	public static final String ipAdEntBcastAddr = ".1.3.6.1.2.1.4.20.1.4";
	public static final String ipAdEntReasmMaxSize = ".1.3.6.1.2.1.4.20.1.5";

	public static final String IpNetToMediaTable = ".1.3.6.1.2.1.4.22";
	public static final String IpRoutingDiscards = ".1.3.6.1.2.1.4.23";

	public static final String IcmpInMsgs = ".1.3.6.1.2.1.5.1";
	public static final String IcmpInErrors = ".1.3.6.1.2.1.5.1";
	public static final String IcmpInDestUnreachs = ".1.3.6.1.2.1.5.1";
	public static final String IcmpInTimeExcds = ".1.3.6.1.2.1.5.1";
	public static final String IcmpInParmProbs = ".1.3.6.1.2.1.5.1";
	public static final String IcmpInSrcQuenchs = ".1.3.6.1.2.1.5.1";
	public static final String IcmpInRedirects = ".1.3.6.1.2.1.5.1";
	public static final String IcmpInEchos = ".1.3.6.1.2.1.5.1";
	public static final String IcmpInEchoReps = ".1.3.6.1.2.1.5.1";
	public static final String IcmpInTimestamps = ".1.3.6.1.2.1.5.1";
	public static final String IcmpInTimestampReps = ".1.3.6.1.2.1.5.1";
	public static final String IcmpInAddrMasks = ".1.3.6.1.2.1.5.1";
	public static final String IcmpInAddrMaskReps = ".1.3.6.1.2.1.5.1";
	public static final String IcmpOutMsgs = ".1.3.6.1.2.1.5.1";
	public static final String IcmpOutErrors = ".1.3.6.1.2.1.5.1";
	public static final String IcmpOutDestUnreachs = ".1.3.6.1.2.1.5.1";
	public static final String IcmpOutTimeExcds = ".1.3.6.1.2.1.5.1";
	public static final String IcmpOutParmProbs = ".1.3.6.1.2.1.5.1";
	public static final String IcmpOutSrcQuenchs = ".1.3.6.1.2.1.5.1";
	public static final String IcmpOutRedirects = ".1.3.6.1.2.1.5.1";
	public static final String IcmpOutEchos = ".1.3.6.1.2.1.5.1";
	public static final String IcmpOutEchoReps = ".1.3.6.1.2.1.5.1";
	public static final String IcmpOutTimestamps = ".1.3.6.1.2.1.5.1";
	public static final String IcmpOutTimestampReps = ".1.3.6.1.2.1.5.1";
	public static final String IcmpOutAddrMasks = ".1.3.6.1.2.1.5.1";
	public static final String IcmpOutAddrMaskReps = ".1.3.6.1.2.1.5.1";

	public static final String TcpRtoAlgorithm = ".1.3.6.1.2.1.6.1";
	public static final String TcpRtoMin = ".1.3.6.1.2.1.6.2";
	public static final String TcpRtoMax = ".1.3.6.1.2.1.6.3";
	public static final String TcpMaxConn = ".1.3.6.1.2.1.6.4";
	public static final String TcpActiveOpens = ".1.3.6.1.2.1.6.5";
	public static final String TcpPassiveOpens = ".1.3.6.1.2.1.6.6";
	public static final String TcpAttemptFails = ".1.3.6.1.2.1.6.7";
	public static final String TcpEstabResets = ".1.3.6.1.2.1.6.8";
	public static final String TcpCurrEstab = ".1.3.6.1.2.1.6.9";
	public static final String TcpInSegs = ".1.3.6.1.2.1.6.10";
	public static final String TcpOutSegs = ".1.3.6.1.2.1.6.11";
	public static final String TcpRetransSegs = ".1.3.6.1.2.1.6.12";
	public static final String TcpConnTable = ".1.3.6.1.2.1.6.13";
	public static final String TcpConnState = ".1.3.6.1.2.1.6.13.1";
	public static final String TcpConnLocalAddress = ".1.3.6.1.2.1.6.13.2";
	public static final String TcpConnLocalPort = ".1.3.6.1.2.1.6.13.3";
	public static final String TcpConnRemAddress = ".1.3.6.1.2.1.6.13.4";
	public static final String TcpConnRemPort = ".1.3.6.1.2.1.6.13.5";
	public static final String TcpInErrs = ".1.3.6.1.2.1.6.14";
	public static final String TcpOutRsts = ".1.3.6.1.2.1.6.15";

	public static final String UpdInDatagrams = ".1.3.6.1.2.1.7.1";
	public static final String UpdNoPorts = ".1.3.6.1.2.1.7.2";
	public static final String UpdInErrors = ".1.3.6.1.2.1.7.3";
	public static final String UpdOutDatagrams = ".1.3.6.1.2.1.7.4";
	public static final String UpdTable = ".1.3.6.1.2.1.7.5";
	public static final String UpdLocalAddress = ".1.3.6.1.2.1.7.5";
	public static final String UpdLocalPort = ".1.3.6.1.2.1.7.5";

	public static final String SnmpInPkts = ".1.3.6.1.2.1.11.1";
	public static final String SnmpOutPkts = ".1.3.6.1.2.1.11.2";
	public static final String SnmpInBadVersions = ".1.3.6.1.2.1.11.3";
	public static final String SnmpInBadCommunityNames = ".1.3.6.1.2.1.11.4";
	public static final String SnmpInBadCommunityUses = ".1.3.6.1.2.1.11.5";
	public static final String SnmpInASNParseErrs = ".1.3.6.1.2.1.11.6";
	public static final String SnmpInTooBigs = ".1.3.6.1.2.1.11.7";
	public static final String SnmpInNoSuchNames = ".1.3.6.1.2.1.11.8";
	public static final String SnmpInBadValues = ".1.3.6.1.2.1.11.9";
	public static final String SnmpInReadOnlys = ".1.3.6.1.2.1.11.10";
	public static final String SnmpInGenErrs = ".1.3.6.1.2.1.11.11";
	public static final String SnmpInTotalReqVars = ".1.3.6.1.2.1.11.12";
	public static final String SnmpInTotalSetVars = ".1.3.6.1.2.1.11.13";
	public static final String SnmpInGetRequests = ".1.3.6.1.2.1.11.14";
	public static final String SnmpInGetNexts = ".1.3.6.1.2.1.11.15";
	public static final String SnmpInSetRequests = ".1.3.6.1.2.1.11.16";
	public static final String SnmpInGetResponses = ".1.3.6.1.2.1.11.16";
	public static final String SnmpInTraps = ".1.3.6.1.2.1.11.17";
	public static final String SnmpOutTooBigs = ".1.3.6.1.2.1.11.18";
	public static final String SnmpOutNoSuchNames = ".1.3.6.1.2.1.11.19";
	public static final String SnmpOutBadValues = ".1.3.6.1.2.1.11.20";
	public static final String SnmpOutGenErrs = ".1.3.6.1.2.1.11.21";
	public static final String SnmpOutGetRequests = ".1.3.6.1.2.1.11.22";
	public static final String SnmpOutGetNexts = ".1.3.6.1.2.1.11.23";
	public static final String SnmpOutSetRequests = ".1.3.6.1.2.1.11.24";
	public static final String SnmpOutGetResponses = ".1.3.6.1.2.1.11.1";
	public static final String SnmpOutTraps = ".1.3.6.1.2.1.11.1";
	public static final String SnmpEnableAuthenTraps = ".1.3.6.1.2.1.11.1";

}
