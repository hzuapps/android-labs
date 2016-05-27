package com.example.pyanz.messagebrowser;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Message_list extends Activity {

    private ListView message_list;
    private ArrayAdapter<String> arr_adapter;
    private SimpleAdapter simp_adapter;
    private List<Map<String,Object>>dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);
        message_list=(ListView) findViewById(R.id.message_list);
        //1、新建一个适配器
        //ArrayAdapter（上下文，当前ListView加载的每一个列表所对应的布局文件，数据源）
        /* SimpleAdapter(context,data,resource,from,to)
        *content：上下文
        *data：数据源（List<? extends Map<String, ?>> data）一个Map所组成的list集合
        *       每一个Map都会去对应ListView列表中的一行
        *       每一个Map（键 - 值对）中的键必须包含在from中所指定的键
        * resource：列表项的布局文件ID
        * from：Map中的键名
        * to：绑定数据视图中的ID
        **/
        //2、适配器加载数据源
        String[]arr_data={"item1","item2","item3","item4"};
        dataList=new ArrayList<Map<String, Object>>();
        //arr_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr_data);
        simp_adapter=new SimpleAdapter(this,getData(),R.layout.message_item,
                new String[]{"Avatar","Name","Message","Time"},
                new int[]{R.id.itemAvatar,R.id.itemName,R.id.itemMessage,R.id.itemTime});
        //3、视图（ListView）加载适配器
        //listView.setAdapter(arr_adapter);
        message_list.setAdapter(simp_adapter);
    }

    private List<Map<String, Object>> getData(){
        for (int i=0;i<10;i++){
            Map<String, Object> map=new HashMap<String,Object>( );
            map.put("Avatar", R.drawable.avatar);
            map.put("Name","用户昵称"+i);
            map.put("Message", "用户留言" + i);
            map.put("Time","发表时间"+i);
            dataList.add(map);
        }
        return dataList;
    }
}