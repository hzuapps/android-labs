package com.example.com1314080901204.board;

import java.util.List;
import com.example.com1314080901204.object.GameConf;
import com.example.com1314080901204.util.ImageUtil;
import com.example.com1314080901204.view.Piece;
import com.example.com1314080901204.view.PieceImage;

/**
 * 定义了一个Pieces数组的模板，由子类去实现具体的状态
 * @author CJP
 *
 */
public abstract class AbstractBoard {

	protected abstract List<Piece> createPieces(GameConf config, Piece[][] pieces);

	public Piece[][] create(GameConf config) {
		// 创建一个Piece[][]数组
		Piece[][] pieces = new Piece[config.getXSize()][config.getYSize()];
		// 返回非空的Piece集合，该集合由子类去创建
		List<Piece> notNullPieces = createPieces(config, pieces);
		// 根据非空Piece对象的集合的大小取图片
		List<PieceImage> playImages = ImageUtil.getPlayImages(
				config.getContext(), notNullPieces.size());
		// 获取图片的宽、高，所有图片都一样大
		int imageWidth = playImages.get(0).getImage().getWidth();
		int imageHeight = playImages.get(0).getImage().getHeight();
		// 遍历非空的Piece集合,设置集合中每个Piece对象的图片，左上角的坐标
		for (int i = 0; i < notNullPieces.size(); i++) {
			// 依次获取每个Piece对象
			Piece piece = notNullPieces.get(i);
			piece.setImage(playImages.get(i));
			// 计算每个方法块左上角的X、Y坐标（以第一张图片的位置为起点）
			piece.setBeginX(piece.getIndexX() * imageWidth
					+ config.getBeginImageX());
			piece.setBeginY(piece.getIndexY() * imageHeight
					+ config.getBeginImageY());
			// 将该方块对象放入方块数组的相应位置
			pieces[piece.getIndexX()][piece.getIndexY()] = piece;
		}
		return pieces;
	}
}
