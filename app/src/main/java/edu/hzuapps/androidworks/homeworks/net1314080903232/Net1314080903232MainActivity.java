package edu.hzuapps.androidworks.homeworks.net1314080903232;


import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Net1314080903232MainActivity extends ActionBarActivity implements OnClickListener{
	private HashMap<String, Integer> spMap=null;    //用于管理音频流
    private SoundPool sp;   // 音频池
    private int soundId;    // 音频ID

    private TextView textView1;
    private List<Item> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spMap = new HashMap<>();

        // 设置最多可容纳16个音频流
        sp = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);

        // 用SoundPool的load方法加载指定音频文件，并用soundId保存返回的音频ID。
        // 用HashMap来管理这些音频流
        soundId = sp.load(this, R.raw.zero, 1);
        spMap.put("0", soundId);
        soundId = sp.load(this, R.raw.one, 1);
        spMap.put("1", soundId);
        soundId = sp.load(this, R.raw.two, 1);
        spMap.put("2", soundId);
        soundId = sp.load(this, R.raw.three, 1);
        spMap.put("3", soundId);
        soundId = sp.load(this, R.raw.four, 1);
        spMap.put("4", soundId);
        soundId = sp.load(this, R.raw.five, 1);
        spMap.put("5", soundId);
        soundId = sp.load(this, R.raw.six, 1);
        spMap.put("6", soundId);
        soundId = sp.load(this, R.raw.seven, 1);
        spMap.put("7", soundId);
        soundId = sp.load(this, R.raw.eight, 1);
        spMap.put("8", soundId);
        soundId = sp.load(this, R.raw.nine, 1);
        spMap.put("9", soundId);
        soundId = sp.load(this, R.raw.ac, 1);
        spMap.put("ac", soundId);
        soundId = sp.load(this, R.raw.del, 1);
        spMap.put("del", soundId);
        soundId = sp.load(this, R.raw.div, 1);
        spMap.put("div", soundId);
        soundId = sp.load(this, R.raw.dot, 1);
        spMap.put(".", soundId);
        soundId = sp.load(this, R.raw.equal, 1);
        spMap.put("equal", soundId);
        soundId = sp.load(this, R.raw.minus, 1);
        spMap.put("minus", soundId);
        soundId = sp.load(this, R.raw.mul, 1);
        spMap.put("mul", soundId);
        soundId = sp.load(this, R.raw.plus, 1);
        spMap.put("plus", soundId);


        textView1 = (TextView) findViewById(R.id.textView1);

        // 为按钮设置点击监听事件
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btnadd).setOnClickListener(this);
        findViewById(R.id.btnsub).setOnClickListener(this);
        findViewById(R.id.btnmul).setOnClickListener(this);
        findViewById(R.id.btndiv).setOnClickListener(this);
        findViewById(R.id.btnclr).setOnClickListener(this);
        findViewById(R.id.btneq).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn0:
                textView1.append("0");  //在UI界面的TextView中显示 0
                sp.play(spMap.get("0"), 1, 1, 0, 0, 1); //播放音频流
                break;
            case R.id.btn1:
                textView1.append("1");
                sp.play(spMap.get("1"), 1, 1, 0, 0, 1);
                break;
            case R.id.btn2:
                textView1.append("2");
                sp.play(spMap.get("2"), 1, 1, 0, 0, 1);
                break;
            case R.id.btn3:
                textView1.append("3");
                sp.play(spMap.get("3"), 1, 1, 0, 0, 1);
                break;
            case R.id.btn4:
                textView1.append("4");
                sp.play(spMap.get("4"), 1, 1, 0, 0, 1);
                break;
            case R.id.btn5:
                textView1.append("5");
                sp.play(spMap.get("5"), 1, 1, 0, 0, 1);
                break;
            case R.id.btn6:
                textView1.append("6");
                sp.play(spMap.get("6"), 1, 1, 0, 0, 1);
                break;
            case R.id.btn7:
                textView1.append("7");
                sp.play(spMap.get("7"), 1, 1, 0, 0, 1);
                break;
            case R.id.btn8:
                textView1.append("8");
                sp.play(spMap.get("8"), 1, 1, 0, 0, 1);
                break;
            case R.id.btn9:
                textView1.append("9");
                sp.play(spMap.get("9"), 1, 1, 0, 0, 1);
                break;
            case R.id.btnadd:
                sp.play(spMap.get("plus"), 1, 1, 0, 0, 1);
                items.add(new Item(Double.parseDouble(textView1.getText().toString()), Type.num));
                checkAndcompute();
                items.add(new Item(0, Type.add));
                textView1.setText("");
                break;
            case R.id.btnsub:
                sp.play(spMap.get("minus"), 1, 1, 0, 0, 1);
                items.add(new Item(Double.parseDouble(textView1.getText().toString()), Type.num));
                checkAndcompute();
                items.add(new Item(0, Type.sub));
                textView1.setText("");
                break;
            case R.id.btnmul:
                sp.play(spMap.get("mul"), 1, 1, 0, 0, 1);
                items.add(new Item(Double.parseDouble(textView1.getText().toString()), Type.num));
                checkAndcompute();
                items.add(new Item(0, Type.mul));
                textView1.setText("");
                break;
            case R.id.btndiv:
                sp.play(spMap.get("div"), 1, 1, 0, 0, 1);
                items.add(new Item(Double.parseDouble(textView1.getText().toString()), Type.num));
                checkAndcompute();
                items.add(new Item(0, Type.div));
                textView1.setText("");
                break;
            case R.id.btneq:
                sp.play(spMap.get("equal"), 1, 1, 0, 0, 1);
                items.add(new Item(Double.parseDouble(textView1.getText().toString()), Type.num));
                checkAndcompute();
                textView1.setText(items.get(0).value + "");
                String str = items.get(0).value+"";
                new Test(str).start();  // 启动另一个线程来播放结果
                items.clear();
                break;
            case R.id.btnclr:
                sp.play(spMap.get("ac"), 1, 1, 0, 0, 1);
                textView1.setText("");
        }
    }
    public void checkAndcompute(){

        if(items.size()>=3){
            double a=items.get(0).value;
            double b=items.get(2).value;
            int opt=items.get(1).type;
            items.clear();
            switch(opt){
                case Type.add:
                    items.add(new Item(a+b,Type.num));
                    break;
                case Type.sub:
                    items.add(new Item(a-b,Type.num));
                    break;
                case Type.mul:
                    items.add(new Item(a*b,Type.num));
                    break;
                case Type.div:
                    items.add(new Item(a/b,Type.num));
                    break;

            }
        }
    }

    //因为等下会用到Thread.sleep()方法，如果放在主线程会导致计算结果要等等音频文件播放结束才会显示
    //这里为了不造成上面所说的UI界面类似卡死的情况，使用一个子线程来播放结果对应的音频
    class Test extends Thread{
        private String result;  //计算的结果
        public Test(String s){
            this.result = s;
        }
        @Override
        public void run() {
            String s;
            sp.stop(spMap.get("equal"));
            // 用for循环把计算结果分割成对应spMap中的key
            for(int i=0; i<result.length(); i++){
                try {
                    Thread.sleep(350);  //该线程睡350毫秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                s = result.substring(i, i+1);
                sp.play(spMap.get(s), 1, 1, 0, 0, 1);
            }
        }
    }
}
