package edu.hzuapps.androidworks.homeworks.com1314080901137;

import android.view.KeyEvent;


public class TankGame extends AndroidGame{

	@Override
	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Assets.items.dispose();
		Assets.city.dispose();
		Assets.controls.dispose();
		Assets.spade.dispose();
		Assets.tank.dispose();
		Assets.player.dispose();
		Assets.titles.dispose();
		Assets.bullets.dispose();
		Assets.level.dispose();
		Assets.tank_safe1.dispose();
		Assets.tank_safe2.dispose();

	}	
}