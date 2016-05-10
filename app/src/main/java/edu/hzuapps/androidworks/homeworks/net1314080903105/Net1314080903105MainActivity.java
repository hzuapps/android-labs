package edu.hzuapps.androidworks.homeworks.net1314080903105;

import java.io.IOException;

import android.hardware.Camera;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
/*整个预览的流程是建立一个surface，
获取surface得控制器surfaceholder，
设立surface的窗口previewdisplay，
最后开始预览startpreview。
当然最后还要添加相机相关的权限。*/
public class Net1314080903105MainActivity extends Activity {
    private SurfaceView mView=null;		//是建立一个surface
    private SurfaceHolder mHolder=null;
    private Camera mCamera=null;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_net131408903105);
        mView=(SurfaceView)this.findViewById(R.id.surfaceView1);
        mHolder=mView.getHolder();		//获取surface得控制器surfaceHolder
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//		mHolder.setFixedSize(1, 700); // 设置Surface分辨率
//		mHolder.setSizeFromLayout();
//		mHolder.setKeepScreenOn(true);// 屏幕常亮
        mHolder.addCallback(new Callback(){

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
                // TODO Auto-generated method stub

            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // TODO Auto-generated method stub
                mCamera=Camera.open(1);					//若要升级增加摄像头选择则应该设置try,catch
                try {
                    mCamera.setPreviewDisplay(mHolder);	//设立surface的窗口PreviewDisplay
                    mCamera.setDisplayOrientation(90);	//设置摄像头角度，以后若有需要可以用来调试手机横竖显示
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mCamera.startPreview();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub
                mCamera.startPreview();
                mCamera.release();
            }});
    }


}
