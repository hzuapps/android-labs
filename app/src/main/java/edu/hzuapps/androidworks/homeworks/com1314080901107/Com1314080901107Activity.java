package com.example.chensongkui.Dict


import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Com1314080901107Activity extends AppCompatActivity  {
    MyDatabaseHelper dbHelper;
    Button insert = null;
    Button search = null;
    Button delete = null;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // 创建MyDatabaseHelper对象，指定数据库版本为1，此处使用相对路径即可
        // 数据库文件自动会保存在程序的数据文件夹的databases目录下
        dbHelper = new MyDatabaseHelper(this, "myDict.db3", 1);
        insert = (Button) findViewById(R.id.insert);
        search = (Button) findViewById(R.id.search);
        delete = (Button) findViewById(R.id.delete);
        insert.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {
                // 获取用户输入
                String word = ((EditText) findViewById(R.id.word))
                        .getText().toString();
                String detail = ((EditText) findViewById(R.id.detail))
                        .getText().toString();
                // 插入生词记录
                insertData(dbHelper.getReadableDatabase(), word, detail);
                // 显示提示信息
                Toast.makeText(MainActivity.this, "添加生词成功！"
                        , Toast.LENGTH_LONG).show();
            }
        });
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {
                // 获取用户输入
                String key = ((EditText) findViewById(R.id.key)).getText()
                        .toString();
                // 执行查询
                Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                        "select * from dict where word like ? or detail like ?",
                        new String[]{"%" + key + "%", "%" + key + "%"});
                // 创建一个Bundle对象
                Bundle data = new Bundle();
                data.putSerializable("data", converCursorToList(cursor));
                // 创建一个Intent
                Intent intent = new Intent(MainActivity.this
                        , ResultActivity.class);
                intent.putExtras(data);
                // 启动Activity
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(dbHelper.getReadableDatabase());
                Toast.makeText(MainActivity.this, "删除生词成功！"
                        , Toast.LENGTH_LONG).show();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }


    protected ArrayList<Map<String, String>>
    converCursorToList(Cursor cursor) {
        ArrayList<Map<String, String>> result =
                new ArrayList<Map<String, String>>();
        // 遍历Cursor结果集
        while (cursor.moveToNext()) {
            // 将结果集中的数据存入ArrayList中
            Map<String, String> map = new HashMap<>();
            // 取出查询记录中第2列、第3列的值
            map.put("word", cursor.getString(1));
            map.put("detail", cursor.getString(2));
            result.add(map);
        }
        return result;
    }

    private void insertData(SQLiteDatabase db, String word
            , String detail) {
        // 执行插入语句
        db.execSQL("insert into dict values(null , ? , ?)"
                , new String[]{word, detail});
    }

    private void deleteData(SQLiteDatabase db) {
        db.execSQL("delete from dict");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 退出程序时关闭MyDatabaseHelper里的SQLiteDatabase
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

}

