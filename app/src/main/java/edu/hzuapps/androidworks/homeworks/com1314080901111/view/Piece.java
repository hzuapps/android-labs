package com.hewq.linkgame2.view;

import android.graphics.Rect;

public class Piece {
	private PieceImage pieceImage;
	private int arrayX; //��1��ʼ
	private int arrayY; //��1��ʼ
	private Rect locationRect;
	
	public PieceImage getPieceImage() {
		return pieceImage;
	}
	public void setPieceImage(PieceImage pieceImage) {
		this.pieceImage = pieceImage;
	}
	public int getArrayX() {
		return arrayX;
	}
	public void setArrayX(int arrayX) {
		this.arrayX = arrayX;
	}
	public int getArrayY() {
		return arrayY;
	}
	public void setArrayY(int arrayY) {
		this.arrayY = arrayY;
	}
	public Rect getLocationRect() {
		return locationRect;
	}
	public void setLocationRect(Rect locationRect) {
		this.locationRect = locationRect;
	}
	public boolean isSameImage(Piece otherPiece){
		return pieceImage.isSameImage(otherPiece.getPieceImage());
	}
}
