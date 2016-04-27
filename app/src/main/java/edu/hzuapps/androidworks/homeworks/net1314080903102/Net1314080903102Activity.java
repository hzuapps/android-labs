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
    private ImageView imav1;
    private ImageView imav2;
    private ImageView imav3;
    private Button reset;
    private TextView content;
    private int[] arr;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903102);
        imav1 = (ImageView) findViewById(R.id.imv1);
        imav2 = (ImageView) findViewById(R.id.imv2);
        imav3 = (ImageView) findViewById(R.id.imv3);
        reset = (Button) findViewById(R.id.reset);
        content = (TextView) findViewById(R.id.content);

    }
}






