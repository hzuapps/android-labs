package net1314080903102.homeworks.androidworks.hzuapps.edu.net1314080903102;

import java.util.Random;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Net1314080903102Activity extends Activity {
    private Button reset;
    private TextView content;
    private int[] arr;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903102);
        reset = (Button) findViewById(R.id.reset);
        content = (TextView) findViewById(R.id.content);
        reset.setOnClickListener(new resetOnClickListener());
    }






    public class resetOnClickListener implements OnClickListener{
        @Override
        public void onClick(View v) {

            content.setText("猜猜看");

        }
    }

}