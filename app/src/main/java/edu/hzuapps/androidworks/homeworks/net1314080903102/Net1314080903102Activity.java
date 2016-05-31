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
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903102);
        imav1 = (ImageView) findViewById(R.id.imv1);
        imav2 = (ImageView) findViewById(R.id.imv2);
        imav3 = (ImageView) findViewById(R.id.imv3);
        reset = (Button) findViewById(R.id.reset);
        content = (TextView) findViewById(R.id.content);
        reset.setOnClickListener(new resetOnClickListener());
    }


    public void click(View v){
        if(v.getId() == imav1.getId()){
            imav1Click();
        }else if(v.getId() == imav2.getId()){
            imav2onClick();
        }else if(v.getId() == imav3.getId()){
            imav3onClick();
        }
    }

    public void imav1Click() {
        retu();
        if(arr[0] == 0)
            content.setText("恭喜你，你猜对了！");
        else
            content.setText("哦哦，猜错了！");
        imav2.setAlpha(100);
        imav3.setAlpha(100);
        imav2.setClickable(false);
        imav3.setClickable(false);
    }

    public void imav2onClick() {
        retu();
        if(arr[1] == 0)
            content.setText("恭喜你，你猜对了！");
        else
            content.setText("哦哦，猜错了！");
        imav1.setAlpha(100);
        imav3.setAlpha(100);
        imav1.setClickable(false);
        imav3.setClickable(false);
    }

    public void imav3onClick() {
        retu();
        if(arr[2] == 0)
            content.setText("恭喜你，你猜对了！");
        else
            content.setText("哦哦，猜错了！");
        imav1.setAlpha(100);
        imav2.setAlpha(100);
        imav1.setClickable(false);
        imav2.setClickable(false);
    }

    public class resetOnClickListener implements OnClickListener{
        @Override
        public void onClick(View v) {
            imav1.setImageResource(R.drawable.p04);
            imav2.setImageResource(R.drawable.p04);
            imav3.setImageResource(R.drawable.p04);
            imav1.setAlpha(255);
            imav2.setAlpha(255);
            imav3.setAlpha(255);
            content.setText("猜猜看");
            imav1.setClickable(true);
            imav2.setClickable(true);
            imav3.setClickable(true);
        }
    }

    private void retu(){
        Random random = new Random();
        arr = new int[3];
        for(int i = 0; i < 3; ++i){
            int index = random.nextInt(3);
            if(arr[index] != 0)
                i--;
            else
                arr[index] = i;
        }
        imav1.setImageResource(R.drawable.p01 + arr[0]);
        imav2.setImageResource(R.drawable.p01 + arr[1]);
        imav3.setImageResource(R.drawable.p01 + arr[2]);
    }
}