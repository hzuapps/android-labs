package com.hewq.linkgame2.service.imp;


import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

import com.hewq.linkgame2.R;
import com.hewq.linkgame2.service.LinkService;
import com.hewq.linkgame2.utils.ImageUtil;
import com.hewq.linkgame2.view.Liner;
import com.hewq.linkgame2.view.Piece;
import com.hewq.linkgame2.view.PieceImage;

import java.util.ArrayList;
import java.util.List;

public class LinkServiceImp implements LinkService {
	private int containerXsize;
	private int containerYsize;
	private int arrayXsize;
	private int arrayYsize;
	private Context mContext;
	private Piece[][] mPieceArray;
	private View mView;
	private int pieceWidth;
	private int pieceHight;
	private Liner mLiner=new Liner();
	private int pieceSumSize;
	private Piece prePiece=null;
	
	
	@Override
	public int startGame(Context context, int arrayXsize, int arrayYsize,View view) {
		mView=view;
		containerXsize =mView.getWidth();
		containerYsize=mView.getHeight();
		mContext=context;
		this.arrayXsize=arrayXsize;
		this.arrayYsize=arrayYsize;
		if((arrayXsize*arrayYsize)%2!=0){ //������һ��ż��
			return R.string.initwrong;
		}
		List<PieceImage> pieceImageList= ImageUtil.createPieceImageList(mContext, arrayXsize * arrayYsize);
		mPieceArray=createPieceArray(pieceImageList);
		pieceSumSize=arrayXsize*arrayYsize;
		return R.string.initsuccess;
	}

	@Override
	public Piece[][]  getPieceArray() {
		return mPieceArray;
	}
	
	/**
	 ����ÿ�����Ԫ�أ����䲼������λ�ã�����λ�ý������� 
	 */
	private  Piece[][] createPieceArray(List<PieceImage> pieceImages){
		Piece[][] piecesArray =new Piece[arrayXsize+2][arrayYsize+2];
		pieceWidth=containerXsize/(arrayXsize+2);
		pieceHight=containerYsize/(arrayYsize+2);
		int listLoaction=0;
		for(int x=1;x<arrayXsize+1;x++){
			for(int y=1;y<arrayYsize+1;y++){				
				Piece tempPiece = new Piece();
				tempPiece.setArrayX(x);
				tempPiece.setArrayY(y);
				tempPiece.setPieceImage(pieceImages.get(listLoaction++));
				int coordLeft=x*pieceWidth+pieceWidth/5;
				int coordTop=y*pieceHight+pieceHight/5;
				int coordright=coordLeft +pieceWidth-pieceWidth/5;
				int coordbottom=coordTop+pieceHight-pieceHight/5; 
				Rect rect = new Rect(coordLeft, coordTop, coordright, coordbottom);
				tempPiece.setLocationRect(rect);
				piecesArray[x][y]=tempPiece;
			}
		}
		return piecesArray;
	}
	
	@Override
	public Piece getTouchPiece(float touchX, float touchY) {
		int currentXindex=(int) touchX/pieceWidth;
		int currentYindex=(int) touchY/pieceHight;
		if(currentXindex<=0||currentYindex<=0||currentXindex>=arrayXsize+1||currentYindex>=arrayYsize+1){
			return null;
		}
		if(prePiece!=null&&currentXindex==prePiece.getArrayX()&&currentYindex==prePiece.getArrayY()){
			return null;
		}
		if(mPieceArray[currentXindex][currentYindex]!=null){
			return mPieceArray[currentXindex][currentYindex];
		}
			return null;
		}
	
	@Override
	public Liner handleTouchDown(Piece currentPiece) {	
		if(null==prePiece){//��һ����û��ʱ����ʼ��prePiece��������ӵ�
			prePiece=currentPiece;
			animateBig(prePiece);
			mLiner.clearPoint();
			return mLiner;
		}else if(currentPiece.getPieceImage().getId()!=prePiece.getPieceImage().getId()){
			animateSmall(prePiece);
			prePiece=currentPiece;
			animateBig(prePiece);
			mLiner.clearPoint();
			return mLiner;
		}
		else if(currentPiece.getPieceImage().getId()==prePiece.getPieceImage().getId()){
			boolean directConnectResult = directConnectTest(prePiece,currentPiece);
			if(directConnectResult){//���ֱ�����ӳɹ���ֱ�ӷ���
				createLinker(prePiece,currentPiece);
				animateBig(currentPiece);
				return mLiner;
			}
			boolean oneCornerConnectResult = oneCornerConnectTest(prePiece,currentPiece);
			if(oneCornerConnectResult){
				animateBig(currentPiece);
				return mLiner;
			}
			boolean twoCornerConnectResult =twoCornerConnectTest(prePiece, currentPiece);
			if(twoCornerConnectResult){
				animateBig(currentPiece);
				return mLiner;
			}

		}
		animateSmall(prePiece);
		prePiece=currentPiece;
		animateBig(prePiece);
		mLiner.clearPoint();
		return mLiner;
	}
	
