package edu.hzuapps.androidworks.homeworks.com1314080901110;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import edu.hzuapps.androidworks.homeworks.com1314080901110.adapter.BoomAdapter;
import edu.hzuapps.androidworks.homeworks.com1314080901110.entity.GridEntity;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    private Timer timer=new Timer();
    private Button startGame;
    private Handler handler;
    private int gameTime=0;
    private TextView showTime;
    private final static int MESSAGE_UPDATE_TIME=1;
    private BoomAdapter adapter;
    private GridView gv;
    private int level=10;
    private boolean isGaming=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent=getIntent();
        level=Integer.parseInt(intent.getStringExtra("level"));
        gv=(GridView)findViewById(R.id.gv);
        adapter=new BoomAdapter(level,gv,this);
        gv.setNumColumns(level);
        gv.setAdapter(adapter);
        inint();
        addListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void inint(){
        startGame=(Button)findViewById(R.id.startGame);
        showTime = (TextView) findViewById(R.id.timeView);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_UPDATE_TIME) {
                    showTime.setText("已用时间：" +gameTime/60+"分"+ gameTime%60 + "秒");
                }
            }
        };
    }

    public void startGame(){
        adapter=new BoomAdapter(level,gv,this);
        gv.setNumColumns(level);
        gv.setAdapter(adapter);
        isGaming=true;
        gameTime=0;
        timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameTime++;
                handler.sendEmptyMessage(MESSAGE_UPDATE_TIME);
            }
        }, 0, 1000);
    }

    /**
     * 方法：结束游戏
     * */
    public void stopGame(){
        isGaming=false;
        timer.cancel();
    }

    public void addListener(){
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
                startGame.setText("重新开始");
            }
        });
        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isGaming) {
                    return true;
                }
                GridEntity grid = adapter.getEntity(position);
                if (grid.isShow()) {
                    return true;
                }
                grid.setIsFlag(!grid.isFlag());
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!isGaming){
                    return;
                }
//                如果游戏开始，通过position获取格子对象
                GridEntity grid=adapter.getItem(position);
//                如果格子对象被标记，则单击无效
                if(grid.isFlag()){
                    return;
                }
                if(!grid.isShow()){
                    if(grid.isBoom()){
                        grid.setIsShow(true);
                        stopGame();
                        adapter.showAllBooms();
//                        检查旗子标记是否正确
                        adapter.checkFlag();
                        Toast.makeText(getApplicationContext(),"游戏失败，请重新开始！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(grid.getBoomsCount()==0&&!grid.isBoom()){
//                        如果格子不是雷格且周围地雷数为0，则展现无雷区域
                        adapter.showNotBoomsArea(position);
                    }
                    grid.setIsShow(true);
                    if(adapter.isWin()){
                        stopGame();
                        Toast.makeText(getApplicationContext(),"恭喜您！闯关成功,您的用时为"+showTime.getText(),Toast.LENGTH_LONG).show();
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
