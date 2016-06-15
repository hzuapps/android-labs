package com.example.lwj_pc.my_classwork;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LWJ-PC on 2016/5/10.
 */
public class Com1314080901221Activity_Insert extends Activity {
    private EditText title,content;
    private Button tijiao;
    private Com1314080901221_MyDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901221_insert);

        dbHelper=new Com1314080901221_MyDBHelper(this,"Myapp.db",null,1);    //获取数据库实例

        title=(EditText)findViewById(R.id.insert_title);
        content=(EditText)findViewById(R.id.insert_context);
        tijiao=(Button)findViewById(R.id.insert_tijiao);
        tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message_title = title.getText().toString();
                String message_content = content.getText().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd;HH:mm:ss");
                Date date=new Date();
                String datetime = dateFormat.format(date);

                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("title",message_title);
                values.put("content",message_content);
                values.put("time",datetime);
                long temp=db.insert("journey",null,values);
                Log.d("插叙序号：","="+temp);
                if (temp>0) {
                    Toast.makeText(Com1314080901221Activity_Insert.this, "添加完成！", Toast.LENGTH_SHORT).show();
                    Com1314080901221Activity_Insert.this.finish();
                }else if(temp==-1){
                    Toast.makeText(Com1314080901221Activity_Insert.this,"标题重复，请修改标题！",Toast.LENGTH_LONG).show();
                }else{
                    finish();
                }

            }
        });

    }
}

