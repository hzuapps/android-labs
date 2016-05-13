package edu.hzuapps.androidworks.homeworks.net1314080903129;

/**
 * Created by Administrator on 2016/4/25.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by dudon on 2016/1/14.
 */
public class Net1314080903129TitleLayout extends LinearLayout {
    public Net1314080903129TitleLayout(final Context context, AttributeSet attr) {
        super(context, attr);
        //动态加载布局
        LayoutInflater.from(context).inflate(R.layout.net1314080903129title_bar, this);
        Button btnBack = (Button) findViewById(R.id.title_left);
        Button btnWeb = (Button) findViewById(R.id.title_right);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
            }
        });
        btnWeb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://huaban.com"));
                ((Activity) context).startActivity(intent);
            }


        });
    }
}
