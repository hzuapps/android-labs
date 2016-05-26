package com.example.link2link.board.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.link2link.board.AbstractBoard;
import com.example.link2link.object.GameConf;
import com.example.link2link.view.Piece;

/**
 * 空出二维数组最外围的部分，填满其中的部分
 * 
 * @author CJP
 * 
 */
public class FullBoard extends AbstractBoard {

	@Override
	protected List<Piece> createPieces(GameConf config, Piece[][] pieces) {
		List<Piece> notNullPieces = new ArrayList<Piece>();
		for (int i = 1; i < pieces.length - 1; i++) {
			for (int j = 1; j < pieces.length - 1; j++) {
				Piece piece = new Piece(i, j);
				notNullPieces.add(piece);
			}
		}
		return notNullPieces;
	}

}
