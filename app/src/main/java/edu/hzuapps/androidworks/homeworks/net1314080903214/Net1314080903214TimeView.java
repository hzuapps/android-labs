package edu.hzuapps.androidworks.homeworks.net1314080903214;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;


public class Net1314080903214TimeView extends LinearLayout{

    private TextView tvTime;

    // 被代码调用的
    public Net1314080903214TimeView(Context context) {
        super(context);
    }
    // 被xml初始化器使用的
    public Net1314080903214TimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    // 初始化时，还可以同时指定它的style
    public Net1314080903214TimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    // 初始化完成之后，执行
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        tvTime = (TextView) findViewById(R.id.tvTime);
//        tvTime.setText("Hello");

        timerHandler.sendEmptyMessage(0);   // 发送空消息
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        if(visibility==View.VISIBLE){
            timerHandler.sendEmptyMessage(0);   // 发送空消息
        }
    }

    // 刷新界面显示的时间
    private void refreshTime(){
        Calendar c = Calendar.getInstance();
        tvTime.setText(String.format("%d:%d:%d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND)));
    }

    private Handler timerHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {    // 接收数据
            refreshTime();
            if(getVisibility()== View.VISIBLE) {    // 可见状态时
                timerHandler.sendEmptyMessageDelayed(0, 1000); // 1000毫秒后再执行handleMessage()方法
            }else{  // 不可见
                timerHandler.removeMessages(0); // 移除所有消息
            }
        }
    };

}
