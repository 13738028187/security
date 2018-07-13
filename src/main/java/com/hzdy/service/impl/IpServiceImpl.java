package com.hzdy.service.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hzdy.dao.IpDao;
import com.hzdy.discovery.Ip;
import com.hzdy.manager.entity.DeviceActualResources;
import com.hzdy.manager.entity.DeviceStaticResources;
import com.hzdy.manager.utils.DeviceResourcesBuilder;
import com.hzdy.service.IpService;

/**
 * 
 * what
 * 
 * @author kirohuji
 * @version 0.1
 */
@Service("ipService")
public class IpServiceImpl implements IpService {
	@Resource
	private IpDao ipDao;

	@Override
	public int save(Ip t) {
		return ipDao.save(t);
	}

	@Override
	public int update(Ip t) {
		return ipDao.update(t);
	}

	@Override
	public int delete(int id) {
		return ipDao.delete(id);
	}

	@Override
	public int deleteBatch(Object[] id) {
		return ipDao.deleteBatch(id);
	}

	@Override
	public Ip queryObject(Object id) {
		return ipDao.queryObject(id);
	}

	@Override
	public int queryTotal() {
		return ipDao.queryTotal();
	}

	@Override
	public ArrayList<Ip> queryAll() {

		return ipDao.queryAll();
	}

	@Override
	public void saveAllIp(ArrayList<String> ipList) {
		ipList.stream().forEach(i -> {
			Ip ip = new Ip(i);
			save(ip);
		});
	}

	@Override
	public DeviceActualResources getDeviceActualResources(String ipAddress, String community) {
		DeviceActualResources dar = DeviceResourcesBuilder.deviceActualResourcesBuild(ipAddress, community);
		return dar;
	}

	@Override
	public DeviceStaticResources getDeviceStaticResources(String ipAddress, String community) {
		DeviceStaticResources dsr = DeviceResourcesBuilder.deviceStaticResourcesBuild(ipAddress, community);
		return dsr;
	}

	@Override
	public void clear() {
		ipDao.clear();
	}
}
