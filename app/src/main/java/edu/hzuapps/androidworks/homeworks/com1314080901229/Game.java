package com.example.administrator.fingergame;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Administrator on 2016/6/18.
 */
public class Game extends Activity {
    public int dn=0;
    public Random r;
    Button bt1,bt2,bt3;
    ImageView imageView;
    TextView tv;
    public static int win;
    public static int fail;
    public static int ping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        bt1=(Button)findViewById(R.id.bt1);
        bt2=(Button)findViewById(R.id.bt2);
        bt3=(Button)findViewById(R.id.bt3);
        imageView=(ImageView)findViewById(R.id.im1);
        tv=(TextView)findViewById(R.id.tv1);
        bt1.setOnClickListener(new ButtonListener());
        bt2.setOnClickListener(new ButtonListener());
        bt3.setOnClickListener(new ButtonListener());




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int all=win+fail+ping;
        DBhelper dBhelper=new DBhelper(Game.this);
        SQLiteDatabase db=dBhelper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("all_data","1");
        cv.put("win_data","2");
        cv.put("fail_data", "3");
        cv.put("ping_data", "4");
        System.out.println(cv.toString());
        long id = db.insert("guess", null, cv);
        System.out.println("longid:"+id);


    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.bt1://0
                    r= new Random();
                    dn=r.nextInt(3);    //0为石头。1为剪刀，2为布
                    switch (dn){
                        case 0:
                            imageView.setBackgroundResource(R.drawable.shitou);
                            tv.setText("平局");
                            ping++;
                            break;
                        case 1:
                            imageView.setBackgroundResource(R.drawable.jiandao);
                            tv.setText("你赢了");
                            win++;
                            break;
                        case 2:
                            imageView.setBackgroundResource(R.drawable.bu);
                            tv.setText("你输了");
                            fail++;
                            break;
                    }



                    break;
                case R.id.bt2://1
                    r= new Random();
                    dn=r.nextInt(3);    //0为石头。1为剪刀，2为布
                    switch (dn) {
                        case 0:
                            imageView.setBackgroundResource(R.drawable.shitou);
                            tv.setText("你输了");
                            fail++;
                            break;
                        case 1:
                            imageView.setBackgroundResource(R.drawable.jiandao);
                            tv.setText("平局");
                            ping++;
                            break;
                        case 2:
                            imageView.setBackgroundResource(R.drawable.bu);
                            tv.setText("你赢了");
                            win++;
                            break;
                    }
                    break;
                case R.id.bt3://2
                    r= new Random();
                    dn=r.nextInt(3);    //0为石头。1为剪刀，2为布
                    switch (dn) {
                        case 0:
                            imageView.setBackgroundResource(R.drawable.shitou);
                            tv.setText("你赢了");
                            win++;
                            break;
                        case 1:
                            imageView.setBackgroundResource(R.drawable.jiandao);
                            tv.setText("你输了");
                            fail++;
                            break;
                        case 2:
                            imageView.setBackgroundResource(R.drawable.bu);
                            tv.setText("平局");
                            ping++;
                            break;
                    }
                    break;
            }


        }
    }
}
