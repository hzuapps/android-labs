package com.example.intelligent_restranant_boss;
//标签页1
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.example.intelligent_restranant_boss.excel.ExcelActivity;
import com.example.intelligent_restranant_boss.menu.MenuAPIActivity;

public class HomeActivity extends Activity implements OnClickListener {

    LinearLayout ll_calculator, ll_mobile_work, ll_search_food,ll_title_search,ll_Search_city,
            ll_employee,ll_camera,ll_task,
            ll_home_vip, ll_home_coupon, ll_home_money, ll_home_rank;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById();
    }

    private void findViewById() {
        ll_calculator = (LinearLayout) this.findViewById(R.id.ll_calculator);
        ll_mobile_work = (LinearLayout) this.findViewById(R.id.ll_mobile_work);
        ll_search_food = (LinearLayout) this.findViewById(R.id.ll_search_food);   //标题的地址
        ll_title_search=(LinearLayout)this.findViewById(R.id.ll_searchBar);    //标题的搜索功能
        ll_employee=(LinearLayout)this.findViewById(R.id.ll_employee);    //员工管理
        ll_camera=(LinearLayout)this.findViewById(R.id.ll_camera);    //相机
        ll_task=(LinearLayout)this.findViewById(R.id.ll_task);    //待办

        ll_calculator.setOnClickListener(this);
        ll_mobile_work.setOnClickListener(this);
        ll_search_food.setOnClickListener(this);
        ll_title_search.setOnClickListener(this);

        ll_home_vip = (LinearLayout) this.findViewById(R.id.ll_home_vip);
        ll_home_coupon = (LinearLayout) this.findViewById(R.id.ll_home_coupon);
        ll_home_rank = (LinearLayout) this.findViewById(R.id.ll_home_rank);
        ll_home_vip.setOnClickListener(this);
        ll_home_coupon.setOnClickListener(this);
        ll_home_rank.setOnClickListener(this);
        ll_employee.setOnClickListener(this);
        ll_camera.setOnClickListener(this);
        ll_task.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_search_food:
            case R.id.ll_searchBar:
                Intent intent_food = new Intent(HomeActivity.this, MenuAPIActivity.class);
                HomeActivity.this.startActivity(intent_food);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                //什么作用?
                break;
            case R.id.ll_calculator:
                Intent intent = new Intent(HomeActivity.this, CalculatorActivity.class);
                HomeActivity.this.startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.ll_mobile_work:
                Intent intent_mobile_work = new Intent(HomeActivity.this, ExcelActivity.class);
                HomeActivity.this.startActivity(intent_mobile_work);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.ll_home_vip:
                Intent intent_vip = new Intent(HomeActivity.this, CommonListActivity.class);
                intent_vip.putExtra(CommonListActivity.LIST_TYPE_KEY, "vip");
                HomeActivity.this.startActivity(intent_vip);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;

            case R.id.ll_home_rank: //热门
                Intent intent_rank = new Intent(HomeActivity.this, CommonListActivity.class);
                intent_rank.putExtra(CommonListActivity.LIST_TYPE_KEY, "rank");
                HomeActivity.this.startActivity(intent_rank);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;


            case R.id.ll_home_coupon:   //优惠券??
                Intent intent_coupon = new Intent(HomeActivity.this, CommonListActivity.class);
                intent_coupon.putExtra(CommonListActivity.LIST_TYPE_KEY, "Coupon");
                HomeActivity.this.startActivity(intent_coupon);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.ll_employee:
                Intent intent_employee = new Intent(HomeActivity.this, CommonListActivity.class);
                intent_employee.putExtra(CommonListActivity.LIST_TYPE_KEY,"employee");
                HomeActivity.this.startActivity(intent_employee);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.ll_camera:
                getParent().findViewById(R.id.ll_video_index).performClick();   //可行?
                break;
            case R.id.ll_task:
                getParent().findViewById(R.id.ll_todolist_index).performClick();
                break;
            default:
                break;
        }

    }

}
