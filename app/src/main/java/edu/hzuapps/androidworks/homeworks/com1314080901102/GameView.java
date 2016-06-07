package edu.hzuapps.androidworks.homeworks.com1314080901102;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.hzuapps.androidworks.homeworks.simple_2048.R;

/**
 * TODO: document your custom view class.
 */
public class GameView extends GridLayout {
    private HomeActivity homeActivity = HomeActivity.getHomeActivity();

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    private void init() {

        setColumnCount(4);
        setBackgroundColor(0xffbbada0);

        setOnTouchListener(new OnTouchListener() {
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                swipeLeft();
                            } else if (offsetX > 5) {
                                swipeRight();
                            }
                        } else {
                            if (offsetY > 5) {
                                swipeUp();
                            } else if (offsetY < -5) {
                                swipeDown();
                            }
                        }
                        break;
                }

                return true;
            }
        });

    }

    @Override
    protected void onSizeChanged(int w,int h,int oldw,int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWeith = (Math.min(w,h)-10)/4;
        addCards(cardWeith,cardWeith);
        startGame();
    }
    private void addCards(int cardWidth,int cardHeith){
        Card c;
        for(int y = 0;y<4;y++){
            for(int x = 0;x<4;x++){
                c = new Card(getContext());
                c.setNum(0);
                addView(c,cardWidth,cardHeith);
                cardsMap[x][y] = c;
            }
        }
    }

    public void startGame(){

        for (int x=0;x<4;x++){
            for (int y=0;y<4;y++){
                cardsMap[x][y].setNum(0);
            }
        }

        addRandomNum();
        addRandomNum();
    }


    public void swipeLeft(){
        boolean mergen = false;
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                for(int x1=x+1;x1<4;x1++){
                    if(cardsMap[x1][y].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x--;
                            mergen = true;
                        }else if(cardsMap[x][y].getNum() == cardsMap[x1][y].getNum()){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            //getScore;
                            homeActivity.addScore(cardsMap[x][y].getNum());
                            mergen = true;
                        }
                        break;
                    }
                }
            }
        }
        if(mergen){
            addRandomNum();
        }
    }

    public void swipeRight(){
        boolean mergen = false;

        for(int y=0;y<4;y++){
            for(int x=3;x>-1;x--){
                for(int x1=x-1;x1>-1;x1--){
                      if(cardsMap[x1][y].getNum()>0){
                          if(cardsMap[x][y].getNum()<=0){
                             cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                             cardsMap[x1][y].setNum(0);
                             x++;
                              mergen = true;
                          }else if(cardsMap[x][y].getNum() == cardsMap[x1][y].getNum()){
                             cardsMap[x][y].setNum(cardsMap[x1][y].getNum()*2);
                             cardsMap[x1][y].setNum(0);
                             homeActivity.addScore(cardsMap[x][y].getNum());
                             mergen = true;
                          }
                          break;
                      }
                }
            }
        }
        if(mergen){
            addRandomNum();
        }
    }

    public void swipeDown(){
        boolean mergen = false;
        for(int x=0;x<4;x++){
            for(int y=0;y<4;y++){
                for(int y1=y+1;y1<4;y1++){
                    if(cardsMap[x][y1].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;
                            mergen = true;
                        }else if(cardsMap[x][y].getNum() == cardsMap[x][y1].getNum()){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            //getScore;
                            homeActivity.addScore(cardsMap[x][y].getNum());
                            mergen = true;
                        }
                        break;
                    }
                }
            }
        }
        if(mergen){
            addRandomNum();
        }

    }

    public void swipeUp(){
        boolean mergen = false;

        for(int x=0;x<4;x++){
            for(int y=3;y>-1;y--){
                for(int y1=y-1;y1>-1;y1--){
                    if(cardsMap[x][y1].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y++;
                            mergen = true;
                        }else if(cardsMap[x][y].getNum() == cardsMap[x][y1].getNum()){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            //getScore;
                            homeActivity.addScore(cardsMap[x][y].getNum());
                            mergen = true;
                        }
                        break;
                    }
                }
            }
        }
        if(mergen){
            addRandomNum();
        }

    }

    public void addRandomNum(){
        emptyList.clear();
        for (int y=0;y<4;y++){
            for (int x=0;x<4;x++){
                if(cardsMap[x][y].getNum()<=0){
                    emptyList.add(new Point(x,y));
                }
            }
        }
        Point p = emptyList.remove((int)(Math.random()*emptyList.size()));
        cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
        checkComplete();
    }

    private void checkComplete(){
        boolean complete = true;
        targ:
        for (int x=0;x<4;x++){
            for (int y=0;y<4;y++){
                if(cardsMap[x][y].getNum()==0||
                        x>0&&cardsMap[x][y].getNum()==cardsMap[x-1][y].getNum()||
                        x<3&&cardsMap[x][y].getNum()==cardsMap[x+1][y].getNum()||
                        y>0&&cardsMap[x][y].getNum()==cardsMap[x][y-1].getNum()||
                        y<3&&cardsMap[x][y].getNum()==cardsMap[x][y+1].getNum()){
                    complete = false;
                    break;
                }
            }
        }

        SharedPreferences preferences = homeActivity.getSharedPreferences("score", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int score = preferences.getInt("highscore",0);

        if(complete){
            int newScore = homeActivity.getScore();
            if(newScore>score){
                editor.putInt("highscore", newScore);
                editor.putString("time", new SimpleDateFormat("yyyy年MM月dd日" + "hh:mm:ss").format(new Date()));
                editor.commit();
                new AlertDialog.Builder(getContext()).setTitle("You Win!").setMessage("刷新记录").setPositiveButton("重来", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGame();
                    }
                }).show();
            }else{
                new AlertDialog.Builder(getContext()).setTitle("Game Over!").setMessage("游戏结束").setPositiveButton("重来", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGame();
                    }
                }).show();
            }

        }

    }

   private List<Point> emptyList = new ArrayList<Point>();
    private Card[][] cardsMap = new Card[4][4];
}