	private boolean directConnectTest(Piece preOne,Piece nextOne){
		if(preOne.getArrayX()==nextOne.getArrayX()||preOne.getArrayY()==nextOne.getArrayY()){
		if(preOne.getArrayX()==nextOne.getArrayX()){//λ��ͬһ��
			int sameX=preOne.getArrayX();
			if(preOne.getArrayY()>nextOne.getArrayY()){
				return directConnectTest(nextOne,preOne); //ֻ�����һ��Ԫ���������������������λ��
			}else{
				int begin=preOne.getArrayY()+1;
				int end=nextOne.getArrayY()-1;
				for(int indexY=begin;indexY<=end;indexY++){
					if(null!=mPieceArray[sameX][indexY]){
						return false;
					}
				}
				return true;
			}			
		}else if(preOne.getArrayY()==nextOne.getArrayY()){//λ��ͬһ��
			int sameY=preOne.getArrayY();
			if(preOne.getArrayX()>nextOne.getArrayX()){
				return(directConnectTest(nextOne,preOne)); //ֻ�����һ��Ԫ������ߵ�������������λ��
			}else{
				int begin=preOne.getArrayX()+1;
				int end=nextOne.getArrayX()-1;
				for(int indexX=begin;indexX<=end;indexX++){
					if(null!=mPieceArray[indexX][sameY]){
						return false;
					}
				}
				 return true;
			}
		}
		}
		return false;	
	}
	
	private boolean oneCornerConnectTest(Piece preOne,Piece nextOne){
		int firstCornerX=preOne.getArrayX();
		int firstCornerY=nextOne.getArrayY();
		int secondCornerX=nextOne.getArrayX();
		int secondCornerY=preOne.getArrayY();	
		if(null!=mPieceArray[firstCornerX][firstCornerY]&&null!=mPieceArray[secondCornerX][secondCornerY]){
			return false;
		}
		if(null==mPieceArray[firstCornerX][firstCornerY]){
			Piece firstCornerPiece = new Piece();
			firstCornerPiece.setArrayX(firstCornerY);
			firstCornerPiece.setArrayY(firstCornerY);
			boolean directConnectFirst = directConnectTest(preOne,firstCornerPiece);
			boolean directConnectSecond = directConnectTest(nextOne,firstCornerPiece);
			if(directConnectFirst&&directConnectSecond)	{
				//��Ӷ��������ߵĹ��죨����ʽ��
				createLinker(preOne,firstCornerPiece,nextOne);
				return true;
			}
		}
		if(null==mPieceArray[secondCornerX][secondCornerY]){
			Piece secondCornerPiece = new Piece();
			secondCornerPiece.setArrayX(secondCornerX);
			secondCornerPiece.setArrayY(secondCornerY);
			boolean directConnectFirst = directConnectTest(preOne,secondCornerPiece);
			boolean directConnectSecond = directConnectTest(nextOne,secondCornerPiece);
			if(directConnectFirst&&directConnectSecond)	{
				createLinker(preOne,secondCornerPiece,nextOne);
				return true;
			}
		}
		return false;
	}
		
	private boolean twoCornerConnectTest(Piece preOne,Piece nextOne){
		List<List<Piece>> piecesList = new ArrayList<List<Piece>>();
		List<Piece> preBlankPieces =createBlankPieceList(preOne);
		List<Piece> nextBlankPieces =createBlankPieceList(nextOne);
		if(null==preBlankPieces||preBlankPieces.size()<1||
			null==nextBlankPieces||nextBlankPieces.size()<1){ //����һ������Χû�пհ׵�ʱ�����޷�����
			return false;
		}
		for(Piece preBlankPiece:preBlankPieces){
			for(Piece nextBlankPiece:nextBlankPieces){
				if(preBlankPiece.getArrayX()==nextBlankPiece.getArrayX()){//λ��ͬ��ʱ���ж��Ƿ��ܹ�ֱ��
					boolean directConnect = directConnectTest(preBlankPiece,nextBlankPiece);
					if(directConnect){
						List<Piece> pieces = new ArrayList<Piece>();
						pieces.add(preOne);
						pieces.add(preBlankPiece);
						pieces.add(nextBlankPiece);
						pieces.add(nextOne);
						piecesList.add(pieces);
					}
				}
				if(preBlankPiece.getArrayY()==nextBlankPiece.getArrayY()){//λ��ͬ��ʱ���ж��Ƿ��ܹ�ֱ��
					boolean directConnect = directConnectTest(preBlankPiece,nextBlankPiece);
					if(directConnect){
						List<Piece> pieces = new ArrayList<Piece>();
						pieces.add(preOne);
						pieces.add(preBlankPiece);
						pieces.add(nextBlankPiece);
						pieces.add(nextOne);
						piecesList.add(pieces);
					}
				}	
			}
		}
		if(piecesList.size()<=0){
			return false;
		}else{
			List<Piece> shortestPieces= calShortestPieces(piecesList);
			createLinker(shortestPieces.get(0),shortestPieces.get(1),shortestPieces.get(2),shortestPieces.get(3));
			return true;
		}
	}
	private List<Piece> calShortestPieces(List<List<Piece>> piecesList){
		int shortestSpan=-1;
		List<Piece> shortestPieces=null;
		for(List<Piece> pieces:piecesList){
			Piece firstPiece =pieces.get(0);
			Piece secondePiece=pieces.get(1);
			Piece thirdPiece=pieces.get(2);
			Piece fourPiece=pieces.get(3);
			int span=countSpan(firstPiece,secondePiece)+countSpan(secondePiece,thirdPiece)+countSpan(thirdPiece,fourPiece);
			if(shortestSpan==-1){
				shortestSpan=span;
				shortestPieces=pieces;
			}else if(shortestSpan>span){
				shortestSpan=span;
				shortestPieces=pieces;
			}
		}
		return shortestPieces;
	}
	
