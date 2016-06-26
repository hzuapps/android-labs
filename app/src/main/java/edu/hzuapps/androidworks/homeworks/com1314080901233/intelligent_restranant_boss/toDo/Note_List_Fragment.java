package com.example.intelligent_restranant_boss.toDo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;


import com.example.intelligent_restranant_boss.R;

import java.util.List;

/**
 * Created by Linco_325 on 2016/4/8.
 */
public class Note_List_Fragment extends Fragment {
    List<LinearLayout>note_list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.note_list_fragment, container, false);
        //获取list源
        note_list= new Notes_List_Provider().get_Notes_List(getActivity(),"");
        if (note_list!=null)    //此处判对象非空,和list没有数据不一样
            set_Note_list(rootView);
        return rootView;
    }

    //绘制list
    private void set_Note_list(View rootView){
        ListView note_list_view=(ListView)rootView.findViewById(R.id.note_list_view);
        //空数据提示
        TextView note_list_empty_tv=new TextView(this.getContext());
        note_list_empty_tv.setText(R.string.note_list_empty_tv);
        note_list_empty_tv.setTextSize(18);
        note_list_empty_tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        note_list_empty_tv.setGravity(Gravity.CENTER);
        //note_list_view.setEmptyView(note_list_empty_tv);  //直接设置是不行的,要用下面方法

        note_list_empty_tv.setVisibility(View.GONE);
        ((ViewGroup)note_list_view.getParent()).addView(note_list_empty_tv);
        note_list_view.setEmptyView(note_list_empty_tv);

        //使用adapter装载数据
        note_list_view.setAdapter(new LincoAdapter(this.getContext(),note_list));
    //     四种得到的是一样的
    //    Log.d("context",getContext()+" "+this.getContext()+" "+getActivity()+" "+this.getActivity()+" ");
        //单击响应
        note_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Edit_Note_Fragment eNF=new Edit_Note_Fragment();
                //用Bundle储存数据,通过setArguments传给下一个Fragment
                Bundle mBundle=new Bundle();
                mBundle.putInt("note_id", (int) id);
                eNF.setArguments(mBundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_main,eNF).commitAllowingStateLoss();
                if (!ToDo_Activity.imageView.equals(null))
                    ToDo_Activity.imageView.setVisibility(View.GONE);
                if (!ToDo_Activity.back_button.equals(null))
                    ToDo_Activity.back_button.setVisibility(View.VISIBLE);
            }
        });
        //长按响应
        note_list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //示例读取
                //如果列表变化,position可能不等于id,应以id为准
                Log.d("long_Item_id",Notes_List_Provider.note_lists_Object.get(position).get_Note_no_()+"");
                //返回真,这个操作已经被我承包了
                int xy[]=new int[2];
                //计算要显示dialog的位置
                //获取选中项的坐标
                view.getLocationOnScreen(xy);
                //创建布局管理器
                android.app.FragmentTransaction mFT=getActivity().getFragmentManager().beginTransaction();
                //判断已有
                android.app.Fragment fragment = getActivity().getFragmentManager().findFragmentByTag("long_click+menu");
                if (null != fragment) {
                    mFT.remove(fragment);
                }
                Long_Click_Dialog mlcd=new Long_Click_Dialog(xy[1],id);
                mlcd.show(mFT,"long_click+menu");
                return true;
            }
        });
    }
}