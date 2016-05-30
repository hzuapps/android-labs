package com.alonso.object;

import java.util.Random;

import com.alonso.myplane.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
/*小型敌机的类*/
public class SmallPlane extends EnemyPlane{
	private static int currentCount = 0;	 //	对象当前的数量
	private Bitmap smallPlane; // 对象图片
	public static int sumCount = 8;	 	 	 //	对象总的数量
	public SmallPlane(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		this.score = 100;		// 为对象设置分数
	}
	//初始化数据
	@Override
	public void initial(int arg0,float arg1,float arg2){
		isAlive = true;
		bloodVolume = 1;
		blood = bloodVolume;
		Random ran = new Random();
		speed = ran.nextInt(8) + 8 * arg0;	
		object_x = ran.nextInt((int)(screen_width - object_width));
		object_y = -object_height * (currentCount*2 + 1);
		currentCount++;
		if(currentCount >= sumCount){
			currentCount = 0;
		}
	}
	// 初始化图片资源
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		smallPlane = BitmapFactory.decodeResource(resources, R.drawable.small);
		object_width = smallPlane.getWidth();			//获得每一帧位图的宽
		object_height = smallPlane.getHeight()/3;		//获得每一帧位图的高
	}
	// 对象的绘图函数
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		//判断敌机是否死亡状态
		if(isAlive){
			//判断敌机是否为爆炸状态
			if(!isExplosion){
				if(isVisible){
					canvas.save();
					canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
					canvas.drawBitmap(smallPlane, object_x, object_y,paint);
					canvas.restore();
				}	
				logic();
			}
			else{
				int y = (int) (currentFrame * object_height); // 获得当前帧相对于位图的Y坐标
				canvas.save();
				canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
				canvas.drawBitmap(smallPlane, object_x, object_y - y,paint);
				canvas.restore();
				currentFrame++;
				if(currentFrame >= 3){
					currentFrame = 0;
					isExplosion = false;
					isAlive = false;
				}
			}
		}
	}
	// 释放资源
	@Override
	public void release() {
		// TODO Auto-generated method stub
		if(!smallPlane.isRecycled()){
			smallPlane.recycle();
		}
	}
}
