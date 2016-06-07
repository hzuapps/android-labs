package com.example.yys.com1314080901141;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.MyAdspter;


public class ContentFragment extends Fragment {
    private ListView listView = null;
    private View mView;
    private HeaderView  mHeaderView;


    private Context mActivity = null;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return mView;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initViews();

        mView = getActivity().getLayoutInflater().inflate(R.layout.fragment_content, null, false);
        listView = (ListView) mView.findViewById(R.id.listaa);
//        mTitle = (View) mView.findViewById(R.id.content_title);
        listView.setOnItemClickListener(new MyListenr());
        List<Map<String, Object>> list = getData();
        listView.setAdapter(new MyAdspter(mActivity, list));


    }


    class MyListenr implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("onItemClick",":"+parent+"view:"+view+"position:"+position+"id:"+id);
            pushNextPage(position);
        }
    }

    public void pushNextPage(int id){
        Intent intent = new Intent();
        intent.putExtra(DefineString.BOUNTKEY_CHAT,id);
        intent.setClass(getActivity(),ChatPage.class);
        startActivity(intent);
    }


    public void onAttach(Activity context) {
        super.onAttach(context);
        mActivity = context;
    }

    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", R.drawable.head11);
            map.put("title", "这是一个标题" + i);
            map.put("info", "这是一个详细信息" + i);
            list.add(map);
        }
        return list;
    }



//    protected void initViews(){
//        mHeaderView = (HeaderView ) getView().findViewById(R.id.otherfeedlist_header);
//        mHeaderView.initStyle(HeaderView.HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
////设置左侧按钮，参数依次为标题文本、图片、左侧按钮侦听类
//        mHeaderView.setTitleAndLeftImageButton("新闻头条",
//                R.mipmap.ic_launcher, leftButtonClickListener);
////设置右侧按钮，参数依次为标题文本、图片、右侧按钮侦听类
//        mHeaderView.setTitleAndRightImageButton("新闻头条",
//                R.mipmap.ic_launcher, rightButtonClickListener);
//    }
//    //实现HeadView组件的左侧按钮侦听接口
//    private HeaderView.onLeftImageButtonClickListener leftButtonClickListener=
//            new HeaderView.onLeftImageButtonClickListener() {
//                public void onClick() {
//                    Toast.makeText(getActivity().getApplicationContext(), "您点击了左侧按钮！",
//                            Toast.LENGTH_SHORT).show();
//                }
//            };
//    //实现HeadView组件的右侧右侧侦听接口
//    private HeaderView.onRightImageButtonClickListener rightButtonClickListener=
//            new HeaderView.onRightImageButtonClickListener() {
//                public void onClick() {
//                    Toast.makeText(getActivity().getApplicationContext(), "您点击了右侧按钮！",
//                            Toast.LENGTH_SHORT).show();
//                }
//            };

}
