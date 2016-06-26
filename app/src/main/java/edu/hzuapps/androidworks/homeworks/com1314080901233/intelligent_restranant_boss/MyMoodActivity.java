package com.example.intelligent_restranant_boss;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
//p4 今日签到
public class MyMoodActivity extends Activity implements OnClickListener {

    ImageButton main_head_back_button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_add_mood);
        findViewById();

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_head_back_button:
                finish();
                break;

            default:
                break;
        }

    }

    private void findViewById() {
        main_head_back_button = (ImageButton) this
                .findViewById(R.id.main_head_back_button);
        main_head_back_button.setOnClickListener(this);

    }
}
