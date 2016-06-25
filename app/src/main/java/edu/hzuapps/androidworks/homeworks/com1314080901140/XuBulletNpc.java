package com.hzu.xu.planewar;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class XuBulletNpc {
	// ------------ 子弹
	Bitmap img_bullet;
	String img_bullet_name = "bullet-7.png";;
	int bullet_speed;
	int bullet_x, bullet_y, bullet_w, bullet_h;

	public int bullet_statu;
	public static final int BULLET_MOVE = 0X0001;
	public static final int BULLET_DIE = 0X0002;

	// ----------- 爆炸
	Bitmap img_boom;
	int boom_x, boom_y, boom_w, boom_h, boom_col, boom_row;
	public int boom_statu;
	public static final int BOOM_POWER = 0X0001;
	public static final int BOOM_DIE = 0X0002;


	public BulletNpc() {

	}

	public void setImgBulletName(String name){
		this.img_bullet_name = name;
	}

	public void Bullet_Init() {
		InputStream is = this.getImgStream(img_bullet_name);
		Bitmap temp = BitmapFactory.decodeStream(is);
		img_bullet = GameUtils.ZomeBitamp(temp, GameUtils.IMG_ENLARGE,
				GameUtils.IMG_ENLARGE);
		bullet_w = img_bullet.getWidth();
		bullet_h = img_bullet.getHeight();
		bullet_x = -MainGame.Screen_w;
		bullet_y = -MainGame.Screen_h;
		bullet_statu = BULLET_DIE;
		bullet_speed = 5;
		this.Boom_Init();
	}

	public void Deal_Bullet() {
		this.Bullet_Move();
		this.Deal_Boom();
	}

	public void Draw_Bullet(Canvas canvas) {
		if (this.bullet_statu == BULLET_MOVE) {
			GameUtils.Brush(canvas, img_bullet, bullet_x, bullet_y, bullet_w,
					bullet_h, 0, 0);
		} else if (this.bullet_statu == BULLET_DIE) {
			bullet_x = -MainGame.Screen_w;
			bullet_y = -MainGame.Screen_h;
		}
		this.Draw_Boom(canvas);
	}

	public void Bullet_Move() {
		if (this.bullet_y < MainGame.Screen_h) {
			bullet_y += this.bullet_speed;
		} else {
			this.bullet_statu = BULLET_DIE;
		}
	}

	public void Update_Bullet_Local(int x, int y) {
		this.bullet_x = x;
		this.bullet_y = y;
		this.bullet_statu = BULLET_MOVE;
	}

	// ----------- 爆炸
	public void Boom_Init(){
		InputStream is = this.getImgStream("smallplanebom_.png");
		Bitmap temp = BitmapFactory.decodeStream(is);
		img_boom = GameUtils.ZomeBitamp(temp, GameUtils.IMG_ENLARGE,
				GameUtils.IMG_ENLARGE);
		boom_w = img_boom.getWidth();
		boom_h = img_boom.getHeight()/9;
		boom_x = -MainGame.Screen_w;
		boom_y = -MainGame.Screen_h;
		boom_col = 0;
		boom_row = 0;
		boom_statu = BOOM_DIE;
	}

	public void Deal_Boom(){
		if(boom_statu == BOOM_POWER){
			if(boom_row <9){
				boom_row ++;
			}else{
				boom_row = 0;
				boom_x = -MainGame.Screen_w;
				boom_y = -MainGame.Screen_h;
				boom_statu = BOOM_DIE;
			}
		}
	}

	public void Draw_Boom(Canvas canvas){
		GameUtils.Brush(canvas, img_boom, boom_x, boom_y, boom_w, boom_h, boom_col, boom_row);
	}

	public InputStream getImgStream(String name){
		return this.getClass().getResourceAsStream(
				GameUtils.IMG_PATH+name);
	}
}
