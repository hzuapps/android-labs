package edu.hzuapps.androidworks.homeworks.net1314080903149;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/27.
 */
public class Net1314080903149_wuziqiPanel extends View
{
    private int mPanelWidth;
    private float mLineHeight;
    private int MAX_LINE=10;
    private int MAX_COUNT_IN_LINE=5;    //设置一个常量，相邻的同色棋子最多5个

    private Paint mPaint = new Paint();

    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;

    private float ratioPiece0fLightHeight=3*1.0f/4;    //棋子的大小是行高的3/4

    private boolean mIsWhite=true;                            //白棋先走
    private ArrayList<Point> mWhiteArray=new ArrayList<>();   //存放玩家点击的坐标
    private ArrayList<Point> mBlackArray=new ArrayList<>();

    private boolean mIsGameOver;
    private boolean mIsWhiteWinner;

    public Net1314080903149_wuziqiPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        //setBackgroundColor(0x44ff0000);
        init();
    }

    private void init() {
        mPaint.setColor(0x88000000);       //网格颜色是灰色，半透明
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mWhitePiece= BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);   //引入棋子的图片
        mBlackPiece= BitmapFactory.decodeResource(getResources(),R.drawable.stone_b1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heighMode = MeasureSpec.getMode(heightMeasureSpec);

        int width =Math.min(widthSize, heightSize);
        if (widthMode==MeasureSpec.UNSPECIFIED)
        {
            width=heightSize;
        }else if (heighMode==MeasureSpec.UNSPECIFIED)
        {
            width=widthSize;
        }
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {     //对尺寸相关的一些变量进行赋值
        super.onSizeChanged(w, h, oldw, oldh);

        mPanelWidth=w;
        mLineHeight=mPanelWidth*1.0f/MAX_LINE;   //行高

        int pieceWidth = (int) (mLineHeight*ratioPiece0fLightHeight);         //定义棋子的大小
        mWhitePiece=Bitmap.createScaledBitmap(mWhitePiece,pieceWidth,pieceWidth,false);
        mBlackPiece=Bitmap.createScaledBitmap(mBlackPiece,pieceWidth,pieceWidth,false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action==MotionEvent.ACTION_UP)
        {
            if(mIsGameOver) return false;      //如果游戏结束，return false
            int x= (int) event.getX();
            int y= (int) event.getY();
            Point p =getValidPoint(x,y);

            if (mWhiteArray.contains(p)||mBlackArray.contains(p))
            {
                return false;
            }

            if(mIsWhite)
            {
                mWhiteArray.add(p);
            }else
            {
                mBlackArray.add(p);
            }
            invalidate();
            mIsWhite=!mIsWhite;

        }
        return true;
    }

    private Point getValidPoint(int x, int y) {
        return new Point((int)(x/mLineHeight), (int) (y/mLineHeight));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);     //创建一个方法，用来绘制棋盘
        drawPieces(canvas);   //创建一个方法，用来绘制棋子
        checkGameOver();     //创建一个方法，游戏结束
    }

    private void checkGameOver() {                              //检查是否已经有五子连珠了
        boolean whitewin= checkFiveInLine(mWhiteArray);        //白棋赢
        boolean blackwin= checkFiveInLine(mBlackArray);        //黑棋赢
        if(whitewin||blackwin)
        {
            mIsGameOver=true;                              //白棋赢或黑棋赢，游戏结束
            mIsWhiteWinner=whitewin;
            String text=mIsWhiteWinner?"白棋胜利":"黑棋胜利";   //如果白棋胜利，显示白棋胜利，否则显示黑棋胜利
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkFiveInLine(List<Point> points) {         //判断是否五子连珠
        for (Point p:points)            //针对每一个棋子进行循环
        {
            int x =p.x;
            int y =p.y;
            boolean win =checkHorizontal(x,y,points);
            if (win)return true;
            win=checkVertical(x,y,points);
            if (win)return true;
            win=checkLeftDiagonal(x,y,points);
            if (win)return true;
            win=checkRightDiagonal(x,y,points);
            if (win)return true;
        }
        return false;
    }

    private boolean checkHorizontal(int x, int y, List<Point> points) {        //判断x,y位置的棋子，横向相邻的棋子是否五个同色
        int count=1;
        for (int i=1;i<MAX_COUNT_IN_LINE;i++)      //判断横向左边棋子个数
        {
            if(points.contains(new Point(x-i,y)))
            {
                count++;
            }else
            {
                break;        //不是同色棋子结束循环
            }
        }

        if (count==MAX_COUNT_IN_LINE) return true;

        for (int i=1;i<MAX_COUNT_IN_LINE;i++)      //判断横向右边边棋子个数
        {
            if(points.contains(new Point(x+i,y)))
            {
                count++;
            }else
            {
                break;        //不是同色棋子结束循环
            }
        }

        if (count==MAX_COUNT_IN_LINE) return true;  //如果相邻棋子达到5个，return true

        return false;
    }

    private boolean checkVertical(int x, int y, List<Point> points) {        //判断x,y位置的棋子，纵向相邻的棋子是否五个同色
        int count=1;
        for (int i=1;i<MAX_COUNT_IN_LINE;i++)      //判断纵向上边棋子个数
        {
            if(points.contains(new Point(x,y-i)))
            {
                count++;
            }else
            {
                break;        //不是同色棋子结束循环
            }
        }

        if (count==MAX_COUNT_IN_LINE) return true;

        for (int i=1;i<MAX_COUNT_IN_LINE;i++)      //判断纵向下边棋子个数
        {
            if(points.contains(new Point(x,y+i)))
            {
                count++;
            }else
            {
                break;        //不是同色棋子结束循环
            }
        }

        if (count==MAX_COUNT_IN_LINE) return true;  //如果相邻棋子达到5个，return true

        return false;
    }

    private boolean checkLeftDiagonal(int x, int y, List<Point> points) {        //判断x,y位置的棋子，左斜相邻的棋子是否五个同色
        int count=1;
        for (int i=1;i<MAX_COUNT_IN_LINE;i++)      //判断左斜左下棋子个数
        {
            if(points.contains(new Point(x-i,y+i)))
            {
                count++;
            }else
            {
                break;        //不是同色棋子结束循环
            }
        }

        if (count==MAX_COUNT_IN_LINE) return true;

        for (int i=1;i<MAX_COUNT_IN_LINE;i++)      //判断左斜右上棋子个数
        {
            if(points.contains(new Point(x+i,y-i)))
            {
                count++;
            }else
            {
                break;        //不是同色棋子结束循环
            }
        }

        if (count==MAX_COUNT_IN_LINE) return true;  //如果相邻棋子达到5个，return true

        return false;
    }

    private boolean checkRightDiagonal(int x, int y, List<Point> points) {        //判断x,y位置的棋子，右斜的棋子是否五个同色
        int count=1;
        for (int i=1;i<MAX_COUNT_IN_LINE;i++)      //判断右斜左上棋子个数
        {
            if(points.contains(new Point(x-i,y-i)))
            {
                count++;
            }else
            {
                break;        //不是同色棋子结束循环
            }
        }

        if (count==MAX_COUNT_IN_LINE) return true;

        for (int i=1;i<MAX_COUNT_IN_LINE;i++)      //判断右斜右上棋子个数
        {
            if(points.contains(new Point(x+i,y+i)))
            {
                count++;
            }else
            {
                break;        //不是同色棋子结束循环
            }
        }

        if (count==MAX_COUNT_IN_LINE) return true;  //如果相邻棋子达到5个，return true

        return false;
    }


    private void drawPieces(Canvas canvas) {
        for(int i=0,n=mWhiteArray.size();i<n;i++)     //绘制白子
        {
            Point whitePoint = mWhiteArray.get(i);
            canvas.drawBitmap(mWhitePiece,
                    (whitePoint.x+(1-ratioPiece0fLightHeight)/2)*mLineHeight,
                    (whitePoint.y+(1-ratioPiece0fLightHeight)/2)*mLineHeight,null);
        }

        for(int i=0,n=mBlackArray.size();i<n;i++)     //绘制黑子
        {
            Point blackPoint = mBlackArray.get(i);
            canvas.drawBitmap(mBlackPiece,
                    (blackPoint.x+(1-ratioPiece0fLightHeight)/2)*mLineHeight,
                    (blackPoint.y+(1-ratioPiece0fLightHeight)/2)*mLineHeight,null);
        }
    }

    private void drawBoard(Canvas canvas) {     //绘制棋盘
        int w= mPanelWidth;                    //棋盘宽度
        float lineHeight = mLineHeight;
        for (int i=0;i<MAX_LINE;i++)
        {
            int startX=(int)(lineHeight/2);      //起始坐标
            int endX=(int)(w-lineHeight/2);     //结束坐标
            int y=(int)((0.5+i)*lineHeight);
            canvas.drawLine(startX,y,endX,y,mPaint);     //绘制横线
            canvas.drawLine(y,startX,y,endX,mPaint);    //绘制纵线
        }
    }

    public void start()
    {
        mWhiteArray.clear();
        mBlackArray.clear();
        mIsGameOver=false;
        mIsWhiteWinner = false;
        invalidate();
    }

    private static final String INSTANCE ="instance";
    private static final String INSTANCE_GAME_OVER ="instance_game_over";
    private static final String INSTANCE_WHITE_ARRAY ="instance_white_array";
    private static final String INSTANCE_BLACK_ARRAY ="instance_black_array";

    @Override
    protected Parcelable onSaveInstanceState() {              //对棋子位置的存储
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAME_OVER, mIsGameOver);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY, mWhiteArray);
        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAY, mBlackArray);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {        //对棋子位置的恢复
        if(state instanceof  Bundle)
        {
            Bundle bundle=(Bundle)state;
            mIsGameOver=bundle.getBoolean(INSTANCE_GAME_OVER);
            mWhiteArray=bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
            mBlackArray=bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAY);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
