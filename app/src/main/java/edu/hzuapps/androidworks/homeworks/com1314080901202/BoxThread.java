package wyf.ytl;
public class BoxThread extends Thread{
	int span = 130;//睡眠的时间
	PushBoxActivity pushBoxActivity;//Activity引用
	int dir;//方向
	int i;
	int j;
	public BoxThread(int dir,PushBoxActivity pushBoxActivity, int i, int j){//构造器
		this.pushBoxActivity = pushBoxActivity;//得到activity的引用
		this.dir = dir;//方向
		this.i = i;
		this.j = j;
	}
	public void run(){//重写的run方法
		if(pushBoxActivity.isSound){//需要播放声音时
			pushBoxActivity.pushBoxSound.start();//播放音乐
		}
		if(dir == 1){//向上 
	        int X = pushBoxActivity.gameView.initX+36*j-15*(i+1);//计算出实际x坐标
	        int Y = pushBoxActivity.gameView.initY+10*j+25*(i+1);//计算出实际y坐标
			for(int c=0; c<2; c++){//分两步走
				X += 7;
				Y -= 12;
				pushBoxActivity.gameView.tx = X;//设置x坐标
				pushBoxActivity.gameView.ty = Y;//设置y坐标
				try {
					Thread.sleep(span);//睡眠
				} catch (InterruptedException e) {
					e.printStackTrace();//打印异常的堆栈信息
				}
			}
			pushBoxActivity.gameView.tempi = -1;//设置当前没有箱子移动
			pushBoxActivity.gameView.tempj = -1;
		}
		else if(dir == 2){//向下
	        int X = pushBoxActivity.gameView.initX+36*j-15*(i-1);//计算出当前箱子的实际坐标
	        int Y = pushBoxActivity.gameView.initY+10*j+25*(i-1);
			for(int c=0; c<2; c++){//分两步走
				X -= 7;
				Y += 12;
				pushBoxActivity.gameView.tx = X;//移动
				pushBoxActivity.gameView.ty = Y;				
				try {
					Thread.sleep(span);//休息
				} catch (InterruptedException e) {
					e.printStackTrace();//打印异常信息
				}
			}
			pushBoxActivity.gameView.tempi = -1;//设置当前没有箱子移动
			pushBoxActivity.gameView.tempj = -1;
		}
		else if(dir == 3){//向左
	        int X = pushBoxActivity.gameView.initX+36*(j+1)-15*i;//计算实际坐标
	        int Y = pushBoxActivity.gameView.initY+10*(j+1)+25*i;
			for(int c=0; c<2; c++){//分两步走，
				X -= 17;
				Y -= 5;//计算移动
				pushBoxActivity.gameView.tx = X;//移动
				pushBoxActivity.gameView.ty = Y;				
				try {
					Thread.sleep(span);//睡眠
				} catch (InterruptedException e) {
					e.printStackTrace();//打印异常信息
				}
			}
			pushBoxActivity.gameView.tempi = -1;
			pushBoxActivity.gameView.tempj = -1; 
		}
		else if(dir == 4){//向右
	        int X = pushBoxActivity.gameView.initX+36*(j-1)-15*i;//计算实际坐标
	        int Y = pushBoxActivity.gameView.initY+10*(j-1)+25*i;
			for(int c=0; c<2; c++){
				X += 17;
				Y += 5; 
				pushBoxActivity.gameView.tx = X;
				pushBoxActivity.gameView.ty = Y;//移动坐标		
				try {
					Thread.sleep(span);//睡眠指定毫秒数
				} catch (InterruptedException e) {
					e.printStackTrace();//打印异常信息
				}
			}
			pushBoxActivity.gameView.tempi = -1;
			pushBoxActivity.gameView.tempj = -1;
		}
		if(isWin()){//当胜利时 
			pushBoxActivity.gameView.status = 1;
			if(pushBoxActivity.isSound){
				pushBoxActivity.winSound.start();//播放声音
			}
		}
	}
	/**
	 * 判断当前是否已经胜利
	 * 只需检查当前界面是否还存在没有变绿的箱子即可
	 */
    public boolean isWin(){
    	for(int i=0; i<pushBoxActivity.map2.length; i++ ){
    		for(int j=0; j<pushBoxActivity.map2[i].length; j++){
    			if(pushBoxActivity.map2[i][j] == 1){//有不是绿色的箱子
    				return false;
    			}
    		}
    	}
    	return true;
    }
}