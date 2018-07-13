package com.hzdy.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hzdy.entity.WhitelistRuleCIP;
import com.hzdy.entity.WhitelistRuleICMP;
import com.hzdy.entity.WhitelistRuleMODBUS;
import com.hzdy.entity.WhitelistRuleOPC;
import com.hzdy.entity.WhitelistRuleS7;
import com.hzdy.entity.WhitelistRuleTCP;
import com.hzdy.entity.WhitelistRuleUDP;

/**
 * 白名单规则业务类
 * 
 * @author yuwenming
 *
 */
@Service("whitelistRuleService")
public interface WhitelistRuleService {
	
	/**
	 * 查询OPC白名单
	 * 
	 * @param map
	 * @return
	 */
	List<WhitelistRuleOPC> queryOPCwhitelist(Map<String, Object> map);

	/**
	 * 查询OPC白名单总数
	 * 
	 * @param map
	 * @return
	 */
	Integer queryOPCtotal(Map<String, Object> map);

	/**
	 * 根据ID查询OPC白名单
	 * 
	 * @param whitelistRuleOPCId
	 * @return
	 */
	WhitelistRuleOPC queryOPCwhitelistById(Integer whitelistRuleOPCId);

	/**
	 * 添加OPC白名单
	 * 
	 * @param opcRule
	 */
	String insertOPCwhitelist(WhitelistRuleOPC opcRule);

	/**
	 * 删除OPC白名单
	 * 
	 * @param whitelistRuleOPCId
	 * @return
	 */
	String deleteOPCwhitelist(String[] whitelistRuleOPCIds);

	/**
	 * 更新OPC白名单
	 * 
	 * @param opcRule
	 * @return
	 */
	String updateOPCwhitelist(WhitelistRuleOPC opcRule);
	
	/**
	 * 查询S7白名单
	 * 
	 * @param map
	 * @return
	 */
	List<WhitelistRuleS7> queryS7whitelist(Map<String, Object> map);

	/**
	 * 查询S7白名单总数
	 * 
	 * @param map
	 * @return
	 */
	Integer queryS7total(Map<String, Object> map);

	/**
	 * 根据ID查询S7白名单
	 * 
	 * @param whitelistRuleS7Id
	 * @return
	 */
	WhitelistRuleS7 queryS7whitelistById(Integer whitelistRuleS7Id);

	/**
	 * 添加S7白名单
	 * 
	 * @param s7Rule
	 */
	String insertS7whitelist(WhitelistRuleS7 s7Rule);

	/**
	 * 删除S7白名单
	 * 
	 * @param whitelistRuleS7Id
	 * @return
	 */
	String deleteS7whitelist(String[] whitelistRuleS7Ids);

	/**
	 * 更新S7白名单
	 * 
	 * @param s7Rule
	 * @return
	 */
	String updateS7whitelist(WhitelistRuleS7 s7Rule);
	
	/**
	 * 查询MODBUS白名单
	 * 
	 * @param map
	 * @return
	 */
	List<WhitelistRuleMODBUS> queryMODBUSwhitelist(Map<String, Object> map);

	/**
	 * 查询MODBUS白名单总数
	 * 
	 * @param map
	 * @return
	 */
	Integer queryMODBUStotal(Map<String, Object> map);

	/**
	 * 根据ID查询MODBUS白名单
	 * 
	 * @param whitelistRuleMODBUSId
	 * @return
	 */
	WhitelistRuleMODBUS queryMODBUSwhitelistById(Integer whitelistRuleMODBUSId);

	/**
	 * 添加MODBUS白名单
	 * 
	 * @param modbusRule
	 */
	String insertMODBUSwhitelist(WhitelistRuleMODBUS modbusRule);

	/**
	 * 删除MODBUS白名单
	 * 
	 * @param whitelistRuleMODBUSId
	 * @return
	 */
	String deleteMODBUSwhitelist(String[] whitelistRuleMODBUSIds);

	/**
	 * 更新MODBUS白名单
	 * 
	 * @param modbusRule
	 * @return
	 */
	String updateMODBUSwhitelist(WhitelistRuleMODBUS modbusRule);
	
	/**
	 * 查询CIP白名单
	 * 
	 * @param map
	 * @return
	 */
	List<WhitelistRuleCIP> queryCIPwhitelist(Map<String, Object> map);

	/**
	 * 查询CIP白名单总数
	 * 
	 * @param map
	 * @return
	 */
	Integer queryCIPtotal(Map<String, Object> map);

	/**
	 * 根据ID查询CIP白名单
	 * 
	 * @param whitelistRuleCIPId
	 * @return
	 */
	WhitelistRuleCIP queryCIPwhitelistById(Integer whitelistRuleCIPId);

