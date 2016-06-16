package edu.hzuapps.androidworks.homeworks.com1314080901137;

import java.util.List;
import edu.hzuapps.androidworks.homeworks.com1314080901137.Input.TouchEvent;


public class MainMenuScreen extends Screen {

	public MainMenuScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> events = game.getInput().getTouchEvents();
		
		for(int i = 0,length = events.size();i<length;i++){
			TouchEvent event = events.get(i);
			if(event.type == TouchEvent.TOUCH_UP){				
				if(event.x > 352 && event.x < 480 && event.y > 224 && event.y < 288){
					game.setScreen(new GameScreen(game));
					return;
				}
				if(event.x > 352 && event.x < 480 && event.y > 320 && event.y < 384){
					game.setScreen(null);
					return;
				}
				if(event.x > 736 && event.x < 800 && event.y > 224 && event.y < 288){
					Setting.level++;
					if(Setting.level > 20){
						Setting.level = 0;
					}
					return;
				}
				if(event.x > 736 && event.x < 800 && event.y > 352 && event.y < 416){
					Setting.level --;
					if(Setting.level < 1){
						Setting.level = 20;
					}
					return;
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.clear(0);
		g.drawPixmap(Assets.items, 32*3 , 32 * 2 , 10 * 32, 32 * 2, 19 * 32 , 2*32);
		g.drawPixmap(Assets.city, 32 * 6,  32 * 4);
		g.drawPixmap(Assets.items, 11 * 32 , 7 *32 , 0 , 0 ,32 * 4 , 32 * 2);
		g.drawPixmap(Assets.items, 11 * 32 , 10 * 32 , 32 * 4, 0, 32 * 4, 32 * 2);

		int i = 0 , j = 0;
		if(Setting.level <10){
			j = Setting.level;
		}else if(Setting.level >= 10 && Setting.level !=20){
			i = 1;
			j = Setting.level - 10;
			g.drawPixmap(Assets.items, 23 * 32 , 32 * 9 , 32 * 1,  32 * 2 , 32, 32 * 2);
		}else if(Setting.level == 20){
			i = 1;
			j = 0;
			g.drawPixmap(Assets.items, 23 * 32 , 32 * 9 , 32 * 2,  32 * 2 , 32, 32 * 2);
		}

		g.drawPixmap(Assets.items, 23 * 32 + 32 * i , 32 * 9 , 32 * j ,  32 * 2 , 32, 32 * 2);
		
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