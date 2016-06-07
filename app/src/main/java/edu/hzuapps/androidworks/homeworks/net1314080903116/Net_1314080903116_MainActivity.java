package edu.hzuapps.androidworks.homeworks.net1314080903116;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import edu.hzuapps.androidworks.homeworks.net1314080903116.R;
import edu.hzuapps.androidworks.homeworks.net1314080903116.runnable.MyThread;

public class Net_1314080903116_MainActivity extends Activity implements SurfaceHolder.Callback, View.OnTouchListener {
    private SurfaceView mSurfaceView;
    private MyThread thread;
    private int w, h;

    private int gameType = 2;
    private int gameSpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        starGameView();
    }

    private void starGameView() {
        mSurfaceView = new SurfaceView(this);
        mSurfaceView.getHolder().addCallback(this);
        mSurfaceView.setOnTouchListener(this);
        setContentView(mSurfaceView);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 创建画布是初始化
        // 得到画板 然后画板和线程绑定
        w = mSurfaceView.getWidth();// 画板宽
        h = mSurfaceView.getHeight();// 画板高
        // 得到游戏区域
        // 留1/5显示时间
        gameSpan = h * 4 / (5 * gameType);
        thread = new MyThread(this, holder, w, h, gameType);
        // mSurfaceView.
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.setStart(false);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // 点击区域让精灵跳起来
        switch (thread.getGameStatu())
        {
            case MyThread.RUNNING:
                confirmRole(event);
                break;
            case MyThread.STANDOFF:
                resStart(event);
                break;

            default:
                break;
        }

        return true;
    }

    private void resStart(MotionEvent event) {
        int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                // 第一根手指按下
                float y = event.getY();
                // 点击了重来
                if (y >= h * 0.5 / 5 && y <= h * 1 / 5){
                    gameType=2;
                    starGameView();
                }else if(y >= h * 1  / 5 && y <= h * 1.5 / 5){
                    gameType=3;
                    starGameView();
                }

                break;

            default:
                break;
        }

    }

    private void confirmRole(MotionEvent event) {
        int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                // 第一根手指按下
                float y = event.getY();
                if (thread.isStart())
                    roleJump(y);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                // 其他手指陆续按下
                float y2 = event.getY(event.getPointerCount() - 1);
                if (thread.isStart())
                    roleJump(y2);
                break;
            default:
                break;
        }
    }

    /**
     * 获取对应区域让对应区域人物跳起来
     *
     * @param y
     */
    private void roleJump(float y) {
        // 找到区域
        try {
            for (int i = 0; i < gameType; i++) {
                // 判断在那一个游戏区域
                int lineD = h / 10 + (i + 1) * gameSpan;
                int lineU = h / 10 + (i) * gameSpan;
                if (y >= lineU && y < lineD && !thread.roles[i].isJump()) {
                    thread.roles[i].setSpeedY(-h / 48);
                    // 设置跳起状态
                    thread.roles[i].setJump(true);
                    break;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}
