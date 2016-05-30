package com.example.ranine99.ranine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by ranine99 on 2016/4/26.
 */
public class ThirdActivity_London extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_layout_london);

    }

    public void change(View view) {
        //Intent intent = new Intent("com.example.ranine99.ranine.ACTION_START");
        //intent.addCategory("com.example.ranine99.ranine.MY_CATEGORY");
        Intent intent = new Intent(this, ListViewActivity.class);
        // intent.addCategory("com.example.ranine99.ranine.MY_CATEGORY");

        startActivity(intent);

    }
}
