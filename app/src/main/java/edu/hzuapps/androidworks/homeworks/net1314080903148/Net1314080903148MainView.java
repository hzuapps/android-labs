package net1314080903148.homeworks.androidworks.hzuapps.edu.net1314080903148;

/**
 * Created by zhu on 2016/5/12.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import net1314080903148.homeworks.androidworks.hzuapps.edu.net1314080903148.R;

public class Net1314080903148MainView extends View {

    Paint paint = new Paint(); //画笔
    Bitmap shangBitmap1; //上面的大矩形
    Bitmap shangBitmap2; //上面的气泡
    Bitmap zuoBitmap1; //左面的大矩形图
    Bitmap zuoBitmap2; //左面图的气泡
    Bitmap zhongBitmap1; //中间的大圆图
    Bitmap zhongBitmap2; //中间的小气泡
    Bitmap xiaBitmap1; //右下的矩形图
    Bitmap xiaBitmap2; //右下的气泡

    //背景矩形的位置声明
    int shang1_X = 60; //上面的大矩形图
    int shang1_Y = 12;
    int zuo1_X = 12; //左面的大矩形图
    int zuo1_Y = 60;
    int zhong1_X = 65; //中间的大圆图
    int zhong1_Y = 65;
    int xia1_X = 145; //右下的矩形图
    int xia1_Y = 145;//水泡的位置声明
    int shang2_X; //上面的气泡XY 坐标
    int shang2_Y;
    int zuo2_X; //左面图的气泡XY 坐标
    int zuo2_Y;
    int zhong2_X; //中间的小气泡XY 坐标
    int zhong2_Y;
    int xia2_X; //右下的气泡XY 坐标
    int xia2_Y;

    public Net1314080903148MainView(Context context, AttributeSet attrs){
        super(context, attrs);
        initBitmap(); //初始化图片资源
        initLocation(); //初始化气泡的位置
    }

    private void initBitmap(){ //初始化图片的方法
        //该处省略了部分代码，将在后面进行介绍
        shangBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.net1314080903148_level_shang1);
        shangBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.net1314080903148_level_shang2);
        zuoBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.net1314080903148_level_zuo1);
        zuoBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.net1314080903148_level_zuo2);
        zhongBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.net1314080903148_level_zhong1);
        zhongBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.net1314080903148_level_zhong2);
        xiaBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.net1314080903148_level_xia1);
        xiaBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.net1314080903148_level_xia2);
    }

    private void initLocation(){ //初始化气泡位置的方法
        //该处省略了部分代码，将在后面进行介绍
        shang2_X = shang1_X + shangBitmap1.getWidth()/2- shangBitmap2.getWidth()/2;
        shang2_Y = shang1_Y + shangBitmap1.getHeight()/2- shangBitmap2.getHeight()/2;
        zuo2_X = zuo1_X + zuoBitmap1.getWidth()/2- zuoBitmap2.getWidth()/2;
        zuo2_Y = zuo1_Y + zuoBitmap1.getHeight()/2- zuoBitmap2.getHeight()/2;
        zhong2_X = zhong1_X + zhongBitmap1.getWidth()/2- zhongBitmap2.getWidth()/2;
        zhong2_Y = zhong1_Y + zhongBitmap1.getHeight()/2- zhongBitmap2.getHeight()/2;
        xia2_X = xia1_X + xiaBitmap1.getWidth()/2- xiaBitmap2.getWidth()/2;
        xia2_Y = xia1_Y + xiaBitmap1.getHeight()/2- xiaBitmap2.getHeight()/2;
    }

    @Override
    protected void onDraw(Canvas canvas){//重写的绘制方法
        super.onDraw(canvas);
        //该处省略了部分代码，将在后面进行介绍
        canvas.drawColor(Color.WHITE); //设置背景色为白色

        paint.setColor(Color.BLUE); //设置画笔颜色
        paint.setStyle(Style.STROKE); //设置画笔为不填充
       // canvas.drawRect(5, 5, 315, 315, paint);//绘制外边框矩形

        //画背景矩形
        canvas.drawBitmap(shangBitmap1, shang1_X,shang1_Y, paint); //上
        canvas.drawBitmap(zuoBitmap1, zuo1_X,zuo1_Y, paint); //左
        canvas.drawBitmap(zhongBitmap1, zhong1_X,zhong1_Y, paint); //中
        canvas.drawBitmap(xiaBitmap1, xia1_X,xia1_Y, paint); //下

        //开始绘制气泡
        canvas.drawBitmap(shangBitmap2, shang2_X,shang2_Y, paint); //上
        canvas.drawBitmap(zuoBitmap2, zuo2_X,zuo2_Y, paint); //左
        canvas.drawBitmap(zhongBitmap2, zhong2_X,zhong2_Y, paint); //中
        canvas.drawBitmap(xiaBitmap2, xia2_X, xia2_Y, paint);//下

        paint.setColor(Color.GRAY);//设置画笔颜色用来绘制刻度

        //绘制上面方框中的刻度
        canvas.drawLine (shang1_X+shangBitmap1.getWidth()/2-7,shang1_Y, shang1_X+shangBitmap1.getWidth()/2-7,shang1_Y+shangBitmap1.getHeight()-2, paint);
        canvas.drawLine (shang1_X+shangBitmap1.getWidth()/2+7,shang1_Y, shang1_X+shangBitmap1.getWidth()/2+7,shang1_Y+shangBitmap1.getHeight()-2, paint);

        //绘制左面方框中的刻度
        canvas.drawLine(zuo1_X,zuo1_Y+zuoBitmap1.getHeight()/2-7,zuo1_X+zuoBitmap1.getWidth()-2,zuo1_Y+zuoBitmap1.getHeight()/2-7, paint);canvas.drawLine(zuo1_X,zuo1_Y+zuoBitmap1.getHeight()/2+7,zuo1_X+zuoBitmap1.getWidth()-2,zuo1_Y+zuoBitmap1.getHeight()/2+7, paint);

        //绘制下面方框中的刻度

        canvas.drawLine(xia1_X+xiaBitmap1.getWidth()/2-10,xia1_Y+xiaBitmap1.getHeight()/2-20,xia1_X+xiaBitmap1.getWidth()/2+20,xia1_Y+xiaBitmap1.getHeight()/2+10, paint);
        canvas.drawLine(xia1_X+xiaBitmap1.getWidth()/2-20,xia1_Y+xiaBitmap1.getHeight()/2-10,xia1_X+xiaBitmap1.getWidth()/2+10,xia1_Y+xiaBitmap1.getHeight()/2+20, paint);

        //中间圆圈中的刻度(小圆)
        RectF oval = new RectF(zhong1_X+zhongBitmap1.getWidth()/2-10,zhong1_Y+zhongBitmap1.getHeight()/2-10,zhong1_X+zhongBitmap1.getWidth()/2+10,zhong1_Y+zhongBitmap1.getHeight()/2+10);

        canvas.drawOval(oval, paint);//绘制基准线(圆)
    }
}
