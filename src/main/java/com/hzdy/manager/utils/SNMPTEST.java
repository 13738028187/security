package com.hzdy.manager.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.hzdy.controller.EquipmentController;

/**
 * 
 * what
 * 
 * @author
 * @version 0.1
 */
public class SNMPTEST {
	public static void main(String args[]) {
		Map<String, String> data = new HashMap<String, String>();
		String ipAddress = "192.168.2.9";
		String community = "public";
		EquipmentController ec=new EquipmentController();
		System.out.println(ec.getLinkInterfacesEntry("192.168.2.4", "192.168.1.3", community));
	}
}
