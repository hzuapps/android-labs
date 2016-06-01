package edu.hzuapps.androidworks.homeworks.net1314080903114;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by hzxy on 2016/5/31.
 */
public class Net1314080903114_MyThread extends Thread {
    //	声明并初始化一个自己数组，数组大小为1024字节=1KB
    private byte byteBuffer[] = new byte[1024];
    //	声明输出流
    private OutputStream outsocket;
    //	声明字节数组输出流
    private ByteArrayOutputStream myoutputstream;
    //	声明IP地址
    private String ipname;
    //	构造函数用来初始化（复赋值）
    public Net1314080903114_MyThread(ByteArrayOutputStream myoutputstream, String ipname){
        this.myoutputstream = myoutputstream;
        this.ipname = ipname;
        try {
//        	关流
            myoutputstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try{
            //将图像数据通过Socket发送出去
            Socket tempSocket = new Socket(ipname, 6000);
//           获取输出流
            outsocket = tempSocket.getOutputStream();
//            将自己数组输出流转化成字节数组后套接在字节数组输入流上
            ByteArrayInputStream inputstream = new ByteArrayInputStream(myoutputstream.toByteArray());
            int amount;
//            将输出流读到自己数组中
            while ((amount = inputstream.read(byteBuffer)) != -1) {
//            	将字节数组的数据写到socket输出流上
//
                outsocket.write(byteBuffer, 0, amount);
            }
//            刷新
            myoutputstream.flush();
//            关闭流
            myoutputstream.close();
            tempSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
