package com.hzdy.websocket;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MySessionListener implements HttpSessionListener {

	public static Integer onlineNum = 0;

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		onlineNum++;
		System.out.println(onlineNum);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		onlineNum--;
		System.out.println(onlineNum);
	}

}
