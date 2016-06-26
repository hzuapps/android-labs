package com.example.intelligent_restranant_boss.toDo;

//负责获取和管理列表的类

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


import com.example.intelligent_restranant_boss.R;

/**
 * Created by Linco_325 on 2016/4/7.
 */
public class Notes_List_Provider {
    //对象形式储存的note_lists,写在这里,取静态,然后才能由LL的id(集合编号)对应note_lists_Object;,而后获取对象no其他信息
    public static List<Single_Note_Object>note_lists_Object;
    //FragmentActivity activity本来应该是哪里的Acitivty或content,就用哪里get的值传过来,才不会出错
    public List<LinearLayout> get_Notes_List(FragmentActivity activity,String str){
        //封装成布局的note_lists
        ArrayList<LinearLayout>note_lists_LL=new ArrayList<>();
        //获取信息
        note_lists_Object= ToDo_Activity.notes_dataBase_op.select(str);
        //遍历列表,适配到LL
        for (Single_Note_Object one_note_Object:note_lists_Object){
            //用这种方法加载xml到布局对象,activity是getActivity得到的参数
            //已知xml描述的布局类型.可以直接将加载后的view转化为对应
            LinearLayout one_note_LL = (LinearLayout)activity.getLayoutInflater()
            .inflate(R.layout.one_note_layout, null);
            //绑定控件
            TextView one_note_title= (TextView) one_note_LL.findViewById(R.id.one_note_layout_title);
        //    TextView one_note_content= (TextView) one_note_LL.findViewById(R.id.one_note_layout_content);
            TextView one_note_time= (TextView) one_note_LL.findViewById(R.id.one_note_layout_time);
            //设置值
            one_note_title.setText(one_note_Object.get_Note_Title());
        //    one_note_content.setText(one_note_Object.get_Note_Content());
            one_note_time.setText(one_note_Object.get_Note_time());
            //这里会自动添加ID,id不会因为列表变化而做出更改,可以用来确定构造时对应note_lists_Object的第几项,;
            Log.d("get_Notes_List: ",one_note_Object.get_Note_no_()+ "");
            note_lists_LL.add(one_note_LL);
        }
        return note_lists_LL;
        //return new ArrayList<LinearLayout>();
    }

}
