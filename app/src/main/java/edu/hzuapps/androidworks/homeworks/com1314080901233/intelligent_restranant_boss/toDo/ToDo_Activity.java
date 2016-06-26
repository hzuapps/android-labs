package com.example.intelligent_restranant_boss.toDo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.intelligent_restranant_boss.R;
import com.example.intelligent_restranant_boss.toDo.SQL.Notes_DataBase_OP;

/**
 * Created by Linco_325 on 2016/6/11.
 */
public class ToDo_Activity extends AppCompatActivity {
    public static Notes_DataBase_OP notes_dataBase_op;
    public static ImageView imageView;
    public static Button back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_layout);
        notes_dataBase_op = new Notes_DataBase_OP(getApplicationContext());
        //切换Fragment
        Note_List_Fragment nLF = new Note_List_Fragment();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_main, nLF).commit();

        //新增按钮
        imageView=(ImageView)findViewById(R.id.btn_add_todo);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                New_Note_Fragment nNF = new New_Note_Fragment();
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_main, nNF).commitAllowingStateLoss();
                imageView.setVisibility(View.INVISIBLE);
                back_button.setVisibility(View.VISIBLE);
            }
        });

        //标题
        ((TextView)findViewById(R.id.tv_head_title)).setText("待办事项");

        //返回按钮
        back_button=(Button)findViewById(R.id.btn_left_header);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findViewById(R.id.note_list_view)==null) {
                    Note_List_Fragment nLF = new Note_List_Fragment();
                    android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_main, nLF).commit();
                    back_button.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        });
        back_button.setVisibility(View.INVISIBLE);
    }
    //返回键逻辑,如果不在列表,跳到列表
    @Override
    public void onBackPressed() {
        if (findViewById(R.id.note_list_view)==null) {
            Note_List_Fragment nLF = new Note_List_Fragment();
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_main, nLF).commit();
        }
    }

}
