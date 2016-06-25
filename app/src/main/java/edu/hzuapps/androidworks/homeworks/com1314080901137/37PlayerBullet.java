package edu.hzuapps.androidworks.homeworks.com1314080901137;

public class PlayerBullet extends Bullet {

	public PlayerBullet(MapListener mapListener) {
		super(mapListener);
	}

	@Override
	public void changeType(int type) {
		this.bullet_type = type;
		this.bullet_speed = Bullet.player_bullet_speed[type];
		this.bullet_strength = Bullet.player_bullet_strength[type];
	}

	@Override
	public void setGood() {
		this.isGood = true;
	}
	

		}


