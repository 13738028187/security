package com.hzdy.websocket;

public class MySessionSingleton {
	private int activeUser=0;
	private static MySessionSingleton instance = null;
	
	public static synchronized MySessionSingleton getInstance() {
		if(instance == null) {
			instance = new MySessionSingleton();
		}
		return instance;
	}

	public int getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(int activeUser) {
		this.activeUser = activeUser;
	}

}
