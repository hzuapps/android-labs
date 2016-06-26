package edu.hzuapps.androidworks.homeworks.com1314080901137;

import java.util.Random;

public abstract class Tank extends ObjectCubo {

	public static final int[] player_type = { 0, 1, 2, 3 };
	public static final int[] player_lifes = { 1, 1, 1, 2 };
	public static final int[] player_speed = { Setting.Tank_Mode_Speed,
			Setting.Tank_Fase_Speed, Setting.Tank_Fase_Speed,
			Setting.Tank_Fase_Speed };

	public static final int[] enemy_type = { 0, 1, 2, 3, 4, 5, 6, 7 };
	public static final int[] enemy_lifes = { 1, 2, 1, 2, 1, 2, 3, 4 };
	public static final int[] enemy_speed = { Setting.Tank_Mode_Speed,
			Setting.Tank_Fase_Speed, Setting.Tank_Mode_Speed,
			Setting.Tank_Fase_Speed, Setting.Tank_Mode_Speed,
			Setting.Tank_Mode_Speed, Setting.Tank_Mode_Speed,
			Setting.Tank_Fase_Speed };
	protected boolean isGood;
	protected boolean isPass = true;
	protected boolean notIntroblue;
	

	public int direction;
	public int tank_speed;
	public int tank_lifes;
	public int tank_type;

	public Bullet bullet;
	public float tickTime;
	public float tickTime2;
	public Random random = new Random();

	public Tank(MapListener mapListener) {
		this.mapListener = mapListener;
		this.width = Setting.Tank_Size;
		this.height = Setting.Tank_Size;
	}

	public void initTank(int x, int y, int type, int direction) {
		isLive = true;
		this.x = x;
		this.y = y;
		this.tank_type = type;
		this.direction = direction;
		setGood();		
		changeType(type);		
	}

	public abstract void setGood();

	public abstract void changeType(int type);

	public abstract void changeDirection(float deltaTime);
	
	public void shoot(float deltaTime) {
		if(!isLive)
			return;					
		if (bullet == null)
			throw new RuntimeException(
					"the bullet in enemytank's shoot is null ");
		if (bullet.isLive) {
			return;
		} else {			
			tickTime2 +=deltaTime;
			if(tickTime2 > new Random().nextFloat()){
				tickTime2 = 0;
				bullet.isLive = true;
				bullet.initBullet(x + Setting.Tank_Size / 2
						- Setting.Bullet_Size / 2, y + Setting.Tank_Size / 2
						- Setting.Bullet_Size / 2, direction);
			}
		}
	};

	public void update(float deltaTime) {
		move(deltaTime);
		shoot(deltaTime);
		changeDirection(deltaTime);
		bullet.update(deltaTime);
	}

	public void move(float deltaTime) {
		if(!isLive)
			return;
		isPass = true;
		notIntroblue = true;
		int x1 = x;
		int y1 = y;
		switch (direction) {
		case Setting.LEFT:
			x1 -= tank_speed;
			break;
		case Setting.UP:
			y1 -= tank_speed;
			break;
		case Setting.RIGHT:
			x1 += tank_speed;
			break;
		case Setting.DOWN:
			y1 += tank_speed;
			break;
		}
		if (x1 < 0 || x1 >= 480 - Setting.Tank_Size
				|| y1 >= 480 - Setting.Tank_Size || y1 < 0) {
			if (!isGood)
				changeDirection(deltaTime);			
			return;
		}

		notIntroblue = mapListener.checkTank(this , x, y)
				&& mapListener.checkTank(this , x + Setting.Tank_Size, y)
				&& mapListener.checkTank(this , x, y + Setting.Tank_Size)
				&& mapListener.checkTank(this , x + Setting.Tank_Size, y
						+ Setting.Tank_Size) && mapListener.checkTankP(this , x , y);

		if (!notIntroblue) {
			x = x1;
			y = y1;
			return;
		} else {
			isPass = mapListener.checkTank(this , x1, y1)
					&& mapListener.checkTank(this ,x1 + Setting.Tank_Size, y1)
					&& mapListener.checkTank(this , x1, y1 + Setting.Tank_Size)
					&& mapListener.checkTank(this ,x1 + Setting.Tank_Size, y1
							+ Setting.Tank_Size) && mapListener.checkTankP(this ,x1 ,y1);
			if (!isPass) {
				if (!isGood)
					changeDirection(deltaTime);
				return;
			}
			x = x1;
			y = y1;
		}
	}
}