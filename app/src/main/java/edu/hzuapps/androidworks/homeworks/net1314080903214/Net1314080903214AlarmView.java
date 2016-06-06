package edu.hzuapps.androidworks.homeworks.net1314080903214;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.Calendar;


public class Net1314080903214AlarmView extends LinearLayout{

    private Button btnAddAlarm;     // 添加闹钟的按钮
    private ListView lvAlarmList;   // 用于显示添加的闹钟
    private static final String KEY_ALARM_LIST = "alarmlist";
    private ArrayAdapter<AlarmData> adapter;
    private AlarmManager alarmManager;  // 提供对系统闹钟服务的访问接口，在特定时刻为我们广播一个特定的Intent

    public Net1314080903214AlarmView(Context context) {
        super(context);
        init();
    }

    public Net1314080903214AlarmView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Net1314080903214AlarmView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){    // 获取系统闹钟服务
        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        btnAddAlarm = (Button) findViewById(R.id.btnAddAlarm);      // 绑定界面中的按钮
        lvAlarmList = (ListView) findViewById(R.id.lvAlarmList);    // 绑定界面中的ListView

        adapter = new ArrayAdapter<Net1314080903214AlarmView.AlarmData>(getContext(), android.R.layout.simple_list_item_1);// android.R.layout.simple_expandable_list_item_1是android里已提供的样式，也可以换成自己的
        // 添加并显示
        lvAlarmList.setAdapter(adapter);
        // 读取已经保存的闹钟数据
        readSaveAlarmList();

        // 为添加时钟按钮添加点击事件
        btnAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAlarm(); // 添加时钟
            }
        });

        // 为ListView添加长按点击事件
        lvAlarmList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // 弹出对话框，选择将要执行的操作
                new AlertDialog.Builder(getContext()).setTitle("操作选项").setItems(new CharSequence[]{"删除"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                deleteAlarm(position);  // 删除该时钟
                                break;
                            default:
                                break;
                        }
                    }
                }).setNegativeButton("取消", null).show();
                return true;
            }
        });
    }

    // 删除闹钟
    private void deleteAlarm(int position){
        AlarmData ad = adapter.getItem(position);   // 获得被长按的那个闹钟
        adapter.remove(ad); // 从adapter中移除被长按的那个闹钟
        saveAlarmList();    // 保存ListView列表

        // 移除闹钟
        alarmManager.cancel(PendingIntent.getBroadcast(getContext(), ad.getId(), new Intent(getContext(), Net1314080903214AlarmReceiver.class), 0));
    }

    private void addAlarm(){
        // 获取当前的时间
        Calendar c = Calendar.getInstance();

        // 时间对话框。第一个参数（MenuView.this）为弹出的时间对话框所在的activity指针；
        // 第三个参数（hour）和第四个参数（minute）为弹出的时间对话框的初始显示的小时和分钟；
        // 第五个参数为设置24时显示参数，true代表时间以24时制显示时间。
        new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();

                // 获取设置的时间
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);   // 秒数清零
                calendar.set(Calendar.MILLISECOND, 0);  // 毫秒清零

                Calendar currentTime = Calendar.getInstance();

                // 如果设置的时间比当前的时间要小，则是设置的闹钟是第二天的
                if(calendar.getTimeInMillis()<=currentTime.getTimeInMillis()){
                    calendar.setTimeInMillis(calendar.getTimeInMillis()+24*60*60*1000);
                }

                AlarmData ad = new AlarmData(calendar.getTimeInMillis());
                // 添加闹钟
                adapter.add(ad);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                        ad.getTime(),
                        10*60*1000, // 每10分钟重复一次闹铃
                        PendingIntent.getBroadcast(getContext(), ad.getId(), new Intent(getContext(), Net1314080903214AlarmReceiver.class), 0));
                saveAlarmList();
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
    }

    // 保存闹钟
    private void saveAlarmList(){
        // SharedPreferences是Android平台上一个轻量级的存储类，简单的说就是可以存储一些我们需要的变量信息。
        // 数据写入，需要使用SharedPreferences的一个内部接口Editor，可使用edit()函数得到
        SharedPreferences.Editor editor = getContext().getSharedPreferences(Net1314080903214AlarmView.class.getName(), Context.MODE_PRIVATE).edit();

        StringBuffer sb = new StringBuffer();
        for(int i=0; i<adapter.getCount(); i++){
            sb.append(adapter.getItem(i).getTime()).append(",");
        }
        // 去掉最后的逗号
        if(sb.length()>1){
            String content = sb.toString().substring(0, sb.length() - 1);
            // 向editor对象里写入数据，使用的是像Map一样的方法写入键值对，调用普通XX()的方法
            editor.putString(KEY_ALARM_LIST, content);
        }else{
            editor.putString(KEY_ALARM_LIST, null);
        }

        // 写完数据后，要关闭editor
        editor.commit();
    }

    // 读取已经保存的闹钟数据
    private void readSaveAlarmList(){
        // getSharedPreferences()打开一个SharedPreferences的数据Map
        SharedPreferences sp = getContext().getSharedPreferences(Net1314080903214AlarmView.class.getName(), Context.MODE_PRIVATE);
        // 数据的读取
        // 调用getXX的方法，第一个是键（key）名，第二个，如果没有值，那么返回的默认值
        String content = sp.getString(KEY_ALARM_LIST, null);

        if(content!=null){
            String[] timeStrings = content.split(",");
            for(String string : timeStrings){
                adapter.add(new AlarmData(Long.parseLong(string)));
            }

        }
    }

    private static class AlarmData{

        private String timeLabel = "";
        private long time = 0;
        private Calendar date;

        public AlarmData(long time){
            this.time = time;

            date = Calendar.getInstance();
            // 用给定的long值设置此Calendar的当前时间值
            date.setTimeInMillis(time);

            timeLabel = String.format("%d月%d日 %d:%d",
                    date.get(Calendar.MONTH)+1,
                    date.get(Calendar.DAY_OF_MONTH),
                    date.get(Calendar.HOUR_OF_DAY),
                    date.get(Calendar.MINUTE));
        }
        public long getTime(){
            return time;
        }
        public String getTimeLabel(){
            return timeLabel;
        }

        public int getId(){
            return (int) (getTime()/1000/60);   // 返回分钟
        }

        @Override
        public String toString() {
            return getTimeLabel();
        }

    }
}
