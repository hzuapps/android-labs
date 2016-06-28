package com.example.administrator.fingergame;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/6/18.
 */
public class GameData extends Activity {
    String win;
    String fail;
    String ping;
    String all;
    String winlu;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_layout);


                DBhelper dbHelper = new DBhelper(GameData.this);
                SQLiteDatabase readdb = dbHelper.getReadableDatabase();
                Cursor c = readdb.query("guess", null, null, null, null, null, null);
                while (c.moveToNext()) {
                    all = c.getString(c.getColumnIndex("all_data"));
                    win = c.getString(c.getColumnIndex("win_data"));
                    fail = c.getString(c.getColumnIndex("fail_data"));
                    ping = c.getString(c.getColumnIndex("ping_data"));

//            System.out.println("摇一摇："+String.format("win=%s,fail=%s,ping=%s",win,fail,ping));
                    System.out.println("Game_data all:" + all + "win：" + win);
                }
                readdb.close();

            }

//        tv.setText("胜率："+ping+"负："+fail);



    }

