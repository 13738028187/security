package com.hzdy.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Service;

import com.hzdy.dao.BaselineDeviceDao;
import com.hzdy.dao.NoFlowDao;
import com.hzdy.dao.SecurityAreaConfigDao;
import com.hzdy.dao.SecurityAreaDao;
import com.hzdy.discovery.FoundationDiscovery;
import com.hzdy.entity.BaselineDevice;
import com.hzdy.entity.NoFlow;
import com.hzdy.message.MessageProducter;
/*import com.hzdy.message.MessageProducter;*/
import com.hzdy.message.SendMessage;
import com.hzdy.service.NoFlowService;

@Service("noFlowServcie")
public class NoFlowServcieImpl implements NoFlowService {

    @Resource
    private NoFlowDao noFlowDao;
    @Resource
    private BaselineDeviceDao baselineDeviceDao;
    @Resource
    private SecurityAreaDao securityAreaDao;
    @Resource
    private SecurityAreaConfigDao securityAreaConfigDao;

    private Map<Integer, Timer> map = SingletonMap.getInstance();

    @Override
    public List<NoFlow> queryList(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return noFlowDao.queryList(map);
    }

    @Override
    public NoFlow queryNoFlowById(Integer id) {
        // TODO Auto-generated method stub
        return noFlowDao.queryNoFlowById(id);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return noFlowDao.queryTotal(map);
    }

    @Override
    public int insert(NoFlow noFlow) {
        // TODO Auto-generated method stub
        return noFlowDao.insert(noFlow);
    }

    @Override
    public int update(NoFlow noFlow) {
        // TODO Auto-generated method stub
        // 更新策略内容时，会暂停正在执行的同一id的监测策略
        if (map.get(noFlow.getId()) != null) {
            map.get(noFlow.getId()).cancel();
            map.remove(noFlow.getId());
        }
        return noFlowDao.update(noFlow);
    }

    @Override
    public int delete(String ids) {
        // TODO Auto-generated method stub
        try {
            // 删除策略时，会将正在执行的同一id的监测策略暂停并将其从Map中删除
            String[] id = ids.split(";");
            for (int i = 0; i < id.length; i++) {
                noFlowDao.delete(Integer.parseInt(id[i]));
                if (map.get(Integer.parseInt(id[i])) != null) {
                    map.get(Integer.parseInt(id[i])).cancel();
                    map.remove(Integer.parseInt(id[i]));
                }
            }
            return 1;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public String execute(NoFlow noFlow) {
        // TODO Auto-generated method stub
        try {
            BaselineDevice device = baselineDeviceDao.queryDeviceByIP(noFlow.getOriginIp());
            if (device == null) {
                return "没有找到该IP对应的设备信息";
            } else {
                String message = "无法连接到源IP";

                String username = device.getDeviceUsername();
                String password = device.getDevicePassword();
                String deviceType = device.getType();
                String originIP = noFlow.getOriginIp();
                String goalIP = noFlow.getGoalIp();
                String goalADSL = noFlow.getGoalADSL();
                String protocol = noFlow.getProtocol();
                int interval = noFlow.getInterval();

                // 先向iptables中添加需要监测的规则
                // 获取当前项目路径
                String path = System.getProperty("user.dir");

                Properties props = System.getProperties();
                String osName = props.getProperty("os.name");
                String command = null;

                // 不同的系统，路径的写法也不同
                if (osName.contains("Windows"))
                    command = "python C:\\Users\\98443\\security\\data\\service_run_script\\addNoFlowRule.py "
                            + originIP + " " + username + " " + password + " " + deviceType + " " + originIP + " "
                            + goalIP + " " + goalADSL + " " + protocol;
                if (osName.contains("Linux"))
                    command = "python " + path + "/data/service_run_script/addNoFlowRule.py " + originIP + " "
                            + username + " " + password + " " + deviceType + " " + originIP + " " + goalIP + " "
                            + goalADSL + " " + protocol;
                System.out.println(command);

                Process pr = Runtime.getRuntime().exec(command);

                BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.contains("iptables: Saving firewall rules to /etc/sysconfig/iptables: [60G[[0;32m  OK  [0;39m]")
                            || line.contains("Firewall reloaded")) {
                        message = "执行成功";
                    } else if (line.contains("iptables: Bad rule")) {
                        message = "执行失败";
                    }
                }
                in.close();
                pr.waitFor();

                if (message.equals("执行成功")) {
                    // 更新策略状态
                    noFlowDao.updateStatus(noFlow.getId(), 1);

                    // 成功添加监测规则后，利用Timer定时器在指定时间间隔里获取流量信息
                    List<String> flows = new ArrayList<>();

                    TimerTask task = new TimerTask() {
                        int i = 0;

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            try {
                                String command = null;
                                if (osName.contains("Windows"))
                                    command = "python C:\\Users\\98443\\security\\data\\service_run_script\\noFlowMonitor.py "
                                            + originIP + " " + username + " " + password;
                                if (osName.contains("Linux"))
                                    command = "python " + path + "/data/service_run_script/noFlowMonitor.py "
                                            + originIP + " " + username + " " + password;
                                System.out.println(command);

                                Process pr2 = Runtime.getRuntime().exec(command);

                                BufferedReader in2 = new BufferedReader(new InputStreamReader(pr2.getInputStream()));
                                String line2;
                                while ((line2 = in2.readLine()) != null) {
                                    int originIPIndex = line2.indexOf(noFlow.getOriginIp());
                                    int goalIPIndex = line2.indexOf(noFlow.getGoalIp());
                                    if (originIPIndex != -1 && goalIPIndex != -1 && originIPIndex < goalIPIndex
                                            && line2.contains(protocol) && line2.contains(goalADSL)) {
                                        getFlow(flows, line2, i, noFlow, securityAreaConfigDao, securityAreaDao);
                                        break;
                                    }
                                }
                                in2.close();
                                pr2.waitFor();
                                i++;
                            } catch (Exception e) {
                                // TODO: handle exception
                            }
                        }
                    };

                    // 声明一个单例的Map对象，当监测策略开始执行时，将该策略对应的Timer添加到Map中，便于后续操作的处理
                    if (map.get(noFlow.getId()) != null) {
                        map.get(noFlow.getId()).cancel();
                        map.get(noFlow.getId()).schedule(task, 0, interval * 1000);
                    } else {
                        Timer timer = new Timer();
                        map.put(noFlow.getId(), timer);

                        timer.schedule(task, 0, interval * 1000);
                    }
                }

                return message;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "执行失败";
        }
    }

