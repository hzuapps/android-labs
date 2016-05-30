package com.hewq.linkgame2.view;

import android.graphics.Bitmap;

public class PieceImage {
	private int id;//����android�Զ����ɵ�id��������
	private Bitmap bitmap;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	public boolean isSameImage(PieceImage otherImage){
		if(null==otherImage){
			return false;
		}
		return id==otherImage.getId();
	}
}
