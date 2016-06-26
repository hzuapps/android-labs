package com.example.intelligent_restranant_boss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//计算器
public class CalculatorActivity extends Activity implements OnClickListener {

	Button btn_left_header;
	TextView tv_head_title;

	View mView;
	String mString = "", mSecondString = "";
	EditText et_primary;
	/* EditText secondaryET; */
	int flag = 0, c = 0;
	double b = 0.0, g = 0.0, f = 0.0;

	// 对输出的数进行规范化
	public void substr() {
		int a = mString.indexOf(".");
		int b = mString.indexOf("E");
		String tempstr = mString;
		if (a == -1) {
			if (mString.length() > 12)
				mString = mString.substring(0, 12);
		}
		if (a > 0) {
			if (b == -1) {
				if (mString.length() > 12)
					mString = mString.substring(0, 12);
			}
			if (b > 0) {
				tempstr = mString.substring(b);
				if (mString.length() > 12) {
					int perStrLen = 12 - tempstr.length();
					mString = mString.substring(0, perStrLen) + tempstr;
				}
			}
		}

	}

	// 计算方法
	public double calc() {
		switch (c) {
		case 0:
			f = g;
			break;
		case 1:
			f = b + g;
			break;
		case 2:
			f = b - g;
			break;
		case 3:
			f = b * g;
			break;
		case 4:
			f = b / g;
			break;
		}
		b = f;
		c = 0;
		return f;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_calculator);
		findViewById();
		initTitle();

