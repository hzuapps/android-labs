package com.example.zhenglide.exam;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.database.*;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class ExamActivity extends AppCompatActivity {
    private int count;
    private int current;
    private boolean wrongMode;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DBservice dBservice = new DBservice();
        final List<Question> list = dBservice.getQuestion();
        count = list.size();
        current = 0;
        wrongMode=false;
        final TextView tv_question = (TextView) findViewById(R.id.question);
        final RadioButton[] radioButtons = new RadioButton[4];
        radioButtons[0] = (RadioButton) findViewById(R.id.answerA);
        radioButtons[1] = (RadioButton) findViewById(R.id.answerB);
        radioButtons[2] = (RadioButton) findViewById(R.id.answerC);
        radioButtons[3] = (RadioButton) findViewById(R.id.answerD);
        final TextView tv_explaination = (TextView) findViewById(R.id.explaination);
        Button btn_next = (Button) findViewById(R.id.btn_next);
        Button btn_previous = (Button) findViewById(R.id.btn_previous);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        Question q = list.get(0);
        tv_question.setText(q.question);
        tv_explaination.setText(q.explaination);
        radioButtons[0].setText(q.AnswerA);
        radioButtons[1].setText(q.answerB);
        radioButtons[2].setText(q.answerC);
        radioButtons[3].setText(q.answerD);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current < count - 1) {
                    current++;
                    Question q = list.get(current);
                    tv_question.setText(q.question);
                    tv_explaination.setText(q.explaination);
                    radioButtons[0].setText(q.AnswerA);
                    radioButtons[1].setText(q.answerB);
                    radioButtons[2].setText(q.answerC);
                    radioButtons[3].setText(q.answerD);
                    radioGroup.clearCheck();
                    if (q.selectedAnswer != -1) {
                        radioButtons[q.selectedAnswer].setChecked(true);

                    }
                }
                else if(current==count-1 && wrongMode==true){
                    new AlertDialog.Builder(ExamActivity.this)
                            .setTitle("提示")
                            .setMessage("已经到最后一提，是否退出？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ExamActivity.this.finish();
                                }
                            })
                            .setNegativeButton("取消",null)
                            .show();

                }
                else
                {
                    final List<Integer> wrongList=checkAnswer(list);
                    if (wrongList.size()==0){
                        new AlertDialog.Builder(ExamActivity.this)
                                .setTitle("提示")
                                .setMessage("恭喜你全部答对")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ExamActivity.this.finish();
                                    }
                                }).show();
                    }
                    new AlertDialog.Builder(ExamActivity.this)
                            .setTitle("提示")
                            .setMessage("您答对了"+(list.size()-wrongList.size())+"您打错了" + wrongList.size() + "道题目，是否查看错题")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    wrongMode=true;
                                    List<Question> newList =new ArrayList<Question>();
                                    for (int i=0;i<wrongList.size();i++)
                                    {
                                        newList.add(list.get(wrongList.get(i)));
                                    }
                                    list.clear();
                                    for(int i=0;i<newList.size();i++){
                                        list.add(newList.get(i));
                                    }
                                    current=0;
                                    count=list.size();
                                    Question q = list.get(current);
                                    tv_question.setText(q.question);
                                    tv_explaination.setText(q.explaination);
                                    radioButtons[0].setText(q.AnswerA);
                                    radioButtons[1].setText(q.answerB);
                                    radioButtons[2].setText(q.answerC);
                                    radioButtons[3].setText(q.answerD);
                                    tv_explaination.setVisibility(View.VISIBLE);

                                    }
                                }
                            )
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ExamActivity.this.finish();
                                }
                            })
                            .show();

                }

            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current > 0) {
                    current--;
                    Question q = list.get(current);
                    tv_question.setText(q.question);
                    tv_explaination.setText(q.explaination);
                    radioButtons[0].setText(q.AnswerA);
                    radioButtons[1].setText(q.answerB);
                    radioButtons[2].setText(q.answerC);
                    radioButtons[3].setText(q.answerD);
                    radioGroup.clearCheck();
                    if (q.selectedAnswer != -1) {
                        radioButtons[q.selectedAnswer].setChecked(true);

                    }


                }

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i=0;i<4;i++){
                    if(radioButtons[i].isChecked()==true){
                        list.get(current).selectedAnswer=i;
                        break;
                    }

                }

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private  List<Integer> checkAnswer(List<Question> list){

        List<Integer> wrongList =new ArrayList<Integer>();
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).answer != list.get(i).selectedAnswer){
                wrongList.add(i);
            }
        }
        return wrongList;
    }
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Exam Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.zhenglide.exam/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Exam Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.zhenglide.exam/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