    @Override
    public String stop(NoFlow noFlow) {
        if (map.get(noFlow.getId()) != null) {
            map.get(noFlow.getId()).cancel();
            map.remove(noFlow.getId());

            noFlowDao.updateStatus(noFlow.getId(), 0);
            return "已停止" + noFlow.getOriginIp() + "与" + noFlow.getGoalIp() + "的流量监测";
        } else
            return "该策略未执行";
    }

    private static void getFlow(List<String> flows, String line2, int i, NoFlow noFlow,
                                SecurityAreaConfigDao securityAreaConfigDao, SecurityAreaDao securityAreaDao)
            throws HttpException, IOException {
        FoundationDiscovery fd = new FoundationDiscovery();
        flows.add(i, line2.substring(8, 17).trim());
        System.out.println(line2);
        System.out.println("本次监测流量：" + line2.substring(8, 17).trim());
        if (i > 0) {
            int primaryFlow = Integer.parseInt(flows.get(i));
            int lasttimeFlow = Integer.parseInt(flows.get(i - 1));
            System.out.println("与上一次相比变化：" + (primaryFlow - lasttimeFlow));

            // 如果两次监测结果的差值等于0，说明发生了无流量
            if (primaryFlow - lasttimeFlow == 0) {
                String networkNumber = fd.andCompute(noFlow.getOriginIp(), noFlow.getOriginIpMask());
                Integer securityAreaId = securityAreaConfigDao.querySecurityAreaIdByNum(networkNumber);
                if (securityAreaId != null) {
                    // 获取源设备ip所在安全域等级，进行不同等级的告警
                    String level = securityAreaDao.queryLevelById(securityAreaId);
                    if ("1".equals(level)) {
                        MessageProducter producter = new MessageProducter();
                        producter.init();
                        producter.sendMessage("源IP" + noFlow.getOriginIp() + "与目的IP" + noFlow.getGoalIp() + "发生无流量");

                    } else if ("2".equals(level)) {
                        SendMessage.sendMail("无流量监测策略" + noFlow.getRuleName() + "产生无流量告警，请及时处理");
                    } else if ("3".equals(level)) {
                        SendMessage.sendSMS2("51de388507b8883f48ce2e3726ae62a9",
                                "无流量监测策略" + noFlow.getRuleName() + "产生无流量告警，请及时处理", "13456308137");
                    } else if ("4".equals(level)) {
                        SendMessage.sendMail("无流量监测策略" + noFlow.getRuleName() + "产生无流量告警，请及时处理");
                        SendMessage.sendSMS2("51de388507b8883f48ce2e3726ae62a9",
                                "无流量监测策略" + noFlow.getRuleName() + "产生无流量告警，请及时处理", "13456308137");
                    }
                }
            }
        }
    }

}

class SingletonMap {
    private static Map<Integer, Timer> map = null;

    public static Map<Integer, Timer> getInstance() {
        if (map == null)
            map = new HashMap<>();
        return map;
    }
}
