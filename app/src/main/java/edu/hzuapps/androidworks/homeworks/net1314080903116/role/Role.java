package edu.hzuapps.androidworks.homeworks.net1314080903116.role;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Role {
	private Bitmap bitmap;// 当前图片
	private Bitmap[] bms;// 完成一个动作的图片数组
	private float x, y; // 坐标
	private int width, height;// 图片的宽高
	private int index;// 记录图片下标
	private boolean isJump;// 是否跳起来了

	private float speedX, speedY;

	private long lastTime = 0;

	// 创建精灵
	public Role(Bitmap[] bms) {
		this.bms = bms;
		this.bitmap = bms[0];
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();

	}

	// 画自己
	public void drawSelf(Canvas canvas) {

		if (System.currentTimeMillis() - lastTime >= 200) {
			index++;
			if (index == bms.length) {
				index = 0;
			}
			bitmap = bms[index];
			lastTime=System.currentTimeMillis();
		}
		//画自己
		canvas.drawBitmap(bitmap, this.getX(), this.getY()-8,null);

	}

	public boolean isJump() {
		return isJump;
	}

	public void setJump(boolean isJump) {
		this.isJump = isJump;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public Bitmap[] getBms() {
		return bms;
	}

	public void setBms(Bitmap[] bms) {
		this.bms = bms;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}
    
	/**
	 * 获取任务矩形
	 * @return
	 */
	public Rect getRectFromRole() {
		Rect rect=new Rect();
		//根据role本身
		rect.left=(int) this.getX()+8;
		rect.right=(int) (this.getX()+this.getWidth()-10);
		rect.top=(int) this.getY();
		rect.bottom=(int) (this.getY()+this.getHeight());
		
		return rect;
	}
	

}
