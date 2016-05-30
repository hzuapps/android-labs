package com.alonso.object;


import java.util.ArrayList;
import java.util.List;

import com.alonso.constant.ConstantUtil;
import com.alonso.factory.GameObjectFactory;
import com.alonso.myplane.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
/*BOSS敌机的类*/
public class BossPlane extends EnemyPlane{
	private static int currentCount = 0;	 //	对象当前的数量
	private static int sumCount = 1;
	private Bitmap boosPlane;
	private Bitmap boosPlaneBomb;
	private int direction;			//移动的方向
	private int interval;			//发射子弹的间隔
	private float leftBorder;		//飞机能移动的左边界
	private float rightBorder;		//飞机能移动的右边界
	private boolean isFire;			//是否允许射击
	private boolean isCrazy;		//是否为疯狂状态
	private List<Bullet> bullets;	//子弹类
	private MyPlane myplane;
	public BossPlane(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		this.score = 10000;
		interval = 1;
		bullets = new ArrayList<Bullet>();
		//工厂类
		GameObjectFactory factory = new GameObjectFactory();
		for(int i=0;i<2;i++){	
			BossBullet bullet = (BossBullet) factory.createBossBullet(resources);//生产子弹
			bullets.add(bullet);
		}
	}
	public void setMyPlane(MyPlane myplane){
		this.myplane = myplane;
	}
	//初始化数据
	@Override
	public void setScreenWH(float screen_width,float screen_height){
		super.setScreenWH(screen_width, screen_height);
		for(Bullet obj:bullets){	
			obj.setScreenWH(screen_width, screen_height);
		}
		leftBorder = -object_width/2;
		rightBorder = screen_width - object_width/2;
	}
	//初始化数据
	@Override
	public void initial(int arg0,float arg1,float arg2){
		isAlive = true;	
		isVisible = true;
		isCrazy = false;
		isFire = false;
		speed = 6;	
		bloodVolume = 500;	
		blood = bloodVolume;
		direction = ConstantUtil.DIR_LEFT;	
		object_x = screen_width/2 - object_width/2;
		object_y = -object_height * (arg0*2 + 1);
		currentCount++;
		if(currentCount >= sumCount){
			currentCount = 0;
		}
	}
	//初始化图片
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		boosPlane = BitmapFactory.decodeResource(resources, R.drawable.bossplane);
		boosPlaneBomb = BitmapFactory.decodeResource(resources, R.drawable.bossplanebomb);
		object_width = boosPlane.getWidth();		//获得每一帧位图的宽
		object_height = boosPlane.getHeight()/3;		//获得每一帧位图的高	
	}
	//初始化子弹对象
	public void initButtle(){
		if(isFire){
			if(interval == 1){
				for(GameObject obj:bullets){	
					if(!obj.isAlive()){
						obj.initial(0,object_x + object_width/2,object_height);
						break;
					}
				}
			}
			interval++;
			if(interval >= 8){
				interval = 1;
			}
		}
	}
	//绘图函数
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isAlive){
			if(!isExplosion){
				int y = (int) (currentFrame * object_height); // 获得当前帧相对于位图的Y坐标
				canvas.save();
				canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
				canvas.drawBitmap(boosPlane, object_x, object_y - y,paint);
				canvas.restore();
				logic();
				currentFrame++;
				if(currentFrame >= 3){
					currentFrame = 0;
				}
				shoot(canvas);		//射击
			}
			else{
				int y = (int) (currentFrame * object_height); // 获得当前帧相对于位图的Y坐标
				canvas.save();
				canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
				canvas.drawBitmap(boosPlaneBomb, object_x, object_y - y,paint);
				canvas.restore();
				currentFrame++;
				if(currentFrame >= 5){
					currentFrame = 0;
					isExplosion = false;
					isAlive = false;
				}
			}	
		}	
	}
	//发射子弹
	public boolean shoot(Canvas canvas){
		if(isFire){
			//遍历子弹的对象
			for(Bullet obj:bullets){	
				if(obj.isAlive()){
					obj.drawSelf(canvas);//绘制子弹
					if(obj.isCollide(myplane)){
						myplane.setAlive(false);
						return true;
					}
				}
			}
		}
		return false;
	}
	//释放资源
	@Override
	public void release() {
		// TODO Auto-generated method stub
		for(Bullet obj:bullets){	
			obj.release();
		}
		if(!boosPlane.isRecycled()){
			boosPlane.recycle();
		}
		if(!boosPlaneBomb.isRecycled()){
			boosPlaneBomb.recycle();
		}
	}
	// 检测碰撞
	@Override
	public boolean isCollide(GameObject obj) {
		return super.isCollide(obj);
	}
	//对象的逻辑函数
	@Override
	public void logic(){
		if (object_y < 0) {
			object_y += speed;
		}
		else{
			if(!isFire){
				isFire = true;
			}
			if(blood < 150){
				if(!isCrazy){
					isCrazy = true;
					speed = 20;
				}
			}
			if(object_x > leftBorder && direction == ConstantUtil.DIR_LEFT){
				object_x -= speed;
				if(object_x <= leftBorder){
					direction = ConstantUtil.DIR_RIGHT;
				}
			}
			if(object_x < rightBorder && direction == ConstantUtil.DIR_RIGHT){
				object_x += speed;
				if(object_x >= rightBorder){
					direction = ConstantUtil.DIR_LEFT;
				}
			}
		}
	}
}
