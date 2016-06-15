package com.example.lwj_pc.my_classwork;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by LWJ-PC on 2016/4/19.
 */
public class Com1314080901221Activity extends ActionBarActivity {
    private String[] strings={"list 1!!!","list 2!!!"};
    private ListView listView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Com1314080901221_MyDBHelper dbHelper;
    private ArrayAdapter<String>  adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901221_main);
        //设置界面
        toolbar=(Toolbar)findViewById(R.id.mytoolbar);
        toolbar.setTitle("行程提醒");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        drawerLayout=(DrawerLayout)findViewById(R.id.main_drawer);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        dbHelper=new Com1314080901221_MyDBHelper(this,"Myapp.db",null,1);
        adapter=new ArrayAdapter<String>(Com1314080901221Activity.this,android.R.layout.simple_list_item_1);
        listView=(ListView)findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String temp=adapter.getItem(position);
                Log.d("点击位置是：",position+"---"+temp);
                Intent intent=new Intent(Com1314080901221Activity.this,Com1314080901221Activity_showitem.class);
                intent.putExtra("title",temp);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.addAll(loadlist());
        listView.setAdapter(adapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.clear();
    }

    private String[] loadlist(){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select title from journey",null);
        String[] titles=new String[cursor.getCount()];
        int i=0;
        if(cursor.moveToFirst()){
            String title;
            do{
                title=cursor.getString(cursor.getColumnIndex("title"));
                titles[i++]=title;
                Log.d("标题",title);
            }while(cursor.moveToNext());
        }else{
            Log.d("状态：","数据库为空");
        }
        Log.d("状态","完成！");
        cursor.close();
        return titles;
    }
    //OnMenuItemClickListener 的监听者
    private Toolbar.OnMenuItemClickListener onMenuItemClick=new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            String msg="";
            switch (item.getItemId()){
                case R.id.action_insert:
                    Intent insertintent=new Intent(Com1314080901221Activity.this,Com1314080901221Activity_Insert.class);
                    startActivity(insertintent);
                    break;
                case R.id.action_more:
                    msg+="Click More!";
                    break;
                case R.id.action_settings:
                    msg+="Click Setting!";
                    break;
                default:
                    break;
            }
            if(!msg.equals("")){
                Toast.makeText(Com1314080901221Activity.this,msg,Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };
    //创建Toolbar抽屉按钮的点击效果
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
    //创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.com1314080901221_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
