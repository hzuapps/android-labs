package wyf.ytl;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MenuViewDrawThread extends Thread{
	private int sleepSpan = 200;//睡眠的毫秒数
	private boolean flag = true;//循环标记位
	MenuView menuView;//菜单界面的引用
	SurfaceHolder surfaceHolder = null;
	public MenuViewDrawThread(MenuView menuView,SurfaceHolder surfaceHolder){//构造器
		this.menuView = menuView;
		this.surfaceHolder = surfaceHolder;
	}
	public void run(){
		Canvas c;//画布
		while(flag){
			c = null;
			try {
				// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
			    c = surfaceHolder.lockCanvas(null);
			    synchronized (this.surfaceHolder) {
			    	try{
			    		menuView.onDraw(c);
			    	}
			    	catch(Exception e){}
			    }
			} finally {
			    if (c != null) {
			    	//更新屏幕显示内容
			        surfaceHolder.unlockCanvasAndPost(c);
			    }
			}
			try{
				Thread.sleep(sleepSpan);//睡眠sleepSpan毫秒
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
    public void setFlag(boolean flag) {//设置循环标记
    	this.flag = flag;
    }	
}
