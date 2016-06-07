package edu.hzuapps.androidworks.homeworks.net1314080903114;

import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * Created by hzxy on 2016/5/31.
 */
public class Net1314080903114_StreamIt implements Camera.PreviewCallback {
    private String ipname;
    //声明一个构造函数
    public Net1314080903114_StreamIt(String ipname){
        this.ipname = ipname;
    }
    /*会自动重载这个函数：public void onPreviewFrame(byte[] data, Camera camera) {}这个函数里的
    data就是实时预览帧视频。一旦程序调用PreviewCallback接口，就会自动调用onPreviewFrame这个函数*/
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
//    	获取摄像头的尺寸大小
        Camera.Size size = camera.getParameters().getPreviewSize();
        try{
            //调用image.compressToJpeg（）将YUV格式图像数据data转为jpg格式
//        	首先将字节数组data转成YuvImage格式的图片
            YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
//            如果此时图片不为空
            if(image!=null){
//            	声明并初始化字节数组输出流
                ByteArrayOutputStream outstream = new ByteArrayOutputStream();
//            	将此图片压缩成JPEG格式的图片存放在输出流中
                image.compressToJpeg(new Rect(0, 0, size.width, size.height), 80, outstream);
                outstream.flush();
                //启用线程将图像数据发送出去
                Thread th = new Net1314080903114_MyThread(outstream,ipname);
//               开启线程
                th.start();
            }
        }catch(Exception ex){
            Log.e("Sys", "Error:" + ex.getMessage());
        }
    }
}
