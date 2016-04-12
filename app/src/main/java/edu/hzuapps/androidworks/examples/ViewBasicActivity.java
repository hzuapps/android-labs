package edu.hzuapps.androidworks.examples;

import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.hzuapps.androidworks.R;
import edu.hzuapps.androidworks.homeworks.BackActivity;

/**
 * 演示View的3种用法.
 */
public class ViewBasicActivity extends BackActivity {

    private LinearLayout mTextContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 默认使用XML创建的View
        setContentView(R.layout.activity_view_basic);

        // 演示使用Java创建的View
//        createViewInJava();
//        setContentView(mTextContainer);

        // 演示结合使用Java和XML来创建View
        setTextValueInJava();
    }

    /**
     * 在Java中创建View.
     */
    private void createViewInJava() {
        mTextContainer = new LinearLayout(this);
        LayoutParams layoutParams = new LayoutParams( //
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mTextContainer.setLayoutParams(layoutParams);
        mTextContainer.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView = new TextView(this);
        textView.setText("显示一些文字.");

        mTextContainer.addView(textView);
    }

    private void setTextValueInJava() {
        TextView textView = (TextView) findViewById(R.id.text_id);
        textView.setText(R.string.text_demo);
    }

}
