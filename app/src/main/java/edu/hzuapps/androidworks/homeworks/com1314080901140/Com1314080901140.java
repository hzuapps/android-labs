package com.hzu.xu.planewar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Com1314080901140 extends Activity {
    private TextView mTextView;
    private Button Button0;
    private Button Button1;
    private boolean mChanged = false;
    //final Data dd=(Data) getApplication();
    public int Count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口没有标题
        setContentView(R.layout.activity_main);
        mTextView = (TextView)findViewById(R.id.show_textview);
        Button1=(Button)findViewById(R.id.button1);
        Button0=(Button)findViewById(R.id.button0);


        //read the mChanged is true or false
        readLoadFile();
        refreTextView();
        initButton();
    }
    public void Btn1OnClick(View view){
        Intent intent = new Intent();
        intent.setClass(Com1314080901140.this, GameActivity.class);
        startActivity(intent);
    }
    private void initButton()
    {
        Button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {
                readLoadFile();
                mChanged = true;
                refreTextView();
                Count=Count+2;
                saveFileStorage();
            }
        });
    }
    public void refreTextView()
    {
        mTextView.setText("当前分数为:"+Count+"!");
        }

    private void readLoadFile()
    {
        //make the Properties

        Properties properties = new Properties();

        try{
            FileInputStream istream = this.openFileInput("dmfile.cfg");
            properties.load(istream);
        }
        catch(FileNotFoundException e){
            return;
        }
        catch(IOException e){
            return;
        }
        mChanged = Boolean.valueOf(properties.getProperty("mChanging").toString());
        Count = Integer.valueOf(properties.getProperty("mButton").toString());
    }

    private boolean saveFileStorage()
    {
        Properties properties = new Properties();
        properties.put("mChanging", String.valueOf(mChanged));
        properties.put("mButton", String.valueOf(Count));
        try{
            FileOutputStream ostream = this.openFileOutput("dmfile.cfg", Context.MODE_WORLD_WRITEABLE);
            properties.store(ostream, "");
        }
        catch(FileNotFoundException e)
        {
            return false;
        }
        catch(IOException e)
        {
            return false;
        }
        return true;
    }

}




