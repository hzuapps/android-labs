package skyward.com.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 自定义相机
 * 1.创建Camera对象
 * 2.创建SurfaceView对象
 * 3.关联Camera对象和SurfaceView对象
 * 4.调整相机的显示效果
 * 5.自定义相机预览界面
 * Created by skyward on 2016/4/29.
 */
public class CustomCameraActivity extends Activity implements SurfaceHolder.Callback{
    private static final String TAG = "CustomCameraActivity";
    private Camera mCamera;
    private SurfaceView mPreview;
    private SurfaceHolder mHolder;
    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //生成照片
            File tempFile = new File("/sdcard/temp.png");
            try {
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(data);
                fos.close();
                Intent intent = new Intent(CustomCameraActivity.this, ResultActivity.class);
                Log.d(TAG, "onPictureTaken: " + tempFile.getAbsolutePath());
                intent.putExtra("picPath", tempFile.getAbsolutePath());
                startActivity(intent);
                CustomCameraActivity.this.finish();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_camera);
        //2.创建SurfaceView对象
        mPreview = (SurfaceView) findViewById(R.id.preview);
        // 点击屏幕时自动对焦
        mPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.autoFocus(null);
            }
        });
        //获取SurfaceHolder对象
        mHolder = mPreview.getHolder();
        mHolder.addCallback(this);
    }

    /**
     * 拍照
     * @param view
     */
    public void capture(View view) {
        //4.调整相机的显示效果
        //参数设置
        Camera.Parameters parameters = mCamera.getParameters();
        //设置照片格式
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setPreviewSize(1280, 720);
        //设置对焦模式为自动对焦
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                //当对焦成功，则拍照
                if (success) {
                    mCamera.takePicture(null, null, mPictureCallback);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mCamera == null) {
            // 1.创建Camera对象
            mCamera = getCamera();
            if (mHolder != null) {
                setStartPreview(mCamera, mHolder);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    /**
     * 获取系统Camera对象
     * @return
     */
    private Camera getCamera() {
        Camera camera;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            camera = null;
            e.printStackTrace();
        }
        return  camera;
    }

    /**
     * 开始预览相机内容
     */
    private void setStartPreview(Camera camera, SurfaceHolder holder) {
        try {
            //3.关联Camera对象和SurfaceView对象
            camera.setPreviewDisplay(holder);
            //系统默认横屏显示
            //因此要将系统camera预览角度调整90度
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放相机资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setStartPreview(mCamera, mHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();
        setStartPreview(mCamera, mHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }
}
