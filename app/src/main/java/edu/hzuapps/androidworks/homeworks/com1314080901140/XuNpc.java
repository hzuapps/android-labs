package com.hzu.xu.planewar;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class XuNpc {
	Bitmap img_npc;
	int npc_speed;
	int npc_x, npc_y, npc_w, npc_h, npc_col, npc_row;

	public int npc_statu;
	public static final int NPC_MOVE = 0X000001;
	public static final int NPC_DIE = 0X000002;

	// ----------------------子弹
	BulletNpc bullets[];
	// 子弹计数器
	int bullet_count;

	public Npc() {
		Npc_Init();
		this.Bullet_Init();
	}

	public void Npc_Init() {
		InputStream is = this.getClass().getResourceAsStream(
				"/assets/img/smallplane_.png");
		Bitmap temp = BitmapFactory.decodeStream(is);
		img_npc = GameUtils.ZomeBitamp(temp, GameUtils.IMG_ENLARGE,
				GameUtils.IMG_ENLARGE);
		npc_w = img_npc.getWidth();
		npc_h = img_npc.getHeight() / 6;
		npc_y = GameUtils.GetRadomNumber(-npc_h * 2, -npc_h);
		npc_x = GameUtils.GetRadomNumber(0, MainGame.Screen_w - npc_w);
		npc_col = 0;
		npc_row = GameUtils.GetRadomNumber(0, 4);
		npc_speed = GameUtils.GetRadomNumber(2, 6);
		npc_statu = NPC_MOVE;
	}

	public void Deal_Npc() {
		this.Npc_Move();
		this.Deal_Bullet();
	}

	public void Npc_Move() {
		if (npc_statu == NPC_MOVE) {
			npc_y += this.npc_speed;

		} else if (npc_statu == NPC_DIE) {
			npc_y = GameUtils.GetRadomNumber(-npc_h * 2, -npc_h);
			npc_x = GameUtils.GetRadomNumber(0, MainGame.Screen_w - npc_w);
			npc_row = GameUtils.GetRadomNumber(0, 4);
			npc_speed = GameUtils.GetRadomNumber(2, 6);
			npc_statu = NPC_MOVE;

		}
		if (npc_y > MainGame.Screen_h) {
			npc_statu = NPC_DIE;
		}
	}

	public void Draw_Npc(Canvas canvas) {
		if (this.npc_statu == NPC_MOVE) {
			GameUtils.Brush(canvas, img_npc, npc_x, npc_y, npc_w, npc_h,
					npc_col, npc_row);
		}
		this.Draw_Bullet(canvas);
	}

	// ----------------------- 子弹
	public void Bullet_Init() {
		bullets = new BulletNpc[25];
		for (int i = 0; i < bullets.length; i++) {
			bullets[i] = new BulletNpc();
//			bullets[i].setImgBulletName("bullet-4.png");
			bullets[i].Bullet_Init();
		}
	}

	public void Deal_Bullet() {
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].Deal_Bullet();
		}
		if (this.bullet_count++ % 30 == 0) {
			for (int i = 0; i < bullets.length; i++) {
				if (bullets[i].bullet_statu == BulletNpc.BULLET_DIE) {
					bullets[i].Update_Bullet_Local(this.npc_x + this.npc_w
							/ 2 - bullets[i].bullet_w / 2, this.npc_y+this.npc_h);
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
