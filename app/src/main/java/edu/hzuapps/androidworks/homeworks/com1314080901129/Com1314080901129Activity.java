package hzu.dadishu;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;


public class Com1314080901129Activity extends Activity {
    private int i = 0; // 记录其打到了几只地鼠
    private ImageView mouse; // 声明一个ImageView对象
    private Handler handler; // 声明一个Handler对象
    public int[][] position = new int[][]{{365,930}, {380,690},
            {297,803}, {150,750}, {552,815}, {594,680},
            {365,600},{509,570}}; // 创建一个表示地鼠位置的数组

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901129);
        mouse = (ImageView) findViewById(R.id.imageView1); // 获取ImageView对象
        mouse.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setVisibility(View.INVISIBLE); // 设置地鼠不显示
                i++;
                Toast.makeText(Com1314080901129Activity.this, "打到[ " + i + " ]只地鼠！",
                        Toast.LENGTH_SHORT).show(); // 显示消息提示框
                return false;
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int index = 0;
                if (msg.what == 0x101) {
                    index = msg.arg1; // 获取位置索引值
                    mouse.setX(position[index][0]); // 设置X轴位置
                    mouse.setY(position[index][1]); // 设置Y轴位置
                    mouse.setVisibility(View.VISIBLE); // 设置地鼠显示
                }
                super.handleMessage(msg);
            }

        };
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                int index = 0; // 创建一个记录地鼠位置的索引值
                while (!Thread.currentThread().isInterrupted()) {
                    index = new Random().nextInt(position.length); // 产生一个随机数
                    Message m = handler.obtainMessage(); // 获取一个Message
                    m.what = 0x101; // 设置消息标识
                    m.arg1 = index; // 保存地鼠标位置的索引值
                    handler.sendMessage(m); // 发送消息

                    try {
                        Thread.sleep(new Random().nextInt(500) + 500); // 休眠一段时间
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }

        });
        t.start(); // 开启线程

    }
}
