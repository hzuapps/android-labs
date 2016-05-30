package com.alonso.object;

import com.alonso.myplane.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
/*玩家飞机的子弹类*/
public class MyBullet extends Bullet{
	private Bitmap bullet; 		 // 子弹的图片
	public MyBullet(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		this.harm = 1;
	}
	// 初始化数据
	@Override
	public void initial(int arg0,float arg1,float arg2){
		isAlive = true;
		speed = 100;	
		object_x = arg1 - object_width / 2;
		object_y = arg2 - object_height;
	}
	// 初始化图片资源的
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		bullet = BitmapFactory.decodeResource(resources, R.drawable.bullet);
		object_width = bullet.getWidth();
		object_height = bullet.getHeight();
	}
	// 对象的绘图方法
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if (isAlive) {
			canvas.save();
			canvas.clipRect(object_x, object_y, object_x + object_width,object_y + object_height);
			canvas.drawBitmap(bullet, object_x, object_y, paint);
			canvas.restore();
			logic();
		}
	}
	// 释放资源的方法
	@Override
	public void release() {
		// TODO Auto-generated method stub
		if(!bullet.isRecycled()){
			bullet.recycle();
		}
	}
	// 对象的逻辑函数
	@Override
	public void logic() {
		if (object_y >= 0) {
			object_y -= speed;
		} else {
			isAlive = false;
		}
	}
	// 检测碰撞的方法
	@Override
	public boolean isCollide(GameObject obj) {	
		return super.isCollide(obj);
	}
}
