package edu.hzuapps.androidworks.homeworks.net1314080903119;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.hzuapps.androidworks.homeworks.net1314080903119.until.SlidingMenu;

public class Net1314080903119MainActivity extends AppCompatActivity {

    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.Title)
    RelativeLayout Title;
    @Bind(R.id.id_viewpager)
    ViewPager idViewpager;
    @Bind(R.id.id_tab_new_img)
    ImageView idTabNewImg;
    @Bind(R.id.tv_new)
    TextView tvNew;
    @Bind(R.id.id_tab_new)
    LinearLayout idTabNew;
    @Bind(R.id.id_tab_address_img)
    ImageView idTabAddressImg;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.id_tab_address)
    LinearLayout idTabAddress;
    @Bind(R.id.id_tab_notice_img)
    ImageView idTabNoticeImg;
    @Bind(R.id.tv_notice)
    TextView tvNotice;
    @Bind(R.id.id_tab_notice)
    LinearLayout idTabNotice;
    @Bind(R.id.id_tab_me_img)
    ImageView idTabMeImg;
    @Bind(R.id.tv_me)
    TextView tvMe;
    @Bind(R.id.id_tab_me)
    LinearLayout idTabMe;
    @Bind(R.id.bottom)
    LinearLayout bottom;
    @Bind(R.id.id_menu)
    SlidingMenu idMenu;
    public static SlidingMenu mLeftMenu;
    public static boolean isOpen;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903119);
        ButterKnife.bind(this);
        initView();
        setSelect(0);
    }

    private void initView() {

        mFragments = new ArrayList<Fragment>();
        Fragment newFragment = new NewFragment();
        Fragment noticeFragment = new NoticeFragment();
        Fragment addressFragment = new AddressFragment();
        Fragment meFragment = new MeFragment();
        mFragments.add(newFragment);
        mFragments.add(noticeFragment);
        mFragments.add(addressFragment);
        mFragments.add(meFragment);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mFragments.get(arg0);
            }
        };
        idViewpager.setAdapter(mAdapter);
        //锟斤拷锟斤拷页锟斤拷浠拷锟斤拷锟斤拷鸩焦锟斤拷锟斤拷锟绞憋拷锟斤拷锟斤拷锟揭伙拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
        idViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                int currentItem = idViewpager.getCurrentItem();
                setTab(currentItem);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void setSelect(int i) {
        setTab(i);
        idViewpager.setCurrentItem(i);
    }

    private void setTab(int i) {
        resetImgs();
        // 锟斤拷锟斤拷图片为锟斤拷色
        // 锟叫伙拷锟斤拷锟斤拷锟斤拷锟斤拷
        switch (i) {
            case 0:
                idTabNewImg.setImageResource(R.drawable.net1314080903119messages_pressed);
                tvNew.setTextColor(Color.parseColor("#00cc00"));
                titleTv.setText("消息列表");
                break;
            case 1:
                idTabAddressImg.setImageResource(R.drawable.net1314080903119address_pressed);
                tvAddress.setTextColor(Color.parseColor("#00cc00"));
                titleTv.setText("联系人");
                break;
            case 2:
                idTabNoticeImg.setImageResource(R.drawable.net1314080903119notice_press);
                tvNotice.setTextColor(Color.parseColor("#00cc00"));
                titleTv.setText("公告");
                break;
            case 3:
                idTabMeImg.setImageResource(R.drawable.net1314080903119me_pressed);
                tvMe.setTextColor(Color.parseColor("#00cc00"));
                titleTv.setText("我的");
                break;
        }
    }

    /**
     * 锟叫伙拷图片锟斤拷锟斤拷色
     */
    private void resetImgs() {
        idTabNewImg.setImageResource(R.drawable.net1314080903119messages);
        idTabAddressImg.setImageResource(R.drawable.net1314080903119address);
        idTabNoticeImg.setImageResource(R.drawable.net1314080903119notice);
        idTabMeImg.setImageResource(R.drawable.net1314080903119me);
        tvNew.setTextColor(Color.parseColor("#ff666666"));
        tvAddress.setTextColor(Color.parseColor("#ff666666"));
        tvNotice.setTextColor(Color.parseColor("#ff666666"));
        tvMe.setTextColor(Color.parseColor("#ff666666"));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.id_tab_new, R.id.id_tab_address, R.id.id_tab_notice, R.id.id_tab_me})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_tab_new:
                setSelect(0);
                break;
            case R.id.id_tab_address:
                setSelect(1);
                break;
            case R.id.id_tab_notice:
                setSelect(2);
                break;
            case R.id.id_tab_me:
                setSelect(3);
                break;
        }
    }
}
