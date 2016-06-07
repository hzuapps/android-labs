package edu.hzuapps.androidworks.homeworks.net1314080903245;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TeamName extends AppCompatActivity implements View.OnClickListener{

    EditText teamAId;
    EditText teamBId;
    Button setTeamId;
    public static String AId;
    public static String BId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_name);

        teamAId = (EditText) findViewById(R.id.teamAIdet);
        teamBId = (EditText) findViewById(R.id.teamBIdet);
        setTeamId = (Button) findViewById(R.id.teamSetIdBtn);
        setTeamId.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
//        Toast.makeText(TeamName.this,"点击",Toast.LENGTH_SHORT).show();
        AId = teamAId.getText().toString();
        BId = teamBId.getText().toString();
        Intent intent = new Intent(this, Net1314080903245MainActivity.class);
        startActivity(intent);
    }
}
