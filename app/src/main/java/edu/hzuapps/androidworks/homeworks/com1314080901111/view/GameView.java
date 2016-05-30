package com.hewq.linkgame2.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.hewq.linkgame2.service.LinkService;


public class GameView extends View{
	private Paint mPaint;  
    private Context mContext;
    private Piece[][]  pieceArray;
    private Liner mLiner;
    private LinkService linkService;
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext=context;
		mPaint =new Paint();
		mPaint.setColor(Color.BLUE);
		mPaint.setStrokeWidth(5);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		pieceArray=linkService.getPieceArray();
		mLiner=linkService.getLiner();
		if(null!=pieceArray&&pieceArray.length>0){
			for(int x=0;x<pieceArray.length;x++){
				for(int y=0;y<pieceArray[x].length;y++){
					if(null!=pieceArray[x][y]){
					Piece piece = pieceArray[x][y];
					canvas.drawBitmap(piece.getPieceImage().getBitmap(),null, piece.getLocationRect(), null);
					}
				}
			}
		}
		if(mLiner.needLink()){
			for(int index=1;index<mLiner.getPoints().size();index++){
				Point point1=mLiner.getPoints().get(index-1);
				Point point2=mLiner.getPoints().get(index);
				canvas.drawLine(point1.x, point1.y,point2.x, point2.y, mPaint);
			}
		}
	}
	public int startGame(int arrayXsize,int arrayYsize){
		int startResult=linkService.startGame(mContext, arrayXsize, arrayYsize, this);
		this.postInvalidate();
		return startResult;
	}
	public void setLinkService(LinkService linkService) {
		this.linkService = linkService;
	}
}
