package com.hzdy.logger.utils;
/**
* 
* what
* 
* @author kirohuji
* @version 0.1
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 实时读取日志文件
 * @author think
 *
 */
public class LogView {  
	   
    private long lastTimeFileSize = 0;  
   
    /** 
     * 实时读取指定文件的内容 
     * @param logFile 
     * @throws FileNotFoundException 
     */  
    public void realtimeShowLog(File logFile) throws FileNotFoundException {  
        //指定文件可读可写  
        final RandomAccessFile randomAccessFile = new RandomAccessFile(logFile, "rw");  
        //启动一个线程每10秒钟读取新增的日志信息  
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);  
        exec.scheduleWithFixedDelay(new Runnable() {  
            @Override  
            public void run() {  
                try {  
                    //获得变化部分的  
                    randomAccessFile.seek(lastTimeFileSize);  
                    String tmp = "";  
                    while ( (tmp = randomAccessFile.readLine()) != null) {  
                        // 输出文件内容  
                        System.out.println(new String(tmp.getBytes("utf8")));  
                        lastTimeFileSize = randomAccessFile.length();  
                    }  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }, 0, 10, TimeUnit.SECONDS);  
   
    }  
   
   
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    String msgInfo = "this is a message, 这是一条信息。this is a message, 这是一条信息。";  
   
    /** 
     * 实时写入日志到指定文件 
     * @throws IOException 
     */  
    public void writerLog(String file) throws IOException {  
        final File logFile = new File(file);  
        if(!logFile.exists()) {  
            logFile.createNewFile();  
        }  
        //启动一个线程每2秒钟向日志文件写一次数据  
        ScheduledExecutorService exec =  Executors.newScheduledThreadPool(1);  
        exec.scheduleWithFixedDelay(new Runnable(){  
            public void run() {  
                try {  
                    if(logFile == null) {  
                        throw new IllegalStateException("logFile can not be null!");  
                    }  
                    Writer txtWriter = new FileWriter(logFile,true);  
                    txtWriter.write(dateFormat.format(new Date()) +"\t"+ msgInfo +"\n");  
                    txtWriter.flush();  
                } catch (IOException e) {  
                    throw new RuntimeException(e);  
                }  
            }  
        }, 0, 2, TimeUnit.SECONDS);  
    }  
   
   
    public static void main(String[] args) throws Exception {  
        LogView view = new LogView();  
        String file=null;
       // view.writerLog(file);  
        final File tmpLogFile = new File("E:\\Program Files\\tomcat6\\logs\\mock.log");  
        view.realtimeShowLog(tmpLogFile);  
    }  
   
}  