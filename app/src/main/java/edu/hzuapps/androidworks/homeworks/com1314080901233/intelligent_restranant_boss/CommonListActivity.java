package com.example.intelligent_restranant_boss;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.intelligent_restranant_boss.Net_Info_Provider.Coupon;
import com.example.intelligent_restranant_boss.Net_Info_Provider.Employee;
import com.example.intelligent_restranant_boss.Net_Info_Provider.Employee_Provider;
import com.example.intelligent_restranant_boss.Net_Info_Provider.Rank;
import com.example.intelligent_restranant_boss.Net_Info_Provider.Rank_Sale_Vip_Provider;
import com.example.intelligent_restranant_boss.Net_Info_Provider.Vip;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class CommonListActivity extends Activity {
    /*
    * 多个列表显示的功能共用的Activity**/
    public final static String LIST_TYPE_KEY="List_key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_list_layout);
        adaptView();
    }
    private void adaptView(){
        //要获取的列表类型
        String list_type=getIntent().getStringExtra(LIST_TYPE_KEY);
        if (!list_type.equals(null)){
            final ListView listView=(ListView)findViewById(R.id.lv_content);
            switch (list_type){
                case "rank":
                    setTitle("热门菜式");
                    //多线程获取数据
                    final BmobQuery<Rank>rbq=new BmobQuery<>();
                    rbq.findObjects(CommonListActivity.this,new FindListener<Rank>() {
                        @Override
                        public void onSuccess(List<Rank> list) {
                            //数据获取成功后交给Provider封装
                            List<LinearLayout>items=new Rank_Sale_Vip_Provider().getList(CommonListActivity.this,list,new Rank());
                            //封装好了,SetAdapter
                            listView.setAdapter(new LincoAdapter(CommonListActivity.this, items));
                        }
                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                    break;
                //下同
                case "Coupon":
                    setTitle("优惠券");
                    final BmobQuery<Coupon>sbq=new BmobQuery<>();
                    sbq.findObjects(CommonListActivity.this,new FindListener<Coupon>() {
                        @Override
                        public void onSuccess(List<Coupon> list) {
                            List<LinearLayout>items=new Rank_Sale_Vip_Provider().getList(CommonListActivity.this,list,new Coupon());
                            listView.setAdapter(new LincoAdapter(CommonListActivity.this, items));
                        }
                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                    break;
                case "vip":
                    setTitle("会员管理");
                    final BmobQuery<Vip>vbq=new BmobQuery<>();
                    vbq.findObjects(CommonListActivity.this,new FindListener<Vip>() {
                        @Override
                        public void onSuccess(List<Vip> list) {
                            List<LinearLayout>items=new Rank_Sale_Vip_Provider().getList(CommonListActivity.this,list,new Vip());
                            listView.setAdapter(new LincoAdapter(CommonListActivity.this, items));
                        }
                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                    break;
                case "employee":
                    setTitle("员工管理");
                    final BmobQuery<Employee>ebq=new BmobQuery<>();
                    ebq.findObjects(CommonListActivity.this,new FindListener<Employee>() {
                        @Override
                        public void onSuccess(List<Employee> list) {
                            List<LinearLayout>items=new Employee_Provider().getList(CommonListActivity.this,list);
                            listView.setAdapter(new LincoAdapter(CommonListActivity.this, items));
                        }
                        @Override
                        public void onError(int i, String s) {
                            Employee e=new Employee("傻宾","前台主管","15768650000",10000);
                            e.save(CommonListActivity.this);
                        }
                    });
                    break;

                default:
                    break;
            }
            ImageView emptyImage=new ImageView(this);
            emptyImage.setImageResource(R.drawable.file_lib);
            emptyImage.setVisibility(View.GONE);
            ((ViewGroup)listView.getParent()).addView(emptyImage);
            listView.setEmptyView(emptyImage);
            listView.setEmptyView(emptyImage);
        }else{
            finish();
        }
    }
}
class LincoAdapter extends BaseAdapter {     //这个adapter是操作一组LL的集合,LL的数据是先填好的,没有对应适配数据的过程
    private List<LinearLayout>mData;
    Context mcontext;
    public LincoAdapter(Context context,List<LinearLayout>mData){
        this.mData=mData;
        mcontext=context;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FrameLayout mfl=new FrameLayout(mcontext);
        mfl.addView(mData.get(position));
        return mfl;
    }
}
