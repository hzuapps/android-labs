package edu.hzuapps.androidworks.homeworks3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_BaseColumns;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_CauseInfo;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_CollectColumns;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_DBManager;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_ErrorColumns;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_ExamErrorColumns;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_ExamResultColumns;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_HisResult;
import edu.hzuapps.androidworks.homeworks4.Net1314080903124_TimeUtils;

import com.mobile.cetfour.R;

public class Net1314080903124_ResultActivity extends Activity implements OnClickListener {

	private TextView yida;
	private TextView weida;
	private TextView dadui;
	private TextView haoshi;
	private TextView other;
	private TextView search;
	private int intYida;
	private int intWeida;
	private int intDadui;
	private int intHaoshi;
	private int intDefen;
	private TextView score;
	private int time;
	public static String TIME = "time";
	private String curTime;
	private String exam_name;

	public static void intentToResultActivity(Context context, int time, String name) {
		Intent intent = new Intent(context, Net1314080903124_ResultActivity.class);
		intent.putExtra(TIME, time);
		intent.putExtra(Net1314080903124_ExamActivity.NAME, name);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		curTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		setContentView(R.layout.activity_result);
		resetTitlebar();
		time = getIntent().getIntExtra(TIME, 0);
		exam_name = getIntent().getStringExtra(Net1314080903124_ExamActivity.NAME);
		score = (TextView) findViewById(R.id.score);
		yida = (TextView) findViewById(R.id.yida);
		weida = (TextView) findViewById(R.id.weida);
		dadui = (TextView) findViewById(R.id.dadui);
		haoshi = (TextView) findViewById(R.id.haoshi);
		other = (TextView) findViewById(R.id.other);
		search = (TextView) findViewById(R.id.search);
		new QueryTask().execute();
		search.setOnClickListener(this);
	}

	private class QueryTask extends AsyncTask<Void, Void, ArrayList<Net1314080903124_CauseInfo>> {
		@Override
		protected ArrayList<Net1314080903124_CauseInfo> doInBackground(Void... params) {
			ArrayList<Net1314080903124_CauseInfo> list = Net1314080903124_DBManager.getInstance(Net1314080903124_ResultActivity.this).query(Net1314080903124_ExamResultColumns.TABLE_NAME);
			return list;
		}

		@Override
		protected void onPostExecute(ArrayList<Net1314080903124_CauseInfo> list) {
			if (list.size() == 0) {
				yida.setText("已答：" + 0 + "题");
				weida.setText("未答：" + 20 + "题");
				dadui.setText("答对：" + 0 + "题");
				haoshi.setText("耗时：" + Net1314080903124_TimeUtils.secToTime(60 * 10 - time));
				score.setText("得分：" + 0 + "分");
				return;
			}
			for (int i = 0; i < list.size(); i++) {
				Net1314080903124_CauseInfo net1314080903124_CauseInfo = list.get(i);
				int reply = net1314080903124_CauseInfo.getReply();
				String types = net1314080903124_CauseInfo.getTypes();
				String daan_one = net1314080903124_CauseInfo.getDaan_one();
				String daan_tow = net1314080903124_CauseInfo.getDaan_tow();
				String daan_three = net1314080903124_CauseInfo.getDaan_three();
				String daan_four = net1314080903124_CauseInfo.getDaan_four();
				if (reply == Net1314080903124_BaseColumns.NULL) {
					intWeida++;
				} else {
					intYida++;
					int rightIntValue = getRightIntValue(daan_one, daan_tow, daan_three, daan_four, types);
					if (rightIntValue == reply) {
						intDadui++;
					} else {
						Net1314080903124_DBManager.getInstance(Net1314080903124_ResultActivity.this).insert(Net1314080903124_ExamErrorColumns.TABLE_NAME, net1314080903124_CauseInfo);
					}
				}
			}
			String useTime = "" + Net1314080903124_TimeUtils.secToTime(60 * 10 - time);
			String hisResult = "" + intDadui * 5 + "分";
			Net1314080903124_HisResult myData = new Net1314080903124_HisResult(curTime, useTime, hisResult, exam_name);
			Net1314080903124_DBManager.getInstance(Net1314080903124_ResultActivity.this).insertHisResult(myData);
			yida.setText("已答：" + intYida + "题");
			weida.setText("未答：" + intWeida + "题");
			dadui.setText("答对：" + intDadui + "题");
			haoshi.setText("耗时：" + Net1314080903124_TimeUtils.secToTime(60 * 10 - time));
			score.setText("得分：" + intDadui * 5 + "分");
			if (intDadui * 5 >= 90) {
				other.setText("成绩还不错，再接再厉哦！");
			} else if (intDadui * 5 == 100) {
				other.setText("您太厉害了，全答对！");
			}
		}
	}

