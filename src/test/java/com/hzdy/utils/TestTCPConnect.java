package com.hzdy.utils;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestTCPConnect {

    private static final int PORT = 6666;

    public static void main(String[] args) {
        Socket s = null;
        OutputStream out = null;
        InputStream in = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        String serverHost = "192.168.1.128";
        byte[] b = new byte[1024];
        try {
            /**
             * 客户端通过IP和端口和服务器连接，连接上服务端后
             * 就可以像服务端输出消息和接受服务端返回的消息
             * 通过IP就能找到一台独一无二的电脑终端，通过PORT找到
             * 终端的某一个独立的应用程序
             */
            s = new Socket(serverHost, PORT);
            out = s.getOutputStream();
            dos = new DataOutputStream(out);
            dos.write("Hello Server I am client A".getBytes());
            in = s.getInputStream();
            dis = new DataInputStream(in);
            dis.read(b);
            String serverToClient = new String(b);
            System.out.println("服务端返回到客户端的信息:" + serverToClient);
        } catch (UnknownHostException e) {
            System.out.println("Host未知。。。");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //最后要关闭资源
                out.close();
                in.close();
                dos.close();
                dos.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
