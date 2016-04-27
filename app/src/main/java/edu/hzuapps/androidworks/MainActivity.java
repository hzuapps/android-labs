package edu.hzuapps.androidworks;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;

import edu.hzuapps.androidworks.exmaples.ActivityBasicActivity;
import edu.hzuapps.androidworks.exmaples.FragmentDemoActivity;
import edu.hzuapps.androidworks.exmaples.ViewBasicActivity;
import edu.hzuapps.androidworks.homeworks.BackActivity;

public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//         TODO 解析学生实验目录
        setContentView(R.layout.activity_main);

        // 显示例子按钮
        prepareExampleButton(R.id.button_activitybasic);
        prepareExampleButton(R.id.button_viewbasic);
        prepareExampleButton(R.id.button_fragmentdemo);

        // 随机显示一位同学的作业
        prepareExampleButton(R.id.button_random);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            System.out.println("Yes");
//         Log.i(this.getClass().getSimpleName(), "View created!");
            Log.i("标题", "信息");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openActivity(Activity activity, Class activityClass) {
        Intent intent = new Intent(activity, activityClass);
        activity.startActivity(intent);
    }

    private void prepareExampleButton(final int buttonId) {
        final Activity thisActivity = this;
        Button button = (Button) findViewById(buttonId);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openActivity(thisActivity, getActivity(buttonId));
                }
            });
        } else {
            Log.e(TAG, "按钮不存在: " + buttonId);
        }
    }

    private Class getActivity(int id) {
        if (R.id.button_activitybasic == id) {
            return ActivityBasicActivity.class;
        } else if (R.id.button_viewbasic == id) {
            return ViewBasicActivity.class;
        } else if (R.id.button_fragmentdemo == id) {
            return FragmentDemoActivity.class;
        } else {
            return BackActivity.class;
        }
    }
}
