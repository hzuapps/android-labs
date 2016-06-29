package edu.hzuapps.androidworks.homeworks.com1314080901132;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener{
    Button btn_0;
    Button btn_1;
    Button btn_2;
    Button btn_3;
    Button btn_4;
    Button btn_5;
    Button btn_6;
    Button btn_7;
    Button btn_8;
    Button btn_9;
    Button btn_asterisk;
    Button btn_pound_key;
    ImageButton btn_dial;
    ImageButton btn_msg;
    ImageButton btn_del;
    static StringBuilder buffer;//用来存放输入数字的sb
    EditText et_input;//电话号码输入

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_keyboard_layout);

        //以下是实例化按钮
        btn_0 = (Button) findViewById(R.id.zero);
        btn_1 = (Button) findViewById(R.id.one);
        btn_2 = (Button) findViewById(R.id.two);
        btn_3 = (Button) findViewById(R.id.three);
        btn_4 = (Button) findViewById(R.id.four);
        btn_5 = (Button) findViewById(R.id.five);
        btn_6 = (Button) findViewById(R.id.six);
        btn_7 = (Button) findViewById(R.id.seven);
        btn_8 = (Button) findViewById(R.id.eight);
        btn_9 = (Button) findViewById(R.id.nine);
        btn_asterisk = (Button) findViewById(R.id.asterisk);
        btn_pound_key = (Button) findViewById(R.id.pound_key);
        btn_dial = (ImageButton) findViewById(R.id.dial);
        btn_msg = (ImageButton) findViewById(R.id.msg);
        btn_del = (ImageButton) findViewById(R.id.del);
        buffer = new StringBuilder();
        et_input = (EditText)findViewById(R.id.et_input);

        /*
        //设置控件EditText不能输入文本
        et_input.setKeyListener(null);
        */
        et_input.setFocusable(false);//不让该et_input获得焦点
        et_input.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                et_input.setInputType(InputType.TYPE_NULL); // 关闭软键盘，这样当点击该et_input的时候，不会弹出系统自带的输入法
                return false;
            }
        });


        //以下设置按钮点击事件
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_asterisk.setOnClickListener(this);
        btn_0.setOnClickListener(this);
        btn_pound_key.setOnClickListener(this);
        btn_dial.setOnClickListener(this);
        btn_msg.setOnClickListener(this);
        btn_del.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dial:
                call();
                et_input.setText((buffer.delete(0,buffer.length())).toString().trim());
                break;
                /*
                if (userTel != null && !"".equals(userTel.toString().trim())
                        && !"null".equals(userTel.toString().trim().toLowerCase())) {
                    if (loadingDialog == null)
                        loadingDial
                    // String userTel = (String)et_inpog = AlertUtil
                                .buildLoadingDialog(ToufangActivity.this);
                    loadingDialog.show();
                    // 判断是否填写手机号ut.getText().toString();
                    sendCouponByTelHandler = new SendCouponByTelHandler(offer);
                    if (userTel != null
                            && !"".equals(userTel.toString().trim())
                            && !"null".equals(userTel.toString().trim()
                            .toLowerCase())) {
                        // 创建线程 （生成验证码+绑定验证码）
                        if (match(userTel)) {// 验证手机号是否合法
                            new SendCouponByTelThread(offer, userTel).start();
                        } else {
                            loadingDialog.dismiss();
                            AlertUtil.toast(ToufangActivity.this,
                                    R.drawable.dialog_err_icon, "请填写正确的手机号!");
                            buffer.replace(0, buffer.length(), "");
                            et_input.setText("");
                        }
                    }
                } else {
                    AlertUtil.toast(ToufangActivity.this,
                            R.drawable.dialog_err_icon, "请填写手机号!");
                }
                */
            case R.id.msg:
                send();
                break;
            case R.id.del:
                if (buffer.length() - 1 >= 0) {
                    buffer.delete(buffer.length() - 1, buffer.length());
                    et_input.setText(buffer.toString().trim());
                }
                break;

            case R.id.one:
                buffer.append(btn_1.getText().toString().trim());
                et_input.setText(buffer.toString().trim());
                break;
            case R.id.two:
                buffer.append(btn_2.getText().toString().trim());
                et_input.setText(buffer.toString().trim());
                break;
            case R.id.three:
                buffer.append(btn_3.getText().toString().trim());
                et_input.setText(buffer.toString().trim());
                break;
            case R.id.four:
                buffer.append(btn_4.getText().toString().trim());
                et_input.setText(buffer.toString().trim());
                break;
            case R.id.five:
                buffer.append(btn_5.getText().toString().trim());
                et_input.setText(buffer.toString().trim());
                break;
            case R.id.six:
                buffer.append(btn_6.getText().toString().trim());
                et_input.setText(buffer.toString().trim());
                break;
            case R.id.seven:
                buffer.append(btn_7.getText().toString().trim());
                et_input.setText(buffer.toString().trim());
                break;
            case R.id.eight:
                buffer.append(btn_8.getText().toString().trim());
                et_input.setText(buffer.toString().trim());
                break;
            case R.id.nine:
                buffer.append(btn_9.getText().toString().trim());
                et_input.setText(buffer.toString().trim());
                break;
            case R.id.asterisk:
                buffer.append(btn_asterisk.getText().toString().trim());
                et_input.setText(buffer.toString().trim());
                break;
            case R.id.zero:
                buffer.append(btn_0.getText().toString().trim());
                et_input.setText(buffer.toString().trim());
                break;
            case R.id.pound_key:
                buffer.append(btn_pound_key.getText().toString().trim());
                et_input.setText(buffer.toString().trim());
                break;
        }
    }
    private void call(){
        String userTel = et_input.getText().toString();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:"+userTel));
        //startActivityForResult(intent,1);
        startActivity(intent);
       // Toast.makeText(MainActivity.this,"11111111",Toast.LENGTH_SHORT).show();
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(MainActivity.this,"你hi当皇帝",Toast.LENGTH_SHORT).show();
    }
    */

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(MainActivity.this,"佳煜帅哥！",Toast.LENGTH_SHORT).show();
    }

    private void send(){
        /*
          //第一种方式：
          Intent intent = new Intent(this,MsgActivity.class);
          startActivity(intent);
          //finish();
         */

        //第二种方式：
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,MsgActivity.class);
        startActivity(intent);
        //MainActivity.this.finish();
    }

}
