package com.hzu.xu.planewar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.app.Activity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class MainGame extends View implements Runnable {
	Data d;
	private int Count=0;

	int Game_State;
	// 游戏的Logo 状态
	public static final int GAME_LOGO = 0X0001;
	// 游戏的加载 状态
	public static final int GAME_INIT = 0X0002;
	// 游戏的菜单 状态
	public static final int GAME_MENU = 0X0003;
	// 游戏的进行 状态
	public static final int GAME_PLAY = 0X0004;
	// 游戏的暂停 状态
	public static final int GAME_PAUSE = 0X0005;
	// 游戏的退出 状态
	public static final int GAME_EXIT = 0X0006;

	// 屏幕宽和高
	static int Screen_w, Screen_h;

	// 是否触摸了屏幕
	static boolean isPoint;
	// 触点x,y的位置
	static int Point_x, Point_y;

	// 画笔
	static Paint paint;

	// 游戏运行线程
	Thread thread;
	// 游戏线程是否在运行
	boolean isRun;

	// ---------------- 主人公
	Hero hero;

	// ---------------- Npc
	Npc npcs[];

	public MainGame(Context context, Display display) {
		super(context);
		Screen_w = display.getWidth();
		Screen_h = display.getHeight();
		this.Game_State = GAME_LOGO;
		paint = new Paint();
		this.isRun = true;
		this.thread = new Thread(this);
		this.thread.start();
	}

	@Override
	public void run() {
		while (this.isRun) {
			this.Deal();
			this.postInvalidate();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void Deal() {
		switch (this.Game_State) {
			case GAME_LOGO:
				this.Deal_Logo();
				break;
			case GAME_INIT:
				this.Deal_Init();
				break;
			case GAME_MENU:
				this.Deal_Menu();
				break;
			case GAME_PLAY:
				this.Deal_Play();
				break;
			case GAME_PAUSE:
				this.Deal_Pause();
				break;
			case GAME_EXIT:
				this.Deal_Exit();
				break;
			default:
				break;
		}
	}

	public void Deal_Logo() {
		this.Game_State = GAME_INIT;
	}

	public void Deal_Init() {
		this.Hero_Init();
		this.Npc_Init();
		this.Game_State = GAME_MENU;
	}

	public void Deal_Menu() {
		this.Game_State = GAME_PLAY;

	}

	public void Deal_Play() {
		this.Deal_Hero();
		this.Deal_Npc();
		this.HeroBulletCollNpc();
		this.NpcBulletCollHero();
	}

	public void Deal_Pause() {

	}

	public void Deal_Exit() {

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		switch (this.Game_State) {
			case GAME_LOGO:
				this.Draw_Logo(canvas);
				break;
			case GAME_INIT:
				this.Draw_Init(canvas);
				break;
			case GAME_MENU:
				this.Draw_Menu(canvas);
				break;
			case GAME_PLAY:
				this.Draw_Play(canvas);
				break;
			case GAME_PAUSE:
				this.Draw_Pause(canvas);
				break;
			case GAME_EXIT:
				this.Draw_Exit(canvas);
				break;
		}
	}

	public void Draw_Logo(Canvas canvas) {

	}

	public void Draw_Init(Canvas canvas) {

	}

	public void Draw_Menu(Canvas canvas) {

	}

	public void Draw_Play(Canvas canvas) {
		this.Draw_Hero(canvas);
		this.Draw_Npc(canvas);
		if(this.hero.hero_statu == Hero.HERO_DIE){
			this.Draw_Game_Over(canvas);
		}
	}

	public void Draw_Pause(Canvas canvas) {

	}

	public void Draw_Exit(Canvas canvas) {

	}

	public void Draw_Game_Over(Canvas canvas) {
		paint.setTextSize(70);
		paint.setColor(Color.RED);
		canvas.drawText("得分为"+Count+"！！！", 20, Screen_h/2, paint);
		paint.reset();
	}

	// ------------------- 触屏
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Point_x = (int) event.getRawX();
		Point_y = (int) event.getRawY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				isPoint = true;
				break;
			case MotionEvent.ACTION_UP:
				isPoint = false;
				this.hero.hero_row = 1;
				break;
			default:
				break;
		}
		return true;
	}

	// ---------------- 主人公

	public void Hero_Init() {
		hero = new Hero();
	}

	public void Deal_Hero() {
		hero.Deal_Hero();
	}

	public void Draw_Hero(Canvas canvas) {
		hero.Draw_Hero(canvas);
	}

	// ---------------- Npc

	public void Npc_Init() {
		npcs = new Npc[10];
		for (int i = 0; i < npcs.length; i++) {
			npcs[i] = new Npc();
		}
	}

	public void Deal_Npc() {
		for (int i = 0; i < npcs.length; i++) {
			npcs[i].Deal_Npc();
		}
	}

	public void Draw_Npc(Canvas canvas) {
		for (int i = 0; i < npcs.length; i++) {
			if (npcs[i].npc_statu == Npc.NPC_MOVE) {
				npcs[i].Draw_Npc(canvas);
			}
		}
	}

	// --------------- Hero的子弹碰撞npc
	public void HeroBulletCollNpc() {
		for (int i = 0; i < this.hero.bullets.length; i++) {
			for (int j = 0; j < this.npcs.length; j++) {
				if (GameUtils.isRam(this.hero.bullets[i].bullet_x,
						this.hero.bullets[i].bullet_y,
						this.hero.bullets[i].bullet_w,
						this.hero.bullets[i].bullet_h, this.npcs[j].npc_x,
						this.npcs[j].npc_y, this.npcs[j].npc_w,
						this.npcs[j].npc_h)) {
					this.hero.bullets[i].boom_statu = BulletHero.BOOM_POWER;
					this.hero.bullets[i].boom_x = this.hero.bullets[i].bullet_x;
					this.hero.bullets[i].boom_y = this.hero.bullets[i].bullet_y;
					Count++;
					this.hero.bullets[i].bullet_statu = BulletHero.BULLET_DIE;
					this.hero.bullets[i].bullet_x = 0;
					this.hero.bullets[i].bullet_y = -Screen_h;

					//将npc移出屏幕，不然子弹打在该处还会爆炸
					this.npcs[j].npc_statu = Npc.NPC_DIE;

					this.npcs[j].npc_x = 0;
					this.npcs[j].npc_y = Screen_h * 3;
					break;
				}
			}
		}
	}

	// --------------- npc的子弹碰撞hero
	public void NpcBulletCollHero() {
		for (int a = 0; a < this.npcs.length; a++) {
			for (int i = 0; i < this.npcs[a].bullets.length; i++) {
				if (GameUtils.isRam(this.npcs[a].bullets[i].bullet_x,
						this.npcs[a].bullets[i].bullet_y,
						this.npcs[a].bullets[i].bullet_w,
						this.npcs[a].bullets[i].bullet_h, this.hero.hero_x,
						this.hero.hero_y, this.hero.hero_w, this.hero.hero_h)) {
					this.npcs[a].bullets[i].boom_statu = BulletNpc.BOOM_POWER;
					this.npcs[a].bullets[i].boom_x = this.hero.hero_x;
					this.npcs[a].bullets[i].boom_y = this.hero.hero_y;

					this.npcs[a].bullets[i].bullet_statu = BulletHero.BULLET_DIE;
					this.npcs[a].bullets[i].bullet_x = 0;
					this.npcs[a].bullets[i].bullet_y = -Screen_h;

					//将英雄移出屏幕，不然子弹打在英雄处还是会爆炸
					this.hero.hero_statu = Hero.HERO_DIE;
					this.hero.hero_x = 0;
					this.hero.hero_y = Screen_h * 3;
					break;
				}
			}
		}
	}

}
