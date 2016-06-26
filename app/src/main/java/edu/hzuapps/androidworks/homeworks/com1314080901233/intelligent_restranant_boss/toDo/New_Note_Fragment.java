package com.example.intelligent_restranant_boss.toDo;

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
public class New_Note_Fragment extends Fragment {
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

    //绑定控件,设置监听(分类写)
    private void init(final View rootView) {
        //当前时间做内部类参数
        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat my_dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        //绑定View
        final TextView new_note_title = (TextView) rootView.findViewById(R.id.new_note_title);
        final TextView new_note_content = (TextView) rootView.findViewById(R.id.new_note_content);
        final My_TextButton commit = (My_TextButton) rootView.findViewById(R.id.commit);

        //监听

        //对Commit监听
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new_note_title.getText().toString().equals(""))
                    if (ToDo_Activity.notes_dataBase_op.insert(new_note_title.getText().toString(), new_note_content.getText().toString(),
                            my_dateFormat.format(calendar.getTime()), 0)) {
                        Note_List_Fragment nLF = new Note_List_Fragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, nLF).commitAllowingStateLoss();
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
