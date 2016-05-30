package edu.hzuapps.androidworks.homeworks.com1314080901134.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import edu.hzuapps.androidworks.homeworks.com1314080901134.R;

/**
 * Created by Administrator on 2016/5/25.
 */
public class SplashActivity extends AppCompatActivity {
    @ViewInject(R.id.login_P)
    private ImageView login_p;
    @ViewInject(R.id.login_layout)
    private RelativeLayout relativeLayout;
    private static final int DELAY_TIME = 2000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, DELAY_TIME);

    }
}
