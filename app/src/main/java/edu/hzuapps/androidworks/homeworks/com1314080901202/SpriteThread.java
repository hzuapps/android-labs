package wyf.ytl;
/**
 * 
 * 该类为精灵的换帧线程
 * 自动检查总共几帧，然后根据方向没隔一定时间换一帧
 *
 */
public class SpriteThread extends Thread{//换帧
	int span = 205;//睡眠的毫秒数
	int dir;//方向
	PushBoxActivity pushBoxActivity;//Activity引用
	boolean typeFlag;//绘制什么类型的图片，true为走路的图，false为推箱子的图
	public SpriteThread(int dir, PushBoxActivity pushBoxActivity,boolean typeFlag){//构造器
		this.dir = dir;
		this.pushBoxActivity = pushBoxActivity;
		this.typeFlag = typeFlag;
	}
	public void run(){ 
		if(typeFlag){//绘制走路的图
			for(int i=0; i<pushBoxActivity.mySprite.manDown.length; i++){//因为每个方向帧个数相同，所以只循环一次
				if(dir == 1){//上
					pushBoxActivity.mySprite.man = pushBoxActivity.mySprite.manUp[i];
				}
				if(dir == 2){//下
					pushBoxActivity.mySprite.man = pushBoxActivity.mySprite.manDown[i];
				} 
				if(dir == 3){//左
					pushBoxActivity.mySprite.man = pushBoxActivity.mySprite.manLeft[i];
				}
				if(dir == 4){//右
					pushBoxActivity.mySprite.man = pushBoxActivity.mySprite.manRight[i];
				}
				try{
					Thread.sleep(span);//睡眠
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}		
		}
		else{//绘制推箱子的图
			for(int i=0; i<pushBoxActivity.mySprite.manPushDown.length; i++){//因为每个方向帧个数相同，所以只循环一次
				if(dir == 1){//上
					pushBoxActivity.mySprite.man = pushBoxActivity.mySprite.manPushUp[i];
				}
				if(dir == 2){//下
					pushBoxActivity.mySprite.man = pushBoxActivity.mySprite.manPushDown[i];
				} 
				if(dir == 3){//左
					pushBoxActivity.mySprite.man = pushBoxActivity.mySprite.manPushLeft[i];
				}
				if(dir == 4){//右
					pushBoxActivity.mySprite.man = pushBoxActivity.mySprite.manPushRight[i];
				}
				try{
					Thread.sleep(span);//睡眠
				}
				catch(Exception e){
					e.printStackTrace();//打印异常堆栈信息
				}
			}			
		}
		pushBoxActivity.kt.keyFlag = true;//恢复键盘监听
	}
}