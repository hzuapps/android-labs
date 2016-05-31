package com.alonso.object;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
/*游戏对象类*/
abstract public class GameObject {
	protected int currentFrame; 	// 当前动画帧
	protected int speed; 			// 对象的速度
	protected float object_x; 		// 对象的横坐标
	protected float object_y;		// 对象的纵坐标
	protected float object_width; 	// 对象的宽度
	protected float object_height; 	// 对象的高度
	protected float screen_width; 	// 屏幕的宽度
	protected float screen_height;  // 屏幕的高度
	protected boolean isAlive;		// 判断是否存活
	protected Paint paint; 			// 画笔对象
	protected Resources resources;  // 资源类
	// 构造函数
	public GameObject(Resources resources) {
		this.resources = resources;
		this.paint = new Paint();
	}
	// 设置屏幕宽度和高度
	public void setScreenWH(float screen_width, float screen_height) {
		this.screen_width = screen_width;
		this.screen_height = screen_height;
	}
	// 初始化数据//参数分别为:速度增加的倍数,x中心坐标,y中心坐标,
	public void initial(int arg0,float arg1,float arg2){}
	// 初始化图片资源的
	protected abstract void initBitmap();
	// 对象的绘图方法
	public abstract void drawSelf(Canvas canvas);
	// 释放资源的方法
	public abstract void release();
	// 检测碰撞的方法
	public boolean isCollide(GameObject obj) {	
		return true;
	}
	// 对象的逻辑方法
	public void logic(){}
	//getter和setter方法
	public int getSpeed(){
		return speed;
	}
	public void setSpeed(int speed){
		this.speed = speed;
	}
	public float getObject_x() {
		return object_x;
	}
	public void setObject_x(float object_x) {
		this.object_x = object_x;
	}
	public float getObject_y() {
		return object_y;
	}
	public void setObject_y(float object_y) {
		this.object_y = object_y;
	}
	public float getObject_width() {
		return object_width;
	}
	public void setObject_width(float object_width) {
		this.object_width = object_width;
	}
	public float getObject_height() {
		return object_height;
	}
	public void setObject_height(float object_height) {
		this.object_height = object_height;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
}
