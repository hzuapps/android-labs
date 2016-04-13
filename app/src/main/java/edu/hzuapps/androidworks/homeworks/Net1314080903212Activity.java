package edu.hzuapps.androidworks.homeworks;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.hzuapps.androidworks.R;
//import edu.hzuapps.androidworks.homeworks.Net1314080903212;

/**
 * Created by dell on 2016/3/21.
 */
public class Net1314080903212 extends BackActivity {

    private LinearLayout mTextContainer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//      TextView textView = new TextView(this);
//      textView.setText("isshe");

        createViewInJava();
        setContentView(mTextContainer);

//      默认使用XML创建的View
//      setContentView(R.layout.net1314080903212);

}

    private void createViewInJava() {
        mTextContainer = new LinearLayout(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams( //
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTextContainer.setLayoutParams(layoutParams);
        mTextContainer.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView = new TextView(this);
        textView.setText("isshe: Net13140809032122");

        mTextContainer.addView(textView);
    }

}
