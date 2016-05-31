package com.alonso.object;

import android.content.res.Resources;
import android.graphics.Canvas;
/*子弹基类*/
public class Bullet extends GameObject{
	protected int harm;
	public Bullet(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		initBitmap();
	}
	@Override
	protected void initBitmap() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}
	// 检测碰撞的方法
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
			if(obj instanceof SmallPlane){
				if(object_y - speed < obj.getObject_y()){
					isAlive = false;
					return true;
				}
			}
			else
				return false;
		}
		isAlive = false;
		return true;
	}
	//getter和setter方法
	public int getHarm() {
		// TODO Auto-generated method stub
		return harm;
	}
	public void setHarm(int harm) {
		// TODO Auto-generated method stub
		this.harm = harm;
	}
}
