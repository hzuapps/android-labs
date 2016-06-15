package test.wjj.com.formdemo;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.EditText;

import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import fab_transformation.FabTransformation;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.nameeditText)EditText nameeditText;
    @InjectView(R.id.passwordeditText)EditText passwordeditText;
    @InjectView(R.id.sexswitch)Switch sexswitch;
    @InjectView(R.id.schoolspinner)Spinner schoolspinner;
    @InjectView(R.id.fab) FloatingActionButton fab;
    @InjectView(R.id.overlay) View overlay;
    @InjectView(R.id.sheet) View sheet;
    //性别
    String sex="";
    //公司
    String Schoole="";
    //spinner的适配器
    private ArrayAdapter<String> adapter;
    private List<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        init();
    }

    @OnClick(R.id.fab)
    void onClickFab() {
        if (fab.getVisibility() == View.VISIBLE) {
            FabTransformation.with(fab).setOverlay(overlay).transformTo(sheet);
        }
    }

    @OnClick({R.id.row_1,R.id.row_2})
    void onClickRow1(View view) {
        if(view.getId()==R.id.row_1){
            Toast.makeText(MainActivity.this,"提交",Toast.LENGTH_SHORT).show();
            process();
        }else if(view.getId()==R.id.row_2){
            Toast.makeText(MainActivity.this,"取消",Toast.LENGTH_SHORT).show();
        }
        if (fab.getVisibility() != View.VISIBLE) {
            FabTransformation.with(fab).setOverlay(overlay).transformFrom(sheet);
        }
    }

    @OnCheckedChanged(R.id.sexswitch)
    void onsexSwitch(boolean isChecked){
        if(isChecked){
            sex="女";
        }else{
            sex="男";
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (fab.getVisibility() != View.VISIBLE) {
            FabTransformation.with(fab).setOverlay(overlay).transformFrom(sheet);
            return;
        }
        super.onBackPressed();
    }

    private  void init(){
        //第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项
        list.add("新浪");
        list.add("腾讯");
        list.add("百度");
        list.add("网易");
        list.add("奇虎");

        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schoolspinner.setAdapter(adapter);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        schoolspinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Schoole = adapter.getItem(arg2);
                /* 将mySpinner 显示*/
                arg0.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                arg0.setVisibility(View.VISIBLE);
            }
        });
        /*下拉菜单弹出的内容选项触屏事件处理*/
        schoolspinner.setOnTouchListener(new Spinner.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        /*下拉菜单弹出的内容选项焦点改变事件处理*/
        schoolspinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void process(){

        if(nameeditText.getText().toString().length()<=0||nameeditText.getText().toString().equals("")){
            Toast.makeText(MainActivity.this,"姓名不能为空",Toast.LENGTH_SHORT).show();
        }else if(passwordeditText.getText().toString().length()<=0||passwordeditText.getText().toString().equals("")){
            Toast.makeText(MainActivity.this,"微信不能为空",Toast.LENGTH_SHORT).show();
        }else if(sex==null){
            Toast.makeText(MainActivity.this,"性别没有选",Toast.LENGTH_SHORT).show();
        }else if(Schoole.length()<=0||Schoole.equals("")){
            Toast.makeText(MainActivity.this,"公司没有选",Toast.LENGTH_SHORT).show();
//        }else if(SeekBarValue==0){
//            Toast.makeText(MainActivity.this,"请拖动进度条",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this,"名字叫："+nameeditText.getText().toString()+"  "+"微信是："+passwordeditText.getText()+"  "+
                    "性别是："+sex+"  "+"公司是："+Schoole+" ",Toast.LENGTH_LONG).show();
        }
    }
}
