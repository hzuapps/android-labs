package edu.hzuapps.androidworks.homeworks.com1314080901137;

import android.graphics.Rect;

public abstract class ObjectCubo {

	public int x;
	public int y;
	public int width;
	public int height;
	public boolean isLive;
	
	public Rect getRect(){
		return new Rect(x, y, x+width, y+height);
	}
	
	public MapListener mapListener;
	
	public interface MapListener{
		public boolean checkTankP(Tank tank , int x ,int y);
		public boolean checkTank(Tank tank , int x ,int y);
		public boolean checkBullet(Bullet bullet,int x ,int y);
	}
}
