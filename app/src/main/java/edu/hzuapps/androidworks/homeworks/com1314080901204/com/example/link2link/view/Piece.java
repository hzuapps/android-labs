package com.example.link2link.view;

import android.graphics.Point;

/**
 * 封装了游戏界面上的方块，包括方块上的图片，方块的左上角坐标，方块在二维数组中的索引值， 
 * 方块的中心点和判断两个方块是否相同的逻辑
 * 
 * @author CJP
 * 
 */
public class Piece {

	// 表示该方块上的图片
	private PieceImage image;
	// 由方块左上角的坐标决定方块在游戏界面中的位置
	// 1.表示该方块的左上角的x坐标
	private int beginX;
	// 2.表示该方块的左上角的y坐标
	private int beginY;
	// 该对象在Piece[][]数组中第一维的索引值
	private int indexX;
	// 该对象在Piece[][]数组中第二维的索引值
	private int indexY;

	public Piece(int indexX, int indexY) {
		super();
		this.indexX = indexX;
		this.indexY = indexY;
	}

	public PieceImage getImage() {
		return image;
	}

	public void setImage(PieceImage image) {
		this.image = image;
	}

	public int getBeginX() {
		return beginX;
	}

	public void setBeginX(int beginX) {
		this.beginX = beginX;
	}

	public int getBeginY() {
		return beginY;
	}

	public void setBeginY(int beginY) {
		this.beginY = beginY;
	}

	public int getIndexX() {
		return indexX;
	}

	public void setIndexX(int indexX) {
		this.indexX = indexX;
	}

	public int getIndexY() {
		return indexY;
	}

	public void setIndexY(int indexY) {
		this.indexY = indexY;
	}

	// 获取该方块的中心点
	public Point getCenter() {
		return new Point(getImage().getImage().getWidth() / 2 + getBeginX(),
				getImage().getImage().getHeight() / 2 + getBeginY());
	}

	// 判断两个方块上的图片是否相同
	public boolean isSameImage(Piece other) {
		if (image != null) {
			return image.getImageId() == other.image.getImageId();
		}
		return false;
	}

}
