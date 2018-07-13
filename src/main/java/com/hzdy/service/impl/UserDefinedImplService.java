package com.hzdy.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzdy.dao.UserDefinedDao;
import com.hzdy.entity.UserDefinedMODBUS;
import com.hzdy.entity.UserDefinedOPC;
import com.hzdy.entity.UserDefinedS7;
import com.hzdy.service.UserDefinedService;

@Service("/userDefinedService")
public class UserDefinedImplService implements UserDefinedService {
	
	@Resource
	private UserDefinedDao userDefinedDao;

	@Override
	public List<UserDefinedOPC> queryOPCuserDefined(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userDefinedDao.queryOPCuserDefined(map);
	}

	@Override
	public Integer queryOPCtotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userDefinedDao.queryOPCtotal(map);
	}

	@Override
	public UserDefinedOPC queryOPCuserDefinedById(Integer userDefinedOPCId) {
		// TODO Auto-generated method stub
		return userDefinedDao.queryOPCuserDefinedById(userDefinedOPCId);
	}

	@Override
	public String insertOPCuserDefined(UserDefinedOPC opcRule) {
		// TODO Auto-generated method stub
		UserDefinedOPC existOPCRule = userDefinedDao.checkOPCRuleDuplication(opcRule);
		if (existOPCRule == null) {
			userDefinedDao.insertOPCuserDefined(opcRule);
			return "添加成功";
		} else
			return "规则名称" + opcRule.getRuleName() + "已存在";
	}

	@Transactional
	@Override
	public String deleteOPCuserDefined(String[] userDefinedOPCIds) {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < userDefinedOPCIds.length; i++) {
				userDefinedDao.deleteOPCuserDefined(Integer.parseInt(userDefinedOPCIds[i]));
			}
			return "删除成功";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "删除失败";
		}
	}

	@Override
	public String updateOPCuserDefined(UserDefinedOPC opcRule) {
		// TODO Auto-generated method stub
		UserDefinedOPC existOPCRule = userDefinedDao.checkOPCRuleDuplication(opcRule);
		if (existOPCRule == null) {
			userDefinedDao.updateOPCuserDefined(opcRule);
			return "修改成功";
		} else
			return "规则名称" + opcRule.getRuleName() + "已存在";
	}

	@Override
	public List<UserDefinedS7> queryS7userDefined(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userDefinedDao.queryS7userDefined(map);
	}

	@Override
	public Integer queryS7total(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userDefinedDao.queryS7total(map);
	}

	@Override
	public UserDefinedS7 queryS7userDefinedById(Integer userDefinedS7Id) {
		// TODO Auto-generated method stub
		return userDefinedDao.queryS7userDefinedById(userDefinedS7Id);
	}

	@Override
	public String insertS7userDefined(UserDefinedS7 s7Rule) {
		// TODO Auto-generated method stub
		UserDefinedS7 existS7Rule = userDefinedDao.checkS7RuleDuplication(s7Rule);
		if (existS7Rule == null) {
			userDefinedDao.insertS7userDefined(s7Rule);
			return "添加成功";
		} else
			return "规则名称" + s7Rule.getRuleName() + "已存在";
	}

	@Transactional
	@Override
	public String deleteS7userDefined(String[] userDefinedS7Ids) {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < userDefinedS7Ids.length; i++) {
				userDefinedDao.deleteS7userDefined(Integer.parseInt(userDefinedS7Ids[i]));
			}
			return "删除成功";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "删除失败";
		}
	}

	@Override
	public String updateS7userDefined(UserDefinedS7 s7Rule) {
		// TODO Auto-generated method stub
		UserDefinedS7 existS7Rule = userDefinedDao.checkS7RuleDuplication(s7Rule);
		if (existS7Rule == null) {
			userDefinedDao.updateS7userDefined(s7Rule);
			return "修改成功";
		} else
			return "规则名称" + s7Rule.getRuleName() + "已存在";
	}

	@Override
	public List<UserDefinedMODBUS> queryMODBUSuserDefined(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userDefinedDao.queryMODBUSuserDefined(map);
	}

	@Override
	public Integer queryMODBUStotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userDefinedDao.queryMODBUStotal(map);
	}

	@Override
	public UserDefinedMODBUS queryMODBUSuserDefinedById(Integer userDefinedMODBUSId) {
		// TODO Auto-generated method stub
		return userDefinedDao.queryMODBUSuserDefinedById(userDefinedMODBUSId);
	}

	@Override
	public String insertMODBUSuserDefined(UserDefinedMODBUS modbusRule) {
		// TODO Auto-generated method stub
		UserDefinedMODBUS existMODBUSRule = userDefinedDao.checkMODBUSRuleDuplication(modbusRule);
		if (existMODBUSRule == null) {
			userDefinedDao.insertMODBUSuserDefined(modbusRule);
			return "添加成功";
		} else
			return "规则名称" + modbusRule.getRuleName() + "已存在";
	}

	@Transactional
	@Override
	public String deleteMODBUSuserDefined(String[] userDefinedMODBUSIds) {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < userDefinedMODBUSIds.length; i++) {
				userDefinedDao.deleteMODBUSuserDefined(Integer.parseInt(userDefinedMODBUSIds[i]));
			}
			return "删除成功";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "删除失败";
		}
	}

	@Override
	public String updateMODBUSuserDefined(UserDefinedMODBUS modbusRule) {
		// TODO Auto-generated method stub
		UserDefinedMODBUS existMODBUSRule = userDefinedDao.checkMODBUSRuleDuplication(modbusRule);
		if (existMODBUSRule == null) {
			System.err.println(modbusRule);
			userDefinedDao.updateMODBUSuserDefined(modbusRule);
			return "修改成功";
		} else
			return "规则名称" + modbusRule.getRuleName() + "已存在";
	}

}
