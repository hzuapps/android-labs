package com.hzu.xu.planewar;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class XuHero {
	// ----------------------------- 人物hero
	Bitmap img_hero;

	final int hero_spreed = 15;

	int hero_x, hero_y, hero_w, hero_h, hero_col, hero_row;

	public int hero_statu;
	public static final int HERO_MOVE = 0X000001;
	public static final int HERO_DIE = 0X000002;

	// ----------------------子弹
	BulletHero bullets[];
	// 子弹计数器
	int bullet_count;

	public Hero() {
		Hero_Init();
		Bullet_Init();
	}

	public void Hero_Init() {
		InputStream is = this.getClass().getResourceAsStream(
				"/assets/img/Plane_1.png");
		Bitmap temp = BitmapFactory.decodeStream(is);
		img_hero = GameUtils.ZomeBitamp(temp, GameUtils.IMG_ENLARGE,
				GameUtils.IMG_ENLARGE);
		hero_w = img_hero.getWidth();
		hero_h = img_hero.getHeight() / 3;
		hero_x = MainGame.Screen_w / 2;
		hero_y = MainGame.Screen_h - hero_h;
		hero_col = 0;
		hero_row = 1;
		hero_statu = HERO_MOVE;
	}

	public void Deal_Hero() {
		this.Hero_Move();
		this.Deal_Bullet();
	}

	public void Draw_Hero(Canvas canvas) {
		if (this.hero_statu == HERO_MOVE) {
			GameUtils.Brush(canvas, this.img_hero, hero_x, hero_y, hero_w,
					hero_h, hero_col, hero_row);
			this.Draw_Bullet(canvas);
		}
	}

	public void Hero_Move() {
		if (MainGame.isPoint) {
			// 左
			if (MainGame.Point_x < this.hero_x) {
				this.hero_x -= this.hero_spreed;
				this.hero_row = 2;
			}
			// 上
			if (MainGame.Point_y < this.hero_y) {
				this.hero_y -= this.hero_spreed;
			}
			// 右
			if (MainGame.Point_x > this.hero_x + this.hero_w) {
				this.hero_x += this.hero_spreed;
				this.hero_row = 0;
			}
			// 下
			if (MainGame.Point_y > this.hero_y + this.hero_h) {
				this.hero_y += this.hero_spreed;
			}
		}
	}

	// ----------------------- 子弹
	public void Bullet_Init() {
		bullets = new BulletHero[25];
		for (int i = 0; i < bullets.length; i++) {
			bullets[i] = new BulletHero();
			bullets[i].Bullet_Init();
		}
	}

	public void Deal_Bullet() {
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].Deal_Bullet();
		}
		if (this.bullet_count++ % 4 == 0) {
			for (int i = 0; i < bullets.length; i++) {
				if (bullets[i].bullet_statu == BulletHero.BULLET_DIE) {
					bullets[i].Update_Bullet_Local(this.hero_x + this.hero_w
							/ 2 - bullets[i].bullet_w / 2, this.hero_y);
					break;
				}
			}
		}
	}

	public void Draw_Bullet(Canvas canvas) {
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].Draw_Bullet(canvas);
		}
	}

}
