package edu.hzuapps.androidworks.homeworks.com1314080901139;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//import android.view.View.OnClickListener;

/**
 * Created by wenlangchu on 16/6/15.
 */
public class Com1314080901139Activity extends Activity {
    private Button btnInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901139);

        btnInit = (Button) findViewById(R.id.buttonInit);
        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Com1314080901139Activity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
