package com.example.pyanz.com1314080901227;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Com1314080901227Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901227);
    }
    public class Main extends Activity {
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_com1314080901227);
            this.getWindow().setBackgroundDrawableResource(R.drawable.background);
        }
    }
}
