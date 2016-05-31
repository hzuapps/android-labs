package com.alonso.object;

import java.util.Random;

import com.alonso.constant.ConstantUtil;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
/*物品类*/
public class GameGoods extends GameObject{
	protected Bitmap bmp;								
	private int direction;			//物品的方向
	public GameGoods(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		this.speed = 10;
		Random ran = new Random();
		direction = ran.nextInt(2) + 3;
		initBitmap();
	}
	// 初始化数据
	@Override
	public void initial(int arg0,float arg1,float arg2){
		isAlive = true;
		object_x = screen_width/2 - object_width/2;
		object_y = -object_height * (arg0*2 + 1);
	}
	// 初始化图片资源的
	@Override
	protected void initBitmap() {
		// TODO Auto-generated method stub

	}
	// 对象的绘图方法
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isAlive){
			canvas.save();
			canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
			canvas.drawBitmap(bmp, object_x, object_y,paint);
			canvas.restore();
			logic();
		}
	}
	// 释放资源的方法
	@Override
	public void release() {
		// TODO Auto-generated method stub
		if(!bmp.isRecycled()){
			bmp.recycle();
		}
	}
	// 对象的逻辑函数
	@Override
	public void logic() {
		Random ran = new Random();
		//物品移动的原方向为左上方
		if(direction == ConstantUtil.DIR_LEFT_UP){
			object_x -= ran.nextInt(3) + speed;
			object_y -= ran.nextInt(3) + speed;	
			if(object_x <= 0 || object_y <= 0){
				if(object_x <= 0)
					object_x = 0;
				else
					object_y = 0;
				int dir = 0;
				do{
					dir = ran.nextInt(4)+1;
				}
				while(dir == direction);
				direction = dir;
				this.speed = 10 + ran.nextInt(5);
			}
		}
		//物品移动的原方向为右上方
		else if(direction == ConstantUtil.DIR_RIGHT_UP){
			object_x += ran.nextInt(3) + speed;
			object_y -= ran.nextInt(3) + speed;	
			if(object_x >= screen_width - object_width || object_y <= 0){
				if(object_x >= screen_width - object_width)
					object_x = screen_width - object_width;
				else
					object_y = 0;
				int dir = 0;
				do{
					dir = ran.nextInt(4)+1;
				}
				while(dir == direction);
				direction = dir;
				this.speed = 10 + ran.nextInt(5);
			}
		}
		//物品移动的原方向为左下方
		else if(direction == ConstantUtil.DIR_LEFT_DOWN){
			object_x -= ran.nextInt(3) + speed;
			object_y += ran.nextInt(3) + speed;	
			if(object_x <= 0 || object_y >= screen_height - object_height){
				if(object_x <= 0)
					object_x = 0;
				else
					object_y = screen_height - object_height;
				int dir = 0;
				do{
					dir = ran.nextInt(4)+1;
				}
				while(dir == direction);
				direction = dir;
				this.speed = 10 + ran.nextInt(5);
			}
		}
		//物品移动的原方向为右下方
		else if(direction == ConstantUtil.DIR_RIGHT_DOWN){
			object_x += ran.nextInt(3) + speed;
			object_y += ran.nextInt(3) + speed;	
			if(object_x >= screen_width - object_width || object_y >= screen_height - object_height){
				if(object_x >= screen_width - object_width)
					object_x = screen_width - object_width;
				else
					object_y = screen_height - object_height;
				int dir = 0;
				do{
					dir = ran.nextInt(4)+1;
				}
				while(dir == direction);
				direction = dir;
				this.speed = 10 + ran.nextInt(5);
			}
		}
	}
	// 检测碰撞
	@Override
	public boolean isCollide(GameObject obj) {
		// 矩形1位于矩形2的左侧
		if (object_x <= obj.getObject_x()
				&& object_x + object_width <= obj.getObject_x()) {
			return false;
		}
		// 矩形1位于矩形2的右侧
		else if (obj.getObject_x() <= object_x
				&& obj.getObject_x() + obj.getObject_width() <= object_x) {
			return false;
		}
		// 矩形1位于矩形2的上方
		else if (object_y <= obj.getObject_y()
				&& object_y + object_height <= obj.getObject_y()) {
			return false;
		}
		// 矩形1位于矩形2的下方
		else if (obj.getObject_y() <= object_y
				&& obj.getObject_y() + obj.getObject_height() <= object_y) {
			return false;
		}
		isAlive = false;
		return true;
	}
}

