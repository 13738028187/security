package com.hzdy.hardware;

import java.util.HashMap;
import java.util.Map;
import com.hzdy.hardware.entity.MBeanNode;
import com.hzdy.hardware.entity.MemoryInformations;
import com.hzdy.hardware.entity.SystemInfo;

class XStreamAlias {
	private XStreamAlias() {
		super();
	}

	static Map<String, Class<?>> getMap() {
		final Map<String, Class<?>> result = new HashMap<String, Class<?>>();
		result.put("memoryInformations", MemoryInformations.class);
		result.put("mbeanNode", MBeanNode.class);
		result.put("systemInfo", SystemInfo.class);
		return result;
	}
}