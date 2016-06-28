package wyf.ytl;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
public class MenuView extends SurfaceView implements SurfaceHolder.Callback{
	PushBoxActivity pushBoxActivity;//主Activity的引用
	MenuViewDrawThread menuViewDrawThread;
	Paint paint;//画笔
	Bitmap start1;//开始游戏图片
	Bitmap sound1;//声音图片
	Bitmap sound2;
	Bitmap help1;//游戏说明图片
	Bitmap exit1;//退出游戏图片
	Bitmap menubackground;//大背景图片
	Bitmap menubackground2;//小背景图片
	int menubackgroudX = 0;//需要移动的背景的坐标
	public MenuView(PushBoxActivity pushBoxActivity) {//构造器
		super(pushBoxActivity);
		this.pushBoxActivity = pushBoxActivity;
		menuViewDrawThread = new MenuViewDrawThread(this, getHolder());
		getHolder().addCallback(this);
		paint = new Paint() ;
		start1 = BitmapFactory.decodeResource(getResources(), R.drawable.start1);//初始化开始
		sound1 = BitmapFactory.decodeResource(getResources(), R.drawable.sound1);//初始化打开声音
		sound2 = BitmapFactory.decodeResource(getResources(), R.drawable.sound2);//初始化关闭声音
		help1 = BitmapFactory.decodeResource(getResources(), R.drawable.help1);//初始化帮助
		exit1 = BitmapFactory.decodeResource(getResources(), R.drawable.exit1);//初始化退出
		menubackground = BitmapFactory.decodeResource(getResources(), R.drawable.menubackground);
		menubackground2 = BitmapFactory.decodeResource(getResources(), R.drawable.menubackground2);
	}
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(menubackground, menubackgroudX, 0, paint);//绘制大背景
		canvas.drawBitmap(menubackground2, 21, 20, paint);//绘制小背景
		canvas.drawBitmap(start1, 60, 60, paint);
		if(pushBoxActivity.isSound){//根据声音的状态绘制声音按钮图片
			canvas.drawBitmap(sound1, 60, 150, paint);
		}
		else{
			canvas.drawBitmap(sound2, 60, 150, paint);
		}
		canvas.drawBitmap(help1, 60, 240, paint);//绘制帮助
		canvas.drawBitmap(exit1, 60, 330, paint);//绘制退出按钮
	}
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getX()>50 && event.getX()< 50+start1.getWidth()
				&& event.getY()>60 && event.getY()<60+start1.getHeight()){//点击了开始游戏菜单
			if(pushBoxActivity.startSound.isPlaying()){
				pushBoxActivity.startSound.stop();
			}			
			pushBoxActivity.myHandler.sendEmptyMessage(2);//向Activity的Handler发送消息
		}
		else if(event.getX()>50 && event.getX()<50+sound1.getWidth()
				&& event.getY()>70+start1.getHeight() && event.getY()<70+start1.getHeight()+sound1.getHeight()){
			//点击了声音菜单
			pushBoxActivity.isSound = !pushBoxActivity.isSound;//将声音开关取反
			if(!pushBoxActivity.isSound){
				if(pushBoxActivity.startSound.isPlaying()){
					pushBoxActivity.startSound.pause();//停止播放声音
				}
				if(pushBoxActivity.backSound.isPlaying()){		
					pushBoxActivity.backSound.pause();//停止播放声音
				}
			}
			else{
				if(!pushBoxActivity.startSound.isPlaying()){
					pushBoxActivity.startSound.start();//开始播放声音
				}
			}
		}
		else if(event.getX()>50 && event.getX()<50+help1.getWidth()
				&& event.getY()>80+start1.getHeight()+sound1.getHeight() && event.getY()<80+start1.getHeight()+help1.getHeight()+help1.getHeight()){
			//点击的是游戏说明菜单
		}
		else if(event.getX()>50 && event.getX()<50+exit1.getWidth()
				&& event.getY()>90+start1.getHeight()+help1.getHeight()+help1.getHeight()
				&& event.getY()<90+start1.getHeight()+help1.getHeight()+help1.getHeight()+exit1.getHeight()){
			//点击的是退出游戏菜单
			System.exit(0);//退出游戏
		}
		return super.onTouchEvent(event);
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	public void surfaceCreated(SurfaceHolder holder) {
		menuViewDrawThread.setFlag(true);
		menuViewDrawThread.start();
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        menuViewDrawThread.setFlag(false);//停止刷帧线程
        while (retry) {
            try {
            	menuViewDrawThread.join();//等待刷帧线程结束
                retry = false;
            } 
            catch (InterruptedException e) {//不断地循环，直到等待的线程结束
            }
        }
	}
}