package com.example.com1314080901204.object;

import android.content.Context;

/**
 * 保存游戏的配置信息，包括每个方块中图片的宽、高，游戏的总时长，方块数组第一维、第二维的长度，
 * Board中第一张图片出现的x，y坐标，游戏通关的时长，游戏的上下文
 * 
 * @author CJP
 * 
 */
public class GameConf {
	// 设置连连看的每个方块的图片的宽、高
	public static final int PIECE_WIDTH = 40;
	public static final int PIECE_HEIGHT = 40;
	// 记录游戏的总时间（100秒）.
	public static int DEFAULT_TIME = 100;
	// Piece[][]数组第一维的长度
	private int xSize;
	// Piece[][]数组第二维的长度
	private int ySize;
	// Board中第一张图片出现的x座标
	private int beginImageX;
	// Board中第一张图片出现的y座标
	private int beginImageY;
	// 记录游戏的总时间, 单位是毫秒
	private long gameTime;
	private Context context;

	/**
	 * 提供一个参数构造器
	 * 
	 * @param xSize
	 *            Piece[][]数组第一维长度
	 * @param ySize
	 *            Piece[][]数组第二维长度
	 * @param beginImageX
	 *            Board中第一张图片出现的x座标
	 * @param beginImageY
	 *            Board中第一张图片出现的y座标
	 * @param gameTime
	 *            设置每局的时间, 单位是秒
	 * @param context
	 *            应用上下文
	 */
	public GameConf(int xSize, int ySize, int beginImageX, int beginImageY,
			long gameTime, Context context) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.beginImageX = beginImageX;
		this.beginImageY = beginImageY;
		this.gameTime = gameTime;
		this.context = context;
	}

	public long getGameTime() {
		return gameTime;
	}

	public int getXSize() {
		return xSize;
	}

	public int getYSize() {
		return ySize;
	}

	public int getBeginImageX() {
		return beginImageX;
	}

	public int getBeginImageY() {
		return beginImageY;
	}

	public Context getContext() {
		return context;
	}
}
