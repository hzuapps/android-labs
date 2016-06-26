package com.example.intelligent_restranant_boss.toDo;

import android.app.DialogFragment;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.intelligent_restranant_boss.R;

/**
 * Created by Linco_325 on 2016/4/20.
 */
public class Long_Click_Dialog extends DialogFragment {
    //位置参考的x,y
    int py;
    long id;
    //构造方法
    public Long_Click_Dialog(){
        setArguments(new Bundle());
    }
    //构造方法
    public Long_Click_Dialog(int py, long id){
        this.py=py;
        //获取到真实id
        this.id= Notes_List_Provider.note_lists_Object.get((int)id).get_Note_no_();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无边框样式,在onCreateView前调用
        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View set_appear_dialog=inflater.inflate(R.layout.long_click_dialog, container, false);
        //用一个点测量屏幕
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point point=new Point();
        display.getSize(point);
        //布置dialog
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.y = point.y - py;
        params.width=point.x/4;
        params.height=point.y/9;
        getDialog().getWindow().setAttributes(params);
        getDialog().setCanceledOnTouchOutside(true); //点击dialog区域之外的地方，dialog消失
        //删除按钮监听
        TextView delete_tv=(TextView)set_appear_dialog.findViewById(R.id.long_click_dialog_tv_l);
        delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除数据库对应id的备忘
                // 真实id,不再是位置关系,不用加一
                ToDo_Activity.notes_dataBase_op.delete((int)id);
                //关闭DialogFragment
                dismiss();
                //重新加载列表,注意用到的类型转换
                Note_List_Fragment nLF=new Note_List_Fragment();
                android.support.v4.app.FragmentTransaction transaction = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_main,nLF).commit();
            }
        });
        return set_appear_dialog;
    }

    @Override
    public void onResume() {
        //用一个点测量屏幕
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point point=new Point();
        display.getSize(point);
        //布置dialog
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.y = point.y - py;
        params.width=point.x/3;
        params.height=point.y/10;
        getDialog().getWindow().setAttributes(params);
        getDialog().setCanceledOnTouchOutside(true); //点击dialog区域之外的地方，dialog消失
        super.onResume();
    }

}
