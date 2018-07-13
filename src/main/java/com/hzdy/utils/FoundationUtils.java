package com.hzdy.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class FoundationUtils {
	public static boolean isContainsIp(List<String> lists, String ip) {
		for (int i = 0; i < lists.size(); i++) {
			if (lists.get(i).equals(ip)) {
				return true;
			}
		}
		return false;
	}

}
