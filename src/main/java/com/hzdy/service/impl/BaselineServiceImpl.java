package com.hzdy.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzdy.dao.BaselineDeviceDao;
import com.hzdy.dao.BaselineStrategyDao;
import com.hzdy.dao.BaselineStrategyDeviceDao;
import com.hzdy.entity.BaselineDevice;
import com.hzdy.entity.BaselineStrategy;
import com.hzdy.service.BaselineService;

@Service("baselineService")
public class BaselineServiceImpl implements BaselineService {

    @Resource
    private BaselineDeviceDao baselineDeviceDao;
    @Resource
    private BaselineStrategyDao baselineStrategyDao;
    @Resource
    private BaselineStrategyDeviceDao baselineStrategyDeviceDao;

    @Override
    public List<BaselineDevice> queryDevices() {
        // TODO Auto-generated method stub
        return baselineDeviceDao.queryDevices();
    }

    @Override
    public BaselineDevice queryDeviceById(Integer deviceId) {
        // TODO Auto-generated method stub
        return baselineDeviceDao.queryDeviceById(deviceId);
    }

    @Override
    public List<BaselineStrategy> queryStrategies(String name) {
        // TODO Auto-generated method stub
        return baselineStrategyDao.queryStrategies(name);
    }

    @Override
    public BaselineStrategy queryStrategyById(Integer strategyId) {
        // TODO Auto-generated method stub
        return baselineStrategyDao.queryStrategyById(strategyId);
    }

    @Override
    public String insertStrategy(BaselineStrategy strategy) {
        // TODO Auto-generated method stub
        BaselineStrategy baselineStrategy = baselineStrategyDao.checkDuplication(strategy);
        if (baselineStrategy == null) {
            baselineStrategyDao.insertStrategy(strategy);
            return "添加策略成功";
        } else
            return "策略名称已存在";

    }

    @Override
    public String updateStrategy(BaselineStrategy strategy) {
        // TODO Auto-generated method stub
        BaselineStrategy baselineStrategy = baselineStrategyDao.checkDuplication(strategy);
        if (baselineStrategy == null) {
            baselineStrategyDao.updateStrategy(strategy);
            return "修改策略成功";
        } else
            return "策略名称已存在";

    }

    @Transactional
    @Override
    public String deleteStrategy(String[] strategyIds) {
        // TODO Auto-generated method stub
        try {
            for (int i = 0; i < strategyIds.length; i++) {
                baselineStrategyDao.deleteStrategy(Integer.parseInt(strategyIds[i]));
            }
            return "删除配置成功";
        } catch (Exception e) {
            // TODO: handle exception
            return "删除配置失败";
        }

    }

    @Transactional
    @Override
    public String strategyIssued(Integer strategyId, String[] deviceIds) {
        // TODO Auto-generated method stub
        try {
            BaselineStrategy strategy = baselineStrategyDao.queryStrategyById(strategyId);
            Integer strategyType = strategy.getType();
            String ips = strategy.getIps();
            Integer ipStatus = strategy.getIpStatus();
            String ports = strategy.getPorts();
            Integer portStatus = strategy.getPortStatus();

            String message = "";

            for (int i = 0; i < deviceIds.length; i++) {
                // baselineStrategyDeviceDao.insertStrategyDevice(strategyId,
                // Integer.parseInt(deviceIds[i]));
                BaselineDevice device = baselineDeviceDao.queryDeviceById(Integer.parseInt(deviceIds[i]));
                String deviceIP = device.getIp();
                String username = device.getDeviceUsername();
                String password = device.getDevicePassword();
                String deviceType = device.getType();

                String msg = "连接设备" + device.getDeviceName() + "失败\n";

                // 调用python脚本修改防火墙端口配置
                // 获取当前项目路径
                String path = System.getProperty("user.dir");

                Properties props = System.getProperties();
                String osName = props.getProperty("os.name");
                String command = null;

                if (strategyType == 0) {
                    // 不同的系统，路径的写法也不同
                    if (osName.contains("Windows"))
                        command = "python C:\\Users\\98443\\security\\data\\service_run_script\\firewallpolicyPort.py "
                                + deviceIP + " " + username + " " + password + " " + deviceType + " " + ips + " "
                                + ports + " " + portStatus;
                    if (osName.contains("Linux"))
                        command = "python " + path + "/data/service_run_script/firewallpolicyPort.py " + deviceIP + " "
                                + " " + username + " " + password + " " + deviceType + " " + ips + " " + ports + " "
                                + portStatus;
                    System.out.println(command);
                } else if (strategyType == 1) {
                    if (osName.contains("Windows"))
                        command = "python C:\\Users\\98443\\security\\data\\service_run_script\\firewallpolicyIP.py "
                                + deviceIP + " " + username + " " + password + " " + deviceType + " " + ips + " "
                                + ipStatus;
                    if (osName.contains("Linux"))
                        command = "python " + path + "/data/service_run_script/firewallpolicyIP.py " + deviceIP + " "
                                + username + " " + password + " " + deviceType + " " + ips + " " + ipStatus;
                    System.out.println(command);
                }

                Process pr = Runtime.getRuntime().exec(command);

                BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                String line;
                // 分析脚本执行完成后返回的信息，判断是否执行成功
                while ((line = in.readLine()) != null) {
                    if (line.contains("iptables: Saving firewall rules to /etc/sysconfig/iptables: [60G[[0;32m  OK  [0;39m]") || line.contains("Firewall reloaded")) {
                        baselineStrategyDeviceDao.insertStrategyDevice(strategyId, Integer.parseInt(deviceIds[i]));
                        msg = "设备" + device.getDeviceName() + "执行策略成功";
                    } else if (line.contains("iptables: Bad rule")) {
                        msg = "设备" + device.getDeviceName() + "执行策略失败";
                    }
                }

                in.close();
                pr.waitFor();
                message = message + msg + "；";
            }
            return message;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "策略下发失败";
        }
    }

}
