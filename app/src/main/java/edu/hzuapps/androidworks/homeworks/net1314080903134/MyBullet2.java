package com.alonso.object;

import com.alonso.myplane.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
/*玩家飞机的子弹类*/
public class MyBullet2 extends Bullet{
	private Bitmap bullet; 		 // 子弹的图片
	private float object_x2;	
	private float object_y2;
	private boolean isAlive2;	
	private boolean attack;						//标记子弹是否击中
	private boolean attack2;
	public MyBullet2(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		this.harm = 1;
	}
	// 初始化数据
	@Override
	public void initial(int arg0,float arg1,float arg2){
		isAlive = true;
		isAlive2 = true;
		speed = 100;	
		object_x = arg1 - 2*object_width;
		object_y = arg2 - object_height;
		object_x2 = arg1 + object_width;
		object_y2 = object_y;
	}
	// 初始化图片资源的
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		bullet = BitmapFactory.decodeResource(resources, R.drawable.bullet2);	
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
		}
		if (isAlive2) {
			canvas.save();
			canvas.clipRect(object_x2, object_y2, object_x2 + object_width,object_y2 + object_height);
			canvas.drawBitmap(bullet, object_x2, object_y2, paint);
			canvas.restore();
		}
		logic();
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
		}
		else {
			isAlive = false;
		}
		if (object_y2 >= 0) {
			object_y2 -= speed;
		} 
		else{
			isAlive2 = false;
		}
	}
	// 检测碰撞的方法
	@Override
	public boolean isCollide(GameObject obj) {	
		attack = false;
		attack2 = false;
		// 判断左边的子弹是否存活
		if (isAlive) {
			if (object_x <= obj.getObject_x()
					&& object_x + object_width <= obj.getObject_x()) {}
			// 矩形1位于矩形2的右侧
			else if (obj.getObject_x() <= object_x
					&& obj.getObject_x() + obj.getObject_width() <= object_x) {}
			// 矩形1位于矩形2的上方
			else if (object_y <= obj.getObject_y()
					&& object_y + object_height + 30 <= obj.getObject_y()) {}
			// 矩形1位于矩形2的下方
			else if (obj.getObject_y() <= object_y
					&& obj.getObject_y() + obj.getObject_height() + 30 <= object_y) 
			{
				if(obj instanceof SmallPlane){
					if(object_y - speed < obj.getObject_y()){
						isAlive = false;
						attack = true;
					}
				}
			} 
			else {
				isAlive = false;
				attack = true;
			}
		}
		if (isAlive2) {
			if (object_x2 <= obj.getObject_x()
					&& object_x2 + object_width <= obj.getObject_x()) {}
			// 矩形1位于矩形2的右侧
			else if (obj.getObject_x() <= object_x2
					&& obj.getObject_x() + obj.getObject_width() <= object_x2) {}
			// 矩形1位于矩形2的上方
			else if (object_y2 <= obj.getObject_y()
					&& object_y2 + object_height + 30 <= obj.getObject_y()) {}
			// 矩形1位于矩形2的下方
			else if (obj.getObject_y() <= object_y2
					&& obj.getObject_y() + obj.getObject_height() + 30 <= object_y2) 
			{
				if(obj instanceof SmallPlane){
					if(object_y - speed < obj.getObject_y()){
						isAlive2 = false;
						attack2 = true;
					}
				}
			} 
			else {
				isAlive2 = false;
				attack2 = true;
			}
		}
		if (attack && attack2)
			harm = 2;
		return attack || attack2;
	}
	//判断子弹是否存在
	@Override
	public boolean isAlive() {
		return isAlive || isAlive2;
	}
}

