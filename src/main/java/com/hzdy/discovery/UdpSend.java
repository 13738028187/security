package com.hzdy.discovery;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Timer;

public class UdpSend {  
    public void sendData()throws SocketException,  
    UnknownHostException{  
        DatagramSocket ds = new DatagramSocket();// 创建用来发送数据报包的套接字  
        String str = "hello";  
        DatagramPacket dp = new DatagramPacket(str.getBytes(),  
                str.getBytes().length,  
                InetAddress.getByName("255.255.255.255"), 3001);  
        // 构造数据报包，用来将长度为 length 的包发送到指定主机上的指定端口号  
        try {  
            ds.send(dp);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        ds.close();  
          
    }  
    public static void main(String[] args) {  
        Timer timer = new Timer();  
        timer.schedule(new MyTask(), 1000, 1000);  
    }  
    static class MyTask extends java.util.TimerTask{   
        @Override  
        public void run() {   
            UdpSend tt = new UdpSend();  
            try {  
            	System.out.println("1");
                    tt.sendData();  
            } catch (SocketException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } catch (UnknownHostException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
    }  
} 