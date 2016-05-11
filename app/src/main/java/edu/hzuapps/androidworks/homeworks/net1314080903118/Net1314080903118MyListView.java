package edu.hzuapps.androidworks.homeworks.net1314080903118;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 重写ListView,解决ListView与ScrollView滑动冲突问题
 * Created by RImpression on 2016/4/30 0030.
 */
public class Net1314080903118MyListView extends ListView {
    public Net1314080903118MyListView(Context context) {
        super(context);
    }

    public Net1314080903118MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Net1314080903118MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
