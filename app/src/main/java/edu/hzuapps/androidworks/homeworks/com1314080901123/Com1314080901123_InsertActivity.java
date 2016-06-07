package edu.hzuapps.androidworks.homeworks.com1314080901123;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Com1314080901123_InsertActivity extends AppCompatActivity implements OnClickListener {
    private EditText buyStyleEdit,moneyEdit;
    private TextView buyDateEdit;
    private Button toSave,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_com1314080901123);

        initView();

        initEvent();

    }


    /**
     * 初始化控件
     */
    private void initView() {


        buyStyleEdit = (EditText) findViewById(R.id.buyStyle);
        moneyEdit = (EditText) findViewById(R.id.money);
        buyDateEdit = (TextView) findViewById(R.id.buyDate);


        toSave = (Button) findViewById(R.id.toSave);
        cancel = (Button) findViewById(R.id.cancel);

        toSave.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }
    /**
     * 初始化事件
     */
    private void initEvent() {
        //设置日期
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String t =format.format(new Date());
        buyDateEdit.setText(t);

    }

    /**
     * 点击事件
     * @param v
     */
    public void onClick(View v) {

        switch(v.getId())
        {
            //保存按钮的逻辑
            case R.id.toSave:
                if(!buyStyleEdit.getText().toString().equals(""))
                {
                    //保存笔记到数据库
//                    DBUtil.addNewRecord(buyStyleEdit.getText().toString(),moneyEdit.getText().toString(),
//                            buyDateEdit.getText().toString());
                    ContentValues values = new ContentValues();
                    values.put("buystyle", buyStyleEdit.getText().toString());
                    values.put("money", moneyEdit.getText().toString());
                    values.put("buydate",buyDateEdit.getText().toString());
                    Com1314080901123_DatabaseAdapter dbHelper = new Com1314080901123_DatabaseAdapter(this,"DB", null, 1);
                    //获取已经存在的数据库
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.insert("record", null, values);

                    values.clear();

                    Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Com1314080901123_InsertActivity.this,Com1314080901123_Activity.class);
                    startActivity(intent);
                }
                break;
            //取消按钮的逻辑
            case R.id.cancel:

                Intent intent = new Intent(Com1314080901123_InsertActivity.this,Com1314080901123_Activity.class);
                startActivity(intent);
                break;

                }
        }



    }





