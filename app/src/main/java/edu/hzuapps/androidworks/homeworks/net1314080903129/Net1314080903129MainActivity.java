package edu.hzuapps.androidworks.homeworks.net1314080903129;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Net1314080903129MainActivity extends Activity {

    ListView listView;
    Net1314080903129UserAdapter adapter;
    List<Net1314080903129User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.net1314080903129activity_main);


        if (userList == null) {
            //创建用户数据
            Resources resources = getResources();
            String[] names = resources.getStringArray(R.array.user_name);
            userList = new ArrayList<Net1314080903129User>();
            for (int i = 0; i < names.length; i++) {
                Net1314080903129User user = new Net1314080903129User(Net1314080903129User.images[i], names[i], new String());
                userList.add(user);
            }
        }

        //填充适配器
        listView = (ListView) findViewById(R.id.user_list);
        adapter = new Net1314080903129UserAdapter(Net1314080903129MainActivity.this, R.layout.net1314080903129user_item, userList);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Net1314080903129MainActivity.this, ChatActivity.class);
                intent.putExtra("user", userList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
