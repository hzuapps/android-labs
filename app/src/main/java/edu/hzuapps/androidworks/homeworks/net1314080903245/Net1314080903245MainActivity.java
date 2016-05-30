package edu.hzuapps.androidworks.homeworks.net1314080903245;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Net1314080903245MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView AIdTV;
    TextView BIdTV;
    int scoreTeamA;
    int scoreTeamB;
    Button btnShowAllScore;
    Button btnSave;
    Button btnreset;
    TextView allMatchScore;
    String matchMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903245_main);

        AIdTV = (TextView) findViewById(R.id.AId);
        BIdTV = (TextView) findViewById(R.id.BId);
        AIdTV.setText(TeamName.AId);
        AIdTV.setTextSize(24);
        BIdTV.setText(TeamName.BId);
        BIdTV.setTextSize(24);
        btnShowAllScore = (Button) findViewById(R.id.showAllMatchScore);
        btnSave = (Button) findViewById(R.id.save);
        btnreset = (Button) findViewById(R.id.reset);
        btnShowAllScore.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnreset.setOnClickListener(this);
        allMatchScore = (TextView) findViewById(R.id.allMatchScore);
        allMatchScore.setTextSize(24);
//        matchMessage = TeamName.AId + ":" + scoreTeamA + "\n" + TeamName.BId + ":" + scoreTeamB+"\n";
    }

    public void delOneForTeamA(View v) {
        scoreTeamA = scoreTeamA - 1;
        displayForTeamA(scoreTeamA);
    }

    public void addOneForTeamA(View v) {
        scoreTeamA = scoreTeamA + 1;
        displayForTeamA(scoreTeamA);
    }

    public void displayForTeamA(int score) {
        TextView socreView = (TextView) findViewById(R.id.team_a_score);
        socreView.setText(String.valueOf(score));
    }


    public void delOneForTeamB(View v) {
        scoreTeamB = scoreTeamB - 1;
        displayForTeamB(scoreTeamB);
    }

    public void addOneForTeamB(View v) {
        scoreTeamB = scoreTeamB + 1;
        displayForTeamB(scoreTeamB);
    }

    public void displayForTeamB(int score) {
        TextView socreView = (TextView) findViewById(R.id.team_b_score);
        socreView.setText(String.valueOf(score));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.showAllMatchScore:
                allMatchScore.setText(readFiles());
                break;
            case R.id.save:
                try {
                    WriteFiles(TeamName.AId,TeamName.BId,scoreTeamA,scoreTeamB);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Toast.makeText(Net1314080903245MainActivity.this,"保存成功!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.reset:
                scoreTeamA = 0;
                scoreTeamB = 0;
                displayForTeamA(scoreTeamA);
                displayForTeamB(scoreTeamB);
                break;
        }
    }
//
//    //保存文件内容
//    public void WriteFiles(String content){
//        try {
//            FileOutputStream fos = openFileOutput("save.txt", MODE_APPEND);
//            fos.write(content.getBytes());
//            fos.close();
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }


    //保存文件内容
    public void WriteFiles(String TeamA,String TeamB,int scoreA,int scoreB) throws FileNotFoundException {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        out = openFileOutput("save.txt", Context.MODE_APPEND);
        writer = new BufferedWriter(new OutputStreamWriter(out));
        try {
            writer.write(TeamA+":"+scoreA+"\n"+TeamB+":"+scoreB+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //读取文件内容
    public String readFiles(){
        String content = null;
        try {
            FileInputStream fis= openFileInput("save.txt");
            ByteArrayOutputStream baos =  new ByteArrayOutputStream();
            byte [] buffer =  new byte[1024];
            int len = 0;
            while ((len=fis.read(buffer))!=-1) {
                baos.write(buffer, 0, len);
            }
            content = baos.toString();
            fis.close();
            baos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return content;
    }
}