	private void resetTitlebar() {
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.view_comm_titlebar);
		final TextView title = (TextView) findViewById(R.id.titlebar_title);
		LinearLayout back = (LinearLayout) findViewById(R.id.titlebar_left_layout);
		title.setText("本次测试结果");
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private int getRightIntValue(String daan_one, String daan_tow, String daan_three, String daan_four, String types) {
		boolean isRightA = TextUtils.isEmpty(daan_one) ? false : true;
		boolean isRightB = TextUtils.isEmpty(daan_tow) ? false : true;
		boolean isRightC = TextUtils.isEmpty(daan_three) ? false : true;
		boolean isRightD = TextUtils.isEmpty(daan_four) ? false : true;
		int RIGHT_ANSWER = 0;
		if ("单选".equals(types)) {
			if (isRightA == true && isRightB == false && isRightC == false && isRightD == false) {
				RIGHT_ANSWER = Net1314080903124_BaseColumns.A;
			} else if (isRightA == false && isRightB == true && isRightC == false && isRightD == false) {
				RIGHT_ANSWER = Net1314080903124_BaseColumns.B;
			} else if (isRightA == false && isRightB == false && isRightC == true && isRightD == false) {
				RIGHT_ANSWER = Net1314080903124_BaseColumns.C;
			} else if (isRightA == false && isRightB == false && isRightC == false && isRightD == true) {
				RIGHT_ANSWER = Net1314080903124_BaseColumns.D;
			}
		} else if ("多选".equals(types)) {
			if (isRightA == true && isRightB == true && isRightC == false && isRightD == false) {
				RIGHT_ANSWER = Net1314080903124_BaseColumns.AB;
			} else if (isRightA == true && isRightC == true && isRightB == false && isRightD == false) {
				RIGHT_ANSWER = Net1314080903124_BaseColumns.AC;
			} else if (isRightA == true && isRightD == true && isRightB == false && isRightC == false) {
				RIGHT_ANSWER = Net1314080903124_BaseColumns.AD;
			} else if (isRightB == true && isRightC == true && isRightA == false && isRightD == false) {
				RIGHT_ANSWER = Net1314080903124_BaseColumns.BC;
			} else if (isRightB == true && isRightD == true && isRightA == false && isRightC == false) {
				RIGHT_ANSWER = Net1314080903124_BaseColumns.BD;
			} else if (isRightC == true && isRightD == true && isRightA == false && isRightB == false) {
				RIGHT_ANSWER = Net1314080903124_BaseColumns.CD;
			} else if (isRightA == true && isRightB == true && isRightC == true && isRightD == false) {
				RIGHT_ANSWER = Net1314080903124_BaseColumns.ABC;
			} else if (isRightA == true && isRightB == true && isRightD == true && isRightC == false) {
				RIGHT_ANSWER = Net1314080903124_BaseColumns.ABD;
			} else if (isRightA == true && isRightC == true && isRightD == true && isRightB == false) {
				RIGHT_ANSWER = Net1314080903124_BaseColumns.ACD;
			} else if (isRightB == true && isRightC == true && isRightD == true && isRightA == false) {
				RIGHT_ANSWER = Net1314080903124_BaseColumns.BCD;
			} else if (isRightA == true && isRightB == true && isRightC == true && isRightD == true) {
				RIGHT_ANSWER = Net1314080903124_BaseColumns.ABCD;
			}
		} else if ("判断".equals(types)) {
			if (isRightA == true && isRightB == false && isRightC == false && isRightD == false) {
				RIGHT_ANSWER = Net1314080903124_BaseColumns.TRUE;
			} else if (isRightA == false && isRightB == true && isRightC == false && isRightD == false) {
				RIGHT_ANSWER = Net1314080903124_BaseColumns.FALSE;
			}
		}

		return RIGHT_ANSWER;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Net1314080903124_DBManager.getInstance(this).removeAll(Net1314080903124_ExamResultColumns.TABLE_NAME);
		Net1314080903124_DBManager.getInstance(this).removeAll(Net1314080903124_ExamErrorColumns.TABLE_NAME);
		Net1314080903124_DBManager.getInstance(this).updateWhenDestroy(Net1314080903124_CollectColumns.TABLE_NAME);
		Net1314080903124_DBManager.getInstance(this).updateWhenDestroy(Net1314080903124_ErrorColumns.TABLE_NAME);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search:
			startActivity(new Intent(this, Net1314080903124_ExamErrorActivity.class));
			break;

		default:
			break;
		}
	}

}
