package com.hzdy.dao;

import java.util.List;
import java.util.Map;

import com.hzdy.entity.UserDefinedMODBUS;
import com.hzdy.entity.UserDefinedOPC;
import com.hzdy.entity.UserDefinedS7;

/**
 * 用户自定义规则
 * 
 * @author yuwenming
 *
 */
public interface UserDefinedDao {

	/**
	 * 查询OPC自定义规则
	 * 
	 * @param map
	 * @return
	 */
	List<UserDefinedOPC> queryOPCuserDefined(Map<String, Object> map);

	/**
	 * 查询OPC自定义规则总数
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
	UserDefinedOPC checkOPCRuleDuplication(UserDefinedOPC opcRule);

	/**
	 * 根据ID查询OPC自定义规则
	 * 
	 * @param userDefinedOPCId
	 * @return
	 */
	UserDefinedOPC queryOPCuserDefinedById(Integer userDefinedOPCId);

	/**
	 * 添加OPC自定义规则
	 * 
	 * @param opcRule
	 */
	void insertOPCuserDefined(UserDefinedOPC OPCRule);

	/**
	 * 删除OPC自定义规则
	 * 
	 * @param userDefinedOPCId
	 */
	void deleteOPCuserDefined(Integer userDefinedOPCId);

	/**
	 * 更新OPC自定义规则
	 * 
	 * @param icmpRule
	 */
	void updateOPCuserDefined(UserDefinedOPC opcRule);
	
	/**
	 * 查询S7自定义规则
	 * 
	 * @param map
	 * @return
	 */
	List<UserDefinedS7> queryS7userDefined(Map<String, Object> map);

	/**
	 * 查询S7自定义规则总数
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
	UserDefinedS7 checkS7RuleDuplication(UserDefinedS7 s7Rule);

	/**
	 * 根据ID查询S7自定义规则
	 * 
	 * @param userDefinedS7Id
	 * @return
	 */
	UserDefinedS7 queryS7userDefinedById(Integer userDefinedS7Id);

	/**
	 * 添加S7自定义规则
	 * 
	 * @param s7Rule
	 */
	void insertS7userDefined(UserDefinedS7 S7Rule);

	/**
	 * 删除S7自定义规则
	 * 
	 * @param userDefinedS7Id
	 */
	void deleteS7userDefined(Integer userDefinedS7Id);

	/**
	 * 更新S7自定义规则
	 * 
	 * @param s7Rule
	 */
	void updateS7userDefined(UserDefinedS7 s7Rule);
	
	/**
	 * 查询MODBUS自定义规则
	 * 
	 * @param map
	 * @return
	 */
	List<UserDefinedMODBUS> queryMODBUSuserDefined(Map<String, Object> map);

	/**
	 * 查询MODBUS自定义规则总数
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
	UserDefinedMODBUS checkMODBUSRuleDuplication(UserDefinedMODBUS modbusRule);

	/**
	 * 根据ID查询MODBUS自定义规则
	 * 
	 * @param userDefinedMODBUSId
	 * @return
	 */
	UserDefinedMODBUS queryMODBUSuserDefinedById(Integer userDefinedMODBUSId);

	/**
	 * 添加MODBUS自定义规则
	 * 
	 * @param modbusRule
	 */
	void insertMODBUSuserDefined(UserDefinedMODBUS MODBUSRule);

	/**
	 * 删除MODBUS自定义规则
	 * 
	 * @param userDefinedMODBUSId
	 */
	void deleteMODBUSuserDefined(Integer userDefinedMODBUSId);

	/**
	 * 更新MODBUS自定义规则
	 * 
	 * @param modbusRule
	 */
	void updateMODBUSuserDefined(UserDefinedMODBUS modbusRule);

}
