package com.example.yys.com1314080901141;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.FriendAdspter;
import util.FriendCategory;
import util.MyAdspter;


public class FriendFragment extends Fragment{

    private ListView listView = null;
    private View mView;
    private Context mActivity = null;
    private List<String> listTag = new ArrayList<String>();
    private List<String> list = new ArrayList<String>();
    private FriendAdspter friendAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return mView;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = getActivity().getLayoutInflater().inflate(R.layout.fragment_friend, null, false);
        listView = (ListView) mView.findViewById(R.id.friendlist);
//        List<Map<String, Object>> list = getData();
        ArrayList<FriendCategory> listData = getData();
        friendAdapter = new FriendAdspter(getActivity().getBaseContext(), listData);
        listView.setAdapter(friendAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getBaseContext(),  (String)friendAdapter.getItem(position),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onAttach(Activity context) {
        super.onAttach(context);
        mActivity = context;
    }

    /**
     * 创建测试数据
     */
    private ArrayList<FriendCategory> getData() {
        ArrayList<FriendCategory> listData = new ArrayList<FriendCategory>();
        FriendCategory categoryOne = new FriendCategory("新朋友");
        categoryOne.addItem("群聊");
        categoryOne.addItem("标签");
        categoryOne.addItem("公共号");
        FriendCategory categoryTwo = new FriendCategory("A");
        categoryTwo.addItem("A1");
        categoryTwo.addItem("A2");
        FriendCategory categoryThree = new FriendCategory("B");
        categoryThree.addItem("B1");
        categoryThree.addItem("B2");
        categoryThree.addItem("B3");
        categoryThree.addItem("B4");
        FriendCategory categoryFour = new FriendCategory("C");
        categoryFour.addItem("C1");
        categoryFour.addItem("C2");
        categoryFour.addItem("C3");
        categoryFour.addItem("C4");
        categoryFour.addItem("C5");
        categoryFour.addItem("C6");


        listData.add(categoryOne);
        listData.add(categoryTwo);
        listData.add(categoryThree);
        listData.add(categoryFour);

        return listData;
    }



}
