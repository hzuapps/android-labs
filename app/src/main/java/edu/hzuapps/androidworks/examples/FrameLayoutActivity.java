package edu.hzuapps.androidworks.examples;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.FrameLayout;

import edu.hzuapps.androidworks.R;

public class FrameLayoutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout);

        View root = findViewById(R.id.frame_layout);

        FrameLayout frameLayout = (FrameLayout) root;

//        frameLayout.setForeground(new Drawable() {
//            @Override
//            public void draw(Canvas canvas) {
//
//            }
//
//            @Override
//            public void setAlpha(int i) {
//
//            }
//
//            @Override
//            public void setColorFilter(ColorFilter colorFilter) {
//
//            }
//
//            @Override
//            public int getOpacity() {
//                return 0;
//            }
//        });
//        frameLayout.setForegroundGravity(1);
//        frameLayout.setMeasureAllChildren(true);

    }

}
