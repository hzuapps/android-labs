package com.example.intelligent_restranant_boss;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.intelligent_restranant_boss.toDo.ToDo_Activity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

// 软件框架界面
public class FrameActivity extends ActivityGroup {

    private LinearLayout ll_home_index, ll_video_index, ll_todolist_index,
            ll_my_index, ll_more_index;
    private ImageView iv_index_home, iv_index_video, iv_index_todolist,
            iv_index_my, iv_index_more;
    private TextView tv_index_home, tv_index_video, tv_index_todolist,
            tv_index_my, tv_index_more;
    private List<View> mList = new ArrayList<View>();// 相当于数据源
    private View view = null;
    private View view1 = null;
    private View view2 = null;
    private View view3 = null;
    private View view4 = null;
    public static android.support.v4.view.ViewPager mViewPager;
    private PagerAdapter pagerAdapter = null;// 数据源和viewpager之间的桥梁

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_frame);
        initView();
    }

    // 初始化控件
    private void initView() {
        //初始化bmobSDK
        Bmob.initialize(this,"95aaa30a3f78b409438bd48b1e89f011");
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        // 查找以linearlayout为按钮作用的控件
        ll_home_index = (LinearLayout) findViewById(R.id.ll_home_index);
        ll_video_index = (LinearLayout) findViewById(R.id.ll_video_index);
        ll_todolist_index = (LinearLayout) findViewById(R.id.ll_todolist_index);
        ll_my_index = (LinearLayout) findViewById(R.id.ll_my_index);
        ll_more_index = (LinearLayout) findViewById(R.id.ll_more_index);
        // 查找linearlayout中的imageview
        iv_index_home = (ImageView) findViewById(R.id.iv_index_home);
        iv_index_video = (ImageView) findViewById(R.id.iv_index_video);
        iv_index_todolist = (ImageView) findViewById(R.id.iv_index_todolist);
        iv_index_my = (ImageView) findViewById(R.id.iv_index_my);
        iv_index_more = (ImageView) findViewById(R.id.iv_index_more);
        // 查找linearlayout中的textview
        tv_index_home = (TextView) findViewById(R.id.tv_index_home);
        tv_index_video = (TextView) findViewById(R.id.tv_index_video);
        tv_index_todolist = (TextView) findViewById(R.id.tv_index_todolist);
        tv_index_my = (TextView) findViewById(R.id.tv_index_my);
        tv_index_more = (TextView) findViewById(R.id.tv_index_more);
        createView();

        // 写一个内部类pageradapter
        pagerAdapter = new PagerAdapter() {
            // 判断再次添加的view和之前的view 是否是同一个view
            // arg0新添加的view，arg1之前的
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            // 返回数据源长度
            public int getCount() {
                return mList.size();
            }

            // 销毁被滑动走的view
            // ViewGroup 代表了我们的viewpager 相当于activitygroup当中的view容器， 添加之前先移除。
            // position 第几条数据 Object 被移出的view
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // 移除view
                container.removeView(mList.get(position));
            }

            // instantiateItem viewpager要现实的view ViewGroup viewpager position
            // 第几条数据
            public Object instantiateItem(ViewGroup container, int position) {
                // 获取view添加到容器当中，并返回
                View v = mList.get(position);   //直接使用外部类的mlist作为data
                container.addView(v);
                return v;
            }
        };
        // 设置我们的adapter
        mViewPager.setAdapter(pagerAdapter);

        MyBtnOnclick listener = new MyBtnOnclick();
        ll_home_index.setOnClickListener(listener);
        ll_video_index.setOnClickListener(listener);
        ll_todolist_index.setOnClickListener(listener);
        ll_my_index.setOnClickListener(listener);
        ll_more_index.setOnClickListener(listener);

        // 设置viewpager界面切换监听,监听viewpager切换第几个界面以及滑动的
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            // arg0 获取 viewpager里面的界面切换到第几个的
            public void onPageSelected(int arg0) {
                // 先清除按钮样式
                initBottomBtn();
                // 按照对应的view的tag来判断到底切换到哪个界面。
                // 更改对应的button状态
                int flag = (Integer) mList.get((arg0)).getTag();
                if (flag == 0) {
                    iv_index_home.setImageResource(R.drawable.home_p);
                    tv_index_home.setTextColor(Color.parseColor("#FF8C00"));
                } else if (flag == 1) {
                    iv_index_video
                            .setImageResource(R.drawable.index_video_pressed);
                    tv_index_video.setTextColor(Color.parseColor("#FF8C00"));
                } else if (flag == 2) {
                    iv_index_todolist
                            .setImageResource(R.drawable.todolist_pressed);
                    tv_index_todolist.setTextColor(Color.parseColor("#FF8C00"));
                } else if (flag == 3) {
                    iv_index_my.setImageResource(R.drawable.my_pressed);
                    tv_index_my.setTextColor(Color.parseColor("#FF8C00"));
                } else if (flag == 4) {
                    iv_index_more.setImageResource(R.drawable.more_pressed);
                    tv_index_more.setTextColor(Color.parseColor("#FF8C00"));
                }
            }

            // 监听页面滑动的 arg0 表示当前滑动的view arg1 表示滑动的百分比 arg2 表示滑动的距离
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            // 监听滑动状态 arg0 表示我们的滑动状态 0:默认状态 1:滑动状态 2:滑动停止
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }

    // 把viewpager里面要显示的view实例化出来，并且把相关的view添加到一个list当中
    private void createView() {
        view = this
                .getLocalActivityManager()
                .startActivity("home",
                        new Intent(FrameActivity.this, HomeActivity.class))
                .getDecorView();
        // 用来更改下面button的样式的标志
        view.setTag(0);
        mList.add(view);
        view1 = FrameActivity.this
                .getLocalActivityManager()
                .startActivity("video",
                        new Intent(FrameActivity.this, VideoActivity.class))
                .getDecorView();
        view1.setTag(1);
        mList.add(view1);
        view2 = FrameActivity.this
                .getLocalActivityManager()
                .startActivity(
                        "todolist",
                        new Intent(FrameActivity.this,
                                ToDo_Activity.class)).getDecorView();
        view2.setTag(2);
        mList.add(view2);
        view3 = FrameActivity.this
                .getLocalActivityManager()
                .startActivity("my",
                        new Intent(FrameActivity.this, MyActivity.class))
                .getDecorView();
        view3.setTag(3);
        mList.add(view3);
        view4 = FrameActivity.this
                .getLocalActivityManager()
                .startActivity("more",
                        new Intent(FrameActivity.this, MoreActivity.class))
                .getDecorView();
        view4.setTag(4);
        mList.add(view4);
    }

    /**
     * 用linearlayout作为按钮的监听事件
     */
    private class MyBtnOnclick implements View.OnClickListener {
        public void onClick(View arg0) {
            int mBtnid = arg0.getId();
            switch (mBtnid) {
                case R.id.ll_home_index:
                    // //设置我们的viewpager跳转那个界面0这个参数和我们的list相关,相当于list里面的下标
                    mViewPager.setCurrentItem(0);   //转换"子"Activity
                    initBottomBtn();
                    iv_index_home.setImageResource(R.drawable.home_p);
                    tv_index_home.setTextColor(Color.parseColor("#FF8C00"));
                    break;
                case R.id.ll_video_index:
                    mViewPager.setCurrentItem(1);
                    initBottomBtn();
                    iv_index_video.setImageResource(R.drawable.index_video_pressed);
                    tv_index_video.setTextColor(Color.parseColor("#FF8C00"));
                    break;
                case R.id.ll_todolist_index:
                    mViewPager.setCurrentItem(2);
                    initBottomBtn();
                    iv_index_todolist.setImageResource(R.drawable.todolist_pressed);
                    tv_index_todolist.setTextColor(Color.parseColor("#FF8C00"));
                    break;
                case R.id.ll_my_index:
                    mViewPager.setCurrentItem(3);
                    initBottomBtn();
                    iv_index_my.setImageResource(R.drawable.my_pressed);
                    tv_index_my.setTextColor(Color.parseColor("#FF8C00"));
                    break;
                case R.id.ll_more_index:
                    mViewPager.setCurrentItem(4);
                    initBottomBtn();
                    iv_index_more.setImageResource(R.drawable.more_pressed);
                    tv_index_more.setTextColor(Color.parseColor("#FF8C00"));
                    break;
            }
        }
    }

    /**
     * 初始化控件的颜色
     */
    private void initBottomBtn() {
        iv_index_home.setImageResource(R.drawable.home_n);
        iv_index_video.setImageResource(R.drawable.search_button_video);
        iv_index_todolist.setImageResource(R.drawable.search_button_todolist);
        iv_index_my.setImageResource(R.drawable.search_bottom_my);
        iv_index_more.setImageResource(R.drawable.search_bottom_more);
        tv_index_home.setTextColor(ContextCompat.getColor(this,
                R.color.search_bottom_textcolor));
        tv_index_video.setTextColor(ContextCompat.getColor(this,
                R.color.search_bottom_textcolor));
        tv_index_todolist.setTextColor(ContextCompat.getColor(this,
                R.color.search_bottom_textcolor));
        tv_index_my.setTextColor(ContextCompat.getColor(this,
                R.color.search_bottom_textcolor));
        tv_index_more.setTextColor(ContextCompat.getColor(this,
                R.color.search_bottom_textcolor));
    }

    // 返回按钮的监听，用来询问用户是否退出程序
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                Builder builder = new Builder(FrameActivity.this);
                builder.setTitle("提示");
                builder.setMessage("你确定要退出吗？");
                builder.setIcon(R.drawable.boss_lite);
                DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (arg1 == DialogInterface.BUTTON_POSITIVE) {
                            arg0.cancel();
                        } else if (arg1 == DialogInterface.BUTTON_NEGATIVE) {
                            FrameActivity.this.finish();
                        }
                    }
                };
                builder.setPositiveButton("取消", dialog);
                builder.setNegativeButton("确定", dialog);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        }
        return false;
    }

}
