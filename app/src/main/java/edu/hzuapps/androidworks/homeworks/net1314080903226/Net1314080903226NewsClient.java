package com.yuedong.csdn.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.qhad.ads.sdk.adcore.Qhad;
import com.qhad.ads.sdk.interfaces.IQhAdEventListener;
import com.qhad.ads.sdk.interfaces.IQhBannerAd;
import com.yuedong.csdn.R;
import com.yuedong.csdn.fragment.MainFragment;

public class MainActivity extends BaseActivity {

    private TabLayout mTab;
    private ViewPager mViewpager;
    private FragmentPagerAdapter mAdapter;
    private String[] titles;
    private  LinearLayout mainadv;
    private  IQhBannerAd bannerad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTab = (TabLayout) findViewById(R.id.id_tablayout);
        mViewpager = (ViewPager) findViewById(R.id.id_viewpager);
        mainadv=(LinearLayout)findViewById(R.id.mainadv);
//        mViewpager.setOffscreenPageLimit(4);
        initData();
        init360adv();
        bannerad.showAds(this);
    }

    private void initData() {
        titles = new String[]{getResources().getString(R.string.yejie), getResources().getString(R.string.yidong)
                , getResources().getString(R.string.yunjisuan), getResources().getString(R.string.yanfa)};
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return MainFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position % titles.length];
            }
        };
        mViewpager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mViewpager);
    }
    private void init360adv(){
        bannerad = Qhad.showBanner(mainadv, MainActivity.this, "5kklueDWau", false);
        bannerad.setAdEventListener(new IQhAdEventListener() {
            @Override
            public void onAdviewGotAdSucceed() {

            }

            @Override
            public void onAdviewGotAdFail() {

            }

            @Override
            public void onAdviewRendered() {

            }

            @Override
            public void onAdviewIntoLandpage() {

            }

            @Override
            public void onAdviewDismissedLandpage() {

            }

            @Override
            public void onAdviewClicked() {

            }

            @Override
            public void onAdviewClosed() {

            }

            @Override
            public void onAdviewDestroyed() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bannerad.closeAds();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        bannerad.showAds(this);
//    }
}
