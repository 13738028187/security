package com.hzdy.dao;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.WhitelistRuleCIP;
import com.hzdy.entity.WhitelistRuleICMP;
import com.hzdy.entity.WhitelistRuleMODBUS;
import com.hzdy.entity.WhitelistRuleOPC;
import com.hzdy.entity.WhitelistRuleS7;
import com.hzdy.entity.WhitelistRuleTCP;
import com.hzdy.entity.WhitelistRuleUDP;

/**
 * 白名单规则
 * 
 * @author yuwenming
 *
 */
public interface WhitelistRuleDao {
	
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
	 * 检查重复
	 * 
	 * @param opcRule
	 * @return
	 */
	WhitelistRuleOPC checkOPCRuleDuplication(WhitelistRuleOPC opcRule);

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
	void insertOPCwhitelist(WhitelistRuleOPC OPCRule);

	/**
	 * 删除OPC白名单
	 * 
	 * @param whitelistRuleOPCId
	 */
	void deleteOPCwhitelist(Integer whitelistRuleOPCId);

	/**
	 * 更新OPC白名单
	 * 
	 * @param icmpRule
	 */
	void updateOPCwhitelist(WhitelistRuleOPC opcRule);
	
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
	 * 检查重复
	 * 
	 * @param s7Rule
	 * @return
	 */
	WhitelistRuleS7 checkS7RuleDuplication(WhitelistRuleS7 s7Rule);

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
	void insertS7whitelist(WhitelistRuleS7 S7Rule);

	/**
	 * 删除S7白名单
	 * 
	 * @param whitelistRuleS7Id
	 */
	void deleteS7whitelist(Integer whitelistRuleS7Id);

	/**
	 * 更新S7白名单
	 * 
	 * @param s7Rule
	 */
	void updateS7whitelist(WhitelistRuleS7 s7Rule);
	
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
	 * 检查重复
	 * 
	 * @param modbusRule
	 * @return
	 */
	WhitelistRuleMODBUS checkMODBUSRuleDuplication(WhitelistRuleMODBUS modbusRule);

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
	void insertMODBUSwhitelist(WhitelistRuleMODBUS MODBUSRule);

	/**
	 * 删除MODBUS白名单
	 * 
	 * @param whitelistRuleMODBUSId
	 */
	void deleteMODBUSwhitelist(Integer whitelistRuleMODBUSId);

	/**
	 * 更新MODBUS白名单
	 * 
	 * @param modbusRule
	 */
	void updateMODBUSwhitelist(WhitelistRuleMODBUS modbusRule);
	
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
	 * 检查重复
	 * 
	 * @param cipRule
	 * @return
	 */
	WhitelistRuleCIP checkCIPRuleDuplication(WhitelistRuleCIP cipRule);

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
	void insertCIPwhitelist(WhitelistRuleCIP CIPRule);

	/**
	 * 删除CIP白名单
	 * 
	 * @param whitelistRuleCIPId
	 */
	void deleteCIPwhitelist(Integer whitelistRuleCIPId);

	/**
	 * 更新CIP白名单
	 * 
	 * @param ipRule
	 */
	void updateCIPwhitelist(WhitelistRuleCIP cipRule);
	
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
	 * 检查重复
	 * 
	 * @param icmpRule
	 * @return
	 */
	WhitelistRuleICMP checkICMPRuleDuplication(WhitelistRuleICMP icmpRule);

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
	void insertICMPwhitelist(WhitelistRuleICMP icmpRule);

	/**
	 * 删除ICMP白名单
	 * 
	 * @param whitelistRuleICMPId
	 */
	void deleteICMPwhitelist(Integer whitelistRuleICMPId);

	/**
	 * 更新ICMP白名单
	 * 
	 * @param icmpRule
	 */
	void updateICMPwhitelist(WhitelistRuleICMP icmpRule);
	
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
	 * 检查重复
	 * 
	 * @param tcpRule
	 * @return
	 */
	WhitelistRuleTCP checkTCPRuleDuplication(WhitelistRuleTCP tcpRule);

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
	void insertTCPwhitelist(WhitelistRuleTCP tcpRule);

	/**
	 * 删除TCP白名单
	 * 
	 * @param whitelistRuleTCPId
	 */
	void deleteTCPwhitelist(Integer whitelistRuleTCPId);

	/**
	 * 更新TCP白名单
	 * 
	 * @param tcpRule
	 */
	void updateTCPwhitelist(WhitelistRuleTCP tcpRule);
	
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
	 * 检查重复
	 * 
	 * @param udpRule
	 * @return
	 */
	WhitelistRuleUDP checkUDPRuleDuplication(WhitelistRuleUDP udpRule);

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
	void insertUDPwhitelist(WhitelistRuleUDP udpRule);

	/**
	 * 删除UDP白名单
	 * 
	 * @param whitelistRuleUDPId
	 */
	void deleteUDPwhitelist(Integer whitelistRuleUDPId);

	/**
	 * 更新UDP白名单
	 * 
	 * @param udpRule
	 */
	void updateUDPwhitelist(WhitelistRuleUDP udpRule);

}
