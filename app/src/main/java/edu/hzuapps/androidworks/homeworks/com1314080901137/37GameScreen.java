package edu.hzuapps.androidworks.homeworks.com1314080901137;

import android.graphics.Color;

import edu.hzuapps.androidworks.homeworks.com1314080901137.World.WorldListener;

public class GameScreen extends Screen {

	private WorldListener worldListener;
	private World world;
	public int tickTime = 0;
	public int[] tickTimes;
	public int huTime = 0;

	public GameScreen(Game game) {
		super(game);
		final FileIO fileIo = game.getFileIO();
		worldListener = new WorldListener() {

			@Override
			public int[][] loadMap(int level) {
				return MapFactory.createMap(fileIo, level);
			}
		};
		world = new World(worldListener);
		tickTimes = new int[6];
	}

	@Override
	public void update(float deltaTime) {
		world.update(deltaTime);
		if (!world.gameOver) {
			if (game.getInput().isTouchDown(0)) {
				int x = game.getInput().getTouchX(0);
				int y = game.getInput().getTouchY(0);
				if (x < 320 && y > 160) {
					world.player.direction = pressInwhichRect(160, 320, x, y);
					world.player.move(deltaTime);
				} else if( x < 320 && x > 160 && y < 80){
					game.setScreen(new MainMenuScreen(game));
				}else if (x > 320) {
					world.player.shoot(deltaTime);
				}
			}
			if (game.getInput().isTouchDown(1)) {
				int x = game.getInput().getTouchX(1);
				if (x > 320) {
					world.player.shoot(deltaTime);
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		drawWorld(g, deltaTime);
	}

	public void drawWorld(Graphics g, float deltaTime) {

		g.clear(0);
		g.drawPixmap(Assets.controls, 0, 0);
		g.drawPixmap(Assets.level, 12, 20);
		g.drawText("" + Setting.level, 60, 40, Color.RED, 22);
		g.drawText("" + (20 - world.createTankNum), 60, 95, Color.RED, 22);

		g.drawPixmap(Assets.life, 20, 125);
		if (world.playerLife > 0) {
			g.drawText("" + world.playerLife, 60, 145, Color.RED, 22);
		} else
			g.drawText("" + 0, 60, 145, Color.RED, 22);		
		drawMap(g);

		drawPlayer(g);

		if (world.gameOver) {
			g.drawText("Game Over", 320 + 240, 240, Color.RED, 80);
		}
	}


	private void drawPlayer(Graphics g) {
		if (world.player.isLive) {
			switch (world.player.tank_type) {
			case 0:
				g.drawPixmap(Assets.player, 320 + world.player.x,
						world.player.y, 0, 22 * world.player.direction,
						Setting.Tank_Size, Setting.Tank_Size);
				break;
			case 1:
				g.drawPixmap(Assets.player, 320 + world.player.x,
						world.player.y, 22 * 2, 22 * world.player.direction,
						Setting.Tank_Size, Setting.Tank_Size);
				break;
			case 2:
				g.drawPixmap(Assets.player, 320 + world.player.x,
						world.player.y, 22 * 4, 22 * world.player.direction,
						Setting.Tank_Size, Setting.Tank_Size);
				break;
			case 3:
				g.drawPixmap(Assets.player, 320 + world.player.x,
						world.player.y, 22 * 6, 22 * world.player.direction,
						Setting.Tank_Size, Setting.Tank_Size);
				break;
			}
			if (world.player.hasHat) {
				tickTime++;
				tickTime = tickTime % 10;
				if (tickTime == 0) {
					g.drawPixmap(Assets.tank_safe1, 320 + world.player.x - 1,
							world.player.y - 1);
				} else if (tickTime == 5) {
					g.drawPixmap(Assets.tank_safe2, 320 + world.player.x - 1,
							world.player.y - 1);
				}
			}
		}
		if (world.player.bullet.isLive) {
			g.drawPixmap(Assets.bullets, 320 + world.player.bullet.x,
					world.player.bullet.y, 4 * world.player.bullet.direction,
					0, Setting.Bullet_Size, Setting.Bullet_Size);
		}

		for(int i = 0; i< world.map.length ;i++){
			for(int j = 0; j< world.map[0].length ;j++){
				if(world.map[i][j] == 3){
					g.drawPixmap(Assets.titles, 320 + 24 * j, 24 * i, 24 * 2,
							0, 24, 24);
				}
			}
		}
	}


	private void drawMap(Graphics g) {
		for (int i = 0; i < World.World_Height; i++) {
			for (int j = 0; j < World.World_Width; j++) {
				int title = world.map[i][j];
				switch (title) {
				case 1:
					g.drawPixmap(Assets.titles, 320 + 24 * j, 24 * i, 0, 0, 24,
							24);
					break;
				case 2:
					g.drawPixmap(Assets.titles, 320 + 24 * j, 24 * i, 24, 0,
							24, 24);
					break;
				case 4:
					huTime ++;
					huTime = huTime%200;
					if(huTime < 100){
						g.drawPixmap(Assets.titles, 320 + 24 * j, 24 * i, 24 * 3,
								0, 24, 24);
					}else{
						g.drawPixmap(Assets.titles, 320 + 24 * j, 24 * i, 24 * 4,
								0, 24, 24);
					}
					break;				
				case 6:
					g.drawPixmap(Assets.titles, 320 + 24 * j, 24 * i, 24 * 5,
							0, 24, 24);
					break;
				case 7:
					g.drawPixmap(Assets.titles, 320 + 24 * j, 24 * i, 24 * 6,
							0, 24, 24);
					break;
				}
			}
		}
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		
	}

	public int pressInwhichRect(int x1, int y1, int x2, int y2) {
		if (Math.abs(x2 - x1) > Math.abs(y2 - y1)) {
			if (x2 - x1 > 0) {
				return 1;
			} else {
				return 3;
			}
		} else {
			if (y2 - y1 > 0) {
				return 2;
			} else {
				return 0;
			}
		}
	}
}