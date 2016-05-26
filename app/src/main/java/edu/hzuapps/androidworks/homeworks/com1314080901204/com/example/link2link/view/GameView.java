package com.example.link2link.view;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.example.link2link.R;
import com.example.link2link.board.GameService;
import com.example.link2link.object.LinkInfo;
import com.example.link2link.util.ImageUtil;

public class GameView extends View {

	// 游戏逻辑的实现类
	private GameService gameService;
	// 保存当前已经被选中的方块
	private Piece selectedPiece;
	// 连接信息对象
	private LinkInfo linkInfo;
	private Paint paint;
	// 选中标识的图片对象
	private Bitmap selectImage;

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.paint = new Paint();
		// 使用位图平铺作为连接线条（不太明白）
		this.paint.setShader(new BitmapShader(BitmapFactory.decodeResource(
				context.getResources(), R.drawable.heart),
				Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
		// 设置连接线的粗细
		this.paint.setStrokeWidth(9);
		this.selectImage = ImageUtil.getSelectImage(context);
	}

	public void setLinkInfo(LinkInfo linkInfo) {
		this.linkInfo = linkInfo;
	}

	public void setGameService(GameService gameService) {
		this.gameService = gameService;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (this.gameService == null) {
			return;
		}
		// 返回游戏当前状态，即还剩下多少方块
		Piece[][] pieces = gameService.getPieces();
		if (pieces != null) {
			// 遍历pieces，并将其中的piece画出来
			for (int i = 0; i < pieces.length; i++) {
				for (int j = 0; j < pieces[i].length; j++) {
					if (pieces[i][j] != null) {
						// 得到这个Piece对象
						Piece piece = pieces[i][j];
						// 根据方块左上角X、Y坐标绘制方块
						canvas.drawBitmap(piece.getImage().getImage(),
								piece.getBeginX(), piece.getBeginY(), null);
					}
				}
			}
		}

		// 如果当前对象中有linkInfo对象, 即连接信息
		if (this.linkInfo != null) {
			// 绘制连接线
			drawLine(this.linkInfo, canvas);
			// 处理完后清空linkInfo对象
			this.linkInfo = null;
		}
		// 画选中标识的图片
		if (this.selectedPiece != null) {
			canvas.drawBitmap(this.selectImage, this.selectedPiece.getBeginX(),
					this.selectedPiece.getBeginY(), null);
		}
	}

	// 根据LinkInfo绘制连接线的方法。
	private void drawLine(LinkInfo linkInfo, Canvas canvas) {
		// 获取LinkInfo中封装的所有连接点
		List<Point> points = linkInfo.getLinkPoints();
		// 依次遍历linkInfo中的每个连接点
		for (int i = 0; i < points.size() - 1; i++) {
			// 获取当前连接点与下一个连接点
			Point currentPoint = points.get(i);
			Point nextPoint = points.get(i + 1);
			// 绘制连线
			canvas.drawLine(currentPoint.x, currentPoint.y, nextPoint.x,
					nextPoint.y, this.paint);
		}
	}

	// 设置当前选中方块的方法
	public void setSelectedPiece(Piece piece) {
		this.selectedPiece = piece;
	}

	// 开始游戏方法
	public void startGame() {
		this.gameService.start();
		this.postInvalidate();
	}

}
