package edu.hzuapps.androidworks.homeworks.net134080903206;

import com.bengua.whiteblock.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Net1314080903206StepOnWhiteActivity extends Net1314080903206BaseActivity {
	
	
	private int score = 0;
	private TextView nowScore;
	private TextView bestScore;
	private Button mGameRule;
	private GridLayout gl;
	private TextView showtime;
	public static final String SP_KEY_BEST_SCORE = "bestScore";
	private static int time=0;
	private static final int sendtime=1;
	 int t=30;
	private Handler h=new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case sendtime:
				
				showtime.setText(time+"s");
				showtime.setTextColor(Color.RED);

				break;
			default:
				break;
				
			}
		}
	};;
	
	
	private static Net1314080903206StepOnWhiteActivity SOWActivity = null;
	
	public static Net1314080903206StepOnWhiteActivity getSOWActivity() {
		return SOWActivity;
	}
	
	public Net1314080903206StepOnWhiteActivity() {
		SOWActivity = this;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net1314080903206_step_on_white);
		
		nowScore=(TextView) findViewById(R.id.nowscore);
		bestScore=(TextView) findViewById(R.id.bestscore);
		showtime=(TextView) findViewById(R.id.showtime);
		gl=(GridLayout) findViewById(R.layout.net1314080903206_step_on_white);	
		mGameRule = (Button) findViewById(R.id.game_rule_button);
		
		mGameRule.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent i = new Intent(Net1314080903206StepOnWhiteActivity.this, Net1314080903206GameRuleActivity.class);
				startActivity(i);
				
			}
		});
		
		
	}


	public void clearScore(){
		score = 0;
		showScore();
	}
	
	public void showScore(){
		nowScore.setText(score+"");
	}
	
	public String getScore()
	{
		return (String) nowScore.getText();
	}
	
	public void addScore(int s){
		score+=s;
		showScore();
		
		int maxScore = Math.max(score, getBestScore());
		saveBestScore(maxScore);
		showBestScore(maxScore);
	}
	
	public void saveBestScore(int s){
		Editor e = getPreferences(MODE_PRIVATE).edit();
		e.putInt(SP_KEY_BEST_SCORE, s);
		e.commit();
	}

	public int getBestScore(){
		return getPreferences(MODE_PRIVATE).getInt(SP_KEY_BEST_SCORE, 0);
	}

	public void showBestScore(int s){
		bestScore.setText(s+"");
	}
	
	
	private void time()
	{
		Log.d("StepOnWhiteActivity", t+"");
		new Thread(new Runnable()
		{
			
			@Override
			public void run() {
				for(time=t;time>=0;time--)
				{
					Message m=new Message();
					m.what=sendtime;
					h.sendMessage(m);
					try 
					{
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} 
				
				}
			}
		}).start();
		


	}	
	
	public void TimeStart()
	{
		time();
	}
	
	public int getTime()
	{
		return time;
	}
	
	public void setTime()
	{
		t=30;
	}
	
	public void exitGame()
	{
		Intent i=new Intent(Net1314080903206StepOnWhiteActivity.this,Net1314080903206MainActivity.class);
		startActivity(i);
	}
	public void showStart()
	{
		Toast.makeText(Net1314080903206StepOnWhiteActivity.this, "ÓÎÏ·¿ªÊ¼", Toast.LENGTH_SHORT).show();
	}
	
}









