package com.example.intelligent_restranant_boss.Net_Info_Provider;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.intelligent_restranant_boss.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Linco_325 on 2016/6/12.
 */
//提供三类列表数据
public class Rank_Sale_Vip_Provider {
    public List<LinearLayout> getList(Activity activity,List<Rank>mDataList,Rank rank){
        List<LinearLayout> mList=new LinkedList<>();
        //获取信息
        //遍历列表,适配到LL
        int image_id;
        image_id=R.drawable.home_rank;
        for (Rank one_item_Object:mDataList){
            //用这种方法加载xml到布局对象,activity是getActivity得到的参数
            //已知xml描述的布局类型.可以直接将加载后的view转化为对应
            LinearLayout one_item_LL = (LinearLayout)activity.getLayoutInflater()
                    .inflate(R.layout.item_rank_sale_vip, null);
            //绑定控件
            ImageView iv_pic=(ImageView)one_item_LL.findViewById(R.id.item_pic);
            TextView title=(TextView)one_item_LL.findViewById(R.id.title);
            TextView content=(TextView)one_item_LL.findViewById(R.id.item_content);
            iv_pic.setImageResource(image_id);
            title.setText(one_item_Object.getTitle());
            content.setText(one_item_Object.getContent());

            mList.add(one_item_LL);
        }
        return mList;
    }
    public List<LinearLayout> getList(Activity activity,List<Coupon>mDataList,Coupon Coupon){
        List<LinearLayout> mList=new LinkedList<>();
        //获取信息
        //遍历列表,适配到LL
        int image_id;
        image_id=R.drawable.ticket;
        for (Coupon one_item_Object:mDataList){
            //用这种方法加载xml到布局对象,activity是getActivity得到的参数
            //已知xml描述的布局类型.可以直接将加载后的view转化为对应
            LinearLayout one_item_LL = (LinearLayout)activity.getLayoutInflater()
                    .inflate(R.layout.item_rank_sale_vip, null);
            //绑定控件
            ImageView iv_pic=(ImageView)one_item_LL.findViewById(R.id.item_pic);
            TextView title=(TextView)one_item_LL.findViewById(R.id.title);
            TextView content=(TextView)one_item_LL.findViewById(R.id.item_content);
            iv_pic.setImageResource(image_id);
            title.setText(one_item_Object.getTitle());
            content.setText(one_item_Object.getContent());

            mList.add(one_item_LL);
        }
        return mList;
    }
    public List<LinearLayout> getList(Activity activity,List<Vip>mDataList,Vip vip){
        List<LinearLayout> mList=new LinkedList<>();
        //获取信息
        //遍历列表,适配到LL
        int image_id;
        image_id=R.drawable.vip;
        for (Vip one_item_Object:mDataList){
            //用这种方法加载xml到布局对象,activity是getActivity得到的参数
            //已知xml描述的布局类型.可以直接将加载后的view转化为对应
            LinearLayout one_item_LL = (LinearLayout)activity.getLayoutInflater()
                    .inflate(R.layout.item_rank_sale_vip, null);
            //绑定控件
            ImageView iv_pic=(ImageView)one_item_LL.findViewById(R.id.item_pic);
            TextView title=(TextView)one_item_LL.findViewById(R.id.title);
            TextView content=(TextView)one_item_LL.findViewById(R.id.item_content);
            iv_pic.setImageResource(image_id);
            title.setText(one_item_Object.getTitle());
            content.setText(one_item_Object.getContent());

            mList.add(one_item_LL);
        }
        return mList;
    }
}
