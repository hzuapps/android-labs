package wyf.ytl;
/**
 * 
 * 该类为键盘监听线程类
 * 定时检测当前键盘的状态
 * 然后根据状态调用相应的处理
 * 
 */
public class KeyThread extends Thread{
	int sleepSpan = 150;
	PushBoxActivity pushBoxActivity;//Activity的引用
	boolean flag = true;//循环标志
	boolean keyFlag = true;//是否监听标志
	int action;//键盘状态码
	
	private boolean KEY_UP = false;//向上键是否被按下
	private boolean KEY_DOWN = false;//向下键是否被按下
	private boolean KEY_LEFT = false;//向左的键被按下
	private boolean KEY_RIGHT = false;//向右的键被按下
	
	public KeyThread(PushBoxActivity pushBoxActivity){//构造器
		this.pushBoxActivity = pushBoxActivity;
	}
	@Override
	public void run(){//重写的方法
		while(flag){
			if(keyFlag){//是否需要键盘监听	
				boolean typeFlag = true;//什么类型。推箱子或者走路
				action = pushBoxActivity.action;//得到当前键盘的状态码
				if((action & 0x08) != 0){//上
					KEY_UP = true;
				}
				else{
					KEY_UP = false;
				}
				if((action & 0x04) != 0){//下
					KEY_DOWN = true;
				}
				else{
					KEY_DOWN = false;
				}
				if((action & 0x02) != 0){//左
					KEY_LEFT = true;
				}
				else{
					KEY_LEFT = false;
				}
				if((action & 0x01) != 0){//右
					KEY_RIGHT = true;
				}
				else{
					KEY_RIGHT = false;
				}
				
				if(KEY_UP == true){//向上键被按下
					this.keyFlag = false;
		   			if(!(pushBoxActivity.mySprite.i-1 <=0)){//没到最上方时
		     			if(pushBoxActivity.map2[pushBoxActivity.mySprite.i-1][pushBoxActivity.mySprite.j] == 1 || pushBoxActivity.map2[pushBoxActivity.mySprite.i-1][pushBoxActivity.mySprite.j] == 3){//当上侧有箱子时
		    				if(pushBoxActivity.map2[pushBoxActivity.mySprite.i-2][pushBoxActivity.mySprite.j] == 0){//检测箱子的上边是否为空地
		    					if(pushBoxActivity.map1[pushBoxActivity.mySprite.i-2][pushBoxActivity.mySprite.j] == 2 || pushBoxActivity.map1[pushBoxActivity.mySprite.i-2][pushBoxActivity.mySprite.j] == 3){//是否为目的地
		    						pushBoxActivity.map2[pushBoxActivity.mySprite.i-2][pushBoxActivity.mySprite.j] = 3;//绿色的箱子
		    					}
		    					else{
		    						pushBoxActivity.map2[pushBoxActivity.mySprite.i-2][pushBoxActivity.mySprite.j] = 1;
		    					}
		    					pushBoxActivity.map2[pushBoxActivity.mySprite.i-1][pushBoxActivity.mySprite.j] = 0;
		    					pushBoxActivity.gameView.tempi = pushBoxActivity.mySprite.i-2;//移动箱子
		    					pushBoxActivity.gameView.tempj = pushBoxActivity.mySprite.j;
		    					BoxThread bt = new BoxThread(1,pushBoxActivity,pushBoxActivity.gameView.tempi,pushBoxActivity.gameView.tempj);//无极运动箱子
		    					bt.start();//启动箱子移动的线程
		    					typeFlag = false;
		    				}
		    			}
	    			}	
	    			SpriteMoveThread smt = new SpriteMoveThread(1,pushBoxActivity, typeFlag);//精灵无极移动
	    			smt.start();
	    			
	    			SpriteThread st = new SpriteThread(1, pushBoxActivity, typeFlag);//精灵换帧
	    			st.start();
				}
				if(KEY_DOWN == true){//向下键被按下
					this.keyFlag = false;
	    			if(!((pushBoxActivity.mySprite.i + 1) >= (pushBoxActivity.map2.length-1))){
		     			if(pushBoxActivity.map2[pushBoxActivity.mySprite.i+1][pushBoxActivity.mySprite.j] == 1 || pushBoxActivity.map2[pushBoxActivity.mySprite.i+1][pushBoxActivity.mySprite.j] == 3){//当下侧有箱子时
		    				if(pushBoxActivity.map2[pushBoxActivity.mySprite.i+2][pushBoxActivity.mySprite.j] == 0){//检测箱子的下边是否为空地
		    					if(pushBoxActivity.map1[pushBoxActivity.mySprite.i+2][pushBoxActivity.mySprite.j] == 2 || pushBoxActivity.map1[pushBoxActivity.mySprite.i+2][pushBoxActivity.mySprite.j] == 3){//是否为目的地
		    						pushBoxActivity.map2[pushBoxActivity.mySprite.i+2][pushBoxActivity.mySprite.j] = 3;
		    					}
		    					else{
		    						pushBoxActivity.map2[pushBoxActivity.mySprite.i+2][pushBoxActivity.mySprite.j] = 1;
		    					}
		    					pushBoxActivity.map2[pushBoxActivity.mySprite.i+1][pushBoxActivity.mySprite.j] = 0;
		    					
		    					pushBoxActivity.gameView.tempi = pushBoxActivity.mySprite.i+2;
		    					pushBoxActivity.gameView.tempj = pushBoxActivity.mySprite.j;
		    					BoxThread bt = new BoxThread(2,pushBoxActivity,pushBoxActivity.gameView.tempi,pushBoxActivity.gameView.tempj);//无极运动箱子
		    					bt.start();	    					
		    					typeFlag = false;
		    				}
		    			}   				
	    			}
	    			SpriteMoveThread smt = new SpriteMoveThread(2,pushBoxActivity, typeFlag);//精灵无极移动
	    			smt.start(); 
	    			SpriteThread st = new SpriteThread(2, pushBoxActivity,typeFlag);//精灵换帧
	    			st.start();					
				}
				if(KEY_LEFT == true){//向左键被按下
					this.keyFlag = false;//去掉键盘监听 
					if(!(pushBoxActivity.mySprite.j-1 <= 0)){
		     			if(pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j-1] == 1 || pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j-1] == 3){//当左侧有箱子时
		    				if(pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j-2] == 0){//检测箱子的左边是否为空地
		    					if(pushBoxActivity.map1[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j-2] == 2 || pushBoxActivity.map1[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j-2] == 3){//是否为目的地
		    						pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j-2] = 3;//绿箱子
		    					}
		    					else{
		    						pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j-2] = 1;
		    					}
		    					pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j-1] = 0;
		    					pushBoxActivity.gameView.tempi = pushBoxActivity.mySprite.i;
		    					pushBoxActivity.gameView.tempj = pushBoxActivity.mySprite.j-2;
		    					BoxThread bt = new BoxThread(3,pushBoxActivity,pushBoxActivity.gameView.tempi,pushBoxActivity.gameView.tempj);//无极运动箱子
		    					bt.start();		    					
		    					typeFlag = false;
		    				}
		     			}
					}
	     			SpriteMoveThread smt = new SpriteMoveThread(3,pushBoxActivity, typeFlag);//精灵无极移动
	     			smt.start(); 			
	    			SpriteThread st = new SpriteThread(3, pushBoxActivity,typeFlag);//精灵换帧
	    			st.start();
				}
				if(KEY_RIGHT == true){//向右键被按下
					this.keyFlag = false;
	    			if(!(pushBoxActivity.mySprite.j+1 >= pushBoxActivity.map2[pushBoxActivity.mySprite.i].length-1)){
		    			if(pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j+1] == 1 || pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j+1] == 3){//当左侧有箱子时
		    				if(pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j+2] == 0){//检测箱子的左边是否为空地
		    					if(pushBoxActivity.map1[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j+2] == 2 || pushBoxActivity.map1[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j+2] == 3){//是否为目的地
		    						pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j+2] = 3;
		    					}	    					
		    					else{
		    						pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j+2] = 1;
		    					}
		    					pushBoxActivity.map2[pushBoxActivity.mySprite.i][pushBoxActivity.mySprite.j+1] = 0;
		    					pushBoxActivity.gameView.tempi = pushBoxActivity.mySprite.i;//移动箱子
		    					pushBoxActivity.gameView.tempj = pushBoxActivity.mySprite.j+2;
		    					BoxThread bt = new BoxThread(4,pushBoxActivity,pushBoxActivity.gameView.tempi,pushBoxActivity.gameView.tempj);//无极运动箱子
		    					bt.start();			    					
		    					typeFlag = false;
		    				}
		    			}    				
	    			}
	    			SpriteMoveThread smt = new SpriteMoveThread(4,pushBoxActivity, typeFlag);//精灵无极移动
	    			smt.start();    			
	    			SpriteThread st = new SpriteThread(4, pushBoxActivity,typeFlag);//精灵换帧
	    			st.start();
				}	
			}
			try{
				Thread.sleep(sleepSpan);//睡觉指定毫秒数
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}