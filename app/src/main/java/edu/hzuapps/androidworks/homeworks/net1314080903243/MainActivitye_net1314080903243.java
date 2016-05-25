package edu.hzuapps.androidworks.homeworks.net1314080903243;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import java.math.BigDecimal;
import java.math.BigInteger;

import java.math.BigInteger;

public class MainActivitye_net1314080903243 extends AppCompatActivity {
    //声明两个参数。接收tvResult前后的值
    String num1 = "0";
    String num2 = "0";
    public BigDecimal  Result =null;//计算结果
    int op = 0;//判断操作数，
    boolean isClickEqu = false;//判断是否按了“=”按钮
    private Button btn0 = null;
    private Button btn1 = null;
    private Button btn2 = null;
    private Button btn3 = null;
    private Button btn4 = null;
    private Button btn5 = null;
    private Button btn6 = null;
    private Button btn7 = null;
    private Button btn8 = null;
    private Button btn9 = null;
    private Button btnBackspace = null;
    private Button btnCE = null;
    private Button btnC = null;
    private Button btnAdd = null;
    private Button btnSub = null;
    private Button btnMul = null;
    private Button btnDiv = null;
    private Button btnEqu = null;
    private TextView tvResult = null;
    private Button btnFactorial = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitye_net1314080903243);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //从布局文件中获取控件，
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btnBackspace = (Button) findViewById(R.id.btnBackspace);
        btnCE = (Button) findViewById(R.id.btnCE);
        btnC = (Button) findViewById(R.id.btnC);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMul = (Button) findViewById(R.id.btnMul);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnFactorial = (Button) findViewById(R.id.butfactorial);
        tvResult = (EditText) findViewById(R.id.tvResult);
        btnEqu = (Button) findViewById(R.id.btnEqu);
        tvResult.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });


        btnC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tvResult.setText(null);
                num1 = "0";
                num2 = "0";
                Result =null;//计算结果
                op = 0;//判断操作数，
                isClickEqu = false;//判断是否按了“=”按钮

            }
        });


        btnBackspace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String myStr = tvResult.getText().toString();
                try {
                    tvResult.setText(myStr.substring(0, myStr.length() - 1));
                } catch (Exception e) {
                    tvResult.setText("");
                }

            }
        });

        btnCE.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tvResult.setText(null);
            }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isClickEqu) {
                    tvResult.setText(null);
                    isClickEqu = false;
                }
                String myString = tvResult.getText().toString();
                myString += "0";
                tvResult.setText(myString);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isClickEqu) {
                    tvResult.setText(null);
                    isClickEqu = false;
                }
                String myString = tvResult.getText().toString();
                myString += "1";
                tvResult.setText(myString);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isClickEqu) {
                    tvResult.setText(null);
                    isClickEqu = false;
                }
                String myString = tvResult.getText().toString();
                myString += "2";
                tvResult.setText(myString);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isClickEqu) {
                    tvResult.setText(null);
                    isClickEqu = false;
                }
                String myString = tvResult.getText().toString();
                myString += "3";
                tvResult.setText(myString);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isClickEqu) {
                    tvResult.setText(null);
                    isClickEqu = false;
                }
                String myString = tvResult.getText().toString();
                myString += "4";
                tvResult.setText(myString);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isClickEqu) {
                    tvResult.setText(null);
                    isClickEqu = false;
                }
                String myString = tvResult.getText().toString();
                myString += "5";
                tvResult.setText(myString);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isClickEqu) {
                    tvResult.setText(null);
                    isClickEqu = false;
                }
                String myString = tvResult.getText().toString();
                myString += "5";
                tvResult.setText(myString);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isClickEqu) {
                    tvResult.setText(null);
                    isClickEqu = false;
                }
                String myString = tvResult.getText().toString();
                myString += "6";
                tvResult.setText(myString);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isClickEqu) {
                    tvResult.setText(null);
                    isClickEqu = false;
                }
                String myString = tvResult.getText().toString();
                myString += "7";
                tvResult.setText(myString);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isClickEqu) {
                    tvResult.setText(null);
                    isClickEqu = false;
                }
                String myString = tvResult.getText().toString();
                myString += "8";
                tvResult.setText(myString);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isClickEqu) {
                    tvResult.setText(null);
                    isClickEqu = false;
                }
                String myString = tvResult.getText().toString();
                tvResult.setText(myString);
                myString += "9";
                tvResult.setText(myString);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String myStringAdd = tvResult.getText().toString();
                if (myStringAdd.equals(null)) {
                    return;
                }
                num1 = String.valueOf(myStringAdd);
                tvResult.setText(null);
                op = 1;
                isClickEqu = false;
            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String myStringSub = tvResult.getText().toString();
                if (myStringSub.equals(null)) {
                    return;
                }
                num1 = String.valueOf(myStringSub);
                tvResult.setText(null);
                op = 2;
                isClickEqu = false;
            }
        });
        btnMul.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String myStringMul = tvResult.getText().toString();
                if (myStringMul.equals(null)) {
                    return;
                }
                num1 = String.valueOf(myStringMul);
                tvResult.setText(null);
                op = 3;
                isClickEqu = false;
            }
        });
        btnDiv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String myStringDiv = tvResult.getText().toString();
                if (myStringDiv.equals(null)) {
                    return;
                }
                num1 = String.valueOf(myStringDiv);
                tvResult.setText(null);
                op = 4;
                isClickEqu = false;
            }
        });
        btnEqu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String myStringEqu = tvResult.getText().toString();
                System.out.print("11111111111111111111" + num2 + "    ---------num1" + num1);
                if (myStringEqu == null) {
                    return;
                }
                num2 = myStringEqu;
                //  tvResult.setText(null);
                BigDecimal  num3 = new BigDecimal(num1);
                BigDecimal num4 = new BigDecimal(myStringEqu);
                System.out.print(num2 + "    ---------num1" + num1);
                switch (op) {
                    case 0:
                        Result = num4;
                        break;
                    case 1:
                        Result = num3.add(num4);
                        break;
                    case 2:
                        Result = num3.subtract(num4);
                        break;
                    case 3:
                        Result = num3.multiply(num4);
                        break;
                    case 4:
                        int scale=5;
                        Result = num3.divide(num4);
                        break;
                    default:
                        Result = null;
                        break;
                }
                tvResult.setText(String.valueOf(Result));
                isClickEqu = true;

            }

        });
        btnFactorial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String myStringDiv = tvResult.getText().toString();
                int n = Integer.valueOf( myStringDiv);
                System.out.print("测试数据---------------------------"+n);
                if (n >0) {
                    BigInteger  Result = new BigInteger("1");
                    for (; n > 0; n--) {
                        // 将数字n转换成字符串后，再构造一个BigInteger对象，与现有结果做乘法
                        Result =  Result.multiply(new BigInteger(new Integer(n).toString()));
                    }
                } else if (n == 0) {
                    BigInteger Result = new BigInteger("0");
                }
                // 将数组换成字符串后构造BigInteger

                tvResult.setText(String.valueOf(Result));
                isClickEqu = true;
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activitye_net1314080903243, menu);
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MainActivitye_net1314080903243 Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://edu.hzuapps.androidworks.homeworks.net1314080903243/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MainActivitye_net1314080903243 Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://edu.hzuapps.androidworks.homeworks.net1314080903243/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}


