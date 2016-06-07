package edu.hzuapps.androidworks.homeworks.net1314080903101;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class Net1314080903101Activity extends ActionBarActivity {

    private View layout;
    private Camera camera;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        //设置窗口没有标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置窗口全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_net1314080903101);



        //利用layout方法，找到两个按钮控件
        layout = this.findViewById(R.id.buttonlayout);
        //获取摄像头窗口
        SurfaceView surfaceView = (SurfaceView) this.findViewById(R.id.surfaceview);
        //将获取的摄像头填满整个窗口
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        //设置窗口分辨率
        surfaceView.getHolder().setFixedSize(176, 144);
        //保持屏幕高亮，不要锁机
        surfaceView.getHolder().setKeepScreenOn(true);
        //设置摄像头被调用监听事件
        surfaceView.getHolder().addCallback(new SurfaceCallback());


    }

    /*
     * 通过switch (v.getId()) 选择拍照事件和对焦事件
     */
    public void takepicture(View v)
    {
        if(camera != null)
        {
            switch (v.getId())
            {
                case R.id.takepicture:
                    //拍照片经过压缩处理后的图片调用MyPictureCallback方法
                    camera.takePicture(null, null, new MyPictureCallback());
                    break;
                case R.id.autofocus:
                    //如果不想得到对焦事件，传送NULL事件进去
                    camera.autoFocus(null);

                default:
                    break;
            }


        }

    }


    /*
     * 获取图片对象
     */
    private final class MyPictureCallback implements PictureCallback
    {

        public void onPictureTaken(byte[] data, Camera camera)
        {

            try
            {
                //将文件存储在SD卡的根目录，并以系统时间将文件命名
                File jpgFile = new File(Environment.getExternalStorageDirectory(),
                        java.lang.System.currentTimeMillis() + ".jpg");
                //文件输出流对象
                FileOutputStream outStream = new FileOutputStream(jpgFile);
                //将文件数据存储到文件中
                outStream.write(data);
                //关闭输出流
                outStream.close();
                //开始预览照片
                camera.startPreview();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }


    /*
     * 设置摄像头参数
     */
    private final class SurfaceCallback implements android.view.SurfaceHolder.Callback
    {

        public void surfaceCreated(SurfaceHolder holder)
        {
            try
            {
                //打开摄像头
                camera = Camera.open();
                //获取摄像头参数对象
                Camera.Parameters parameters = camera.getParameters();
                //设置摄像头分辨率
                parameters.setPreviewSize(800, 480);
                //设置摄像头捕获画面的频率为每秒5个画面
                parameters.setPreviewFrameRate(5);
                //设置拍摄照片的大小
                parameters.setPictureSize(1024, 768);
                //设置捕捉图像的JPEG画质
                parameters.setJpegQuality(80);
                //把参数返回给摄像头
                camera.setParameters(parameters);
                //显示摄像头捕获画面
                camera.setPreviewDisplay(holder);
                //开始预览摄像头
                camera.startPreview();
                //获取摄像头详细参数，并且打印出来
                //Log.i("MainActivity", parameters.flatten());

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int heigh)
        {

        }

        public void surfaceDestroyed(SurfaceHolder holder)
        {
            //如果摄像头不使用时，关闭摄像头
            if(camera != null)
            {
                camera.release();
                camera = null;
            }


        }

    }


    /*
     *屏幕被触摸事件
     *屏幕被按下后，显示相对布局里面的两个按钮
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            layout.setVisibility(ViewGroup.VISIBLE);

        }

        return super.onTouchEvent(event);

    }

}
