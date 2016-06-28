package wyf.ytl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;

public class WelcomeView extends SurfaceView implements SurfaceHolder.Callback, OnClickListener{
	PushBoxActivity pushBoxActivity;
	WelcomeViewDrawThread welcomeViewDrawThread = null;
	Bitmap bitmap;
	Bitmap wallLeft;//左面的墙
	Bitmap wallRight;//右面的墙
	Bitmap iron;//铁门
	Bitmap woodLeft;//左面的木门
	Bitmap woodRight;//右面的木门
	Bitmap background;
	Bitmap mountain;//背景的山
	
	int wallLeftX = 15;//墙的坐标
	int wallLeftY = 10;
	int wallRightX = 150;
	int wallRightY = 10;

	int ironX = 15;//铁门的坐标
	int ironY = 10;
	
	int woodLeftX = 15;//木门的坐标
	int woodLeftY = 10;
	int woodRightX = 150;
	int woodRightY = 10; 		
	public WelcomeView(PushBoxActivity pushBoxActivity) {//构造器
		super(pushBoxActivity);
		this.pushBoxActivity = pushBoxActivity;
		getHolder().addCallback(this);
		welcomeViewDrawThread = new WelcomeViewDrawThread(this,getHolder());
		wallRight = BitmapFactory.decodeResource(getResources(), R.drawable.wallright);
		wallLeft = BitmapFactory.decodeResource(getResources(), R.drawable.wallleft); 
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image4);
		iron = BitmapFactory.decodeResource(getResources(), R.drawable.image2);
		woodLeft = BitmapFactory.decodeResource(getResources(), R.drawable.image33);
		woodRight = BitmapFactory.decodeResource(getResources(), R.drawable.image3);
		background = BitmapFactory.decodeResource(getResources(), R.drawable.background);//背景的水
		mountain = BitmapFactory.decodeResource(getResources(), R.drawable.mountain2);		
	}
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);//背景白色
		canvas.drawBitmap(background, 0, 0, new Paint());//绘制背景
		canvas.drawBitmap(mountain, 0, 0, new Paint());//后面的山图片
		canvas.drawBitmap(wallLeft, wallLeftX, wallLeftY,new Paint());//墙的左面
		canvas.drawBitmap(wallRight, wallRightX, wallRightY,new Paint());//墙的右面
		canvas.drawBitmap(iron, ironX, ironY,new Paint());//铁门
		canvas.drawBitmap(woodLeft, woodLeftX, woodLeftY,new Paint());//木头门左面
		canvas.drawBitmap(woodRight, woodRightX, woodRightY,new Paint());//木头门右面
		canvas.drawBitmap(bitmap, 0, 0, new Paint());
		this.setOnClickListener(this);
	}	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	public void surfaceCreated(SurfaceHolder holder) {
		welcomeViewDrawThread.setFlag(true);
		welcomeViewDrawThread.start();//启动刷帧线程
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        welcomeViewDrawThread.setFlag(false);//停止刷帧线程
        while (retry) {
            try {
            	welcomeViewDrawThread.join();//等待刷帧线程结束
                retry = false;
            } 
            catch (InterruptedException e) {//不断地循环，直到等待的线程结束
            }
        }
	}
	public void onClick(View v) {
		pushBoxActivity.myHandler.sendEmptyMessage(1);
	}	
}