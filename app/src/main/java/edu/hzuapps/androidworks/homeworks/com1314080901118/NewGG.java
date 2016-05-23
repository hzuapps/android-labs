package edu.hzuapps.androidworks.homeworks.com1314080901118;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class NewGG extends AppCompatActivity {
    private static int ROW_NUMBER = -1;
    private static int COL_NUMBER = -1;
    private Context context;
    private Drawable backCard;
    private int [] [] cards;
    private List<Drawable> actor;
    private Card cardOne;
    private Card cardTwo;
    private ButtonListener buttonListener;
    private int numberOfCards;
    private double counter=0;
    private Timer time = new Timer();


    private static Object locker = new Object();

    private TableLayout Table;
    private CardsUpdater handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Runnable timerTick = new Runnable () {
            @Override
            public void run () {
                TextView time_text = (TextView)findViewById(R.id.time);

                counter++;
                time_text.setText("Time:" + String.format("%.2f", counter) + "s");
                if (counter >= 60){
                    time_text.setText("Time:" + String.format("%.2f", counter/60) + "min");
                    if (counter >= 3600){
                        time_text.setText("Time:" + String.format("%.2f", counter/3600) + "hours");
                    }
                }

            }
        };


        time.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(timerTick);
            }
        }, 0, 1000);

        Bundle extras = getIntent().getExtras();
        final int level = 1+extras.getInt("level");
        int r=0;
        int c=0;

        if(level == 1){
            r=3;
            c=4;
        }
        if(level == 2){
            r=4;
            c=5;
        }
        if(level == 3){
            r=4;
            c=6;
        }
        if(level == 4){
            r=5;
            c=6;
        }
        if(level == 5){
            r=6;
            c=6;
        }



        handler = new CardsUpdater();
        loadanimalPics();
        setContentView(R.layout.activity_new_game18);

        backCard =  getResources().getDrawable(R.drawable.mcmini);

        buttonListener = new ButtonListener();

        Table = (TableLayout)findViewById(R.id.TableLayout03);


        context  = Table.getContext();

        ROW_NUMBER = r;
        COL_NUMBER = c;

        numberOfCards = r*c;
        Log.d("initially","initial"+numberOfCards);

        cards = new int [COL_NUMBER] [ROW_NUMBER];


        Table.removeView(findViewById(R.id.TableRow01));
        Table.removeView(findViewById(R.id.TableRow02));

        TableRow tr = ((TableRow)findViewById(R.id.TableRow03));
        tr.removeAllViews();

        Table = new TableLayout(context);
        tr.addView(Table);

        for (int y = 0; y < ROW_NUMBER; y++) {
            Table.addView(rowCreater(y));
        }

        cardOne=null;
        loadCards();
    }
    private void loadanimalPics() {
        actor = new ArrayList<Drawable>();

        actor.add(getResources().getDrawable(R.drawable.card1));
        actor.add(getResources().getDrawable(R.drawable.card2));
        actor.add(getResources().getDrawable(R.drawable.card3));
        actor.add(getResources().getDrawable(R.drawable.card4));
        actor.add(getResources().getDrawable(R.drawable.card5));
        actor.add(getResources().getDrawable(R.drawable.card6));
        actor.add(getResources().getDrawable(R.drawable.card7));
        actor.add(getResources().getDrawable(R.drawable.card8));
        actor.add(getResources().getDrawable(R.drawable.card9));
        actor.add(getResources().getDrawable(R.drawable.card10));
        actor.add(getResources().getDrawable(R.drawable.card11));
        actor.add(getResources().getDrawable(R.drawable.card12));
        actor.add(getResources().getDrawable(R.drawable.card13));
        actor.add(getResources().getDrawable(R.drawable.card14));
        actor.add(getResources().getDrawable(R.drawable.card15));
        actor.add(getResources().getDrawable(R.drawable.card16));
        actor.add(getResources().getDrawable(R.drawable.card17));
        actor.add(getResources().getDrawable(R.drawable.card18));
        actor.add(getResources().getDrawable(R.drawable.card19));
        actor.add(getResources().getDrawable(R.drawable.card20));
        actor.add(getResources().getDrawable(R.drawable.card21));

    }

    private void loadCards(){
        int size = ROW_NUMBER*COL_NUMBER;

        ArrayList<Integer> list = new ArrayList<Integer>();

        for(int i=0;i<size;i++){
            list.add(new Integer(i));
        }


        Random r = new Random();

        for(int i=size-1;i>=0;i--){
            int t=0;

            if(i>0){
                t = r.nextInt(i);
            }

            t=list.remove(t).intValue();
            cards[i%COL_NUMBER][i/COL_NUMBER]=t%(size/2);
        }

    }

    private TableRow rowCreater(int y){
        TableRow row = new TableRow(context);
        row.setHorizontalGravity(Gravity.CENTER);

        for (int x = 0; x < COL_NUMBER; x++) {
            row.addView(imageCreaterButton(x,y));
        }
        return row;
    }

    private View imageCreaterButton(int x, int y){
        Button button = new Button(context);
        button.setBackground(backCard);
        button.setId(100*x+y);
        button.setOnClickListener(buttonListener);
        return button;
    }

    class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            synchronized (locker) {
                if(cardOne!=null && cardTwo != null){
                    return;
                }
                int id = v.getId();
                int x = id/100;
                int y = id%100;
                turnCard((Button)v,x,y);
            }

        }

        private void turnCard(Button button,int x, int y) {
            button.setBackground(actor.get(cards[x][y]));

            if(cardOne==null){
                cardOne = new Card(button,x,y);
            }
            else{

                if(cardOne.x == x && cardOne.y == y){

                    return; //the user pressed the same card

                }

                cardTwo = new Card(button,x,y);


                TimerTask timertask = new TimerTask() {

                    @Override
                    public void run() {
                        synchronized (locker) {
                            handler.sendEmptyMessage(0);
                        }
                    }
                };

                Timer t = new Timer(false);
                t.schedule(timertask, 700);
            }


        }

    }

    class CardsUpdater extends Handler {

        @Override
        public void handleMessage(Message msg) {
            synchronized (locker) {
                checkCards();
            }
        }
        public void checkCards(){
            if(cards[cardTwo.x][cardTwo.y] == cards[cardOne.x][cardOne.y]){
                cardOne.button.setVisibility(View.INVISIBLE);
                cardTwo.button.setVisibility(View.INVISIBLE);
                numberOfCards = numberOfCards - 2;
                Log.d("left","left"+numberOfCards);
                if(numberOfCards == 0){
                    time.cancel();
                    AlertDialog youreDone = new AlertDialog.Builder(NewGG.this).create();
                    youreDone.setTitle("任务完成!");
                    youreDone.setMessage("本关的完成时间是： " + String.format("%.2f", counter)+"s");
                    youreDone.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent2 = new Intent(NewGG.this,Com1314080901118Activity.class);
//                            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            NewGG.this.finish();
                            startActivity(intent2);
                        }
                    }, 3200);


                }
            }
            else {
                cardTwo.button.setBackground(backCard);
                cardOne.button.setBackground(backCard);
                Toast.makeText(getApplicationContext(), "哟！图片不对哦，接着翻！", Toast.LENGTH_SHORT).show();
            }

            cardOne=null;
            cardTwo=null;
        }
    }
}
