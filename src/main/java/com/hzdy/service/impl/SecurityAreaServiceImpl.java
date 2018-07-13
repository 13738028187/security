package com.hzdy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzdy.dao.SecurityAreaDao;
import com.hzdy.entity.SecurityArea;
import com.hzdy.entity.SecurityAreaDevice;
import com.hzdy.service.SecurityAreaService;

@Service("securityAreaService")
public class SecurityAreaServiceImpl implements SecurityAreaService {
	@Resource
	private SecurityAreaDao securityAreaDao;

	@Override
	public List<SecurityArea> queryList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return securityAreaDao.queryList(map);
	}

	@Override
	public SecurityArea querySecurityAreaById(Integer id) {
		// TODO Auto-generated method stub
		return securityAreaDao.querySecurityAreaById(id);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return securityAreaDao.queryTotal(map);
	}

	@Override
	public int insert(SecurityArea securityArea) {
		// TODO Auto-generated method stub
		return securityAreaDao.insert(securityArea);
	}

	@Override
	public int update(SecurityArea securityArea) {
		// TODO Auto-generated method stub
		return securityAreaDao.update(securityArea);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return securityAreaDao.delete(id);
	}

	@Override
	public List<SecurityAreaDevice> queryDevices(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return securityAreaDao.queryDevices(map);
	}

	@Override
	public Integer queryDeviceTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return securityAreaDao.queryDeviceTotal(map);
	}

	/*@Override
	public String updateDeviceInterface(SecurityAreaDevice device) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("deviceName", device.getDeviceName());
		map.put("securityAreaId", device.getSecurityAreaId());

		List<SecurityAreaDevice> existDevices = securityAreaDao.queryDeviceInterfaceById(map);
		String[] newInterfaces = device.getDeviceInterface().split(";");
		for (SecurityAreaDevice existDevice : existDevices) {
			for (int i = 0; i < newInterfaces.length; i++) {
				if (existDevice.getDeviceInterface().indexOf(newInterfaces[i]) != -1)
					return "接口" + newInterfaces[i] + "已存在于其他安全域中";
				else
					continue;
			}
		}
		securityAreaDao.updateDeviceInterface(device);
		return "保存成功";
	}

	@Override
	public String insertDeviceInterface(SecurityAreaDevice device) {
		// TODO Auto-generated method stub
		SecurityAreaDevice existDevice1 = securityAreaDao.checkDuplication1(device);
		SecurityAreaDevice existDevice2 = securityAreaDao.checkDuplication2(device);
		if (existDevice1 != null) {
			if (existDevice1.getDeviceName().equals(device.getDeviceName()))
				return "设备名" + existDevice1.getDeviceName() + "已存在于当前安全域中";
			else if (existDevice1.getDeviceNumber().equals(device.getDeviceNumber()))
				return "设备编号" + existDevice1.getDeviceNumber() + "已存在于当前安全域中";
			else if (existDevice1.getDeviceIP().equals(device.getDeviceIP()))
				return "设备IP" + existDevice1.getDeviceIP() + "已存在于当前安全域中";
			return "该设备已存在于当前安全域中";
		} else if(existDevice1 == null && existDevice2 != null){
			if (!existDevice2.getDeviceName().equals(device.getDeviceName()))
				return "若想为一台设备分配多个安全域，需保证设备名相同";
			else if (!existDevice2.getDeviceNumber().equals(device.getDeviceNumber()))
				return "若想为一台设备分配多个安全域，需保证设备编号相同";
			else if (!existDevice2.getDeviceIP().equals(device.getDeviceIP()))
				return "若想为一台设备分配多个安全域，需保证设备IP相同";
			
			Map<String, Object> map = new HashMap<>();
			map.put("deviceName", device.getDeviceName());
			map.put("securityAreaId", device.getSecurityAreaId());
			
			List<SecurityAreaDevice> existDevices = securityAreaDao.queryDeviceInterfaceById(map);
			String[] newInterfaces = device.getDeviceInterface().split(";");
			for (SecurityAreaDevice existDevice : existDevices) {
				for (int i = 0; i < newInterfaces.length; i++) {
					if (existDevice.getDeviceInterface().indexOf(newInterfaces[i]) != -1)
						return "接口" + newInterfaces[i] + "已存在于其他安全域中";
					else
						continue;
				}
			}
			
			securityAreaDao.insertDeviceInterface(device);
			return "添加成功";
		} else if(existDevice1 == null && existDevice2 == null) {
			Map<String, Object> map = new HashMap<>();
			map.put("deviceName", device.getDeviceName());
			map.put("securityAreaId", device.getSecurityAreaId());
			
			List<SecurityAreaDevice> existDevices = securityAreaDao.queryDeviceInterfaceById(map);
			String[] newInterfaces = device.getDeviceInterface().split(";");
			for (SecurityAreaDevice existDevice : existDevices) {
				for (int i = 0; i < newInterfaces.length; i++) {
					if (existDevice.getDeviceInterface().indexOf(newInterfaces[i]) != -1)
						return "接口" + newInterfaces[i] + "已存在于其他安全域中";
					else
						continue;
				}
			}
			
			securityAreaDao.insertDeviceInterface(device);
			return "添加成功";
		}
		return "添加失败";
	}
*/
	@Transactional
	@Override
	public String deleteDeviceInterface(String[] deviceIds) {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < deviceIds.length; i++) {
				securityAreaDao.deleteDeviceInterfaceByDeviceId(Integer.parseInt(deviceIds[i]));
			}
			return "删除成功";
		} catch (Exception e) {
			// TODO: handle exception
			return "删除失败";
		}
	}

	@Override
	public String updateDeviceInterface(SecurityAreaDevice device) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insertDeviceInterface(SecurityAreaDevice device) {
		// TODO Auto-generated method stub
		return null;
	}

}
