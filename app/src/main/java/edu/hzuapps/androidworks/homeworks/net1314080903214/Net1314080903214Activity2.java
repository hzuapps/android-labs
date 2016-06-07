package com.example.ljl.showpicture;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

public class net1314080903214 extends AppCompatActivity {
    private ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView) findViewById(R.id.iv);
        img.setVisibility(View.VISIBLE);
        img.setImageResource(R.drawable.net1314080903214);




    }
}
