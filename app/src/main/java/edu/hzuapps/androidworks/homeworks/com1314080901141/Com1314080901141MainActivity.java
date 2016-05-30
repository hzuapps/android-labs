package com.example.yys.com1314080901141;

import android.support.v7.app.AppCompatActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.yys.com1314080901141.util.OnRepay;
import com.example.yys.com1314080901141.util.OnRepayCallback;
import com.example.yys.com1314080901141.util.SystemAlbum;
import com.example.yys.com1314080901141.util.XiaoMing;

import java.util.ArrayList;
import java.util.List;


public class Com1314080901141MainActivity extends AppCompatActivity implements View.OnClickListener,OnRepay {

    private RadioGroup radioGroup;
    private Button btn_one;
    private Button btn_two;
    private Button btn_three;
    private Button btn_four;

    private ContentFragment mWeixin;
    private FriendFragment mFriend;
    private FindFragment mFind;
    private SettingFragment mSetting;

    XiaoMing xiaoMing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901141_main);
        bindViews();
        // 设置默认的Fragment
        setDefaultFragment();

//        SystemAlbum systemAlbum = SystemAlbum.getInstance();
//        Log.d("PHOTO","LIST:"+ systemAlbum.recentPhoto(this));
        xiaoMing = new XiaoMing(this);

    }
    @Override
    public boolean onRepay() {// 小明还钱的时候,小红接收, 返回true:收到了钱, 返回false:没收收到
        Log.e("小红", "小红没有收到小明的还钱,可能被快递员私吞了...");
        return true;
    }
    private void bindFragment(){

    }

    private void setDefaultFragment()
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mWeixin = new ContentFragment();
//        mWeixin = new WeixinFragment();
        transaction.replace(R.id.id_content, mWeixin);
        transaction.commit();
    }


    private void bindViews() {
        radioGroup = (RadioGroup) findViewById(R.id.tab_menu);
        btn_one = (Button) findViewById(R.id.rbChat);
        btn_two = (Button) findViewById(R.id.rbAddress);
        btn_three = (Button) findViewById(R.id.rbFind);
        btn_four = (Button) findViewById(R.id.rbMe);

        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_four.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

//        xiaoMing.JieQian();

//        xiaoMing.JieQianCallback(new OnRepayCallback() {
//        @Override
//        boolean onRepayCallBack() {
//            Log.e("小红", "小红收到小明的还钱10元...");
//            return true;
//        }
//        });

        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();

        switch (v.getId()) {

            case R.id.rbChat:

                if (mWeixin == null)
                {
                    mWeixin = new ContentFragment();
                }
                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.id_content, mWeixin);
                Toast.makeText(getApplicationContext(), "rbChat",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.rbAddress:

                if (mFriend == null)
                {
                    mFriend = new FriendFragment();
                }
                transaction.replace(R.id.id_content, mFriend);
                Toast.makeText(getApplicationContext(), "rbAdress",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.rbFind:
                if (mFind == null)
                {
                    mFind = new FindFragment();
                }

                transaction.replace(R.id.id_content, mFind);

                Toast.makeText(getApplicationContext(), "rbFind",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.rbMe:
                if (mSetting == null)
                {
                    mSetting = new SettingFragment();
                }

                transaction.replace(R.id.id_content, mSetting);

                Toast.makeText(getApplicationContext(), "rbMe",
                        Toast.LENGTH_SHORT).show();
                break;

        }
        // 事务提交
        transaction.commit();
    }
}
