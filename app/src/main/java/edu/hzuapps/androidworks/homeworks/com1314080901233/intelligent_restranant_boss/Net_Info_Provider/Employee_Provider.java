package com.example.intelligent_restranant_boss.Net_Info_Provider;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intelligent_restranant_boss.R;

import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Linco_325 on 2016/6/12.
 */
public class Employee_Provider {
    public List<LinearLayout> getList(final Activity activity,List<Employee>mDataList){
        Toast.makeText(activity, "Employee", Toast.LENGTH_SHORT).show();
        List<LinearLayout> mList=new LinkedList<>();

        //获取信息
        //遍历列表,适配到LL
        int image_id;
        image_id= R.drawable.employee;
        for (Employee one_item_Object:mDataList){
            //用这种方法加载xml到布局对象,activity是getActivity得到的参数
            //已知xml描述的布局类型.可以直接将加载后的view转化为对应
            LinearLayout one_item_LL = (LinearLayout)activity.getLayoutInflater()
                    .inflate(R.layout.item_employee, null);
            //绑定控件
            ImageView iv_pic=(ImageView)one_item_LL.findViewById(R.id.iv_Employee);
            TextView name=(TextView)one_item_LL.findViewById(R.id.tv_Employee_name);
            TextView job=(TextView)one_item_LL.findViewById(R.id.tv_Employee_job);
            TextView pay=(TextView)one_item_LL.findViewById(R.id.tv_Employee_pay);
            TextView phone=(TextView)one_item_LL.findViewById(R.id.tv_Employee_phone);
            iv_pic.setImageResource(image_id);
            name.setText(one_item_Object.getName());
            job.setText(one_item_Object.getJob());
            pay.setText("工资: "+one_item_Object.getPay());   //不能直接int,这是id引用
            phone.setText(one_item_Object.getPhone());
            mList.add(one_item_LL);
        }
        return mList;
    }
}
