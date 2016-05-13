package com.hewq.linkgame2;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hewq.linkgame2.service.LinkService;
import com.hewq.linkgame2.service.imp.LinkServiceImp;
import com.hewq.linkgame2.view.GameView;
import com.hewq.linkgame2.view.Piece;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Com1314080901111Activity extends Activity {
    //运行状态
    private static final int STATE_OFF=0;//关闭
    private static final int STATE_ON=1;//运行中
    private static final int STATE_PAUSE=2;//暂停中

    private static final int INITSIZE_X=6;
    private static final int INITSIZE_Y=4;
    private static final int INITREMAIN_TIME=60;
    private static final int ALARM_TIME=10;

    //警告声音每秒播放一次，频率太高，强制每隔alarmSpan秒播放一次
    private static final int ALARMSPAN=2;

    private GameView myGameView;
    private TextView remainTimeTextView;
    private Button startButton;
    private ImageButton refreshButton;

    private int  arrayXsize=INITSIZE_X;
    private int arrayYsize=INITSIZE_Y;
    private int remainTime=INITREMAIN_TIME;

    private int currentState=STATE_OFF;//状态
    private Handler handler;
    private int timeChangeCode=20131120;
    private Timer timer;
    private TimerTask task ;
    private LinkService linkService;
    private Piece currentTouchPiece=null;

    //游戏的音效
    private SoundPool mSoundPool;
    //选中的音效
    private final int soundChoose=1;
    //消失的音效
    private final int soundDisappear=2;
    //时间警告
    private int soundAlarm=3;
    //获胜音效
    private final int soundLose=4;
    //失败音效
    private final int soundWin=5;

    private int soundPriority=1;

    private HashMap<Integer, Integer> soundMap=
            new HashMap<Integer, Integer>();

    //采用SoundPool播放背景音乐加载太慢，采用mediaplay播放背景音乐
    private MediaPlayer bgMediaPlayer;

    private boolean alarmPlay=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myGameView =(GameView)findViewById(R.id.gameview);
        linkService =new LinkServiceImp();
        myGameView.setLinkService(linkService);
        remainTimeTextView=(TextView)findViewById(R.id.remainTime_textview);
        startButton =(Button)findViewById(R.id.start_button);
        remainTimeTextView.setText(" "+remainTime);
        refreshButton=(ImageButton)findViewById(R.id.refresh_button);

        //游戏失败时的弹出框
        final Builder stupidBuilder = new AlertDialog.Builder(this);
        stupidBuilder.setIcon(R.drawable.stupid);
        stupidBuilder.setTitle(R.string.stupidtitle);
        stupidBuilder.setMessage(R.string.stupidmessage);
        stupidBuilder.setPositiveButton(R.string.stupidaccept,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        initGameLogic();
                    }
                });
        stupidBuilder.setNegativeButton(R.string.stupidrefuse,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        com.hewq.linkgame2.Com1314080901111Activity.this.finish();
                    }
                }) ;

        //游戏成功后的弹出框
        final Builder successBuilder = new AlertDialog.Builder(this);
        successBuilder.setIcon(R.drawable.success);
        successBuilder.setTitle(R.string.successtitle);
        successBuilder.setMessage(R.string.successmessage);
        successBuilder.setPositiveButton(R.string.successaccept,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        arrayYsize++;
                        initGameLogic();
                    }
                });

        successBuilder.setNegativeButton(R.string.successrefuse,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Com1314080901111Activity.this.finish();
                    }
                }) ;
        bgMediaPlayer=MediaPlayer.create(this,R.raw.bg);
        //循环设置必须放在prepare的前面，否则不起作用，害我查了半天的资料啊！
        bgMediaPlayer.setLooping(true);
        try {
            bgMediaPlayer.prepare();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        //游戏音效的初始
        mSoundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        soundMap.put(soundChoose, mSoundPool.load(this, R.raw.choose, 1));
        soundMap.put(soundDisappear, mSoundPool.load(this, R.raw.disappear1, 1));
        soundMap.put(soundAlarm, mSoundPool.load(this, R.raw.alarm, 1));
        soundMap.put(soundLose, mSoundPool.load(this, R.raw.lose, 1));
        soundMap.put(soundWin, mSoundPool.load(this, R.raw.win, 1));

        //每秒更新一次游戏剩余时间，监测游戏是否已经到时间了
        handler= new Handler(){
            public void handleMessage(Message msg) {
                if(msg.what==timeChangeCode){
                    remainTime--;
                    remainTimeTextView.setText(" "+remainTime);
                    if(remainTime<=ALARM_TIME){
                        if(!alarmPlay){
                            alarmPlay=true;
                            bgMediaPlayer.pause();
                        }
                        if((remainTime%ALARMSPAN)==0){
                            mSoundPool.play(soundMap.get(soundAlarm), 1, 1, soundPriority, 0, 1);
                        }
                    }
                    if(remainTime==0){//游戏到时间结束处理逻辑
                        mSoundPool.pause(soundMap.get(soundAlarm));
                        stopGameLogic();
                        mSoundPool.play(soundMap.get(soundLose), 1, 1, soundPriority, 0, 1);
                        stupidBuilder.create().show();

                    }
                    if(linkService.isGameOver()){//游戏顺利结束逻辑
                        //弹出回话框
                        mSoundPool.pause(soundMap.get(soundAlarm));
                        stopGameLogic();
                        mSoundPool.play(soundMap.get(soundWin), 1, 1, soundPriority, 0, 1);
                        successBuilder.create().show();
                    }
                }
            }
        };

        //游戏开始按钮
        //游戏的状态：停止》开始》暂停》开始》停止
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentState==STATE_OFF){
                    initGameLogic();
                }else if(currentState==STATE_ON){
                    pauseGameLogic();
                }else if(currentState==STATE_PAUSE){
                    continueGameLogic();
                }
            }
        });

        //刷新按钮
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLogic();
            }
        });

        myGameView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(currentState==STATE_ON){//只有当游戏处于运行状态时才触发
                    if(event.getAction()==MotionEvent.ACTION_DOWN){
                        currentTouchPiece = processTouchDown(event);
                    }
                    if(event.getAction()==MotionEvent.ACTION_UP){
                        if(null!=currentTouchPiece){//只有点击了别的点，才需要做进一步的处理
                            processTouchUp (currentTouchPiece);
                        }
                    }
                }
                return true;
            }

        });
    }
    /*按下的处理逻辑
     * 处理逻辑如下：
     * 点击后，如果是可点击部分，图片变大
     * 调用连接处理逻辑，判断是否进行连线，并构建连线
     */
    private Piece processTouchDown(MotionEvent event){
        float touchX =event.getX();
        float touchY =event.getY();
        Piece touchPiece=linkService.getTouchPiece(touchX, touchY);
        if(null!=touchPiece){
            linkService.handleTouchDown(touchPiece);

            myGameView.postInvalidate();
        }
        return touchPiece;
    }


    private void processTouchUp (Piece touchPiece){
        boolean dispare= linkService.handleTouchUp(touchPiece);
        if(dispare){
            mSoundPool.play(soundMap.get(soundDisappear), 1, 1, soundPriority, 0, 1);
        }else{
            mSoundPool.play(soundMap.get(soundChoose), 1, 1, soundPriority, 0, 1);
        }
        myGameView.postInvalidate();
    }

    //开始，继续游戏逻辑
    private void stratTime(){
        if(timer==null){
            task = new TimerTask() {
                public void run() {
                    Message msg=new Message();
                    msg.what=timeChangeCode;
                    handler.sendMessage(msg);
                }
            };
            timer = new Timer(true);
            timer.schedule(task, 1000, 1000);
        }
    }
    //暂停游戏逻辑
    private void stopTime(){
        if(timer!=null){
            timer.cancel();
            timer=null;
            task=null;
        }
    }


    //退出时释放资源
    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        bgMediaPlayer.stop();
        bgMediaPlayer.release();
        bgMediaPlayer=null;

        mSoundPool.release();
        mSoundPool=null;
    }

    //游戏开始的初始化逻辑
    //初始化游戏时间，启动音乐
    //初始化游戏界面
    //改变游戏状态及按钮显示
    //开始计时
    private void initGameLogic(){
        bgMediaPlayer.start();
        remainTime=INITREMAIN_TIME;
        int result = myGameView.startGame(arrayXsize, arrayYsize);
        startButton.setText(R.string.pausegame);
        stratTime();
        currentState=STATE_ON;
    }
    //暂停游戏的逻辑
    //停止时间，改变状态
    private void pauseGameLogic(){
        startButton.setText(R.string.continuegame);
        stopTime();
        currentState=STATE_PAUSE;
    }
    //继续游戏的逻辑
    //启动时间，改变状态
    private void continueGameLogic(){
        startButton.setText(R.string.pausegame);
        stratTime();
        currentState=STATE_ON;
    }
    //结束游戏的逻辑
    //改变游戏状态
    private void stopGameLogic(){
        startButton.setText(R.string.startgame);
        stopTime();
        currentState=STATE_OFF;
        bgMediaPlayer.pause();
    }
    //刷新逻辑
    private void refreshLogic(){
        stopGameLogic();
        initGameLogic();
    }
}
