package com.example.lwj_pc.my_classwork;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by LWJ-PC on 2016/4/19.
 */
public class Com1314080901221Activity extends Activity {
    private String[] strings={"list 1!!!","list 2!!!"};
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901221_main);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        ArrayAdapter<String>  adapter=new ArrayAdapter<String>(Com1314080901221Activity.this,android.R.layout.simple_list_item_1,strings);
        listView=(ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
