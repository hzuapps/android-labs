package edu.hzuapps.androidworks.homeworks.net1314080903120;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Net1314080903120Activity extends Activity {
    //////////////////////////////////////////////////////////////////定义基本控件
    Button createNew,btest;
    EditText test;
    ListView list;


    //////////////////////////////////////////////////////////////////定义数据存放方式的
    Cursor cursor,cursorInfo;                          //定义指向数据的游标
    Net1314080903120dataBase data;                                    // 数据库
    List<Map<String,String>> listitems;              //存放数据库内容的动态数组
    projectAdapter listViewAdapter;                        //自定义容器
    //boolean ischecked;
//int checkid,mposition;
    LayoutInflater listContainer;                     //相当于findviewbyid 自定义listview布局

    //////////////////////////////////////////////////////////////////   定义提示框的
    TextView topicInfo,periodinfo,dateInfo,
            timeInfo,priorityInfo,detailInfo;
    ////////////////////////////////////////////////////////////////////定义optionmenu的选项
    protected final static int MENU_DETAIL = Menu.FIRST;
    protected final static int MENU_DELETE = Menu.FIRST + 1;
    protected final static int MENU_UPDATE = Menu.FIRST + 2;
    protected final static int MENU_EXIT = Menu.FIRST + 3;


    ////////////////////////////////////////////////////////////////////定义一些中间传递的字符串
    String topic,dateandtime;
    String ItemID,state;
    String getListID="2";
    int checkid,mposition,getPosition;
    String SWITCH="cm";
///////////////////////////////////////////////////////////////////
//Bundle gettest,obtaintopic,obtaindateandtime,obtainpriority;


    ProgressBar pb;                         //进度圈

    @Override
    protected void onCreate(Bundle savedInstanceState) {
///////////////////////////////////////////////////////////////////基本布局
        super.onCreate(savedInstanceState);

        setContentView(R.layout.net1314080903120activity);

        setTitle("个人计划管理");
        pb=(ProgressBar)findViewById(R.id.progressBar1);

/////////////////////////////////////////////////////////////////////////////////////////	测试数据是否存在的
        data=new Net1314080903120dataBase(this);
        cursor=data.select();
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            Log.d("OUTPUT", "" + cursor.getString(1));
            cursor.moveToNext();
        }

        cursor.moveToFirst();


/////////////////////////////////////////////////////////////////////////////////////////	设置listview来装数据
        list=(ListView)findViewById(R.id.projectList);
        listitems=getListItems();
        listViewAdapter=new projectAdapter(listitems, this);

        list.setAdapter(listViewAdapter);


        this.registerForContextMenu(list);






/////////////////////////////////////////////////////////////////////////////////////   触发listview点击事件

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                getListID=listitems.get(arg2).get("ID");
                Log.d("get database ID",""+getListID);   //每个listitem里面都封装了之前所加入的元素
                //使用时关键是找到点击的是那一条item，先调用，然后定位，最后索取

                Log.d("get listview position",""+arg2);
                mposition=arg2;
                SWITCH="om";

                //	int i=Integer.parseInt(s);

            }

        });


///////////////////////////////////////////////////////////////////////////////   触发按键点击事件 新建计划
        createNew=(Button)findViewById(R.id.cancel);
        createNew.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                Intent turn=new Intent(Net1314080903120Activity.this,Net1314080903120newproject.class);
                startActivity(turn);                                               //跳转到下一页
            }

        });
    }

    /////////////////////////////////////////////////////////////////////生成上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        Log.v("test", "populate context menu");


        menu.setHeaderTitle("选择");                              // 设定 menu 标题
        menu.setHeaderIcon(R.drawable.net1314080903120ic_launcher);

        menu.add(0, 1, Menu.NONE, "详细信息");                      //加入每一项
        menu.add(0, 2, Menu.NONE, "删除");
        menu.add(0, 3, Menu.NONE, "编辑");



    }