	/**
	 * 添加CIP白名单
	 * 
	 * @param cipRule
	 */
	String insertCIPwhitelist(WhitelistRuleCIP cipRule);

	/**
	 * 删除CIP白名单
	 * 
	 * @param whitelistRuleCIPId
	 * @return
	 */
	String deleteCIPwhitelist(String[] whitelistRuleCIPIds);

	/**
	 * 更新CIP白名单
	 * 
	 * @param cipRule
	 * @return
	 */
	String updateCIPwhitelist(WhitelistRuleCIP cipRule);
	
	/**
	 * 查询ICMP白名单
	 * 
	 * @param map
	 * @return
	 */
	List<WhitelistRuleICMP> queryICMPwhitelist(Map<String, Object> map);

	/**
	 * 查询ICMP白名单总数
	 * 
	 * @param map
	 * @return
	 */
	Integer queryICMPtotal(Map<String, Object> map);

	/**
	 * 根据ID查询ICMP白名单
	 * 
	 * @param whitelistRuleICMPId
	 * @return
	 */
	WhitelistRuleICMP queryICMPwhitelistById(Integer whitelistRuleICMPId);

	/**
	 * 添加ICMP白名单
	 * 
	 * @param icmpRule
	 */
	String insertICMPwhitelist(WhitelistRuleICMP icmpRule);

	/**
	 * 删除ICMP白名单
	 * 
	 * @param whitelistRuleICMPId
	 * @return
	 */
	String deleteICMPwhitelist(String[] whitelistRuleICMPIds);

	/**
	 * 更新ICMP白名单
	 * 
	 * @param icmpRule
	 * @return
	 */
	String updateICMPwhitelist(WhitelistRuleICMP icmpRule);
	
	/**
	 * 执行ICMP白名单规则
	 * @param ipRule
	 * @return
	 */
	String executeICMPrule(WhitelistRuleICMP ipRule);
	
	/**
	 * 查询TCP白名单
	 * 
	 * @param map
	 * @return
	 */
	List<WhitelistRuleTCP> queryTCPwhitelist(Map<String, Object> map);

	/**
	 * 查询TCP白名单总数
	 * 
	 * @param map
	 * @return
	 */
	Integer queryTCPtotal(Map<String, Object> map);

	/**
	 * 根据ID查询TCP白名单
	 * 
	 * @param whitelistRuleTCPId
	 * @return
	 */
	WhitelistRuleTCP queryTCPwhitelistById(Integer whitelistRuleTCPId);

	/**
	 * 添加TCP白名单
	 * 
	 * @param tcpRule
	 */
	String insertTCPwhitelist(WhitelistRuleTCP tcpRule);

	/**
	 * 删除TCP白名单
	 * 
	 * @param whitelistRuleTCPId
	 * @return
	 */
	String deleteTCPwhitelist(String[] whitelistRuleTCPIds);

	/**
	 * 更新TCP白名单
	 * 
	 * @param tcpRule
	 * @return
	 */
	String updateTCPwhitelist(WhitelistRuleTCP tcpRule);
	
	/**
	 * 执行TCP白名单规则
	 * @param ipRule
	 * @return
	 */
	String executeTCPrule(WhitelistRuleTCP ipRule);
	
	/**
	 * 查询UDP白名单
	 * 
	 * @param map
	 * @return
	 */
	List<WhitelistRuleUDP> queryUDPwhitelist(Map<String, Object> map);

	/**
	 * 查询UDP白名单总数
	 * 
	 * @param map
	 * @return
	 */
	Integer queryUDPtotal(Map<String, Object> map);

	/**
	 * 根据ID查询UDP白名单
	 * 
	 * @param whitelistRuleUDPId
	 * @return
	 */
	WhitelistRuleUDP queryUDPwhitelistById(Integer whitelistRuleUDPId);

	/**
	 * 添加UDP白名单
	 * 
	 * @param udpRule
	 */
	String insertUDPwhitelist(WhitelistRuleUDP udpRule);

	/**
	 * 删除UDP白名单
	 * 
	 * @param whitelistRuleUDPId
	 * @return
	 */
	String deleteUDPwhitelist(String[] whitelistRuleUDPIds);

	/**
	 * 更新UDP白名单
	 * 
	 * @param udpRule
	 * @return
	 */
	String updateUDPwhitelist(WhitelistRuleUDP udpRule);
	
	/**
	 * 执行UDP白名单规则
	 * @param ipRule
	 * @return
	 */
	String executeUDPrule(WhitelistRuleUDP ipRule);

}
