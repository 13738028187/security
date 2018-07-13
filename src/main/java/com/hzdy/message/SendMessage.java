package com.hzdy.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class SendMessage {

	/**
	 * 中国网建接口
	 * @param args
	 * @throws IOException
	 * @throws HttpException
	 */
	public static void sendSMS(String message) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn");
		// 在头文件中设置转码
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
		NameValuePair[] data = { new NameValuePair("Uid", "kirohuji"), new NameValuePair("Key", "3e1993f7207c3b4ca206"),
				new NameValuePair("smsMob", "13456308137"), new NameValuePair("smsText", message) };
		post.setRequestBody(data);
		client.executeMethod(post);
		Header[] headers = post.getRequestHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:" + statusCode);
		for (Header h : headers) {
			System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
		System.out.println(result);
		post.releaseConnection();
	}

	/**
	 * 云片网络接口
	 * @param apikey
	 * @param text
	 * @param mobile
	 * @throws HttpException
	 * @throws IOException
	 */
	public static void sendSMS2(String apikey, String text, String mobile) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://sms.yunpian.com/v2/sms/single_send.json");
		// 在头文件中设置转码
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		NameValuePair[] data = { new NameValuePair("apikey", apikey), new NameValuePair("text", text),
				new NameValuePair("mobile", mobile) };
		post.setRequestBody(data);
		client.executeMethod(post);
		Header[] headers = post.getRequestHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:" + statusCode);
		for (Header h : headers) {
			System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes("utf-8"));
		System.out.println(result);
		post.releaseConnection();
	}

	public static void sendMail(String message) {
		String title = "测试邮件";
		String from = "13456308137@163.com";
		String sendTo[] = { "984439570@qq.com" };
		String content = "【统一安全管理平台】" + message;
		// String fileNames[] = { "C:\\Users\\yuwenming\\Pictures\\Saved
		// Pictures\\Fate01.jpg;Fate01.jpg" };
		JavaMailSendUtil test = new JavaMailSendUtil();
		try {
			test.sendMail(title, from, sendTo, content, /* fileNames, */ "text/html;charset=gb2312");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			SendMessage.sendSMS2("51de388507b8883f48ce2e3726ae62a9", "无流量监测策略策略1产生无流量告警，请及时处理", "13456308137");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
