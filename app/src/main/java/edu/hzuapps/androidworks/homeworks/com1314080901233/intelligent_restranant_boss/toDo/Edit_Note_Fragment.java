package com.example.intelligent_restranant_boss.toDo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.intelligent_restranant_boss.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Linco_325 on 2016/4/8.
 */
public class Edit_Note_Fragment extends Fragment {
    int note_id; //操作的note代号

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_note_fragment, container, false);
        init(rootView);
        return rootView;
    }

    //绑定控件,获取数据,设置监听(分类写)
    private void init(final View rootView) {
        //当前时间做内部类参数
        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat my_dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

        //绑定View
        final TextView new_note_TV = (TextView) rootView.findViewById(R.id.new_note_TV);
        final TextView new_note_title = (TextView) rootView.findViewById(R.id.new_note_title);
        final TextView new_note_content = (TextView) rootView.findViewById(R.id.new_note_content);
        final My_TextButton commit = (My_TextButton) rootView.findViewById(R.id.commit);

        //获取数据
        try {
            int list_id = getArguments().getInt("note_id");
            //由id转化为no_,获取
            note_id = Notes_List_Provider.note_lists_Object.get(list_id).get_Note_no_();
            new_note_TV.setText(getContext().getString(R.string.edit_note_TV));
            new_note_title.setText(Notes_List_Provider.note_lists_Object.get(list_id).get_Note_Title());
            //content没有直接加载到note_lists_Object,到数据库获取
            new_note_content.setText(ToDo_Activity.notes_dataBase_op.getContent(note_id));
        } catch (Exception e) {
            Note_List_Fragment nLF = new Note_List_Fragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, nLF).commit();
            Toast.makeText(getActivity(), getActivity().getString(R.string.exception_catch_toast_text), Toast.LENGTH_SHORT).show();
        }


        //监听

        //对Commit监听
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new_note_title.getText().toString().equals(""))
                    if (ToDo_Activity.notes_dataBase_op.update(note_id,    //数据库自动编号从1开始,比List<>索引大1
                            new String[]{new_note_title.getText().toString(), new_note_content.getText().toString(),
                                    my_dateFormat.format(calendar.getTime())}, 0)) {
                        Toast.makeText(getActivity(), getActivity().getString(R.string.commit_success), Toast.LENGTH_SHORT).show();
                        Note_List_Fragment nLF = new Note_List_Fragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, nLF).commit();
                        if (!ToDo_Activity.imageView.equals(null))
                            ToDo_Activity.imageView.setVisibility(View.VISIBLE);
                        if (!ToDo_Activity.back_button.equals(null))
                            ToDo_Activity.back_button.setVisibility(View.INVISIBLE);
                    } else
                        Toast.makeText(getActivity(), getActivity().getString(R.string.commit_failed), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), getActivity().getString(R.string.commit_noTitle), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
