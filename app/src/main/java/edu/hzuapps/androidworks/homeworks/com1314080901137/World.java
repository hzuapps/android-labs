package edu.hzuapps.androidworks.homeworks.com1314080901137;

import java.util.Random;

import edu.hzuapps.androidworks.homeworks.com1314080901137.ObjectCubo.MapListener;

public class World implements MapListener {

	public interface WorldListener {
		public int[][] loadMap(int level);
	}

	public static final int World_Width = 20;
	public static final int World_Height = 20;
	public float[] tickTimes = new float[7];
	public float homeIsFeTime;
	public int homeshow;
	public Player player;
	public int createTankNum;
	public int movingTankNum;
	public int playerLife;
	public int[][] map;
	public boolean gameOver;
	public WorldListener worldListener;
	public World(WorldListener worldListener) {
		player = new Player(this);
		this.worldListener = worldListener;
		player.initTank(24 * 7 - 5, 480 - Setting.Tank_Size - 1, 0, Setting.UP);
		playerLife = 4;
		initWorld(Setting.level);
	}

	public void initWorld(int level) {
		player.initPlayer(24 * 7 - 5, 480 - Setting.Tank_Size - 1, Setting.UP);
		player.bullet.isLive = false;
		Random random = new Random();
		Setting.level = level;
		gameOver = false;
		createTankNum = 5;
		movingTankNum = 5;
		map = worldListener.loadMap(Setting.level);
	}

	public void update(float deltaTime) {
		for (int i = 0; i < 5; i++) {
		}
		player.update(deltaTime);
		updatePlayer(deltaTime);

		if (movingTankNum > 0 && !gameOver) {
		} else {
			if (createTankNum >= 20) {
				nextLevel(deltaTime);
			}
		}
		if (gameOver) {
			player.changeType(0);
			firstLevel(deltaTime);
		}
	}

	public void firstLevel(float deltaTime) {
		tickTimes[6] += deltaTime;
		if (tickTimes[6] > 3) {
			homeshow = 0;
			homeIsFeTime = 0;
			initWorld(1);
			playerLife = 4;
			tickTimes[6] = 0;
		}
	}

	public void nextLevel(float deltaTime) {
		tickTimes[6] += deltaTime;
		if (tickTimes[6] > 3 && !gameOver) {
			if (Setting.level++ > 20) {
				Setting.level = 1;
			}
			initWorld(Setting.level);
			tickTimes[6] = 0;
			homeshow = 0;
			homeIsFeTime = 0;
		}
	}


	public void updatePlayer(float deltaTime) {
		if (playerLife >= 0) {
			if (player.isLive == false) {
				tickTimes[5] += deltaTime;
				if (tickTimes[5] > 1) {
					tickTimes[5] = 0;
					player.initTank(24 * 7 - 5, 480 - Setting.Tank_Size - 1, 0,
							Setting.UP);
				}
			}
		}
	}




	@Override
	public boolean checkTank(Tank tank, int x, int y) {
		int item1 = map[y / Setting.Item_Size][x / Setting.Item_Size];
		if (item1 == 0 || item1 == 3)
			return true;
		if (tank.isGood) {
			if (item1 == 4 || item1 == 5) {
				if (player.isOnbroad) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean checkBullet(Bullet bullet, int x, int y) {
		int y1 = (y + Setting.Bullet_Size / 2) / Setting.Item_Size;
		int x1 = (x + Setting.Bullet_Size / 2) / Setting.Item_Size;
		int item1 = map[y1][x1];
		switch (item1) {
			case 0:
				return true;
			case 1:
				map[y1][x1] = 0;
				return false;
			case 2:
				if (bullet.bullet_strength == 4) {
					map[y1][x1] = 0;
				}
				return false;
			case 3:
				if (bullet.bullet_strength == 4) {
					map[y1][x1] = 0;
				}
			case 4:
			case 5:
			case 7:
				return true;
			case 6:
				return false;
		}
		return false;
	}

	@Override
	public boolean checkTankP(Tank tank, int x, int y) {
		int x1 = tank.x;
		int y1 = tank.y;
		tank.x = x;
		tank.y = y;

		return false;
	}

}