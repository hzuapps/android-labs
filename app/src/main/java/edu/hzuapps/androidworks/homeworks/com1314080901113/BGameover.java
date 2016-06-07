package com.example.tomny.findcolor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;



/**
 * Created by Tomny on 2016/5/20.
 */
public class BGameover extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bgameover);
        findViewById(R.id.restarts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BGameover.this, Com1314080901113findcolor.class));
            }
        });
    }
}
