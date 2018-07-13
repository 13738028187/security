package com.hzdy.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static void main(String[] args) {
		System.out.println(getDate());
	}
	public static String getDate(){
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
}
