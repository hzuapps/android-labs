package wyf.ytl;
/**
 * 
 * 精灵无极移动类
 * 给定初始位置和结束位置所在的行列
 * 自动计算成实际坐标，然后分两步移动
 * 滚屏的方法也在该类中实现，
 * 当距边界较近时自动滚屏
 *
 */
public class SpriteMoveThread extends Thread {//精灵无极移动
	PushBoxActivity pushBoxActivity; //Activity的引用
	int dir;//方向
	boolean flag = true;
	int span = 350;//睡睡眠的毫秒数
	public SpriteMoveThread(int dir, PushBoxActivity pushBoxActivity,boolean typeFlag){//构造器
		super.setName("SpriteMoveThread");
		this.dir = dir;
		this.pushBoxActivity = pushBoxActivity;
	}
	public void run(){
		pushBoxActivity.mySprite.X = pushBoxActivity.gameView.initX
									+36*pushBoxActivity.mySprite.j
									-15*pushBoxActivity.mySprite.i + 2;
		pushBoxActivity.mySprite.Y = pushBoxActivity.gameView.initY
									+10*pushBoxActivity.mySprite.j
									+25*pushBoxActivity.mySprite.i - 25;//调整坐标
		if(dir == 1){//向上
			if(!(pushBoxActivity.mySprite.i<=0) 
			&& pushBoxActivity.map2[pushBoxActivity.mySprite.i-1][pushBoxActivity.mySprite.j] == 0){//可以移动的时
				//移动精灵
				pushBoxActivity.mySprite.isRun = true;
				for(int i=0; i<2; i++){
					pushBoxActivity.mySprite.X += 7;
					pushBoxActivity.mySprite.Y -= 12;
					try {
						Thread.sleep(span);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				pushBoxActivity.mySprite.i -= 1;
				pushBoxActivity.mySprite.isRun = false;
			}
		}
		else if(dir == 2){//向下
			if(!(pushBoxActivity.mySprite.i >= pushBoxActivity.map2.length-1) 
			&& (pushBoxActivity.map2[pushBoxActivity.mySprite.i+1][pushBoxActivity.mySprite.j] == 0)){
				pushBoxActivity.mySprite.isRun = true;
				pushBoxActivity.mySprite.i += 1;
				for(int i=0; i<2; i++){
					pushBoxActivity.mySprite.X -= 7;
					pushBoxActivity.mySprite.Y += 12;
					try {
						Thread.sleep(span);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				pushBoxActivity.mySprite.isRun = false;
			}
		}
		else if(dir == 3){//向左
			if(!(pushBoxActivity.mySprite.j <= 0) && pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j-1] == 0){//可以移动时
				
				//移动精灵
				pushBoxActivity.mySprite.isRun = true;
				for(int i=0; i<2; i++){
					pushBoxActivity.mySprite.X -= 17;
					pushBoxActivity.mySprite.Y -= 5;
					try {
						Thread.sleep(span);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				pushBoxActivity.mySprite.j -= 1;
				pushBoxActivity.mySprite.isRun = false;
			}	
		}
		else if(dir == 4){//向右
			if(!(pushBoxActivity.mySprite.j+1 > pushBoxActivity.map2.length-1) && pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j+1] == 0){
				pushBoxActivity.mySprite.isRun = true;
				pushBoxActivity.mySprite.j += 1;
				for(int i=0; i<2; i++){
					pushBoxActivity.mySprite.X += 17;
					pushBoxActivity.mySprite.Y += 5;
					try {
						Thread.sleep(span);//睡眠 
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				pushBoxActivity.mySprite.isRun = false;
			}
		}
		//滚屏
        int newX = pushBoxActivity.gameView.initX+36*pushBoxActivity.mySprite.j-15*pushBoxActivity.mySprite.i;
        int newY = pushBoxActivity.gameView.initY+10*pushBoxActivity.mySprite.j+25*pushBoxActivity.mySprite.i;
        this.scroll(newX, newY);//调用滚屏方法，需要时滚，不需要时不滚    
	}
	
	public void scroll(int x, int y){//滚屏方法
        if(x < 100){//距左边距100时
        	pushBoxActivity.gameView.initX += 60;
        }
        if(y < 100){//距上边距100时
        	pushBoxActivity.gameView.initY += 60;
        }
        if(x > 220){//距右边距320-200时
        	pushBoxActivity.gameView.initX -= 60;
        }
        if(y > 380){//距下边距480-380时
        	pushBoxActivity.gameView.initY -= 60;
        }
	}
}