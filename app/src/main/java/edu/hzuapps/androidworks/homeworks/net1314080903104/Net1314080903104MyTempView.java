package edu.hzuapps.androidworks.homeworks.net1314080903104;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class Net1314080903104MyTempView extends View {

    private String temp;
    private float fireTem;
    private float lowTem;
    private Paint currentTmp;
    private Paint mPaint;
    private Paint textPaint;
    private Paint paintCircle;
    private Paint paintLine;
    private Bitmap bitmaplv;
    private Bitmap bitmaplan;
    private Bitmap bitmapred;
    private Bitmap fire;
    private Bitmap ice;
    private Context context;
    private int m;
    private Paint mPaintOther;


    public Net1314080903104MyTempView(Context context, AttributeSet attrs) {
        super(context, attrs);
        currentTmp = new Paint();
        currentTmp.setAntiAlias(true);
        currentTmp.setTextSize(20);
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#F2DED7"));
        // mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        textPaint = new TextPaint();
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(18);
        paintCircle = new Paint();
        // paintCircle.setColor(Color.parseColor("#61BEE7"));
        paintCircle.setAntiAlias(true);
        paintCircle.setTextSize(45);
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setAntiAlias(true);
        paintLine = new Paint();
        paintLine.setStrokeWidth(2.5f);
        paintLine.setColor(Color.BLUE);
        bitmaplv = BitmapFactory.decodeResource(getResources(),
                R.mipmap.kedu_s_net1314080903104);
        bitmaplan = BitmapFactory.decodeResource(getResources(),
                R.mipmap.kedu_lan_small_net1314080903104);
        bitmapred = BitmapFactory.decodeResource(getResources(),
                R.mipmap.kedu_red_small_net1314080903104);
        Paint rightPaint = new Paint();
        rightPaint.setAntiAlias(true);
        fire = BitmapFactory.decodeResource(getResources(), R.mipmap.fire_s_net1314080903104);
        ice = BitmapFactory.decodeResource(getResources(),
                R.mipmap.summer_small_net1314080903104);
        mPaintOther = new Paint();
        mPaintOther.setColor(Color.parseColor("#030102"));
        mPaintOther.setAntiAlias(true);
        mPaintOther.setStrokeWidth(1);

    }

    /**
     * @param temp    温度
     * @param fireTem 高温报警
     * @param lowTem  低温报警
     * @param context
     * @param m       高度
     */
    public void setTemp(String temp, float fireTem, float lowTem,
                        Context context, int m) {
        this.temp = temp;
        this.fireTem = fireTem;
        this.lowTem = lowTem;
        this.context = context;
        this.m = m;
        this.invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, m + 120);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        @SuppressWarnings("deprecation")
        // 屏幕宽度的一半
                int width = getWidth() / 2;
        // int screenHeight = getHeight();
        // int m = (int) (MyUtils.px2dp(context, screenHeight) * 1.1 + 30);
        // 当前温度
        float tem1 = Float.parseFloat(temp);
        // 根据温度得到需要绘制的高度
        int y = m - (int) ((tem1 - 35) * 50);
        int mFireHight = m - (int) ((fireTem - 35) * 50);
//        Log.e("TAG", mFireHight + "????");
        textPaint.setColor(Color.parseColor("#f9847b"));
        // 高温报警温度
        canvas.drawBitmap(fire, width - 35 - 7 - 70 - 115,
                mFireHight - fire.getHeight() / 2 - 4, mPaint);
        //报警温度的长度大于4
        if (String.valueOf(fireTem).length() > 4) {
            canvas.drawText(String.valueOf(fireTem).substring(0, 4) + "°C",
                    width - 35 - 7 - 150, mFireHight + 4, textPaint);
        } else {
            canvas.drawText(String.valueOf(fireTem) + "°C",
                    width - 35 - 7 - 150, mFireHight + 4, textPaint);
        }
        int mLowHight = m - (int) ((lowTem - 35) * 50);
        textPaint.setColor(Color.parseColor("#479aed"));
        // 低温报警温度
        canvas.drawBitmap(ice, width - 35 - 7 - 70 - 115,
                mLowHight - ice.getHeight() / 2 - 4, mPaint);
        //低温温度字符串长度大于4
        if (String.valueOf(lowTem).length() > 4) {
            canvas.drawText(String.valueOf(lowTem).substring(0, 4) + "°C",
                    width - 35 - 7 - 150, mLowHight + 4, textPaint);
        } else {
            canvas.drawText(String.valueOf(lowTem) + "°C",
                    width - 35 - 7 - 150, mLowHight + 4, textPaint);
        }
        // 温度计 矩形
        canvas.drawRect(width - 35, 0, width + 35, m, mPaint);
        // 右侧三角形刻度 字体颜
        // 温度过低
        if (tem1 >= 35 && tem1 < lowTem) {
            currentTmp.setColor(Color.parseColor("#497aed"));
            paintCircle.setColor(Color.parseColor("#497aed"));
            // 当前温度表示 矩形
            canvas.drawRect(width - 35, y, width + 35, m, paintCircle);
            // 圆形
            canvas.drawCircle(width, m + 50, 60, paintCircle);
            // 右侧三角形刻度
            canvas.drawBitmap(bitmaplan, width + 40, y - bitmaplan.getHeight()
                    / 2, mPaint);
            // 当前温度字体
            canvas.drawText(temp + "°C",
                    width + 40 + bitmaplan.getWidth() + 15,
                    y + bitmaplan.getHeight() / 4, paintCircle);
//            Log.e("TAG", temp + "temp");
            canvas.drawText("当前温度", width + 40 + bitmaplan.getWidth() + 15 + 5,
                    y + bitmaplan.getHeight() / 2 + 10 + 5, currentTmp);
            // 正常温度
        } else if (tem1 >= lowTem && tem1 < fireTem) {
            currentTmp.setColor(Color.parseColor("#3DB475"));
            paintCircle.setColor(Color.parseColor("#3DB475"));
            // 当前温度表示 矩形
            canvas.drawRect(width - 35, y, width + 35, m, paintCircle);
            // 圆形
            canvas.drawCircle(width, m + 50, 60, paintCircle);
            // 右侧三角形刻度
            canvas.drawBitmap(bitmaplv, width + 40, y - bitmaplv.getHeight()
                    / 2, mPaint);
            // 当前温度字体
            canvas.drawText(temp + "°C", width + 40 + bitmaplv.getWidth() + 15,
                    y + bitmaplv.getHeight() / 4, paintCircle);
            canvas.drawText("当前温度", width + 40 + bitmaplv.getWidth() + 15 + 5,
                    y + bitmaplv.getHeight() / 2 + 10 + 5, currentTmp);
        } else if ((tem1 + 0.1) >= fireTem) {
            // 温度过高
            paintCircle.setColor(Color.parseColor("#F65402"));
            currentTmp.setColor(Color.parseColor("#F65402"));
            // 当前温度表示 矩形
            canvas.drawRect(width - 35, y, width + 35, m, paintCircle);
            // 圆形
            canvas.drawCircle(width, m + 50, 60, paintCircle);
            // 右侧三角形刻度
            canvas.drawBitmap(bitmapred, width + 40, y - bitmaplv.getHeight()
                    / 2, mPaint);
            // 当前温度字体
            canvas.drawText(temp + "°C",
                    width + 40 + bitmapred.getWidth() + 15,
                    y + bitmapred.getHeight() / 4, paintCircle);
            canvas.drawText("当前温度", width + 40 + bitmapred.getWidth() + 15 + 5,
                    y + bitmapred.getHeight() / 2 + 10 + 5, currentTmp);
        } else if (tem1 < 35) {
            currentTmp.setColor(Color.parseColor("#497aed"));
            paintCircle.setColor(Color.parseColor("#497aed"));
            // 当前温度表示 矩形
            canvas.drawRect(width - 35, y, width + 35, m, paintCircle);
            // 圆形
            canvas.drawCircle(width, m + 50, 60, paintCircle);
            // 右侧三角形刻度
            canvas.drawBitmap(bitmaplan, width + 40, m - bitmaplan.getHeight()
                    / 2, mPaint);
            // 当前温度字体
            canvas.drawText(temp + "°C",
                    width + 40 + bitmaplan.getWidth() + 15,
                    m + bitmaplan.getHeight() / 4, paintCircle);
//            Log.e("TAG", temp + "temp");
            canvas.drawText("当前温度", width + 40 + bitmaplan.getWidth() + 15 + 5 + 25,
                    m + bitmaplan.getHeight() / 2 + 10 + 5, currentTmp);
        }
        int ydegree = m;
//        Log.e("TAG", ydegree + "");
        float tem = 35;
        while (ydegree > 15) {
            canvas.drawLine(width - 35 - 10, ydegree, width - 35, ydegree, mPaintOther);
            if (ydegree % 10 == 0) {
                canvas.drawLine(width - 35 - 18, ydegree, width - 35, ydegree,
                        paintLine);
                textPaint.setColor(Color.parseColor("#17171a"));
                canvas.drawText(tem + "°C", width - 35 - 7 - 90, ydegree + 4,
                        textPaint);
                tem++;

            }
            ydegree = ydegree - 25;
        }
    }
}
