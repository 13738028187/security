package com.hzdy.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hzdy.entity.UserDefinedMODBUS;
import com.hzdy.entity.UserDefinedOPC;
import com.hzdy.entity.UserDefinedS7;


/**
 * 用户自定义规则业务类
 * @author yuwenming
 *
 */
@Service("/userDefinedService")
public interface UserDefinedService {
	
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
	String insertOPCuserDefined(UserDefinedOPC opcRule);

	/**
	 * 删除OPC自定义规则
	 * 
	 * @param userDefinedOPCId
	 * @return
	 */
	String deleteOPCuserDefined(String[] userDefinedOPCIds);

	/**
	 * 更新OPC自定义规则
	 * 
	 * @param opcRule
	 * @return
	 */
	String updateOPCuserDefined(UserDefinedOPC opcRule);
	
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
	String insertS7userDefined(UserDefinedS7 s7Rule);

	/**
	 * 删除S7自定义规则
	 * 
	 * @param userDefinedS7Id
	 * @return
	 */
	String deleteS7userDefined(String[] userDefinedS7Ids);

	/**
	 * 更新S7自定义规则
	 * 
	 * @param s7Rule
	 * @return
	 */
	String updateS7userDefined(UserDefinedS7 s7Rule);
	
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
	String insertMODBUSuserDefined(UserDefinedMODBUS modbusRule);

	/**
	 * 删除MODBUS自定义规则
	 * 
	 * @param userDefinedMODBUSId
	 * @return
	 */
	String deleteMODBUSuserDefined(String[] userDefinedMODBUSIds);

	/**
	 * 更新MODBUS自定义规则
	 * 
	 * @param modbusRule
	 * @return
	 */
	String updateMODBUSuserDefined(UserDefinedMODBUS modbusRule);

}
