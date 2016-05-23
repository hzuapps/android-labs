package net1314080903148.homeworks.androidworks.hzuapps.edu.net1314080903148;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.view.Menu;

import net1314080903148.homeworks.androidworks.hzuapps.edu.net1314080903148.R;

@SuppressWarnings("deprecation")

public class Net1314080903148MainActivity extends AppCompatActivity {
    int k = 45; //灵敏度
    Net1314080903148MainView mv;
    //真机
    SensorManager mySensorManager;

    //测试时
//	SensorManagerSimulator mySensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903148_main);
        mv = (Net1314080903148MainView) findViewById(R.id.mainView);

        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//真机
    }


    private final SensorListener mSensorLisener =new SensorListener(){

        @Override
        public void onAccuracyChanged(int sensor, int accuracy) { }

        public boolean isContain(int x, int y){//判断点是否在圆内

            int tempx =(int) (x + mv.zhongBitmap2.getWidth()/2.0);
            int tempy =(int) (y + mv.zhongBitmap2.getWidth()/2.0);
            int ox = (int) (mv.zhong1_X+ mv.zhongBitmap1.getWidth()/2.0);
            int oy = (int) (mv.zhong1_X+ mv.zhongBitmap1.getWidth()/2.0);
            if(Math.sqrt((tempx-ox)*(tempx-ox)+(tempy-oy)*(tempy-oy))>(mv.zhongBitmap1.getWidth()/2.0-mv.zhongBitmap2.getWidth()/2.0)){
                //不在圆内
                return false;
            }else{
                //在圆内时
                return true;
            }
        }

        @Override
        public void onSensorChanged(int sensor, float[] values) {
            if(sensor == SensorManager.SENSOR_ORIENTATION){
                double pitch = values[SensorManager.DATA_Y];
                double roll = values[SensorManager.DATA_Z];
                int x=0; int y=0;//临时变量，算中间水泡坐标时用
                int tempX=0; int tempY=0;//下面气泡的临时变量

                //开始调整x 的值
                if(Math.abs(roll)<=k){
                    mv.shang2_X = mv.shang1_X //上面的
                            + (int)(((mv.shangBitmap1.getWidth()
                            -mv.shangBitmap2.getWidth())/2.0)
                            -(((mv.shangBitmap1.getWidth()
                            -mv.shangBitmap2.getWidth())/2.0)*roll)/k);

                    x = mv.zhong1_X //中间的
                            + (int)(((mv.zhongBitmap1.getWidth()
                            -mv.zhongBitmap2.getWidth())/2.0)
                            -(((mv.zhongBitmap1.getWidth()
                            -mv.zhongBitmap2.getWidth())/2.0)*roll)/k);
                }else if(roll>k){
                    mv.shang2_X=mv.shang1_X; x = mv.zhong1_X;
                }else{
                    mv.shang2_X=mv.shang1_X+
                            mv.shangBitmap1.getWidth()
                            - mv.shangBitmap2.getWidth();

                    x = mv.zhong1_X+ mv.zhongBitmap1.getWidth()
                            - mv.zhongBitmap2.getWidth();
                }

                //开始调整y 的值

                if(Math.abs(pitch)<=k){
                    mv.zuo2_Y=mv.zuo1_Y //左面的
                            + (int)(((mv.zuoBitmap1.getHeight()
                            -mv.zuoBitmap2.getHeight())/2.0)
                            +(((mv.zuoBitmap1.getHeight()
                            -mv.zuoBitmap2.getHeight())/2.0)*pitch)/k);

                    y =mv.zhong1_Y+ //中间的
                            (int)(((mv.zhongBitmap1.getHeight()
                                    -mv.zhongBitmap2.getHeight())/2.0)
                                    +(((mv.zhongBitmap1.getHeight()
                                    -mv.zhongBitmap2.getHeight())/2.0)*pitch)/k);
                }else if(pitch>k){
                    mv.zuo2_Y=mv.zuo1_Y
                            +mv.zuoBitmap1.getHeight()
                            -mv.zuoBitmap2.getHeight();

                    y=mv.zhong1_Y+mv.zhongBitmap1.getHeight()
                            -mv.zhongBitmap2.getHeight();
                }else{
                    mv.zuo2_Y = mv.zuo1_Y; y = mv.zhong1_Y;
                }

                //下面的
                tempX = -(int) (((mv.xiaBitmap1.getWidth()/2-28)*roll
                        +(mv.xiaBitmap1.getWidth()/2-28)*pitch)/k);

                tempY = -(int) ((-(mv.xiaBitmap1.getWidth()/2-28)*roll
                        -(mv.xiaBitmap1.getWidth()/2-28)*pitch)/k);

                //限制下面的气泡范围
                if(tempY>mv.xiaBitmap1.getHeight()/2-28){
                    tempY = mv.xiaBitmap1.getHeight()/2-28;
                }
                if(tempY < -mv.xiaBitmap1.getHeight()/2+28){
                    tempY = -mv.xiaBitmap1.getHeight()/2+28;
                }

                if(tempX > mv.xiaBitmap1.getWidth()/2-28){
                    tempX = mv.xiaBitmap1.getWidth()/2-28;
                }

                if(tempX < -mv.xiaBitmap1.getWidth()/2+28){
                    tempX = -mv.xiaBitmap1.getWidth()/2+28;
                }

                mv.xia2_X = tempX + mv.xia1_X + mv.xiaBitmap1.getWidth()/2 -mv.xiaBitmap2.getWidth()/2;
                mv.xia2_Y = tempY + mv.xia1_Y + mv.xiaBitmap1.getHeight()/2 - mv.xiaBitmap2.getWidth()/2;

                if(isContain(x, y)){//中间的水泡在圆内才改变坐标
                    mv.zhong2_X = x; mv.zhong2_Y = y;
                }
                mv.postInvalidate();//重绘MainView
            }
        } //传感器监听器类
        //该处省略了部分代码，将在后面进行介绍
    };

    @Override
    protected void onResume(){ //添加监听
        mySensorManager.registerListener(mSensorLisener,SensorManager.SENSOR_ORIENTATION);
        super.onResume();
    }

    @Override
    protected void onPause() { //取消监听
        mySensorManager.unregisterListener (mSensorLisener);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_level, menu);
        return true;
    }
}
