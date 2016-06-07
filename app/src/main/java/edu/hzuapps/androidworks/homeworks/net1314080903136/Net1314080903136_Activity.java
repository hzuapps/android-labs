package edu.hzuapps.androidworks.homeworks;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class Net1314080903136_Activity extends Activity {
	private RelativeLayout root;
	private String key;
	private String encryptFrom, encryptTo; // 加密文件名称，加密之后文件保存路径
	private String decryptFrom, decryptTo; // 解密文件名称，解密之后文件保存路径
	private EditText keyEditText, editText1, editText2, editText3, editText4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
		setContentView(root);
	}

	private void init() {
		// TODO Auto-generated method stub
		root = new RelativeLayout(this);
		root.setBackgroundColor(Color.WHITE);
		root.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));

		keyEditText = new EditText(this);
		keyEditText.setId(10);
		keyEditText.setHint("输入秘钥");
		RelativeLayout.LayoutParams keyParams = new RelativeLayout.LayoutParams(
				-2, -2);
		keyParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

		editText1 = new EditText(this);
		editText1.setId(11);
		editText1.setHint("加密文件名称：默认sdcard根目录");
		RelativeLayout.LayoutParams edit1Params = new RelativeLayout.LayoutParams(
				-2, -2);
		edit1Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		edit1Params.addRule(RelativeLayout.BELOW, 10);

		editText2 = new EditText(this);
		editText2.setId(12);
		editText2.setHint("加密保存路径：默认sdcard根目录");
		RelativeLayout.LayoutParams edit2Params = new RelativeLayout.LayoutParams(
				-2, -2);
		edit2Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		edit2Params.addRule(RelativeLayout.BELOW, 11);

		Button button1 = new Button(this);
		button1.setId(13);
		button1.setText("加密");
		button1.setOnClickListener(new Button1OnClickListener());
		RelativeLayout.LayoutParams b1Params = new RelativeLayout.LayoutParams(
				-2, -2);
		b1Params.addRule(RelativeLayout.BELOW, 12);
		b1Params.addRule(RelativeLayout.CENTER_HORIZONTAL);

		editText3 = new EditText(this);
		editText3.setId(14);
		editText3.setHint("解密文件名称：默认sdcard根目录");
		RelativeLayout.LayoutParams edit3Params = new RelativeLayout.LayoutParams(
				-2, -2);
		edit3Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		edit3Params.addRule(RelativeLayout.BELOW, 13);

		editText4 = new EditText(this);
		editText4.setId(15);
		editText4.setHint("解密保存路径：默认sdcard根目录");
		RelativeLayout.LayoutParams edit4Params = new RelativeLayout.LayoutParams(
				-2, -2);
		edit4Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		edit4Params.addRule(RelativeLayout.BELOW, 14);

		Button button2 = new Button(this);
		button2.setId(16);
		button2.setText("解密");
		button2.setOnClickListener(new Button2OnClickListener());
		RelativeLayout.LayoutParams b2Params = new RelativeLayout.LayoutParams(
				-2, -2);
		b2Params.addRule(RelativeLayout.BELOW, 15);
		b2Params.addRule(RelativeLayout.CENTER_HORIZONTAL);

//		root.addView(keyEditText, keyParams);
		root.addView(editText1, edit1Params);
		root.addView(editText2, edit2Params);
		root.addView(button1, b1Params);
		root.addView(editText3, edit3Params);
		root.addView(editText4, edit4Params);
		root.addView(button2, b2Params);
	}

	/**
	 * 加密
	 * 
	 * @author 许焕然
	 * 
	 */
	private final class Button1OnClickListener implements View.OnClickListener {

		public void onClick(View v) {
			key = keyEditText.getText().toString();
			encryptFrom = editText1.getText().toString();
			encryptTo = editText2.getText().toString();
			Net1314080903136_FileDES net1314080903136_FileDES;
			try {
				net1314080903136_FileDES = new Net1314080903136_FileDES("spring.sky");
				net1314080903136_FileDES.doEncryptFile("sdcard/" + encryptFrom, "sdcard/"
						+ encryptTo); // 加密
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 解密
	 * 
	 * @author 许焕然
	 * 
	 */
	private final class Button2OnClickListener implements View.OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			key = keyEditText.getText().toString();
			decryptFrom = editText3.getText().toString();
			decryptTo = editText4.getText().toString();
			Net1314080903136_FileDES net1314080903136_FileDES;
			try {
				net1314080903136_FileDES = new Net1314080903136_FileDES("spring.sky");
				net1314080903136_FileDES.doDecryptFile("sdcard/" + decryptFrom, "sdcard/"
						+ decryptTo); // 加密
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
