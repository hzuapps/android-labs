package edu.hzuapps.androidworks.homeworks.com1314080901123;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Com1314080901123_QueryActivity extends AppCompatActivity {
    private ListView noteListView;
    private Com1314080901123_ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_com1314080901123);

        initView();

    }
    private void initView() {

        noteListView = (ListView) findViewById(R.id.id_listview);

        //创建数据库
        Com1314080901123_DatabaseAdapter dbHelper  = new Com1314080901123_DatabaseAdapter(this, "DB", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //获取数据库中的记录
        List<String[]> strList = new ArrayList<String[]>();
        Cursor cursor = db.rawQuery("select*from record", null);

        if(cursor.moveToFirst())
        {

            do{
                String[] strRecord = new String[3];

                strRecord[0] = cursor.getString(cursor.getColumnIndex("buystyle"));
                strRecord[1] = cursor.getString(cursor.getColumnIndex("money"));
                strRecord[2] = cursor.getString(cursor.getColumnIndex("buydate"));

                strList.add(strRecord);

            }while(cursor.moveToNext());
            cursor.close();
        }

        mAdapter = new Com1314080901123_ListAdapter(this, strList);

        noteListView.setAdapter(mAdapter);


    }
}