	private int countSpan(Piece firstPiece,Piece secondePiece){
		int span =0;
		if(firstPiece.getArrayX()==secondePiece.getArrayX()){
			span=firstPiece.getArrayY()-secondePiece.getArrayY();
		}
		if(span<0){
			span=-span;
		}
		return span;
	}
	
	private List<Piece> createBlankPieceList(Piece piece){
		int pieceX=piece.getArrayX();
		int pieceY=piece.getArrayY();
		List<Piece> blankPieceList = new ArrayList<Piece>();
		for(int indexY=pieceY-1;indexY>=0;indexY--){
			if(null!=mPieceArray[pieceX][indexY]){
				break;
			}else{
				Piece blankPiece =new Piece();
				blankPiece.setArrayX(pieceX);
				blankPiece.setArrayY(indexY);
				blankPieceList.add(blankPiece);
			}
		}
		for(int indexY=pieceY+1;indexY<arrayYsize+2;indexY++){
			if(null!=mPieceArray[pieceX][indexY]){
				break;
			}else{
				Piece blankPiece =new Piece();
				blankPiece.setArrayX(pieceX);
				blankPiece.setArrayY(indexY);
				blankPieceList.add(blankPiece);
			}
		}
		for(int indexX=pieceX-1;indexX>=0;indexX--){
			if(null!=mPieceArray[indexX][pieceY]){
				break;
			}else{
				Piece blankPiece =new Piece();
				blankPiece.setArrayX(indexX);
				blankPiece.setArrayY(pieceY);
				blankPieceList.add(blankPiece);
			}
		}
		for(int indexX=pieceX+1;indexX<arrayXsize+2;indexX++){
			if(null!=mPieceArray[indexX][pieceY]){
				break;
			}else{
				Piece blankPiece =new Piece();
				blankPiece.setArrayX(indexX);
				blankPiece.setArrayY(pieceY);
				blankPieceList.add(blankPiece);
			}
		}
		return blankPieceList;
	}
	
	private void createLinker(Piece... pieces){
		mLiner.clearPoint();
		for(Piece piece:pieces){
			int xPosition=piece.getArrayX()*pieceWidth+pieceWidth/2;
			int yPosition=piece.getArrayY()*pieceHight+pieceHight/2;			
			mLiner.addPoint(new Point(xPosition,yPosition));
		}	
	}
	
	
	private void animateBig(Piece currentPiece) {
		Rect rect =currentPiece.getLocationRect();
		rect.left=rect.left-pieceWidth/10;		
		rect.top=rect.top-pieceHight/10;
		rect.right=rect.right+pieceWidth/10;
		rect.bottom=rect.bottom+pieceHight/10; 
	}

	private void animateSmall(Piece currentPiece) {
		Rect rect =currentPiece.getLocationRect();
		rect.left=rect.left+pieceWidth/10;		
		rect.top=rect.top+pieceHight/10;
		rect.right=rect.right-pieceWidth/10;
		rect.bottom=rect.bottom-pieceHight/10; 	
	}

	@Override
	public boolean needLink() {
		return mLiner.needLink();
	}

	@Override
	public Liner getLiner() {
		return mLiner;
	}

	@Override
	public boolean handleTouchUp(Piece currentPiece) {
		if(null!=prePiece&&null!=currentPiece&&mLiner.needLink()){
			mPieceArray[prePiece.getArrayX()][prePiece.getArrayY()]=null;
			mPieceArray[currentPiece.getArrayX()][currentPiece.getArrayY()]=null;
			pieceSumSize =pieceSumSize-2;
			prePiece=null;
			mLiner.clearPoint();
			return true;
		}else{
			mLiner.clearPoint();
			return false;
		}
	}

	@Override
	public boolean isGameOver() {
		return pieceSumSize<=0?true:false;
	}
	
	
	
	
}
