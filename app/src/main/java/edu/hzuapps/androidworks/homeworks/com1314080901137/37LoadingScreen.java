package edu.hzuapps.androidworks.homeworks.com1314080901137;


import edu.hzuapps.androidworks.homeworks.com1314080901137.Graphics.PixmapFormat;


public class LoadingScreen extends Screen {
	
	public LoadingScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		Assets.items = g.newPixmap("items.png", PixmapFormat.ARGB4444);	
		Assets.city = g.newPixmap("city.png", PixmapFormat.ARGB4444);
		Assets.controls = g.newPixmap("controls.png", PixmapFormat.ARGB4444);
		Assets.level = g.newPixmap("level.png", PixmapFormat.ARGB4444);
		Assets.life = g.newPixmap("life.png", PixmapFormat.ARGB4444);
		Assets.tank_safe1 = g.newPixmap("tank_safely1.png", PixmapFormat.ARGB4444);
		Assets.tank_safe2 = g.newPixmap("tank_safely2.png", PixmapFormat.ARGB4444);
		Assets.player = g.newPixmap("player.png", PixmapFormat.ARGB4444);
		Assets.titles = g.newPixmap("titles.png", PixmapFormat.ARGB4444);
		Assets.bullets = g.newPixmap("bullet.png", PixmapFormat.ARGB4444);

		game.setScreen(new MainMenuScreen(game));
	}

	@Override
	public void present(float deltaTime) {
		
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
}