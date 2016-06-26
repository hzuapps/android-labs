package edu.hzuapps.androidworks.homeworks.com1314080901137;

public class Player extends Tank {

	public boolean hasHat;
	public boolean isOnbroad;
	public int starNum;
	public boolean hasSpade;
	public boolean eatBomb;
	public float hasShipTime;
	public float hasHatTime;
	
	public Player(MapListener mapListener) {
		super(mapListener);
		bullet = new PlayerBullet(mapListener);	
	}
	
	@Override
	public void initTank(int x, int y, int type, int direction) {
		super.initTank(x, y, type, direction);
		isOnbroad = false;
		hasSpade = false;		
		starNum = 0;
		hasHat = true;		
		eatBomb = false;		
		hasHatTime = 0;
		hasShipTime = 0;
		bullet.isLive = false;
	}
	
	public void initPlayer(int x, int y , int direction){
		this.direction = direction;
		this.x = x;
		this.y = y;
		isOnbroad = false;
		hasSpade = false;		
		hasHat = true;		
		eatBomb = false;		
		hasHatTime = 0;
		hasShipTime = 0;
		bullet.isLive = false;
	}


	@Override
	public void changeType(int type) {
		// TODO Auto-generated method stub
		if (type < 0 || type > 4) {
			throw new RuntimeException(
					"the tank_type of player is now right in changeType()");
		}
		this.tank_type = Tank.player_type[type];
		this.tank_lifes = Tank.player_lifes[type];
		this.tank_speed = Tank.player_speed[type];
		bullet.changeType(type);
	}

	@Override
	public void update(float deltaTime) {
		bullet.update(deltaTime);
	}

	@Override
	public void changeDirection(float deltaTime) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setGood() {
		// TODO Auto-generated method stub
		this.isGood = true;
		bullet.initBullet(x + Setting.Tank_Size / 2 - Setting.Bullet_Size / 2,
				y + Setting.Tank_Size / 2 - Setting.Bullet_Size / 2, direction);
	}

	@Override
	public void shoot(float deltaTime) {
		if (bullet == null)
			throw new RuntimeException(
					"the bullet in enemytank's shoot is null ");
		if (bullet.isLive) {
			return;
		} else {
			bullet.isLive = true;
			bullet.initBullet(x + Setting.Tank_Size / 2 - Setting.Bullet_Size
					/ 2, y + Setting.Tank_Size / 2 - Setting.Bullet_Size / 2,
					direction);
		}
	}
	

}