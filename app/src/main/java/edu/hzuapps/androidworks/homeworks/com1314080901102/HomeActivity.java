package edu.hzuapps.androidworks.homeworks.com1314080901102;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import edu.hzuapps.androidworks.homeworks.simple_2048.R;


public class HomeActivity extends ActionBarActivity {

    public static HomeActivity homeActivity = null;
    private int score = 0;
    private TextView tv;
    private GameView gameView;

    public HomeActivity(){ homeActivity = HomeActivity.this;}
    public static HomeActivity  getHomeActivity(){return homeActivity;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gameView = (GameView)findViewById(R.id.gameView);
        tv = (TextView) findViewById(R.id.tvScore);
    }

    private void showScore(){
        tv.setText(score + "");
    }

    public void addScore(int s){
        score+=s;
        showScore();
    }

    public int getScore(){
        return score;
    }

    public void clearScore(){
        score = 0;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        switch (id){
            case R.id.repaly:
                gameView.startGame();
                clearScore();
                return true;
            case R.id.mainmenu:
                Intent intent = new Intent(HomeActivity.this,Com1314080901102MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
