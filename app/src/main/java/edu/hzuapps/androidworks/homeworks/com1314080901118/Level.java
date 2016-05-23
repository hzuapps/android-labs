package edu.hzuapps.androidworks.homeworks.com1314080901118;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Level extends ListActivity {
    String classes[] = {"关卡 1","关卡 2","关卡 3","关卡 4","关卡 5"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           setListAdapter(new ArrayAdapter<String>(Level.this, R.layout.activity_level18, classes));
        }
        @Override
        protected void onListItemClick(ListView l, View v, int position, long id) {
            // TODO Auto-generated method stub
            super.onListItemClick(l, v, position, id);

            Intent intent1 = new Intent(Level.this,NewGG.class);
            intent1.putExtra("level", position);
            Log.d("sadasd","adas"+position);
            startActivity(intent1);


        }
    }