//////////////////////////////////////////////////////////////////响应上下文菜单项

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // 得到当前被选中的item信息
        super.onContextItemSelected(item);
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();//获得AdapterContextMenuInfo,以此来获得选择的listview项
        ItemID=listitems.get(menuInfo.position).get("ID");
        getPosition=menuInfo.position;
        SWITCH="cm";

        switch(item.getItemId()) {                    //点击不同项 所触发的不同事件
            case 1:
                pb.setVisibility(View.VISIBLE);
                showdetail(ItemID);                        //详细信息

                break;
            case 2:
                Log.d("getlistID",getListID);
                Log.d("itemID",ItemID);
                delete(ItemID);                             //删除



                break;
            case 3:

                edit(ItemID);                                 //编辑

                break;

            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////       设定optionmenu的方法
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);                                              //添加各项
        menu.add(Menu.NONE, MENU_DETAIL, 0, "信息").setIcon(R.drawable.net1314080903120detailinfo);
        menu.add(Menu.NONE, MENU_DELETE, 0, "删除").setIcon(R.drawable.net1314080903120deleteitem);
        menu.add(Menu.NONE, MENU_UPDATE, 0, "修改").setIcon(R.drawable.net1314080903120edititem);
        menu.add(Menu.NONE, MENU_EXIT, 0, "退出").setIcon(R.drawable.net1314080903120exit);

        return true;
    }
    /////////////////////////////////////////////////////////////////////////////////////// 实现各项的方法
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        switch (item.getItemId())
        {
            case MENU_DETAIL:                           //信息


                showdetail(getListID);

                break;
            case MENU_DELETE:                          //删除
                Log.d("getlistID",getListID);
                delete(getListID);


                break;
            case MENU_UPDATE:                            //修改

                edit(getListID);

                Log.d("update",""+item.getItemId());
            case MENU_EXIT:                                            //退出
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
                // System.exit(0);
                break;
        }

        return true;
    }

    ///////////////////////////////////////////////////////////////////////////
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    /////////////////////////////////////////////////////////////////////////////////定义动态数组  以键值对的方式
    List<Map<String,String>> getListItems(){

        List<Map<String,String>> listItems = new ArrayList<Map<String,String>>();

        listItems.clear();

        for(int i=0;i<cursor.getCount();i++){

            Map<String,String> map = new HashMap<String,String>();


            map.put("ID", ""+cursor.getString(0));                                      //放ID

            map.put("priority&topic", ""+"["+cursor.getString(6)+"]"+cursor.getString(2));         //放优先级和主题

            map.put("date&time",""+cursor.getString(3)+","+cursor.getString(4));              //放日期时间

            map.put("state", cursor.getString(7));                                      //放状态

            cursor.moveToNext();


            listItems.add(map);

        }

        cursor.moveToFirst();

        return listItems;
    }
    ////////////////////////////////////////////////////////////////////////////////////////// 定义快捷菜单中的各个方法
    public void showdetail(String getListID)
    {
        LayoutInflater usedisplayinfoxml=LayoutInflater.from(Net1314080903120Activity.this);       //把提示框的布局文件改成view类型
        final View displayview=usedisplayinfoxml.inflate(R.layout.net1314080903120infotipsview, null);


        topicInfo=(TextView)displayview.findViewById(R.id.topicInfo);
        periodinfo=(TextView)displayview.findViewById(R.id.periodinfo);
        dateInfo=(TextView)displayview.findViewById(R.id.dateInfo);
        timeInfo=(TextView)displayview.findViewById(R.id.timeInfo);
        priorityInfo=(TextView)displayview.findViewById(R.id.priorityInfo);
        detailInfo=(TextView)displayview.findViewById(R.id.detailInfo);



        cursorInfo=data.select(getListID);
        cursorInfo.moveToFirst();
        Log.d("CURSORINFO",cursorInfo.getString(2));

        topicInfo.setText("主题:"+cursorInfo.getString(2));                            //设置消息窗口的内容
        periodinfo.setText("计划期限:"+cursorInfo.getString(1));
        dateInfo.setText("日期:"+cursorInfo.getString(3));
        timeInfo.setText("时间:"+cursorInfo.getString(4));
        priorityInfo.setText("事件优先级:"+cursorInfo.getString(6));
        detailInfo.setText("备注:"+cursorInfo.getString(5));



        AlertDialog.Builder displayInfo=new AlertDialog.Builder(Net1314080903120Activity.this);
        displayInfo.setView(displayview);
        displayInfo.setTitle("我的计划");
        displayInfo.setIcon(R.drawable.net1314080903120ic_launcher);


        displayInfo.setPositiveButton("确定", new DialogInterface.OnClickListener() {           //设置消息窗口确定键的监听事件

            @Override
            public void onClick(DialogInterface dialog, int which) {

                pb.setVisibility(View.INVISIBLE);

            }
        });
        displayInfo.show();                                             //显示
    }

    public void edit(String ItemID)                                 //实现编辑事件
    {
        Intent intentToEdit=new Intent(Net1314080903120Activity.this,Net1314080903120editPage.class);
        Bundle sentId=new Bundle();
        sentId.putString("ID", ItemID);
        intentToEdit.putExtras(sentId);
        startActivity(intentToEdit);

    }

    public void delete(String getListID)                                //实现删除事件
    {
        final String getlistid=getListID;
        Log.d("test issss ",""+ItemID);
        AlertDialog.Builder cancelConfirm=new AlertDialog.Builder(Net1314080903120Activity.this);
        cancelConfirm.setTitle("确定删除么？");
        cancelConfirm.setPositiveButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                data.delete(getlistid);

                Toast.makeText(getApplicationContext(),"已经取消删除了！",Toast.LENGTH_SHORT).show();
            }
        });

        cancelConfirm.setNegativeButton("删除吧", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("get list IDDDDDDDD",getlistid);
                Log.d("get list getPoooooo",""+getPosition);
                Log.d("get list mPPPPPPPP",""+mposition);

                data.delete(getlistid);
                if(SWITCH.equals("om"))
                {

                    listitems.remove(mposition);


                }
                if(SWITCH.equals("cm"))
                {
                    listitems.remove(getPosition);    //对于位置的量要求需精确   不能随便更改类型  否则可能会因为丢失精度而定位错误
                    Log.d("cm",SWITCH);

                }
                listViewAdapter.notifyDataSetChanged();  //重绘当前可见区域
                Toast.makeText(getApplicationContext(),"已经删除了！",Toast.LENGTH_SHORT).show();




            }
        });
        cancelConfirm.show();

        //先移除已经删除的item，便于下面的这个东东检测出view的变化


    }


    /////////////////////////////////////////////////////////////////////////自定义的容器 绘制listview用的  继承了baseAdapter类


    public class projectAdapter extends BaseAdapter {

        private List<Map<String,String>> listItems;                                //接受参数数组
        Context context;

        private boolean[] hasChecked;                                       //记录复选框状态




        public final class ListItemView                 //设置listview的内容和格式
        {

            public TextView ID;
            public TextView period;
            public TextView prioritytopic;
            public TextView datetime;
            public TextView detail;
            public CheckBox check;
            public TextView state;

        }

        public projectAdapter(List<Map<String,String>> listItems,Context context)         //构造函数初始化
        {
            this.context=context;
            listContainer=LayoutInflater.from(context);
            this.listItems=listItems;
            //hasChecked = new boolean[getCount()];



        }


        @Override
        public int getCount() {

            return listItems.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        public boolean hasChecked(int checkedID)
        {
            return hasChecked[checkedID];
        }

        public void getID(int checkedID)
        {
            String id= listitems.get(checkedID).get("ID").toString();
            Log.d("checkID",""+id);
        }

        private void checkedChange(int checkedID, boolean isChecked)              //checkbox的状态处理 方法 标记事件是否完成
        {



            if(isChecked==true)
            {
                state="已完成";
                data.modifyState(listitems.get(checkedID).get("ID").toString(), state);
                Toast.makeText(getApplicationContext(), "计划已完成!", Toast.LENGTH_SHORT).show();


            }

            else
            {
                state="未完成";
                data.modifyState(listitems.get(checkedID).get("ID").toString(), state);
                Toast.makeText(getApplicationContext(), "革命尚未成功!", Toast.LENGTH_SHORT).show();


            }



        }




        @Override
        public View getView(int position, View convertView, ViewGroup parent) {    //最重要的地方  重写getview的方法

            final int selectID=position;

            ListItemView listItemView=null;

            if(convertView==null)
            {
                listItemView=new ListItemView();
                convertView=listContainer.inflate(R.layout.net1314080903120my_listitem, null);                         //设置view布局
                listItemView.prioritytopic=(TextView)convertView.findViewById(R.id.ItemPriorityTopic); //获得对象  记得加上布局名字后再加findviewbyid
                listItemView.datetime=(TextView)convertView.findViewById(R.id.ItemDateTime);
                listItemView.check = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(listItemView);                                              //设置空间集到convertView

            }
            else
            {
                listItemView=(ListItemView)convertView.getTag();
            }

            listItemView.prioritytopic.setText((String)listitems.get(position).get("priority&topic"));	  //设置listview的内容
            listItemView.datetime.setText((String)listitems.get(position).get("date&time"));

            getID(selectID);


            listItemView.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {          //listitem点击事件

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    checkedChange(selectID,isChecked);

                    Log.d("checked is pressed","yeah"+isChecked);

                }
            });



            return convertView;
        }
    }
}
