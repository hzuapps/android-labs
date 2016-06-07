package edu.hzuapps.androidworks.homeworks.com1314080901102;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/5/11.
 */
public class Card extends FrameLayout {

    public Card(Context context){
        super(context);
        label = new TextView(getContext());
        label.setTextSize(32);
        label.setBackgroundColor(0x33ffffff);
        label.setGravity(Gravity.CENTER);

        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(10, 10, 0, 0);
        addView(label,lp);

        setNum(0);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        if(num<0){
            label.setText("");
        }else{
            label.setText(num+"");
        }
        this.num = num;
    }

    private int num = 0;
    private TextView label;
}
