package com.hzdy.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzdy.dao.BaselineDeviceDao;
import com.hzdy.dao.WhitelistRuleDao;
import com.hzdy.entity.BaselineDevice;
import com.hzdy.entity.WhitelistRuleCIP;
import com.hzdy.entity.WhitelistRuleICMP;
import com.hzdy.entity.WhitelistRuleMODBUS;
import com.hzdy.entity.WhitelistRuleOPC;
import com.hzdy.entity.WhitelistRuleS7;
import com.hzdy.entity.WhitelistRuleTCP;
import com.hzdy.entity.WhitelistRuleUDP;
import com.hzdy.service.WhitelistRuleService;

@Service("whitelistRuleService")
public class WhitelistRuleServiceImpl implements WhitelistRuleService {

	@Resource
	private WhitelistRuleDao whitelistRuleDao;
	@Resource
	private BaselineDeviceDao baselineDeviceDao;

	@Override
	public List<WhitelistRuleOPC> queryOPCwhitelist(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryOPCwhitelist(map);
	}

	@Override
	public Integer queryOPCtotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryOPCtotal(map);
	}

	@Override
	public WhitelistRuleOPC queryOPCwhitelistById(Integer whitelistRuleOPCId) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryOPCwhitelistById(whitelistRuleOPCId);
	}

	@Override
	public String insertOPCwhitelist(WhitelistRuleOPC opcRule) {
		// TODO Auto-generated method stub
		WhitelistRuleOPC existOPCRule = whitelistRuleDao.checkOPCRuleDuplication(opcRule);
		if (existOPCRule == null) {
			whitelistRuleDao.insertOPCwhitelist(opcRule);
			return "添加成功";
		} else
			return "规则名称" + opcRule.getRuleName() + "已存在";
	}

	@Transactional
	@Override
	public String deleteOPCwhitelist(String[] whitelistRuleOPCIds) {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < whitelistRuleOPCIds.length; i++) {
				whitelistRuleDao.deleteOPCwhitelist(Integer.parseInt(whitelistRuleOPCIds[i]));
			}
			return "删除成功";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "删除失败";
		}
	}

	@Override
	public String updateOPCwhitelist(WhitelistRuleOPC opcRule) {
		// TODO Auto-generated method stub
		WhitelistRuleOPC existOPCRule = whitelistRuleDao.checkOPCRuleDuplication(opcRule);
		if (existOPCRule == null) {
			whitelistRuleDao.updateOPCwhitelist(opcRule);
			return "修改成功";
		} else
			return "规则名称" + opcRule.getRuleName() + "已存在";
	}

	@Override
	public List<WhitelistRuleS7> queryS7whitelist(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryS7whitelist(map);
	}

	@Override
	public Integer queryS7total(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryS7total(map);
	}

	@Override
	public WhitelistRuleS7 queryS7whitelistById(Integer whitelistRuleS7Id) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryS7whitelistById(whitelistRuleS7Id);
	}

	@Override
	public String insertS7whitelist(WhitelistRuleS7 s7Rule) {
		// TODO Auto-generated method stub
		WhitelistRuleS7 existS7Rule = whitelistRuleDao.checkS7RuleDuplication(s7Rule);
		if (existS7Rule == null) {
			whitelistRuleDao.insertS7whitelist(s7Rule);
			return "添加成功";
		} else
			return "规则名称" + s7Rule.getRuleName() + "已存在";
	}

	@Transactional
	@Override
	public String deleteS7whitelist(String[] whitelistRuleS7Ids) {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < whitelistRuleS7Ids.length; i++) {
				whitelistRuleDao.deleteS7whitelist(Integer.parseInt(whitelistRuleS7Ids[i]));
			}
			return "删除成功";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "删除失败";
		}
	}

	@Override
	public String updateS7whitelist(WhitelistRuleS7 s7Rule) {
		// TODO Auto-generated method stub
		WhitelistRuleS7 existS7Rule = whitelistRuleDao.checkS7RuleDuplication(s7Rule);
		if (existS7Rule == null) {
			whitelistRuleDao.updateS7whitelist(s7Rule);
			return "修改成功";
		} else
			return "规则名称" + s7Rule.getRuleName() + "已存在";
	}

	@Override
	public List<WhitelistRuleMODBUS> queryMODBUSwhitelist(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryMODBUSwhitelist(map);
	}

	@Override
	public Integer queryMODBUStotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryMODBUStotal(map);
	}

	@Override
	public WhitelistRuleMODBUS queryMODBUSwhitelistById(Integer whitelistRuleMODBUSId) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryMODBUSwhitelistById(whitelistRuleMODBUSId);
	}

	@Override
	public String insertMODBUSwhitelist(WhitelistRuleMODBUS modbusRule) {
		// TODO Auto-generated method stub
		WhitelistRuleMODBUS existMODBUSRule = whitelistRuleDao.checkMODBUSRuleDuplication(modbusRule);
		if (existMODBUSRule == null) {
			whitelistRuleDao.insertMODBUSwhitelist(modbusRule);
			return "添加成功";
		} else
			return "规则名称" + modbusRule.getRuleName() + "已存在";
	}

	@Transactional
	@Override
	public String deleteMODBUSwhitelist(String[] whitelistRuleMODBUSIds) {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < whitelistRuleMODBUSIds.length; i++) {
				whitelistRuleDao.deleteMODBUSwhitelist(Integer.parseInt(whitelistRuleMODBUSIds[i]));
			}
			return "删除成功";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "删除失败";
		}
	}

	@Override
	public String updateMODBUSwhitelist(WhitelistRuleMODBUS modbusRule) {
		// TODO Auto-generated method stub
		WhitelistRuleMODBUS existMODBUSRule = whitelistRuleDao.checkMODBUSRuleDuplication(modbusRule);
		if (existMODBUSRule == null) {
			System.err.println(modbusRule);
			whitelistRuleDao.updateMODBUSwhitelist(modbusRule);
			return "修改成功";
		} else
			return "规则名称" + modbusRule.getRuleName() + "已存在";
	}

	@Override
	public List<WhitelistRuleCIP> queryCIPwhitelist(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryCIPwhitelist(map);
	}

	@Override
	public Integer queryCIPtotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryCIPtotal(map);
	}

	@Override
	public WhitelistRuleCIP queryCIPwhitelistById(Integer whitelistRuleCIPId) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryCIPwhitelistById(whitelistRuleCIPId);
	}

	@Override
	public String insertCIPwhitelist(WhitelistRuleCIP cipRule) {
		// TODO Auto-generated method stub
		WhitelistRuleCIP existCIPRule = whitelistRuleDao.checkCIPRuleDuplication(cipRule);
		if (existCIPRule == null) {
			whitelistRuleDao.insertCIPwhitelist(cipRule);
			return "添加成功";
		} else
			return "规则名称" + cipRule.getRuleName() + "已存在";
	}

	@Transactional
	@Override
	public String deleteCIPwhitelist(String[] whitelistRuleCIPIds) {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < whitelistRuleCIPIds.length; i++) {
				whitelistRuleDao.deleteCIPwhitelist(Integer.parseInt(whitelistRuleCIPIds[i]));
			}
			return "删除成功";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "删除失败";
		}
	}

	@Override
	public String updateCIPwhitelist(WhitelistRuleCIP cipRule) {
		// TODO Auto-generated method stub
		WhitelistRuleCIP existCIPRule = whitelistRuleDao.checkCIPRuleDuplication(cipRule);
		if (existCIPRule == null) {
			System.err.println(cipRule);
			whitelistRuleDao.updateCIPwhitelist(cipRule);
			return "修改成功";
		} else
			return "规则名称" + cipRule.getRuleName() + "已存在";
	}
	
	@Override
	public List<WhitelistRuleICMP> queryICMPwhitelist(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryICMPwhitelist(map);
	}

	@Override
	public Integer queryICMPtotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryICMPtotal(map);
	}

	@Override
	public WhitelistRuleICMP queryICMPwhitelistById(Integer whitelistRuleICMPId) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryICMPwhitelistById(whitelistRuleICMPId);
	}

	@Override
	public String insertICMPwhitelist(WhitelistRuleICMP icmpRule) {
		// TODO Auto-generated method stub
		WhitelistRuleICMP existICMPRule = whitelistRuleDao.checkICMPRuleDuplication(icmpRule);
		if (existICMPRule == null) {
			whitelistRuleDao.insertICMPwhitelist(icmpRule);
			return "添加成功";
		} else
			return "规则名称" + icmpRule.getRuleName() + "已存在";
	}

	@Transactional
	@Override
	public String deleteICMPwhitelist(String[] whitelistRuleICMPIds) {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < whitelistRuleICMPIds.length; i++) {
				whitelistRuleDao.deleteICMPwhitelist(Integer.parseInt(whitelistRuleICMPIds[i]));
			}
			return "删除成功";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "删除失败";
		}
	}

	@Override
	public String updateICMPwhitelist(WhitelistRuleICMP icmpRule) {
		// TODO Auto-generated method stub
		WhitelistRuleICMP existICMPRule = whitelistRuleDao.checkICMPRuleDuplication(icmpRule);
		if (existICMPRule == null) {
			whitelistRuleDao.updateICMPwhitelist(icmpRule);
			return "修改成功";
		} else
			return "规则名称" + icmpRule.getRuleName() + "已存在";
	}

	@Override
	public String executeICMPrule(WhitelistRuleICMP icmpRule) {
		// TODO Auto-generated method stub
		try {
			BaselineDevice device = baselineDeviceDao.queryDeviceByIP(icmpRule.getGoalIP());
			if (device == null)
				return "没有找到该IP对应的设备信息";
			else {
				String message = "无法连接目的IP";

				String username = device.getDeviceUsername();
				String password = device.getDevicePassword();
				String deviceType = device.getType();
				String originIP = icmpRule.getOriginIP();

				String path = System.getProperty("user.dir");

				Properties props = System.getProperties();
				String osName = props.getProperty("os.name");
				String command = null;

				if (osName.contains("Windows"))
					command = "python C:\\Users\\yuwenming\\security\\data\\service_run_script\\executeIPrule.py " + icmpRule.getGoalIP()
							+ " " + username + " " + password + " " + deviceType + " " + originIP;
				if (osName.contains("Linux"))
					command = "python " + path + "/data/service_run_script/executeIPrule.py " + icmpRule.getGoalIP()
							+ " " + username + " " + password + " " + deviceType + " " + originIP;
				System.out.println(command);

				Process pr = Runtime.getRuntime().exec(command);

				BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					if (line.contains(
							"iptables: Saving firewall rules to /etc/sysconfig/iptables: [60G[[0;32m  OK  [0;39m]")
							|| line.contains("Firewall reloaded")) {
						message = "执行成功";
					} else if (line.contains("iptables: Bad rule")) {
						message = "执行失败";
					}
				}

				in.close();
				pr.waitFor();

				return message;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "执行失败";
		}
	}

	@Override
	public List<WhitelistRuleTCP> queryTCPwhitelist(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryTCPwhitelist(map);
	}

	@Override
	public Integer queryTCPtotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryTCPtotal(map);
	}

	@Override
	public WhitelistRuleTCP queryTCPwhitelistById(Integer whitelistRuleTCPId) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryTCPwhitelistById(whitelistRuleTCPId);
	}

	@Override
	public String insertTCPwhitelist(WhitelistRuleTCP tcpRule) {
		// TODO Auto-generated method stub
		WhitelistRuleTCP existTCPRule = whitelistRuleDao.checkTCPRuleDuplication(tcpRule);
		if (existTCPRule == null) {
			whitelistRuleDao.insertTCPwhitelist(tcpRule);
			return "添加成功";
		} else
			return "规则名称" + tcpRule.getRuleName() + "已存在";
	}

	@Transactional
	@Override
	public String deleteTCPwhitelist(String[] whitelistRuleTCPIds) {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < whitelistRuleTCPIds.length; i++) {
				whitelistRuleDao.deleteTCPwhitelist(Integer.parseInt(whitelistRuleTCPIds[i]));
			}
			return "删除成功";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "删除失败";
		}
	}

	@Override
	public String updateTCPwhitelist(WhitelistRuleTCP tcpRule) {
		// TODO Auto-generated method stub
		WhitelistRuleTCP existTCPRule = whitelistRuleDao.checkTCPRuleDuplication(tcpRule);
		if (existTCPRule == null) {
			whitelistRuleDao.updateTCPwhitelist(tcpRule);
			return "修改成功";
		} else
			return "规则名称" + tcpRule.getRuleName() + "已存在";
	}

	@Override
	public String executeTCPrule(WhitelistRuleTCP tcpRule) {
		// TODO Auto-generated method stub
		try {
			BaselineDevice device = baselineDeviceDao.queryDeviceByIP(tcpRule.getGoalIP());
			if (device == null)
				return "没有找到该IP对应的设备信息";
			else {
				String message = "无法连接目的IP";

				String username = device.getDeviceUsername();
				String password = device.getDevicePassword();
				String deviceType = device.getType();
				String originIP = tcpRule.getOriginIP();

				String path = System.getProperty("user.dir");

				Properties props = System.getProperties();
				String osName = props.getProperty("os.name");
				String command = null;

				if (osName.contains("Windows"))
					command = "python C:\\Users\\yuwenming\\security\\data\\service_run_script\\executeTCPrule.py " + tcpRule.getGoalIP()
							+ " " + username + " " + password + " " + deviceType + " " + originIP;
				if (osName.contains("Linux"))
					command = "python " + path + "/data/service_run_script/executeTCPrule.py " + tcpRule.getGoalIP()
							+ " " + username + " " + password + " " + deviceType + " " + originIP;
				System.out.println(command);

				Process pr = Runtime.getRuntime().exec(command);

				BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					if (line.contains(
							"iptables: Saving firewall rules to /etc/sysconfig/iptables: [60G[[0;32m  OK  [0;39m]")
							|| line.contains("Firewall reloaded")) {
						message = "执行成功";
					} else if (line.contains("iptables: Bad rule")) {
						message = "执行失败";
					}
				}

				in.close();
				pr.waitFor();

				return message;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "执行失败";
		}
	}
	
	@Override
	public List<WhitelistRuleUDP> queryUDPwhitelist(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryUDPwhitelist(map);
	}

	@Override
	public Integer queryUDPtotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryUDPtotal(map);
	}

	@Override
	public WhitelistRuleUDP queryUDPwhitelistById(Integer whitelistRuleUDPId) {
		// TODO Auto-generated method stub
		return whitelistRuleDao.queryUDPwhitelistById(whitelistRuleUDPId);
	}

	@Override
	public String insertUDPwhitelist(WhitelistRuleUDP udpRule) {
		// TODO Auto-generated method stub
		WhitelistRuleUDP existUDPRule = whitelistRuleDao.checkUDPRuleDuplication(udpRule);
		if (existUDPRule == null) {
			whitelistRuleDao.insertUDPwhitelist(udpRule);
			return "添加成功";
		} else
			return "规则名称" + udpRule.getRuleName() + "已存在";
	}

	@Transactional
	@Override
	public String deleteUDPwhitelist(String[] whitelistRuleUDPIds) {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < whitelistRuleUDPIds.length; i++) {
				whitelistRuleDao.deleteUDPwhitelist(Integer.parseInt(whitelistRuleUDPIds[i]));
			}
			return "删除成功";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "删除失败";
		}
	}

	@Override
	public String updateUDPwhitelist(WhitelistRuleUDP udpRule) {
		// TODO Auto-generated method stub
		WhitelistRuleUDP existUDPRule = whitelistRuleDao.checkUDPRuleDuplication(udpRule);
		if (existUDPRule == null) {
			whitelistRuleDao.updateUDPwhitelist(udpRule);
			return "修改成功";
		} else
			return "规则名称" + udpRule.getRuleName() + "已存在";
	}

	@Override
	public String executeUDPrule(WhitelistRuleUDP udpRule) {
		// TODO Auto-generated method stub
		try {
			BaselineDevice device = baselineDeviceDao.queryDeviceByIP(udpRule.getGoalIP());
			if (device == null)
				return "没有找到该IP对应的设备信息";
			else {
				String message = "无法连接目的IP";

				String username = device.getDeviceUsername();
				String password = device.getDevicePassword();
				String deviceType = device.getType();
				String originIP = udpRule.getOriginIP();

				String path = System.getProperty("user.dir");

				Properties props = System.getProperties();
				String osName = props.getProperty("os.name");
				String command = null;

				if (osName.contains("Windows"))
					command = "python C:\\Users\\yuwenming\\security\\data\\service_run_script\\executeUDPrule.py " + udpRule.getGoalIP()
							+ " " + username + " " + password + " " + deviceType + " " + originIP;
				if (osName.contains("Linux"))
					command = "python " + path + "/data/service_run_script/executeUDPrule.py " + udpRule.getGoalIP()
							+ " " + username + " " + password + " " + deviceType + " " + originIP;
				System.out.println(command);

				Process pr = Runtime.getRuntime().exec(command);

				BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					if (line.contains(
							"iptables: Saving firewall rules to /etc/sysconfig/iptables: [60G[[0;32m  OK  [0;39m]")
							|| line.contains("Firewall reloaded")) {
						message = "执行成功";
					} else if (line.contains("iptables: Bad rule")) {
						message = "执行失败";
					}
				}

				in.close();
				pr.waitFor();

				return message;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "执行失败";
		}
	}

}
