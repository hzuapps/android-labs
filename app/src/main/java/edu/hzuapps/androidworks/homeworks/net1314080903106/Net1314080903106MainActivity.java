package net1314080903106.homeworks.androidworks.hzuapps.edu.net1314080903106;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Net1314080903106MainActivity extends AppCompatActivity {
    int[] imageIds = new int[] { R.drawable.net1314080903106shoe_ok, R.drawable.net1314080903106shoe_sorry,
            R.drawable.net1314080903106shoe_sorry};
    private ImageView image1;		//ImageView组件1
    private ImageView image2;		//ImageView组件2
    private ImageView image3;		//ImageView组件3
    private TextView result;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903106_main);//设置显示界面
        image1 = (ImageView) findViewById(R.id.imageView1);
        image2 = (ImageView) findViewById(R.id.imageView2);
        image3 = (ImageView) findViewById(R.id.imageView3);
        result = (TextView) findViewById(R.id.textView1);
        reset();//随机
        image1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                isRight(v, 0);
            }
        });
        image2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                isRight(v, 1);
            }
        });
        image3.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                isRight(v, 2);
            }
        });
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                reset();
                result.setText(R.string.title);
                image1.setAlpha(255);
                image2.setAlpha(255);
                image3.setAlpha(255);
                image1.setImageDrawable(getResources().getDrawable(
                        R.drawable.net1314080903106shoe_default));
                image2.setImageDrawable(getResources().getDrawable(
                        R.drawable.net1314080903106shoe_default));
                image3.setImageDrawable(getResources().getDrawable(
                        R.drawable.net1314080903106shoe_default));
            }

        });
    }

    protected void isRight(View v, int index) {

        image1.setImageDrawable(getResources().getDrawable(imageIds[0]));//显示结果
        image2.setImageDrawable(getResources().getDrawable(imageIds[1]));
        image3.setImageDrawable(getResources().getDrawable(imageIds[2]));
        image1.setAlpha(100);
        image2.setAlpha(100);
        image3.setAlpha(100);
        ImageView v1 = (ImageView) v;
        v1.setAlpha(255);
        if (imageIds[index] == R.drawable.net1314080903106shoe_ok){
            result.setText("恭喜您，猜对了，祝你幸福！");
        } else {
            result.setText("很抱歉，猜错了，要不要再试一次？");
        }
    }

    private void reset() {

        for (int i = 0; i < 3; i++) {
            int temp = imageIds[i];				//将数组元素i保存到临时变量中
            int index = (int) (Math.random() * 2);		//生成一个随机数0 or 1
            imageIds[i] = imageIds[index];			//将随机数指定的数组元素的内容赋值给数组元素i
            imageIds[index] = temp;
        }
    }

}
