package com.example.intelligent_restranant_boss.toDo;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/3/4.
 */
public class My_TextButton extends TextView implements View.OnTouchListener, View.OnFocusChangeListener {
    //在touchListener写按钮界面,在按钮setOnClickListener写行为
    //设置false不影响Clicklistener,而移开取消是Click自带的功能
    //继承IB或IV都可以
    public My_TextButton(Context context) {
        super(context);
        this.setOnTouchListener(this);
        this.setOnFocusChangeListener(this);
    }

    public My_TextButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
        this.setOnTouchListener(this);
        this.setOnFocusChangeListener(this);
    }

    public My_TextButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFocusable(true);
        this.setOnTouchListener(this);
        this.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        //色彩变化
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        if (hasFocus)
            v.setAlpha(0.75f);
        else
            v.setAlpha(1);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //色彩变化
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            v.setAlpha(0.75f);
        else if (event.getAction() == MotionEvent.ACTION_UP)
            v.setAlpha(1);
        //v.getBackground().setColorFilter(new ColorMatrixColorFilter(cm));
        return false;
    }
}
