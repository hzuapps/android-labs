package edu.hzuapps.androidworks.homeworks.com1314080901209;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author chzayi
 */
public class Drawl extends View {
    private int mov_x;// 声明起点坐标
    private int mov_y;
    private Paint paint;// 声明画笔
    private Canvas canvas;// 画布
    private Bitmap bitmap;// 位图

    private List<Point> list;// 装有各个view坐标的集合
    private List<Pair<Point, Point>> lineList;// 记录画过的线

    /**
     * 手指当前在哪个Point内
     */
    private Point currentPoint;
    /**
     * 用户绘图的回调
     */
    private GestureCallBack callBack;

    /**
     * 用户当前绘制的图形密码
     */
    private StringBuilder passWordSb;

    /**
     * 用户传入的passWord
     */
    private String passWord;

    public Drawl(Context context, List<Point> list, String passWord, GestureCallBack callBack) {
        super(context);
        paint = new Paint(Paint.DITHER_FLAG);// 创建一个画笔
        bitmap = Bitmap.createBitmap(480, 854, Bitmap.Config.ARGB_8888); // 设置位图的宽高
        canvas = new Canvas();
        canvas.setBitmap(bitmap);

        paint.setStyle(Style.STROKE);// 设置非填充
        paint.setStrokeWidth(10);// 笔宽5像素
        paint.setColor(Color.rgb(4, 115, 157));// 设置颜色
        paint.setAntiAlias(true);// 不显示锯齿

        this.list = list;
        this.lineList = new ArrayList<Pair<Point, Point>>();
        this.callBack = callBack;

        //初始化密码缓存
        this.passWordSb = new StringBuilder();
        this.passWord = passWord;
    }

    // 画位图
    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    // 触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mov_x = (int) event.getX();
                mov_y = (int) event.getY();

                // 判断当前点击的位置是处于哪个点之内
                currentPoint = getPointAt(mov_x, mov_y);
                if (currentPoint != null) {
                    currentPoint.setHighLighted(true);
                    passWordSb.append(currentPoint.getNum());
                }
                // canvas.drawPoint(mov_x, mov_y, paint);// 画点
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                clearScreenAndDrawList();

                // 得到当前移动位置是处于哪个点内
                Point pointAt = getPointAt((int) event.getX(), (int) event.getY());
                //代表当前用户手指处于点与点之前
                if (currentPoint == null && pointAt == null) {
                    return true;
                } else {//代表用户的手指移动到了点上
                    if (currentPoint == null) {//先判断当前的point是不是为null
                        //如果为空，那么把手指移动到的点赋值给currentPoint
                        currentPoint = pointAt;
                        //把currentPoint这个点设置选中为true;
                        currentPoint.setHighLighted(true);
                        passWordSb.append(currentPoint.getNum());
                    }
                }

                if (pointAt == null || currentPoint.equals(pointAt)
                        || pointAt.isHighLighted()) {
                    // 点击移动区域不在圆的区域 或者
                    // 如果当前点击的点与当前移动到的点的位置相同
                    // 那么以当前的点中心为起点，以手指移动位置为终点画线
                    canvas.drawLine(currentPoint.getCenterX(),
                            currentPoint.getCenterY(), event.getX(), event.getY(),
                            paint);// 画线


                } else {
                    // 如果当前点击的点与当前移动到的点的位置不同
                    // 那么以前前点的中心为起点，以手移动到的点的位置画线
                    canvas.drawLine(currentPoint.getCenterX(),
                            currentPoint.getCenterY(), pointAt.getCenterX(),
                            pointAt.getCenterY(), paint);// 画线

                    pointAt.setHighLighted(true);

                    Pair<Point, Point> pair = new Pair<Point, Point>(currentPoint,
                            pointAt);
                    lineList.add(pair);

                    // 赋值当前的point;
                    currentPoint = pointAt;
                    passWordSb.append(currentPoint.getNum());
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:// 当手指抬起的时候
                // 清掉屏幕上所有的线，只画上集合里面保存的线
                if (passWord.equals(passWordSb.toString())) {
                    //代表用户绘制的密码手势与传入的密码相同
                    callBack.checkedSuccess();
                    Intent intent = new Intent(getContext(), IndexAction.class);
                    getContext().startActivity(intent);
                   android.os.Process.killProcess(android.os.Process.myPid());
                } else {
                    //用户绘制的密码与传入的密码不同。
                    callBack.checkedFail();
                }
                //重置passWordSb
                passWordSb = new StringBuilder();
                //清空保存点的集合
                lineList.clear();
                //重新绘制界面
                clearScreenAndDrawList();
                for (Point p : list) {
                    p.setHighLighted(false);
                }
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 通过点的位置去集合里面查找这个点是包含在哪个Point里面的
     *
     * @param x
     * @param y
     * @return 如果没有找到，则返回null，代表用户当前移动的地方属于点与点之间
     */
    private Point getPointAt(int x, int y) {

        for (Point point : list) {
            // 先判断x
            int leftX = point.getLeftX();
            int rightX = point.getRightX();
            if (!(x >= leftX && x < rightX)) {
                // 如果为假，则跳到下一个对比
                continue;
            }

            int topY = point.getTopY();
            int bottomY = point.getBottomY();
            if (!(y >= topY && y < bottomY)) {
                // 如果为假，则跳到下一个对比
                continue;
            }

            // 如果执行到这，那么说明当前点击的点的位置在遍历到点的位置这个地方
            return point;
        }

        return null;
    }

    /**
     * 清掉屏幕上所有的线，然后画出集合里面的线
     */
    private void clearScreenAndDrawList() {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        for (Pair<Point, Point> pair : lineList) {
            canvas.drawLine(pair.first.getCenterX(), pair.first.getCenterY(),
                    pair.second.getCenterX(), pair.second.getCenterY(), paint);// 画线
        }
    }

    public interface GestureCallBack {

        /**
         * 代表用户绘制的密码与传入的密码相同
         */
        public abstract void checkedSuccess();

        /**
         * 代表用户绘制的密码与传入的密码不相同
         */
        public abstract void checkedFail();
    }

}
