package com.hewq.linkgame2.service;

import android.content.Context;
import android.view.View;

import com.hewq.linkgame2.view.Liner;
import com.hewq.linkgame2.view.Piece;


public interface LinkService {
	int startGame(Context context, int arrayXsize, int arrayYsize, View view);
	
	Piece[][]  getPieceArray();
	
	Piece getTouchPiece(float touchX, float touchY);
		
	Liner handleTouchDown(Piece currentPiece);
	boolean handleTouchUp(Piece currentPiece);
	
	
	boolean needLink();
	
	Liner getLiner();
	
	boolean isGameOver();
}
