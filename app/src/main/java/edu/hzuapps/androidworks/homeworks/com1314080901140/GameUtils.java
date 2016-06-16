package com.hzu.xu.planewar;

import java.io.InputStream;
import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Region.Op;

public class GameUtils {

	//------------- 常量
	/**
	 * 图片放大率
	 */
	final static float IMG_ENLARGE = 2;

	/**
	 * 图片路径
	 */
	final static String IMG_PATH = "/assets/img/";

	// ----------------- 工具方法
	public  Bitmap LoadImage(String path) {
		InputStream is = this.getClass().getResourceAsStream(
				"/assets/img/" + path);
		return BitmapFactory.decodeStream(is);
	}

	/**
	 * 绘画剪裁
	 */
	public static void Brush(Canvas canvas, Bitmap bmp, int x, int y, int w,
							 int h, int col, int row) {
		canvas.clipRect(x, y, x + w, y + h, Op.REPLACE);
		canvas.drawBitmap(bmp, x - col * w, y - row * h, MainGame.paint);
		canvas.clipRect(0, 0, MainGame.Screen_w, MainGame.Screen_h, Op.REPLACE);
	}

	public static Bitmap ZomeBitamp(Bitmap img, float size_w, float size_h) {
		/* 产生resize后的Bitmap对象 */
		Matrix matrix = new Matrix();
		matrix.setScale(size_w, size_h);
		Bitmap resizeBmp = Bitmap.createBitmap(img, 0, 0, img.getWidth(),
				img.getHeight(), matrix, true);
		return resizeBmp;
	}
	/*public static Bitmap ZomeBitamp(Bitmap img, int new_w, int new_h) {
		float w = (float) new_w / img.getWidth();
		float h = (float) new_h / img.getHeight();
		 产生resize后的Bitmap对象
		Matrix matrix = new Matrix();
		matrix.setScale(w, h);
		Bitmap resizeBmp = Bitmap.createBitmap(img, 0, 0, img.getWidth(),
				img.getHeight(), matrix, true);
		return resizeBmp;
	}*/

	/**
	 * 获取随机数
	 *
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @return
	 */
	public static int GetRadomNumber(int min, int max) {
		return new Random().nextInt(max - min) + min;
	}

	//是否发生碰撞
	public static boolean isRam(int r_x, int r_y, int r_w, int r_h, int b_x,
								int b_y, int b_w, int b_h) {
		if (r_x + r_w < b_x || b_x + b_w < r_x || r_y + r_h < b_y
				|| b_y + b_h < r_y) {
			return false;
		} else {
			return true;
		}
	}

}
