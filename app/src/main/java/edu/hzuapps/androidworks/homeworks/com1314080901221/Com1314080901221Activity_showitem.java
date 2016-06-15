package com.example.lwj_pc.my_classwork;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by LWJ-PC on 2016/6/8.
 */
public class Com1314080901221Activity_showitem extends Activity {
    private Com1314080901221_MyDBHelper dbHelper;
    private TextView titleText,timeText,contentText;
    private Button deleteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901221_list_item);
        titleText=(TextView)findViewById(R.id.list_item_title);
        timeText=(TextView)findViewById(R.id.list_item_time);
        contentText=(TextView)findViewById(R.id.list_item_content);
        deleteButton=(Button)findViewById(R.id.list_item_delete);

        Intent receiveintent=getIntent();
        final String title=receiveintent.getStringExtra("title");
        dbHelper=new Com1314080901221_MyDBHelper(this,"Myapp.db",null,1);
        Toast.makeText(Com1314080901221Activity_showitem.this,title,Toast.LENGTH_SHORT).show();
        String [] item=loaditem(title);
        titleText.setText(item[0]);
        contentText.setText(item[1]);
        timeText.setText(item[2]);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(Com1314080901221Activity_showitem.this);
                dialog.setTitle("提示");
                dialog.setMessage("是否确认删除");
                dialog.setCancelable(true);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db=dbHelper.getWritableDatabase();
                        int i=db.delete("journey","title = ?",new String[] { titleText.getText().toString() });
                        Log.d("删除序号等于======",":"+i);
                        Toast.makeText(Com1314080901221Activity_showitem.this,"delete successly",Toast.LENGTH_SHORT);
                        finish();
                    }
                });
                dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();

            }
        });
    }

    private String[] loaditem(String title){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from journey where title='"+title+"'",null);
        String[] strings=new String[3];
        if (cursor.moveToNext()) {
            strings[0]=cursor.getString(0);
            strings[1]=cursor.getString(1);
            strings[2]=cursor.getString(2);
        }
        Log.d("标题：","="+strings[0]);
        Log.d("内容：","="+strings[1]);
        Log.d("时间：","="+strings[2]);
        return strings;
    }
}
