package com.example.com1314080901204.view;

import android.graphics.Bitmap;

/**
 * 封装了方块上的图片，由表示图片的Bitmap对象和图片资源的Id构成
 * 
 * @author CJP
 * 
 */
public class PieceImage {

	// 表示方块上的图片的Bitmap对象
	private Bitmap image;
	// 表示方块上的图片资源的Id,作为判断两个方块上的图片是否相同的标识
	private int imageId;

	public PieceImage(Bitmap image, int imageId) {
		super();
		this.image = image;
		this.imageId = imageId;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

}