		// 获取所有按键，保存到数组当中去
		final Button number[] = new Button[10];
		final Button mark[] = new Button[13];
		// 获取功能按钮
		mark[0] = (Button) findViewById(R.id.markone);
		mark[1] = (Button) findViewById(R.id.marktwo);
		mark[2] = (Button) findViewById(R.id.markthree);
		mark[3] = (Button) findViewById(R.id.markfour);
		mark[4] = (Button) findViewById(R.id.markfive);
		mark[5] = (Button) findViewById(R.id.marksix);
		mark[6] = (Button) findViewById(R.id.markseven);
		mark[7] = (Button) findViewById(R.id.markeight);
		mark[8] = (Button) findViewById(R.id.marknine);
		mark[9] = (Button) findViewById(R.id.markten);
		mark[10] = (Button) findViewById(R.id.markeleven);
		mark[11] = (Button) findViewById(R.id.marktwelve);
		mark[12] = (Button) findViewById(R.id.markthreeteen);
		// 获取数字按钮
		number[0] = (Button) findViewById(R.id.btnzero);
		number[1] = (Button) findViewById(R.id.btnone);
		number[2] = (Button) findViewById(R.id.btntwo);
		number[3] = (Button) findViewById(R.id.btnthree);
		number[4] = (Button) findViewById(R.id.btnfour);
		number[5] = (Button) findViewById(R.id.btnfive);
		number[6] = (Button) findViewById(R.id.btnsix);
		number[7] = (Button) findViewById(R.id.btnseven);
		number[8] = (Button) findViewById(R.id.btneight);
		number[9] = (Button) findViewById(R.id.btnnine);
		// 获取主显示屏
		et_primary = (EditText) findViewById(R.id.et_show);
		// 获取副显示屏 secondaryET = (EditText) findViewById(R.id.edittext1_show);
		et_primary.setText(mString);
		/* secondaryET.setText(secstr); */
		// 对C按钮的事件的处理
		mark[0].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				b = 0.0;
				c = 0;
				g = 0.0;
				mString = "";
				et_primary.setText(mString);
				mSecondString = "";
				/* secondaryET.setText(secstr); */
			}
		});

		// 对CE按钮的事件的处理
		mark[1].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mString = "";
				et_primary.setText(mString);
				mView = v;
			}
		});

		// 对平方的事件的处理
		mark[2].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (mString != "") {
					double temp = Double.parseDouble(mString);
					mString = "" + temp * temp;
					substr();
					et_primary.setText(mString);
				}
			}
		});

		// 对根号的事件的处理
		mark[3].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (mString != "") {
					double temp = Double.parseDouble(mString);
					mString = Math.sqrt(temp) + "";
					substr();
					et_primary.setText(mString);
				}
			}
		});
		// 对backspace的事件的处理
		mark[4].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (mString != "") {
					if (mString.length() == 1) {
						mString = "";
						et_primary.setText(mString);
					} else {
						mString = mString.substring(0, mString.length() - 1);
						et_primary.setText(mString);
					}
				} else {
					et_primary.setText(mString);
				}
			}
		});

		// 对正负号的事件的处理
		mark[5].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (mView != mark[11] && mString != "") {
					char ch = mString.charAt(0);
					if (ch == '-')
						mString = mString.replace("-", "");
					else
						mString = "-" + mString;
					et_primary.setText(mString);
				}
			}
		});

		// 对求1/x事件的处理
		mark[6].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (mString != "") {
					Double d = Double.parseDouble(mString);
					Double s = 1.0 / d;
					mString = "" + s.toString();
					substr();
					et_primary.setText(mString);
				} else {
					et_primary.setText("0");
				}
			}
		});

		// 对除号的事件的处理
		mark[7].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (mString != "") {
					if (mView == mark[7] || mView == mark[8]
							|| mView == mark[9] || mView == mark[10]) {
						c = 4;
					} else {
						g = Double.parseDouble(mString);
						calc();
						mString = "" + f;
						substr();
						et_primary.setText(mString);
						c = 4;
						flag = 1;
						mView = v;
					}
				}
			}
		});

		// 对乘号的事件的处理
		mark[8].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (mString != "") {
					if (mView == mark[7] || mView == mark[8]
							|| mView == mark[9] || mView == mark[10]) {
						c = 3;
					} else {
						g = Double.parseDouble(mString);
						calc();
						mString = "" + f;
						substr();
						et_primary.setText(mString);
						c = 3;
						flag = 1;
						mView = v;
					}
				}
			}
		});

		// 对减号的事件的处理
		mark[9].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (mString != "") {
					if (mView == mark[7] || mView == mark[8]
							|| mView == mark[9] || mView == mark[10]) {
						c = 2;
					} else {
						g = Double.parseDouble(mString);
						calc();
						mString = "" + f;
						substr();
						et_primary.setText(mString);
						c = 2;
						flag = 1;
						mView = v;
					}
				}
			}
		});

		// 对加号的事件的处理
		mark[10].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (mString != "") {
					if (mView == mark[7] || mView == mark[8]
							|| mView == mark[9] || mView == mark[10]) {
						c = 1;
					} else {
						g = Double.parseDouble(mString);
						calc();
						mString = "" + f;
						substr();
						et_primary.setText(mString);
						c = 1;
						flag = 1;
						mView = v;
					}
				}
			}
		});

		// 对小数点的处理
		mark[11].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (mString == "") {
					mString += ".";
					et_primary.setText(mString);
				} else {
					char ch[];
					int x = 0;
					ch = mString.toCharArray();
					for (int i = 0; i < ch.length; i++)
						if (ch[i] == '.')
							x++;
					if (x == 0) {
						mString += ".";
						et_primary.setText(mString);
					}

				}
			}
		});

		// 对等号的事件的处理
		mark[12].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (mString != "" && mView != mark[7] && mView != mark[8]
						&& mView != mark[9] && mView != mark[10]) {
					g = Double.parseDouble(mString);
					calc();
					mString = "" + f;
					substr();
					et_primary.setText(mString);
					flag = 1;
					mView = v;

				}
			}
		});
		// 设定数字按键
		number[0].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (flag == 1) {
					mString = "";
					mString += 0;
					et_primary.setText(mString);
					flag = 0;
				} else {
					char ch1[];
					ch1 = mString.toCharArray();
					if (!(ch1.length == 1 && ch1[0] == '0')) {
						mString += 0;
						et_primary.setText(mString);
					}
				}
				mView = v;
			}
		});

		number[1].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (flag == 1) {
					mString = "";
					mString += 1;
					et_primary.setText(mString);
					flag = 0;
				} else {
					mString += 1;
					et_primary.setText(mString);
				}
				mView = v;
			}
		});

		number[2].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (flag == 1) {
					mString = "";
					mString += 2;
					et_primary.setText(mString);
					flag = 0;
				} else {
					mString += 2;
					et_primary.setText(mString);
				}
				mView = v;
			}
		});

		number[3].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (flag == 1) {
					mString = "";
					mString += 3;
					et_primary.setText(mString);
					flag = 0;
				} else {
					mString += 3;
					et_primary.setText(mString);
				}
				mView = v;
			}
		});

		number[4].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (flag == 1) {
					mString = "";
					mString += 4;
					et_primary.setText(mString);
					flag = 0;
				} else {
					mString += 4;
					et_primary.setText(mString);
				}
				mView = v;
			}
		});

		number[5].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (flag == 1) {
					mString = "";
					mString += 5;
					et_primary.setText(mString);
					flag = 0;
				} else {
					mString += 5;
					et_primary.setText(mString);
				}
				mView = v;
			}
		});

		number[6].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (flag == 1) {
					mString = "";
					mString += 6;
					et_primary.setText(mString);
					flag = 0;
				} else {
					mString += 6;
					et_primary.setText(mString);
				}
				mView = v;
			}
		});

		number[7].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (flag == 1) {
					mString = "";
					mString += 7;
					et_primary.setText(mString);
					flag = 0;
				} else {
					mString += 7;
					et_primary.setText(mString);
				}
				mView = v;
			}
		});

		number[8].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (flag == 1) {
					mString = "";
					mString += 8;
					et_primary.setText(mString);
					flag = 0;
				} else {
					mString += 8;
					et_primary.setText(mString);
				}
				mView = v;
			}
		});

		number[9].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (flag == 1) {
					mString = "";
					mString += 9;
					et_primary.setText(mString);
					flag = 0;
				} else {
					mString += 9;
					et_primary.setText(mString);
				}
				mView = v;
			}
		});
	}

	private void initTitle() {
		tv_head_title.setText("计算器");
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left_header:
			finish();
			break;

		default:
			break;
		}

	}

	private void findViewById() {
		btn_left_header = (Button) this.findViewById(R.id.btn_left_header);
		btn_left_header.setOnClickListener(this);
		tv_head_title = (TextView) this.findViewById(R.id.tv_head_title);

	}
}
