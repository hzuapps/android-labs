0package com.example.magnifiter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.SeekBar;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Net1314080903239 MainActivity extends Activity implements SurfaceHolder.Callback {
	private SurfaceView mSurfaceView;
	private SeekBar mSeekBar;
	private int value = 3;
	private Camera.Parameters parameters;
	private Camera mCamera;
	private SurfaceHolder holder;
	private boolean mFlag = false;
	private int mCameraId = 0;
	private static final int BACK_CAMERA = 0;
	private static final int FRONT_CAMERA = 1;
	private static final int ROTATION = 90;
	private static final int REVERT = 180;
	private static final int PREVIEW_WIDTH = 320;
	private static final int PREVIEW_HEIGHT = 240;
	private int mZoomMax;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTitle(getResources().getText(R.string.app_name));

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
		mSeekBar = (SeekBar) findViewById(R.id.seekBar);

		mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				parameters.setZoom(value);
				mCamera.setParameters(parameters);
				mCamera.startPreview();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				value = progress;
			}
		});
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try {
			if (mCamera != null) {
				mCamera.stopPreview();
				mCamera.release();
				mCamera = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		holder = mSurfaceView.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		startCamera();
		
	}

	private void startCamera() {
		// TODO Auto-generated method stub
		if(mFlag){
			if(mCamera != null){
				mCamera.stopPreview();
				mCamera.release();
				mCamera = null;
			}
		}
		
		try{
			mCamera = Camera.open(mCameraId);
		}catch(RuntimeException e){
			e.printStackTrace();
			mCamera = null;
		}
		
		if(mCamera != null){
			mCamera.setDisplayOrientation(mCameraId == BACK_CAMERA ? ROTATION : ROTATION);
			parameters = mCamera.getParameters();
			parameters.setPictureFormat(PixelFormat.JPEG);
			parameters.set("orientation", "portrait");
			parameters.setPreviewSize(PREVIEW_WIDTH, PREVIEW_HEIGHT);
			parameters.setRotation(mCameraId == BACK_CAMERA ? ROTATION : REVERT + ROTATION);
			
			mZoomMax = value;
			parameters.setZoom(mZoomMax);
			mCamera.setParameters(parameters);
			
			try{
				mCamera.setPreviewDisplay(holder);
				mCamera.startPreview();
				mFlag = true;
			}catch(Exception e){
				mCamera.release();
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }

}
