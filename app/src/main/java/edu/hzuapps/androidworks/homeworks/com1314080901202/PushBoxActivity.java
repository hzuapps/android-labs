package wyf.ytl;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class PushBoxActivity extends Activity{
	WelcomeView welcomeView = null;//欢迎界面
	WelcomeViewGoThread welcomeViewGoThread = null;//欢迎界面中的移动线程
	MenuView menuView = null;
	MenuViewGoThread menuViewGoThread = null;//菜单界面中的移动线程
	GameView gameView = null;
	
	boolean isSound = true;//是否播放声音
	MediaPlayer pushBoxSound;//推箱子声音
	MediaPlayer backSound;//背景音乐
	MediaPlayer winSound;//胜利的音乐
	MediaPlayer startSound;//开始和菜单时的音乐
	
	int map1[][];
	int map2[][];//当前游戏的地图
	int selectMap = 0;//选中的地图
	
	MySprite mySprite;//精灵
	
	KeyThread kt;//键盘监听线程
	int action = 0;//键盘的状态,二进制表示 从左往右表示上下左右
	
	Handler myHandler = new Handler(){//用来更新UI线程中的控件
        public void handleMessage(Message msg) {
        	if(msg.what == 1){//收到WelcomeViewGoThread/Welcome发来的消息
        		welcomeViewGoThread.setFlag(false);
        		if(welcomeView != null){
        			welcomeView = null;  
        		}
        		initAndToMenuView();
        	}
        	else if(msg.what == 2){//收到MenuView发来的消息
        		if(menuView != null){
        			menuView = null;  
        		}
        		initAndToGameView();
        	}   
        	else if(msg.what == 3){
        		if(gameView != null){
        			gameView = null;  
        		}
        		initAndToMenuView();
        	}   
        	else if(msg.what == 4){//收到GameView来的消息，进入下一关
    			if(selectMap+1<MapList.map1.length){
    				selectMap = selectMap+1;
    				initAndToGameView();
    			}
    			else {
    				selectMap = 0;
    				initAndToGameView();
    			}
        	}
        }
	}; 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
        pushBoxSound = MediaPlayer.create(this, R.raw.sound2);//推箱子的声音
        winSound = MediaPlayer.create(this, R.raw.winsound);//胜利的声音
        backSound  = MediaPlayer.create(this, R.raw.sound1);//背景音乐
        backSound.setLooping(true); //设置循环
        startSound = MediaPlayer.create(this, R.raw.sound3);
        startSound.setLooping(true);
		initAndToWelcomeView();
    }
    
    public void initAndToWelcomeView(){
    	welcomeView = new WelcomeView(this);
        if(isSound){
        	startSound.start();
        }
    	this.setContentView(welcomeView);
    	welcomeViewGoThread = new WelcomeViewGoThread(this);
    	welcomeViewGoThread.start();
    }
    
    public void initAndToMenuView(){
    	menuView = new MenuView(this);
    	this.setContentView(menuView);
    	menuViewGoThread = new MenuViewGoThread(this);
    	menuViewGoThread.start();
    }
    
    public void initAndToGameView(){
		map1 = new int[MapList.map1[selectMap].length][MapList.map1[selectMap][0].length];
		for(int i=0; i<MapList.map1[selectMap].length; i++){
			for(int j=0; j<MapList.map1[selectMap][i].length; j++){
				map1[i][j] = MapList.map1[selectMap][i][j];//初始化第一层
			}
		}
		map2 = new int[MapList.map2[selectMap].length][MapList.map2[selectMap][0].length];
		for(int i=0; i<MapList.map2[selectMap].length; i++){
			for(int j=0; j<MapList.map2[selectMap][i].length; j++){
				map2[i][j] = MapList.map2[selectMap][i][j];//初始化第二层
			}
		}    	
    	gameView = new GameView(this);
    	mySprite = new MySprite(this);
        kt = new KeyThread(this);//添加键盘监听
        kt.start();
		if(isSound){
			backSound.start();//播放声音
		}
    	this.setContentView(gameView);
    }
    
	public boolean onKeyUp(int keyCode, KeyEvent event) {//键盘抬起
    	if(keyCode == 19){//上
    		action = action & 0x37;
    	}
    	if(keyCode == 20){//下
    		action = action & 0x3B;
    	}    	
    	if(keyCode == 21){//左
    		action = action & 0x3D;
    	}    	
    	if(keyCode == 22){//右
    		action = action & 0x3E;
    	}
		return false;
	}
    
    public boolean onKeyDown(int keyCode, KeyEvent event){//键盘按下监听
    	if(keyCode == 19){//上
    		action = action | 0x08;
    	}
    	if(keyCode == 20){//下
    		action = action | 0x04;
    	}    	
    	if(keyCode == 21){//左
    		action = action | 0x02;
    	}    	
    	if(keyCode == 22){//右
    		action = action | 0x01;
    	}
		return false;
    }	
}