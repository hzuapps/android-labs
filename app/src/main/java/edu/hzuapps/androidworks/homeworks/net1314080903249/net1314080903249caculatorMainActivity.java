package edu.hzuapps.androidworks.homeworks.net1314080903249;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText ea,eb,ec,ed;
    private Button ba,bb,bc,bd,be;
    private int number1,number2,number3;
    int a,b,c,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ea=(EditText)findViewById(R.id.ea);
        eb=(EditText)findViewById(R.id.eb);
        ec=(EditText)findViewById(R.id.ec);
        ed=(EditText)findViewById(R.id.ed);
        be=(Button)findViewById(R.id.be);
        ba=(Button)findViewById(R.id.ba);
        bb=(Button)findViewById(R.id.bb);
        bc=(Button)findViewById(R.id.bc);
        bd=(Button)findViewById(R.id.bd);
        be=(Button)findViewById(R.id.be);

//监听按钮点击事件
        this.ba.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {
//从EditText获取文本并转换成数字
                String str1 = ea.getText().toString();
                number1 = Integer.parseInt(str1);
                String str2 = ec.getText().toString();
                number2 = Integer.parseInt(str2);
                number3 = number2 + number1;
                eb.setText("+ ");
                ed.setText(number3+"");
            }
        });
        this.bb.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {
//从EditText获取文本并转换成数字
                String str1 = ea.getText().toString();
                number1 = Integer.parseInt(str1);
                String str2 = ec.getText().toString();
                number2 = Integer.parseInt(str2);
                number3 = number1 - number2;
                ed.setText(number3+"");
            }
        });
        this.bc.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {
//从EditText获取文本并转换成数字
                String str1 = ea.getText().toString();
                number1 = Integer.parseInt(str1);
                String str2 = ec.getText().toString();
                number2 = Integer.parseInt(str2);
                number3 = number2 * number1;
                ed.setText(number3+"");
            }
        });
        this.bd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {
//从EditText获取文本并转换成数字
                String str1 = ea.getText().toString();
                number1 = Integer.parseInt(str1);
                String str2 = ec.getText().toString();
                number2 = Integer.parseInt(str2);
                number3 = number1 / number2;
                ed.setText(number3+"");
            }
        });

    }



}
