package wyf.ytl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	PushBoxActivity pushBoxActivity;
	GameViewDrawThread gameViewDrawThread;
	int initX = 70;//绘制时第一个的坐标,即开始绘制的位置
	int initY = 50;
	Paint paint;
	Bitmap greenBox;//绿色的箱子 
	Bitmap wall;//墙
	Bitmap box;//箱子
	Bitmap exit2;//退出按钮图片
	Bitmap goon;//恭喜过关的图片
	int tx = -1;
	int ty = -1;//当前移动箱子的坐标,-1表示没有移动的
	int tempi = 0;//当前移动箱子的i
	int tempj = 0;//当前移动箱子的j
	int status = 0 ;//0正常游戏中,1胜利
	public GameView(PushBoxActivity pushBoxActivity) {//构造器
		super(pushBoxActivity);
		this.pushBoxActivity = pushBoxActivity;
		gameViewDrawThread = new GameViewDrawThread(this,getHolder());
		getHolder().addCallback(this);
		initBitmap();
	}
	public void initBitmap(){ 
		paint = new Paint();
		greenBox = BitmapFactory.decodeResource(getResources(), R.drawable.greenbox);//绿色的箱子
		wall = BitmapFactory.decodeResource(getResources(), R.drawable.wall);//墙
		box = BitmapFactory.decodeResource(getResources(), R.drawable.box);//箱子
		exit2 = BitmapFactory.decodeResource(getResources(), R.drawable.exit2);//墙
		goon = BitmapFactory.decodeResource(getResources(), R.drawable.goon);
	}
	protected void onDraw(Canvas canvas) {
		if(status == 0){//正常游戏中
			paint.setAntiAlias(true);//抗锯齿
			canvas.drawColor(Color.BLACK);//绘制黑背景
			//绘制第一层,即地板层
			for(int i=0; i<pushBoxActivity.map1.length; i++){
				for(int j=0; j<pushBoxActivity.map1[i].length; j++){
	                //根据索引值进行坐标转换
	                int X = initX+36*j-15*i;
	                int Y = initY+10*j+25*i;
	                if(pushBoxActivity.map1[i][j] == 0){//白色空地
	                	paint.setColor(Color.argb(255, 220, 220, 220));
	                	this.myDrawRect(canvas, X, Y);
	                }
	                else if(pushBoxActivity.map1[i][j] == 1){//灰色空地
	                	paint.setColor(Color.argb(255, 170, 170, 170));
	                	this.myDrawRect(canvas, X, Y);              	
	                }
	                else if(pushBoxActivity.map1[i][j] == 2){//目的地1
	                	paint.setColor(Color.argb(255, 127, 255, 130));
	                	this.myDrawRect(canvas, X, Y);
	                }
	                else if(pushBoxActivity.map1[i][j] == 3){//目的地2
	                	paint.setColor(Color.argb(255, 60, 255, 120));
	                	this.myDrawRect(canvas, X, Y);
	                }
				}
			}
			//开始绘制第二层,及建筑所在层
			for(int i=0; i<pushBoxActivity.map2.length; i++){
				for(int j=0; j<pushBoxActivity.map2[i].length; j++){
	                //根据索引值进行坐标转换
	                int X = initX+36*j-15*i;
	                int Y = initY+10*j+25*i;			
	                if(pushBoxActivity.map2[i][j] == 1){//第二层上有箱子处
	                	
	                	if(tempi == i && tempj == j){//是当前移动的箱子
	                		canvas.drawBitmap(box, tx-1, ty-27, paint);
	                	}
	                	else{//不是移动的箱子
	                		canvas.drawBitmap(box, X-1, Y-27, paint);
	                	}
	                }
	                else if(pushBoxActivity.map2[i][j] == 2){//墙
	                	canvas.drawBitmap(wall, X, Y-25, paint);
	                }
	                else if(pushBoxActivity.map2[i][j] == 3){//绿色的箱子
	                	if(tempi == i && tempj == j){//是当前移动的箱子
	                		canvas.drawBitmap(greenBox, tx-1 ,ty-27, paint);
	                	}
	                	else{//不是移动的箱子
	                		canvas.drawBitmap(greenBox, X-1, Y-27, paint);
	                	}
	                }
	                //绘制精灵
	                if(i == pushBoxActivity.mySprite.i && j == pushBoxActivity.mySprite.j){
	                	pushBoxActivity.mySprite.drawMySelf(canvas, paint);
	                }
				} 
			}		
//			canvas.drawBitmap(exit2, 0, 440, paint); //绘制退出按钮
		}
		else if(status == 1){//恭喜过关
			paint.setAntiAlias(true);//抗锯齿
			canvas.drawColor(Color.BLACK);//绘制黑背景
			canvas.drawBitmap(goon, -3, 70, new Paint());//绘制恭喜图片
		}
	}	
	public void myDrawRect(Canvas canvas, int x ,int y){//绘制多边形
    	Path path = new Path();
    	path.moveTo(x+14, y);
    	path.lineTo(x+53, y+10);
    	path.lineTo(x+37, y+37);
    	path.lineTo(x-2, y+26);
    	path.lineTo(x+14, y);
    	canvas.drawPath(path, paint);
	}
	public boolean onTouchEvent(MotionEvent event) {
		if(status == 0){
			if(event.getX()>0 && event.getX()<exit2.getWidth()
					&& event.getY()>440 && event.getY()<exit2.getHeight()+440){//点击了退出游戏按钮
				pushBoxActivity.myHandler.sendEmptyMessage(3);//向Activity发生消息
			}			
		}
		else if(status == 1){
			pushBoxActivity.myHandler.sendEmptyMessage(4);//向Activity发生消息
		}

		return super.onTouchEvent(event);
	}	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {		
	}
	public void surfaceCreated(SurfaceHolder holder) {
		gameViewDrawThread.setFlag(true);
		gameViewDrawThread.start();
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameViewDrawThread.setFlag(false);
        while (retry) {
            try {
            	gameViewDrawThread.join();
                retry = false;
            } 
            catch (InterruptedException e) {//不断地循环，直到刷帧线程结束
            }
        }		
	}
}