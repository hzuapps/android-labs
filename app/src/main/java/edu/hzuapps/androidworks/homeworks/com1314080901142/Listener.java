package edu.hzuapps.androidworks.homework.com1314080901142;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/************************
 * 类名：Listener<br>
 * 功能：核心处理类，接触监听器接口<br>
 * 创建事件：
 *
 * @author xll
 ************************/
public class Listener implements OnClickListener {
    /**
     * 分数
     */
    private int score = 0;
    /**
     * 计时器
     */
    private int time = 0;
    /**
     * 倒计时
     */
    private int DownTime = 60;
    /**
     * 图片按钮  ，即游戏方块
     */
    private ImageButton imageBtn[] = new ImageButton[20];
    /**
     * 接收、并更新方块信息，有方块图片
     */
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            imageBtn[msg.what].setBackgroundResource(R.drawable.com1314080901142_ai);
        }
    };
    /**
     * 接收、并更新方块信息，设置透明、即无方块图片
     */
    private Handler handler1 = new Handler() {
        public void handleMessage(Message msg) {
            //R.drawable.tra 透明
            imageBtn[msg.what].setBackgroundResource(R.drawable.tra);
        }
    };
    /**
     * 接收、并更新分数信息
     */
    private Handler handler_score = new Handler() {
        public void handleMessage(Message msg) {
            int s = score++;
            String score = String.valueOf(s);
            text_score.setText(score);//设置并更新分数文本框
        }
    };
    /**
     * 接收、并更新倒计时信息，控制游戏结束
     */
    private Handler handler_time = new Handler() {
        public void handleMessage(Message msg) {
            if (DownTime > 0) {
                String time = String.valueOf(DownTime--);
                text_time.setText(time);
                if (DownTime <= 0) {
                    gameover();//游戏结束时调用
                }
            }
        }

    };
    /**
     * 布局的20个图片按钮
     */
    private int imgBtnId[] =
            {
                    R.id.imageButton0, R.id.imageButton1, R.id.imageButton2, R.id.imageButton3, R.id.imageButton4,
                    R.id.imageButton5, R.id.imageButton6, R.id.imageButton7, R.id.imageButton8, R.id.imageButton9,
                    R.id.imageButton10, R.id.imageButton11, R.id.imageButton12, R.id.imageButton13, R.id.imageButton14,
                    R.id.imageButton15, R.id.imageButton16, R.id.imageButton17, R.id.imageButton18, R.id.imageButton19
            };
    /**
     * 定义“封装方块信息”的类对象
     */
    private GridPack gridPack[] = new GridPack[20];
    /**
     * 定义倒计时文本框
     */
    private TextView text_time;
    /**
     * 定义分数文本框
     */
    private TextView text_score;
    /**
     * 定义WolfShootingActivity类
     */
    private WolfShootingActivity ws;

    /**
     * 函数名称：Listener<br>
     * 函数功能：构造函数
     *
     * @param ws
     * @return 空
     * @author xll
     */
    public Listener(WolfShootingActivity ws) {
        this.ws = ws;
        for (int i = 0; i <= 19; i++) {
            //获得方块组件
            imageBtn[i] = (ImageButton) ws.findViewById(imgBtnId[i]);
            //设置监听器
            imageBtn[i].setOnClickListener(this);
            //为每个方块创建方块信息
            gridPack[i] = new GridPack();

        }
        //获得分数文本框组件
        text_score = (TextView) ws.findViewById(R.id.text_score);
        //获得倒计时文本框组件
        text_time = (TextView) ws.findViewById(R.id.text_time);
        //创建并开启新线程
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                        upimg();//处理并更新方块信息
                        upDownTime();//处理并更新倒计时信息
                        time++;//计时器累计
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }


        }.start();
    }

    /**
     * 函数名称：upDownTime<br>
     * 函数功能：更新倒计时信息
     *
     * @return 空
     * @author xll
     */
    private void upDownTime() {
        //每隔1秒更新倒计时
        if (time % 10 == 0) {
            Message msg = new Message();//新建消息
            handler_time.sendMessage(msg);//发送消息
        }
    }

    /**
     * 函数名称：upimg<br>
     * 函数功能：更新方块信息，核心代码
     *
     * @return 空
     * @author xll
     */
    private void upimg() {
        create();//构建方块图片
        for (int i = 0; i <= 19; i++) {
            if (gridPack[i].getIsImg() == 1)//gridPack[i].getImg() == 1表示有方块图片
            {
                gridPack[i].setTime(gridPack[i].getTime() + 1);//更新方块图片停留时间
            }
            if (gridPack[i].getTime() >= 10)//停留时间超过1秒
            {
                gridPack[i].setIsImg(0);//gridPack[i].setImg(0) ――――> (0表示无方块图片 | 1表示有方块图片)
                gridPack[i].setTime(0);//重新设置停留时间
                //发送“更新方块信息，设置该方块为透明”
                Message msg = new Message();
                msg.what = i;//方块编号i
                handler1.sendMessage(msg);
            }
        }
    }

    /**
     * 函数名称：create<br>
     * 函数功能：构建方块图片
     *
     * @return 空
     * @author xll
     */
    private void create() {
        if (time % 20 == 0)//计时器，每隔2秒
        {
            crateI();//随机构建一个方块图片

            if (time >= 100)//10秒
            {
                for (int i = 0; i <= 1; i++) {
                    crateI();
                }
            }
            if (time >= 200)//20秒
            {
                for (int i = 0; i <= 1; i++) {
                    crateI();
                }
            }
        }
    }

    /**
     * 函数名称：crateI<br>
     * 函数功能：随机构建一个方块图片
     *
     * @return 空
     * @author xll
     */
    private void crateI() {
        int num = (int) (Math.random() * 20);//产生随机数
        //发送“更新方块”信息
        Message msg = new Message();
        msg.what = num;//方块编号
        handler.sendMessage(msg);
        gridPack[num].setIsImg(1);//设置方块信息-->有方块图片
    }

    //重写按钮监听事件
    @Override
    public void onClick(View v) {
        for (int i = 0; i <= 19; i++) {
            //v.getId() == imgBtnId[i] 确定哪一方块触发监听器
            //gridPack[i].getImg() == 1 确定方块是否有图片
            if (v.getId() == imgBtnId[i] && gridPack[i].getIsImg() == 1) {
                gridPack[i].setIsImg(0);//该方块设置无图片
                v.setBackgroundResource(R.drawable.tra);//方块图片设置为透明
                //发送更新分数信息
                Message msg = new Message();
                handler_score.sendMessage(msg);
                break;
            }
        }
    }

    /**
     * 函数名称：gameover<br>
     * 函数功能：游戏结束时调用
     *
     * @return 空
     * @author xll
     */
    private void gameover() {
        //创建对话框
        new AlertDialog.Builder(ws)
                .setIcon(R.drawable.com1314080901142_shuijing)//设置对话框图标
                .setTitle("游戏结束")//设置标题
                .setMessage("是否重新挑战纪录？")//设置内容
                //设置取消按钮
                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);//结束进程，退出后台
                    }
                })
                //设置确定按钮
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        DownTime = 100;//重置倒计时
                        score = 0;//重置分数

                    }
                }).show();//显示对话框

    }
}
