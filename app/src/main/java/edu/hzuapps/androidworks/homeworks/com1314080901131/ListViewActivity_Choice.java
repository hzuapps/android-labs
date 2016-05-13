package com.example.ranine99.ranine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by ranine99 on 2016/5/3.
 */
public class ListViewActivity_Choice extends Activity {
    private static int click_time = 0;
    private String[] data = { "伦敦  London", "东京  Tokyo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_choice);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                ListViewActivity_Choice.this, android.R.layout.simple_list_item_1, data);
        final ListView listView = (ListView) findViewById(R.id.list_view_choice);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if (click_time==0){
//                    click_time++;
                Toast.makeText(ListViewActivity_Choice.this, "已成功添加", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListViewActivity_Choice.this, ListViewActivity.class);
                intent.putExtra("position", i);
                startActivity(intent);
                finish();
//                }
//                else{
//                    Toast.makeText(ListViewActivity_Choice.this,"您已经添加了该城市",Toast.LENGTH_SHORT).show();
//                }

            }
        });

    }
}
