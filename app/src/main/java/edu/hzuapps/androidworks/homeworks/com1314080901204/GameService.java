package com.example.com1314080901204.board;

import com.example.com1314080901204.object.LinkInfo;
import com.example.com1314080901204.view.Piece;

/**
 * GameSerive负责定义游戏的逻辑，包括游戏的开始，游戏的状态，逻辑的判断方法等
 * 
 * @author CJP
 * 
 */
public interface GameService {

	// 控制游戏开始
	void start();

	// 返回游戏状态，即还剩多少Piece对象
	Piece[][] getPieces();

	// 判断Piece[][]数组中是否还用非空的Piece对象
	boolean hasPieces();

	// 根据触碰点的坐标找出Piece对象
	Piece findPiece(float touchX, float touchY);

	// 判断两个Piece是否可以相连,如果可以，返回LinkInfo对象，不可以则返回null
	LinkInfo link(Piece p1, Piece p2);
}
