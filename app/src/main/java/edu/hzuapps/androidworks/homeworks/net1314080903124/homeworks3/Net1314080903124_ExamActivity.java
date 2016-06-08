package edu.hzuapps.androidworks.homeworks3;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_AnswerColumns;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_BaseColumns;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_CauseInfo;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_CauseInfoHasId;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_CollectColumns;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_DBManager;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_ErrorColumns;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_ExamResultColumns;
import edu.hzuapps.androidworks.homeworks4.Net1314080903124_ConfigPreferences;
import edu.hzuapps.androidworks.homeworks4.Net1314080903124_TimeUtils;
import edu.hzuapps.androidworks.homeworks4.Net1314080903124_ViewHolder;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.cetfour.R;

/**
 * 模拟考试
 * 
 * @author Summer
 * 
 */
public class Net1314080903124_ExamActivity extends Activity implements OnClickListener, OnItemClickListener {

	private ArrayList<Net1314080903124_CauseInfo> list;
	private TextView selectOne;
	private TextView selectTwo;
	private TextView selectThree;
	private TextView selectFour;
	private TextView title;
	private TextView answer;
	private TextView type;
	private ImageView imageOne;
	private ImageView imageTwo;
	private ImageView imageThree;
	private ImageView imageFour;
	private RelativeLayout relOne;
	private RelativeLayout relTwo;
	private RelativeLayout relThree;
	private RelativeLayout relFour;
	private TextView subjectTop;
	private int i = 0;
	// private int replySub = 0;
	// private double rightSub = 0;
	// private double wrongSub = 0;
	private TextView submit;
	private boolean isSelectA;
	private boolean isSelectB;
	private boolean isSelectC;
	private boolean isSelectD;
	// private TextView rightAnswer;
	private MyAdapter myAdapter;
	private ArrayList<Net1314080903124_CauseInfoHasId> listHasId;
	private Dialog dialogs;
	private boolean isClickSelectSubject;
	private int time = 60 * 10;
	private Timer timer = new Timer();
	private String exam_name;
	public static String NAME = "name";

	public static void intentToExamActivity(Context context, String name) {
		Intent intent = new Intent(context, Net1314080903124_ExamActivity.class);
		intent.putExtra(NAME, name);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		exam_name = getIntent().getStringExtra(NAME);
		setContentView(R.layout.activity_exam);
		resetTitlebar();
		title = (TextView) findViewById(R.id.title);
		selectOne = (TextView) findViewById(R.id.selectOne);
		selectTwo = (TextView) findViewById(R.id.selectTwo);
		selectThree = (TextView) findViewById(R.id.selectThree);
		selectFour = (TextView) findViewById(R.id.selectFour);
		// rightAnswer = (TextView) findViewById(R.id.rightAnswer);
		answer = (TextView) findViewById(R.id.answer);
		type = (TextView) findViewById(R.id.type);
		RelativeLayout back = (RelativeLayout) findViewById(R.id.back);
		RelativeLayout subject = (RelativeLayout) findViewById(R.id.subject);
		RelativeLayout collect = (RelativeLayout) findViewById(R.id.collect);
		RelativeLayout forward = (RelativeLayout) findViewById(R.id.forward);
		relOne = (RelativeLayout) findViewById(R.id.relOne);
		relTwo = (RelativeLayout) findViewById(R.id.relTwo);
		relThree = (RelativeLayout) findViewById(R.id.relThree);
		relFour = (RelativeLayout) findViewById(R.id.relFour);
		selectOne = (TextView) findViewById(R.id.selectOne);
		selectTwo = (TextView) findViewById(R.id.selectTwo);
		selectThree = (TextView) findViewById(R.id.selectThree);
		selectFour = (TextView) findViewById(R.id.selectFour);
		imageOne = (ImageView) findViewById(R.id.imageOne);
		imageTwo = (ImageView) findViewById(R.id.imageTwo);
		imageThree = (ImageView) findViewById(R.id.imageThree);
		imageFour = (ImageView) findViewById(R.id.imageFour);
		subjectTop = (TextView) findViewById(R.id.tv_subjectTop);
		submit = (TextView) findViewById(R.id.submit);
		// 先读取数据库中的缓存, 数据量较多比较耗时，应使用AsyncTask
		new QueryTask().execute();
		submit.setOnClickListener(this);
		selectOne.setOnClickListener(this);
		selectTwo.setOnClickListener(this);
		selectThree.setOnClickListener(this);
		selectFour.setOnClickListener(this);
		back.setOnClickListener(this);
		subject.setOnClickListener(this);
		collect.setOnClickListener(this);
		forward.setOnClickListener(this);
	}

	private class QueryTask extends AsyncTask<Void, Void, ArrayList<Net1314080903124_CauseInfo>> {
		@Override
		protected ArrayList<Net1314080903124_CauseInfo> doInBackground(Void... params) {
			list = Net1314080903124_DBManager.getInstance(Net1314080903124_ExamActivity.this).queryExam(Net1314080903124_AnswerColumns.TABLE_NAME);
			return list;
		}

		@Override
		protected void onPostExecute(ArrayList<Net1314080903124_CauseInfo> list) {
			if (list.size() == 0) {
				subjectTop.setText("0/0");
				selectOne.setText("");
				selectTwo.setText("");
				selectThree.setText("");
				selectFour.setText("");
				return;
			}
			int lastSelect = Net1314080903124_ConfigPreferences.getInstance(Net1314080903124_ExamActivity.this).isLastSelectExam();
			i = lastSelect - 1;
			Net1314080903124_CauseInfo myData = list.get(lastSelect - 1);
			title.setText(lastSelect + "." + myData.timu_title);
			type.setText("题型：" + myData.types);
			subjectTop.setText(lastSelect + "/" + list.size());
			if ("单选".equals(myData.types)) {
				submit.setVisibility(View.GONE);
				relThree.setVisibility(View.VISIBLE);
				relFour.setVisibility(View.VISIBLE);
				selectOne.setText("A." + myData.timu_one);
				selectTwo.setText("B." + myData.timu_tow);
				selectThree.setText("C." + myData.timu_three);
				selectFour.setText("D." + myData.timu_four);
			} else if ("多选".equals(myData.types)) {
				submit.setVisibility(View.VISIBLE);
				relThree.setVisibility(View.VISIBLE);
				relFour.setVisibility(View.VISIBLE);
				selectOne.setText("A." + myData.timu_one);
				selectTwo.setText("B." + myData.timu_tow);
				selectThree.setText("C." + myData.timu_three);
				selectFour.setText("D." + myData.timu_four);
			} else if ("判断".equals(myData.types)) {
				submit.setVisibility(View.GONE);
				relThree.setVisibility(View.GONE);
				relFour.setVisibility(View.GONE);
				selectOne.setText(myData.timu_one);
				selectTwo.setText(myData.timu_tow);
			}
		}
	}

	private void resetTitlebar() {
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.view_comm_titlebar);
		final TextView title = (TextView) findViewById(R.id.titlebar_title);
		TextView right = (TextView) findViewById(R.id.titlebar_right_text);
		LinearLayout back = (LinearLayout) findViewById(R.id.titlebar_left_layout);
		right.setText("交卷");
		right.setOnClickListener(this);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if (this == null) {
					return;
				}
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						time--;
						title.setText("模拟考试" + " " + Net1314080903124_TimeUtils.secToTime(time));
						if (time == 0) {
							cancel();
							Net1314080903124_ResultActivity.intentToResultActivity(Net1314080903124_ExamActivity.this, 0, exam_name);
							finish();
						}
					}
				});
			}
		};
		time = 60 * 10;
		timer.schedule(task, 0, 1000);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (list.size() == 0) {
			return;
		}
		Net1314080903124_CauseInfo myData = list.get(i);
		String types = myData.types;
		String daan_one = myData.daan_one;
		String daan_tow = myData.daan_tow;
		String daan_three = myData.daan_three;
		String daan_four = myData.daan_four;
		switch (v.getId()) {

		case R.id.titlebar_right_text:
			AlertDialog dialog = new AlertDialog.Builder(this).setMessage("是否交卷")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							for (int i = 0; i < list.size(); i++) {
								Net1314080903124_CauseInfo info = list.get(i);
								Net1314080903124_DBManager.getInstance(Net1314080903124_ExamActivity.this).insert(Net1314080903124_ExamResultColumns.TABLE_NAME, info);
							}
							timer.cancel();
							Net1314080903124_ResultActivity.intentToResultActivity(Net1314080903124_ExamActivity.this, time, exam_name);
							finish();
						}
					}).setNegativeButton("取消", null).create();
			dialog.show();
			break;

		case R.id.submit:
			if (isSelectA == false && isSelectB == false && isSelectC == false && isSelectD == false) {
				Toast.makeText(this, "选项不能为空", Toast.LENGTH_SHORT).show();
			} else if ((isSelectA == true && isSelectB == false && isSelectC == false && isSelectD == false)
					|| (isSelectB == true && isSelectA == false && isSelectC == false && isSelectD == false)
					|| (isSelectC == true && isSelectA == false && isSelectB == false && isSelectD == false)
					|| (isSelectD == true && isSelectA == false && isSelectB == false && isSelectC == false)) {
				Toast.makeText(this, "此题为多选，至少选两个答案才能提交哦", Toast.LENGTH_SHORT).show();
			} else {
				int RIGHT_ANSWER = getRightIntValue(daan_one, daan_tow, daan_three, daan_four);
				String rightStr = rightStr(RIGHT_ANSWER);

				int XUANXIANG;
				if (isSelectA == true && isSelectB == true && isSelectC == false && isSelectD == false) {
					XUANXIANG = Net1314080903124_BaseColumns.AB;
					myData.setReply(XUANXIANG);
					answer.setText("您提交的答案为：AB");
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
					} else {
						Net1314080903124_DBManager.getInstance(this).insert(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					}
				} else if (isSelectA == true && isSelectC == true && isSelectB == false && isSelectD == false) {
					XUANXIANG = Net1314080903124_BaseColumns.AC;
					myData.setReply(XUANXIANG);
					answer.setText("您提交的答案为：AC");
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
					} else {
						Net1314080903124_DBManager.getInstance(this).insert(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					}
				} else if (isSelectA == true && isSelectD == true && isSelectB == false && isSelectC == false) {
					XUANXIANG = Net1314080903124_BaseColumns.AD;
					myData.setReply(XUANXIANG);
					answer.setText("您提交的答案为：AD");
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
					} else {
						Net1314080903124_DBManager.getInstance(this).insert(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					}
				} else if (isSelectB == true && isSelectC == true && isSelectA == false && isSelectD == false) {
					XUANXIANG = Net1314080903124_BaseColumns.BC;
					myData.setReply(XUANXIANG);
					answer.setText("您提交的答案为：BC");
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
					} else {
						Net1314080903124_DBManager.getInstance(this).insert(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					}
				} else if (isSelectB == true && isSelectD == true && isSelectA == false && isSelectC == false) {
					XUANXIANG = Net1314080903124_BaseColumns.BD;
					myData.setReply(XUANXIANG);
					answer.setText("您提交的答案为：BD");
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
					} else {
						Net1314080903124_DBManager.getInstance(this).insert(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					}
				} else if (isSelectC == true && isSelectD == true && isSelectA == false && isSelectB == false) {
					XUANXIANG = Net1314080903124_BaseColumns.CD;
					myData.setReply(XUANXIANG);
					answer.setText("您提交的答案为：CD");
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
					} else {
						Net1314080903124_DBManager.getInstance(this).insert(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					}
				} else if (isSelectA == true && isSelectB == true && isSelectC == true && isSelectD == false) {
					XUANXIANG = Net1314080903124_BaseColumns.ABC;
					myData.setReply(XUANXIANG);
					answer.setText("您提交的答案为：ABC");
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
					} else {
						Net1314080903124_DBManager.getInstance(this).insert(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					}
				} else if (isSelectA == true && isSelectB == true && isSelectD == true && isSelectC == false) {
					XUANXIANG = Net1314080903124_BaseColumns.ABD;
					myData.setReply(XUANXIANG);
					answer.setText("您提交的答案为：ABD");
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
					} else {
						Net1314080903124_DBManager.getInstance(this).insert(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					}
				} else if (isSelectA == true && isSelectC == true && isSelectD == true && isSelectB == false) {
					XUANXIANG = Net1314080903124_BaseColumns.ACD;
					myData.setReply(XUANXIANG);
					answer.setText("您提交的答案为：ACD");
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
					} else {
						Net1314080903124_DBManager.getInstance(this).insert(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					}
				} else if (isSelectB == true && isSelectC == true && isSelectD == true && isSelectA == false) {
					XUANXIANG = Net1314080903124_BaseColumns.BCD;
					myData.setReply(XUANXIANG);
					answer.setText("您提交的答案为：BCD");
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
					} else {
						Net1314080903124_DBManager.getInstance(this).insert(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					}
				} else if (isSelectA == true && isSelectB == true && isSelectC == true && isSelectD == true) {
					XUANXIANG = Net1314080903124_BaseColumns.ABCD;
					myData.setReply(XUANXIANG);
					answer.setText("您提交的答案为：ABCD");
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
					} else {
						Net1314080903124_DBManager.getInstance(this).insert(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					}
				}
			}
			break;

		case R.id.selectOne:

			if ("单选".equals(types)) {
				imageOne.setImageResource(R.drawable.defaults);
				imageTwo.setImageResource(R.drawable.defaults);
				imageThree.setImageResource(R.drawable.defaults);
				imageFour.setImageResource(R.drawable.defaults);
				selectOne.setBackgroundResource(R.color.select_default);
				selectTwo.setBackgroundResource(R.color.select_default);
				selectThree.setBackgroundResource(R.color.select_default);
				selectFour.setBackgroundResource(R.color.select_default);
				relOne.setBackgroundResource(R.color.select_default);
				relTwo.setBackgroundResource(R.color.select_default);
				relThree.setBackgroundResource(R.color.select_default);
				relFour.setBackgroundResource(R.color.select_default);
				myData.setReply(Net1314080903124_BaseColumns.A);
				Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
				if (!TextUtils.isEmpty(daan_one)) {
					imageOne.setImageResource(R.drawable.more_select);
					relOne.setBackgroundResource(R.color.select_select);
					selectOne.setBackgroundResource(R.color.select_select);
					imageTwo.setImageResource(R.drawable.defaults);
					imageThree.setImageResource(R.drawable.defaults);
					imageFour.setImageResource(R.drawable.defaults);
					selectTwo.setBackgroundResource(R.color.select_default);
					selectThree.setBackgroundResource(R.color.select_default);
					selectFour.setBackgroundResource(R.color.select_default);
					relTwo.setBackgroundResource(R.color.select_default);
					relThree.setBackgroundResource(R.color.select_default);
					relFour.setBackgroundResource(R.color.select_default);
				} else {
					Net1314080903124_DBManager.getInstance(this).insert(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					if (!TextUtils.isEmpty(daan_tow)) {
						imageOne.setImageResource(R.drawable.more_select);
						relOne.setBackgroundResource(R.color.select_select);
						selectOne.setBackgroundResource(R.color.select_select);
						imageTwo.setImageResource(R.drawable.defaults);
						imageThree.setImageResource(R.drawable.defaults);
						imageFour.setImageResource(R.drawable.defaults);
						selectTwo.setBackgroundResource(R.color.select_default);
						selectThree.setBackgroundResource(R.color.select_default);
						selectFour.setBackgroundResource(R.color.select_default);
						relTwo.setBackgroundResource(R.color.select_default);
						relThree.setBackgroundResource(R.color.select_default);
						relFour.setBackgroundResource(R.color.select_default);
					} else if (!TextUtils.isEmpty(daan_three)) {
						imageOne.setImageResource(R.drawable.more_select);
						relOne.setBackgroundResource(R.color.select_select);
						selectOne.setBackgroundResource(R.color.select_select);
						imageTwo.setImageResource(R.drawable.defaults);
						imageThree.setImageResource(R.drawable.defaults);
						imageFour.setImageResource(R.drawable.defaults);
						selectTwo.setBackgroundResource(R.color.select_default);
						selectThree.setBackgroundResource(R.color.select_default);
						selectFour.setBackgroundResource(R.color.select_default);
						relTwo.setBackgroundResource(R.color.select_default);
						relThree.setBackgroundResource(R.color.select_default);
						relFour.setBackgroundResource(R.color.select_default);
					} else if (!TextUtils.isEmpty(daan_four)) {
						imageOne.setImageResource(R.drawable.more_select);
						relOne.setBackgroundResource(R.color.select_select);
						selectOne.setBackgroundResource(R.color.select_select);
						imageTwo.setImageResource(R.drawable.defaults);
						imageThree.setImageResource(R.drawable.defaults);
						imageFour.setImageResource(R.drawable.defaults);
						selectTwo.setBackgroundResource(R.color.select_default);
						selectThree.setBackgroundResource(R.color.select_default);
						selectFour.setBackgroundResource(R.color.select_default);
						relTwo.setBackgroundResource(R.color.select_default);
						relThree.setBackgroundResource(R.color.select_default);
						relFour.setBackgroundResource(R.color.select_default);
					}
				}
			} else if ("多选".equals(types)) {
				isSelectA = !isSelectA;
				if (isSelectA) {
					relOne.setBackgroundResource(R.color.select_select);
					selectOne.setBackgroundResource(R.color.select_select);
					imageOne.setImageResource(R.drawable.more_select);
				} else {
					relOne.setBackgroundResource(R.color.select_default);
					selectOne.setBackgroundResource(R.color.select_default);
					imageOne.setImageResource(R.drawable.defaults);
				}
			} else if ("判断".equals(types)) {
				imageOne.setImageResource(R.drawable.defaults);
				imageTwo.setImageResource(R.drawable.defaults);
				imageThree.setImageResource(R.drawable.defaults);
				imageFour.setImageResource(R.drawable.defaults);
				selectOne.setBackgroundResource(R.color.select_default);
				selectTwo.setBackgroundResource(R.color.select_default);
				selectThree.setBackgroundResource(R.color.select_default);
				selectFour.setBackgroundResource(R.color.select_default);
				relOne.setBackgroundResource(R.color.select_default);
				relTwo.setBackgroundResource(R.color.select_default);
				relThree.setBackgroundResource(R.color.select_default);
				relFour.setBackgroundResource(R.color.select_default);
				if (!TextUtils.isEmpty(daan_one)) {
					myData.setReply(Net1314080903124_BaseColumns.TRUE);
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
					imageOne.setImageResource(R.drawable.more_select);
					relOne.setBackgroundResource(R.color.select_select);
					selectOne.setBackgroundResource(R.color.select_select);
					imageTwo.setImageResource(R.drawable.defaults);
					selectTwo.setBackgroundResource(R.color.select_default);
					relTwo.setBackgroundResource(R.color.select_default);
				} else {
					Net1314080903124_DBManager.getInstance(this).insert(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					myData.setReply(Net1314080903124_BaseColumns.TRUE);
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
					imageOne.setImageResource(R.drawable.more_select);
					relOne.setBackgroundResource(R.color.select_select);
					selectOne.setBackgroundResource(R.color.select_select);
					imageTwo.setImageResource(R.drawable.defaults);
					selectTwo.setBackgroundResource(R.color.select_default);
					relTwo.setBackgroundResource(R.color.select_default);
				}
			}
			break;

		case R.id.selectTwo:
			if ("单选".equals(types)) {
				imageOne.setImageResource(R.drawable.defaults);
				imageTwo.setImageResource(R.drawable.defaults);
				imageThree.setImageResource(R.drawable.defaults);
				imageFour.setImageResource(R.drawable.defaults);
				selectOne.setBackgroundResource(R.color.select_default);
				selectTwo.setBackgroundResource(R.color.select_default);
				selectThree.setBackgroundResource(R.color.select_default);
				selectFour.setBackgroundResource(R.color.select_default);
				relOne.setBackgroundResource(R.color.select_default);
				relTwo.setBackgroundResource(R.color.select_default);
				relThree.setBackgroundResource(R.color.select_default);
				relFour.setBackgroundResource(R.color.select_default);
				myData.setReply(Net1314080903124_BaseColumns.B);
				Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
				if (!TextUtils.isEmpty(daan_tow)) {
					imageTwo.setImageResource(R.drawable.more_select);
					relTwo.setBackgroundResource(R.color.select_select);
					selectTwo.setBackgroundResource(R.color.select_select);
					imageOne.setImageResource(R.drawable.defaults);
					imageThree.setImageResource(R.drawable.defaults);
					imageFour.setImageResource(R.drawable.defaults);
					selectOne.setBackgroundResource(R.color.select_default);
					selectThree.setBackgroundResource(R.color.select_default);
					selectFour.setBackgroundResource(R.color.select_default);
					relOne.setBackgroundResource(R.color.select_default);
					relThree.setBackgroundResource(R.color.select_default);
					relFour.setBackgroundResource(R.color.select_default);
				} else {
					Net1314080903124_DBManager.getInstance(this).insert(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					if (!TextUtils.isEmpty(daan_one)) {
						imageTwo.setImageResource(R.drawable.more_select);
						relTwo.setBackgroundResource(R.color.select_select);
						selectTwo.setBackgroundResource(R.color.select_select);
						imageOne.setImageResource(R.drawable.defaults);
						imageThree.setImageResource(R.drawable.defaults);
						imageFour.setImageResource(R.drawable.defaults);
						selectOne.setBackgroundResource(R.color.select_default);
						selectThree.setBackgroundResource(R.color.select_default);
						selectFour.setBackgroundResource(R.color.select_default);
						relOne.setBackgroundResource(R.color.select_default);
						relThree.setBackgroundResource(R.color.select_default);
						relFour.setBackgroundResource(R.color.select_default);
					} else if (!TextUtils.isEmpty(daan_three)) {
						imageTwo.setImageResource(R.drawable.more_select);
						relTwo.setBackgroundResource(R.color.select_select);
						selectTwo.setBackgroundResource(R.color.select_select);
						imageOne.setImageResource(R.drawable.defaults);
						imageThree.setImageResource(R.drawable.defaults);
						imageFour.setImageResource(R.drawable.defaults);
						selectOne.setBackgroundResource(R.color.select_default);
						selectThree.setBackgroundResource(R.color.select_default);
						selectFour.setBackgroundResource(R.color.select_default);
						relOne.setBackgroundResource(R.color.select_default);
						relThree.setBackgroundResource(R.color.select_default);
						relFour.setBackgroundResource(R.color.select_default);
					} else if (!TextUtils.isEmpty(daan_four)) {
						imageTwo.setImageResource(R.drawable.more_select);
						relTwo.setBackgroundResource(R.color.select_select);
						selectTwo.setBackgroundResource(R.color.select_select);
						imageOne.setImageResource(R.drawable.defaults);
						imageThree.setImageResource(R.drawable.defaults);
						imageFour.setImageResource(R.drawable.defaults);
						selectOne.setBackgroundResource(R.color.select_default);
						selectThree.setBackgroundResource(R.color.select_default);
						selectFour.setBackgroundResource(R.color.select_default);
						relOne.setBackgroundResource(R.color.select_default);
						relThree.setBackgroundResource(R.color.select_default);
						relFour.setBackgroundResource(R.color.select_default);
					}
				}
			} else if ("多选".equals(types)) {
				isSelectB = !isSelectB;
				if (isSelectB) {
					relTwo.setBackgroundResource(R.color.select_select);
					selectTwo.setBackgroundResource(R.color.select_select);
					imageTwo.setImageResource(R.drawable.more_select);
				} else {
					relTwo.setBackgroundResource(R.color.select_default);
					selectTwo.setBackgroundResource(R.color.select_default);
					imageTwo.setImageResource(R.drawable.defaults);
				}
			} else if ("判断".equals(types)) {
				imageOne.setImageResource(R.drawable.defaults);
				imageTwo.setImageResource(R.drawable.defaults);
				imageThree.setImageResource(R.drawable.defaults);
				imageFour.setImageResource(R.drawable.defaults);
				selectOne.setBackgroundResource(R.color.select_default);
				selectTwo.setBackgroundResource(R.color.select_default);
				selectThree.setBackgroundResource(R.color.select_default);
				selectFour.setBackgroundResource(R.color.select_default);
				relOne.setBackgroundResource(R.color.select_default);
				relTwo.setBackgroundResource(R.color.select_default);
				relThree.setBackgroundResource(R.color.select_default);
				relFour.setBackgroundResource(R.color.select_default);
				if (!TextUtils.isEmpty(daan_tow)) {
					myData.setReply(Net1314080903124_BaseColumns.FALSE);
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
					imageTwo.setImageResource(R.drawable.more_select);
					relTwo.setBackgroundResource(R.color.select_select);
					selectTwo.setBackgroundResource(R.color.select_select);
					imageOne.setImageResource(R.drawable.defaults);
					selectOne.setBackgroundResource(R.color.select_default);
					relOne.setBackgroundResource(R.color.select_default);
				} else {
					Net1314080903124_DBManager.getInstance(this).insert(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					myData.setReply(Net1314080903124_BaseColumns.FALSE);
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
					imageTwo.setImageResource(R.drawable.more_select);
					relTwo.setBackgroundResource(R.color.select_select);
					selectTwo.setBackgroundResource(R.color.select_select);
					imageOne.setImageResource(R.drawable.defaults);
					selectOne.setBackgroundResource(R.color.select_default);
					relOne.setBackgroundResource(R.color.select_default);
				}
			}
			break;

		case R.id.selectThree:
			if ("单选".equals(types)) {
				imageOne.setImageResource(R.drawable.defaults);
				imageTwo.setImageResource(R.drawable.defaults);
				imageThree.setImageResource(R.drawable.defaults);
				imageFour.setImageResource(R.drawable.defaults);
				selectOne.setBackgroundResource(R.color.select_default);
				selectTwo.setBackgroundResource(R.color.select_default);
				selectThree.setBackgroundResource(R.color.select_default);
				selectFour.setBackgroundResource(R.color.select_default);
				relOne.setBackgroundResource(R.color.select_default);
				relTwo.setBackgroundResource(R.color.select_default);
				relThree.setBackgroundResource(R.color.select_default);
				relFour.setBackgroundResource(R.color.select_default);
				myData.setReply(Net1314080903124_BaseColumns.C);
				Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
				if (!TextUtils.isEmpty(daan_three)) {
					imageThree.setImageResource(R.drawable.more_select);
					relThree.setBackgroundResource(R.color.select_select);
					selectThree.setBackgroundResource(R.color.select_select);
					imageOne.setImageResource(R.drawable.defaults);
					imageTwo.setImageResource(R.drawable.defaults);
					imageFour.setImageResource(R.drawable.defaults);
					selectOne.setBackgroundResource(R.color.select_default);
					selectTwo.setBackgroundResource(R.color.select_default);
					selectFour.setBackgroundResource(R.color.select_default);
					relOne.setBackgroundResource(R.color.select_default);
					relTwo.setBackgroundResource(R.color.select_default);
					relFour.setBackgroundResource(R.color.select_default);
				} else {
					Net1314080903124_DBManager.getInstance(this).insert(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					if (!TextUtils.isEmpty(daan_one)) {
						imageThree.setImageResource(R.drawable.more_select);
						relThree.setBackgroundResource(R.color.select_select);
						selectThree.setBackgroundResource(R.color.select_select);
						imageOne.setImageResource(R.drawable.defaults);
						imageTwo.setImageResource(R.drawable.defaults);
						imageFour.setImageResource(R.drawable.defaults);
						selectOne.setBackgroundResource(R.color.select_default);
						selectTwo.setBackgroundResource(R.color.select_default);
						selectFour.setBackgroundResource(R.color.select_default);
						relOne.setBackgroundResource(R.color.select_default);
						relTwo.setBackgroundResource(R.color.select_default);
						relFour.setBackgroundResource(R.color.select_default);
					} else if (!TextUtils.isEmpty(daan_tow)) {
						imageThree.setImageResource(R.drawable.more_select);
						relThree.setBackgroundResource(R.color.select_select);
						selectThree.setBackgroundResource(R.color.select_select);
						imageOne.setImageResource(R.drawable.defaults);
						imageTwo.setImageResource(R.drawable.defaults);
						imageFour.setImageResource(R.drawable.defaults);
						selectOne.setBackgroundResource(R.color.select_default);
						selectTwo.setBackgroundResource(R.color.select_default);
						selectFour.setBackgroundResource(R.color.select_default);
						relOne.setBackgroundResource(R.color.select_default);
						relTwo.setBackgroundResource(R.color.select_default);
						relFour.setBackgroundResource(R.color.select_default);
					} else if (!TextUtils.isEmpty(daan_four)) {
						imageThree.setImageResource(R.drawable.more_select);
						relThree.setBackgroundResource(R.color.select_select);
						selectThree.setBackgroundResource(R.color.select_select);
						imageOne.setImageResource(R.drawable.defaults);
						imageTwo.setImageResource(R.drawable.defaults);
						imageFour.setImageResource(R.drawable.defaults);
						selectOne.setBackgroundResource(R.color.select_default);
						selectTwo.setBackgroundResource(R.color.select_default);
						selectFour.setBackgroundResource(R.color.select_default);
						relOne.setBackgroundResource(R.color.select_default);
						relTwo.setBackgroundResource(R.color.select_default);
						relFour.setBackgroundResource(R.color.select_default);
					}
				}
			} else if ("多选".equals(types)) {
				isSelectC = !isSelectC;
				if (isSelectC) {
					relThree.setBackgroundResource(R.color.select_select);
					selectThree.setBackgroundResource(R.color.select_select);
					imageThree.setImageResource(R.drawable.more_select);
				} else {
					relThree.setBackgroundResource(R.color.select_default);
					selectThree.setBackgroundResource(R.color.select_default);
					imageThree.setImageResource(R.drawable.defaults);
				}
			}
			break;

		case R.id.selectFour:
			if ("单选".equals(types)) {
				imageOne.setImageResource(R.drawable.defaults);
				imageTwo.setImageResource(R.drawable.defaults);
				imageThree.setImageResource(R.drawable.defaults);
				imageFour.setImageResource(R.drawable.defaults);
				selectOne.setBackgroundResource(R.color.select_default);
				selectTwo.setBackgroundResource(R.color.select_default);
				selectThree.setBackgroundResource(R.color.select_default);
				selectFour.setBackgroundResource(R.color.select_default);
				relOne.setBackgroundResource(R.color.select_default);
				relTwo.setBackgroundResource(R.color.select_default);
				relThree.setBackgroundResource(R.color.select_default);
				relFour.setBackgroundResource(R.color.select_default);
				myData.setReply(Net1314080903124_BaseColumns.D);
				Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
				if (!TextUtils.isEmpty(daan_four)) {
					imageFour.setImageResource(R.drawable.more_select);
					relFour.setBackgroundResource(R.color.select_select);
					selectFour.setBackgroundResource(R.color.select_select);
					imageOne.setImageResource(R.drawable.defaults);
					imageTwo.setImageResource(R.drawable.defaults);
					imageThree.setImageResource(R.drawable.defaults);
					selectOne.setBackgroundResource(R.color.select_default);
					selectTwo.setBackgroundResource(R.color.select_default);
					selectThree.setBackgroundResource(R.color.select_default);
					relOne.setBackgroundResource(R.color.select_default);
					relTwo.setBackgroundResource(R.color.select_default);
					relThree.setBackgroundResource(R.color.select_default);
				} else {
					Net1314080903124_DBManager.getInstance(this).insert(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					if (!TextUtils.isEmpty(daan_one)) {
						imageFour.setImageResource(R.drawable.more_select);
						relFour.setBackgroundResource(R.color.select_select);
						selectFour.setBackgroundResource(R.color.select_select);
						imageOne.setImageResource(R.drawable.defaults);
						imageTwo.setImageResource(R.drawable.defaults);
						imageThree.setImageResource(R.drawable.defaults);
						selectOne.setBackgroundResource(R.color.select_default);
						selectTwo.setBackgroundResource(R.color.select_default);
						selectThree.setBackgroundResource(R.color.select_default);
						relOne.setBackgroundResource(R.color.select_default);
						relTwo.setBackgroundResource(R.color.select_default);
						relThree.setBackgroundResource(R.color.select_default);
					} else if (!TextUtils.isEmpty(daan_tow)) {
						imageFour.setImageResource(R.drawable.more_select);
						relFour.setBackgroundResource(R.color.select_select);
						selectFour.setBackgroundResource(R.color.select_select);
						imageOne.setImageResource(R.drawable.defaults);
						imageTwo.setImageResource(R.drawable.defaults);
						imageThree.setImageResource(R.drawable.defaults);
						selectOne.setBackgroundResource(R.color.select_default);
						selectTwo.setBackgroundResource(R.color.select_default);
						selectThree.setBackgroundResource(R.color.select_default);
						relOne.setBackgroundResource(R.color.select_default);
						relTwo.setBackgroundResource(R.color.select_default);
						relThree.setBackgroundResource(R.color.select_default);
					} else if (!TextUtils.isEmpty(daan_three)) {
						imageFour.setImageResource(R.drawable.more_select);
						relFour.setBackgroundResource(R.color.select_select);
						selectFour.setBackgroundResource(R.color.select_select);
						imageOne.setImageResource(R.drawable.defaults);
						imageTwo.setImageResource(R.drawable.defaults);
						imageThree.setImageResource(R.drawable.defaults);
						selectOne.setBackgroundResource(R.color.select_default);
						selectTwo.setBackgroundResource(R.color.select_default);
						selectThree.setBackgroundResource(R.color.select_default);
						relOne.setBackgroundResource(R.color.select_default);
						relTwo.setBackgroundResource(R.color.select_default);
						relThree.setBackgroundResource(R.color.select_default);
					}
				}
			} else if ("多选".equals(types)) {
				isSelectD = !isSelectD;
				if (isSelectD) {
					relFour.setBackgroundResource(R.color.select_select);
					selectFour.setBackgroundResource(R.color.select_select);
					imageFour.setImageResource(R.drawable.more_select);
				} else {
					relFour.setBackgroundResource(R.color.select_default);
					selectFour.setBackgroundResource(R.color.select_default);
					imageFour.setImageResource(R.drawable.defaults);
				}
			}
			break;

		case R.id.back:
			if (i <= 0) {
				return;
			}
			answer.setText("");
			isSelectA = false;
			isSelectB = false;
			isSelectC = false;
			isSelectD = false;
			imageOne.setImageResource(R.drawable.defaults);
			imageTwo.setImageResource(R.drawable.defaults);
			imageThree.setImageResource(R.drawable.defaults);
			imageFour.setImageResource(R.drawable.defaults);
			selectOne.setBackgroundResource(R.color.select_default);
			selectTwo.setBackgroundResource(R.color.select_default);
			selectThree.setBackgroundResource(R.color.select_default);
			selectFour.setBackgroundResource(R.color.select_default);
			relOne.setBackgroundResource(R.color.select_default);
			relTwo.setBackgroundResource(R.color.select_default);
			relThree.setBackgroundResource(R.color.select_default);
			relFour.setBackgroundResource(R.color.select_default);
			if (isClickSelectSubject) {
				i = Net1314080903124_ConfigPreferences.getInstance(this).isLastSelectExam() - 1;
				i -= 1;
				isClickSelectSubject = false;
			} else {
				i -= 1;
			}
			Net1314080903124_ConfigPreferences.getInstance(this).setLastSelectExam(i + 1);
			Net1314080903124_CauseInfo myDataBack = list.get(i);
			subjectTop.setText((i + 1) + "/" + list.size());
			if ("单选".equals(myDataBack.types)) {
				submit.setVisibility(View.GONE);
				relThree.setVisibility(View.VISIBLE);
				relFour.setVisibility(View.VISIBLE);
			} else if ("多选".equals(myDataBack.types)) {
				submit.setVisibility(View.VISIBLE);
				relThree.setVisibility(View.VISIBLE);
				relFour.setVisibility(View.VISIBLE);
			} else if ("判断".equals(myDataBack.types)) {
				submit.setVisibility(View.GONE);
				relThree.setVisibility(View.GONE);
				relFour.setVisibility(View.GONE);
			}
			int replyBack = myDataBack.getReply();
			switch (replyBack) {
			case Net1314080903124_BaseColumns.NULL:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				if ("单选".equals(myDataBack.types)) {
					selectOne.setText("A." + myDataBack.timu_one);
					selectTwo.setText("B." + myDataBack.timu_tow);
					selectThree.setText("C." + myDataBack.timu_three);
					selectFour.setText("D." + myDataBack.timu_four);
				} else if ("多选".equals(myDataBack.types)) {
					selectOne.setText("A." + myDataBack.timu_one);
					selectTwo.setText("B." + myDataBack.timu_tow);
					selectThree.setText("C." + myDataBack.timu_three);
					selectFour.setText("D." + myDataBack.timu_four);
				} else if ("判断".equals(myDataBack.types)) {
					selectOne.setText(myDataBack.timu_one);
					selectTwo.setText(myDataBack.timu_tow);
				}
				selectOne.setClickable(true);
				selectTwo.setClickable(true);
				selectThree.setClickable(true);
				selectFour.setClickable(true);
				if ("多选".equals(myDataBack.types)) {
					submit.setVisibility(View.VISIBLE);
				}
				break;

			case Net1314080903124_BaseColumns.A:
				if (myDataBack.daan_one.equals("A")) {
					imageOne.setImageResource(R.drawable.more_select);
					relOne.setBackgroundResource(R.color.select_select);
					selectOne.setBackgroundResource(R.color.select_select);
				} else {
					if (!TextUtils.isEmpty(myDataBack.daan_tow)) {
						imageOne.setImageResource(R.drawable.more_select);
						relOne.setBackgroundResource(R.color.select_select);
						selectOne.setBackgroundResource(R.color.select_select);
					} else if (!TextUtils.isEmpty(myDataBack.daan_three)) {
						imageOne.setImageResource(R.drawable.more_select);
						relOne.setBackgroundResource(R.color.select_select);
						selectOne.setBackgroundResource(R.color.select_select);
					} else if (!TextUtils.isEmpty(myDataBack.daan_four)) {
						imageOne.setImageResource(R.drawable.more_select);
						relOne.setBackgroundResource(R.color.select_select);
						selectOne.setBackgroundResource(R.color.select_select);
					}
				}
				selectOne.setClickable(true);
				selectTwo.setClickable(true);
				selectThree.setClickable(true);
				selectFour.setClickable(true);
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				break;

			case Net1314080903124_BaseColumns.B:
				if (myDataBack.daan_tow.equals("B")) {
					imageTwo.setImageResource(R.drawable.more_select);
					relTwo.setBackgroundResource(R.color.select_select);
					selectTwo.setBackgroundResource(R.color.select_select);
				} else {
					if (!TextUtils.isEmpty(myDataBack.daan_one)) {
						imageTwo.setImageResource(R.drawable.more_select);
						relTwo.setBackgroundResource(R.color.select_select);
						selectTwo.setBackgroundResource(R.color.select_select);
					} else if (!TextUtils.isEmpty(myDataBack.daan_three)) {
						imageTwo.setImageResource(R.drawable.more_select);
						relTwo.setBackgroundResource(R.color.select_select);
						selectTwo.setBackgroundResource(R.color.select_select);
					} else if (!TextUtils.isEmpty(myDataBack.daan_four)) {
						imageTwo.setImageResource(R.drawable.more_select);
						relTwo.setBackgroundResource(R.color.select_select);
						selectTwo.setBackgroundResource(R.color.select_select);
					}
				}
				selectOne.setClickable(true);
				selectTwo.setClickable(true);
				selectThree.setClickable(true);
				selectFour.setClickable(true);
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				break;

			case Net1314080903124_BaseColumns.C:
				if (myDataBack.daan_three.equals("C")) {
					imageThree.setImageResource(R.drawable.more_select);
					relThree.setBackgroundResource(R.color.select_select);
					selectThree.setBackgroundResource(R.color.select_select);
				} else {
					if (!TextUtils.isEmpty(myDataBack.daan_one)) {
						imageThree.setImageResource(R.drawable.more_select);
						relThree.setBackgroundResource(R.color.select_select);
						selectThree.setBackgroundResource(R.color.select_select);
					} else if (!TextUtils.isEmpty(myDataBack.daan_tow)) {
						imageThree.setImageResource(R.drawable.more_select);
						relThree.setBackgroundResource(R.color.select_select);
						selectThree.setBackgroundResource(R.color.select_select);
					} else if (!TextUtils.isEmpty(myDataBack.daan_four)) {
						imageThree.setImageResource(R.drawable.more_select);
						relThree.setBackgroundResource(R.color.select_select);
						selectThree.setBackgroundResource(R.color.select_select);
					}
				}
				selectOne.setClickable(true);
				selectTwo.setClickable(true);
				selectThree.setClickable(true);
				selectFour.setClickable(true);
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				break;

			case Net1314080903124_BaseColumns.D:
				if (myDataBack.daan_four.equals("D")) {
					imageFour.setImageResource(R.drawable.more_select);
					relFour.setBackgroundResource(R.color.select_select);
					selectFour.setBackgroundResource(R.color.select_select);
				} else {
					if (!TextUtils.isEmpty(myDataBack.daan_one)) {
						imageFour.setImageResource(R.drawable.more_select);
						relFour.setBackgroundResource(R.color.select_select);
						selectFour.setBackgroundResource(R.color.select_select);
					} else if (!TextUtils.isEmpty(myDataBack.daan_tow)) {
						imageFour.setImageResource(R.drawable.more_select);
						relFour.setBackgroundResource(R.color.select_select);
						selectFour.setBackgroundResource(R.color.select_select);
					} else if (!TextUtils.isEmpty(myDataBack.daan_three)) {
						imageFour.setImageResource(R.drawable.more_select);
						relFour.setBackgroundResource(R.color.select_select);
						selectFour.setBackgroundResource(R.color.select_select);
					}
				}
				selectOne.setClickable(true);
				selectTwo.setClickable(true);
				selectThree.setClickable(true);
				selectFour.setClickable(true);
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				break;

			case Net1314080903124_BaseColumns.AB:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				isSelectA = true;
				isSelectB = true;
				answer.setText("您提交的答案为：AB");
				break;

			case Net1314080903124_BaseColumns.AC:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				imageOne.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				isSelectA = true;
				isSelectC = true;
				answer.setText("您提交的答案为：AC");
				break;

			case Net1314080903124_BaseColumns.AD:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				imageOne.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				isSelectA = true;
				isSelectD = true;
				answer.setText("您提交的答案为：AD");
				break;

			case Net1314080903124_BaseColumns.BC:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				isSelectB = true;
				isSelectC = true;
				answer.setText("您提交的答案为：BC");
				break;

			case Net1314080903124_BaseColumns.BD:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				imageTwo.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				isSelectB = true;
				isSelectD = true;
				answer.setText("您提交的答案为：BD");
				break;

			case Net1314080903124_BaseColumns.CD:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				isSelectC = true;
				isSelectD = true;
				answer.setText("您提交的答案为：CD");
				break;

			case Net1314080903124_BaseColumns.ABC:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				isSelectA = true;
				isSelectB = true;
				isSelectC = true;
				answer.setText("您提交的答案为：ABC");
				break;

			case Net1314080903124_BaseColumns.ABD:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				isSelectA = true;
				isSelectB = true;
				isSelectD = true;
				answer.setText("您提交的答案为：ABD");
				break;

			case Net1314080903124_BaseColumns.ACD:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				imageOne.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				isSelectA = true;
				isSelectC = true;
				isSelectD = true;
				answer.setText("您提交的答案为：ACD");
				break;

			case Net1314080903124_BaseColumns.BCD:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				isSelectB = true;
				isSelectC = true;
				isSelectD = true;
				answer.setText("您提交的答案为：BCD");
				break;

			case Net1314080903124_BaseColumns.ABCD:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				isSelectA = true;
				isSelectB = true;
				isSelectC = true;
				isSelectD = true;
				answer.setText("您提交的答案为：ABCD");
				break;

			case Net1314080903124_BaseColumns.TRUE:
				if (myDataBack.daan_one.equals("A")) {
					imageOne.setImageResource(R.drawable.more_select);
					relOne.setBackgroundResource(R.color.select_select);
					selectOne.setBackgroundResource(R.color.select_select);
					imageTwo.setImageResource(R.drawable.defaults);
					selectTwo.setBackgroundResource(R.color.select_default);
					relTwo.setBackgroundResource(R.color.select_default);
				} else {
					imageOne.setImageResource(R.drawable.more_select);
					relOne.setBackgroundResource(R.color.select_select);
					selectOne.setBackgroundResource(R.color.select_select);
					imageTwo.setImageResource(R.drawable.defaults);
					selectTwo.setBackgroundResource(R.color.select_default);
					relTwo.setBackgroundResource(R.color.select_default);
				}
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText(myDataBack.timu_one);
				selectTwo.setText(myDataBack.timu_tow);
				selectOne.setClickable(true);
				selectTwo.setClickable(true);
				selectThree.setClickable(true);
				selectFour.setClickable(true);
				break;

			case Net1314080903124_BaseColumns.FALSE:
				if (myDataBack.daan_tow.equals("B")) {
					imageTwo.setImageResource(R.drawable.more_select);
					relTwo.setBackgroundResource(R.color.select_select);
					selectTwo.setBackgroundResource(R.color.select_select);
					imageOne.setImageResource(R.drawable.defaults);
					selectOne.setBackgroundResource(R.color.select_default);
					relOne.setBackgroundResource(R.color.select_default);
				} else {
					imageTwo.setImageResource(R.drawable.more_select);
					relTwo.setBackgroundResource(R.color.select_select);
					selectTwo.setBackgroundResource(R.color.select_select);
					imageOne.setImageResource(R.drawable.defaults);
					selectOne.setBackgroundResource(R.color.select_default);
					relOne.setBackgroundResource(R.color.select_default);
				}
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText(myDataBack.timu_one);
				selectTwo.setText(myDataBack.timu_tow);
				selectOne.setClickable(true);
				selectTwo.setClickable(true);
				selectThree.setClickable(true);
				selectFour.setClickable(true);
				break;

			default:
				break;
			}
			break;

		case R.id.subject:
			listHasId = new ArrayList<Net1314080903124_CauseInfoHasId>();
			for (int i = 0; i < list.size(); i++) {
				Net1314080903124_CauseInfo net1314080903124_CauseInfo = list.get(i);
				Net1314080903124_CauseInfoHasId net1314080903124_CauseInfoHasId = new Net1314080903124_CauseInfoHasId(i + 1, net1314080903124_CauseInfo.timu_title, net1314080903124_CauseInfo.timu_one, net1314080903124_CauseInfo.timu_tow,
						net1314080903124_CauseInfo.timu_three, net1314080903124_CauseInfo.timu_four, net1314080903124_CauseInfo.daan_one, net1314080903124_CauseInfo.daan_tow, net1314080903124_CauseInfo.daan_three,
						net1314080903124_CauseInfo.daan_four, net1314080903124_CauseInfo.detail, net1314080903124_CauseInfo.types, net1314080903124_CauseInfo.reply);
				listHasId.add(net1314080903124_CauseInfoHasId);
			}
			View layout = getLayoutInflater().inflate(R.layout.select_subject, null);
			dialogs = new AlertDialog.Builder(this).create();
			dialogs.show();
			dialogs.getWindow().setContentView(layout);
			GridView gridView1 = (GridView) layout.findViewById(R.id.gridView1);
			layout.findViewById(R.id.state1).setVisibility(View.GONE);
			layout.findViewById(R.id.state2).setVisibility(View.VISIBLE);
			myAdapter = new MyAdapter();
			gridView1.setAdapter(myAdapter);
			gridView1.setOnItemClickListener(this);
			break;

		case R.id.collect:
			myData.setReply(0);
			Net1314080903124_DBManager.getInstance(this).insert(Net1314080903124_CollectColumns.TABLE_NAME, myData);
			Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
			break;

		case R.id.forward:
			if (i >= list.size() - 1) {
				return;
			}
			answer.setText("");
			isSelectA = false;
			isSelectB = false;
			isSelectC = false;
			isSelectD = false;
			imageOne.setImageResource(R.drawable.defaults);
			imageTwo.setImageResource(R.drawable.defaults);
			imageThree.setImageResource(R.drawable.defaults);
			imageFour.setImageResource(R.drawable.defaults);
			selectOne.setBackgroundResource(R.color.select_default);
			selectTwo.setBackgroundResource(R.color.select_default);
			selectThree.setBackgroundResource(R.color.select_default);
			selectFour.setBackgroundResource(R.color.select_default);
			relOne.setBackgroundResource(R.color.select_default);
			relTwo.setBackgroundResource(R.color.select_default);
			relThree.setBackgroundResource(R.color.select_default);
			relFour.setBackgroundResource(R.color.select_default);
			if (isClickSelectSubject) {
				i = Net1314080903124_ConfigPreferences.getInstance(this).isLastSelectExam() - 1;
				i += 1;
				isClickSelectSubject = false;
			} else {
				i += 1;
			}
			Net1314080903124_ConfigPreferences.getInstance(this).setLastSelectExam(i + 1);
			Net1314080903124_CauseInfo myDataForward = list.get(i);
			subjectTop.setText((i + 1) + "/" + list.size());
			if ("单选".equals(myDataForward.types)) {
				submit.setVisibility(View.GONE);
				relThree.setVisibility(View.VISIBLE);
				relFour.setVisibility(View.VISIBLE);
			} else if ("多选".equals(myDataForward.types)) {
				submit.setVisibility(View.VISIBLE);
				relThree.setVisibility(View.VISIBLE);
				relFour.setVisibility(View.VISIBLE);
			} else if ("判断".equals(myDataForward.types)) {
				submit.setVisibility(View.GONE);
				relThree.setVisibility(View.GONE);
				relFour.setVisibility(View.GONE);
			}
			int replyForward = myDataForward.getReply();
			switch (replyForward) {
			case Net1314080903124_BaseColumns.NULL:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				if ("单选".equals(myDataForward.types)) {
					selectOne.setText("A." + myDataForward.timu_one);
					selectTwo.setText("B." + myDataForward.timu_tow);
					selectThree.setText("C." + myDataForward.timu_three);
					selectFour.setText("D." + myDataForward.timu_four);
				} else if ("多选".equals(myDataForward.types)) {
					selectOne.setText("A." + myDataForward.timu_one);
					selectTwo.setText("B." + myDataForward.timu_tow);
					selectThree.setText("C." + myDataForward.timu_three);
					selectFour.setText("D." + myDataForward.timu_four);
				} else if ("判断".equals(myDataForward.types)) {
					selectOne.setText(myDataForward.timu_one);
					selectTwo.setText(myDataForward.timu_tow);
				}
				selectOne.setClickable(true);
				selectTwo.setClickable(true);
				selectThree.setClickable(true);
				selectFour.setClickable(true);
				if ("多选".equals(myDataForward.types)) {
					submit.setVisibility(View.VISIBLE);
				}
				break;

			case Net1314080903124_BaseColumns.A:
				if (myDataForward.daan_one.equals("A")) {
					imageOne.setImageResource(R.drawable.more_select);
					relOne.setBackgroundResource(R.color.select_select);
					selectOne.setBackgroundResource(R.color.select_select);
				} else {
					if (!TextUtils.isEmpty(myDataForward.daan_tow)) {
						imageOne.setImageResource(R.drawable.more_select);
						relOne.setBackgroundResource(R.color.select_select);
						selectOne.setBackgroundResource(R.color.select_select);
					} else if (!TextUtils.isEmpty(myDataForward.daan_three)) {
						imageOne.setImageResource(R.drawable.more_select);
						relOne.setBackgroundResource(R.color.select_select);
						selectOne.setBackgroundResource(R.color.select_select);
					} else if (!TextUtils.isEmpty(myDataForward.daan_four)) {
						imageOne.setImageResource(R.drawable.more_select);
						relOne.setBackgroundResource(R.color.select_select);
						selectOne.setBackgroundResource(R.color.select_select);
					}
				}
				selectOne.setClickable(true);
				selectTwo.setClickable(true);
				selectThree.setClickable(true);
				selectFour.setClickable(true);
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				break;

			case Net1314080903124_BaseColumns.B:
				if (myDataForward.daan_tow.equals("B")) {
					imageTwo.setImageResource(R.drawable.more_select);
					relTwo.setBackgroundResource(R.color.select_select);
					selectTwo.setBackgroundResource(R.color.select_select);
				} else {
					if (!TextUtils.isEmpty(myDataForward.daan_one)) {
						imageTwo.setImageResource(R.drawable.more_select);
						relTwo.setBackgroundResource(R.color.select_select);
						selectTwo.setBackgroundResource(R.color.select_select);
					} else if (!TextUtils.isEmpty(myDataForward.daan_three)) {
						imageTwo.setImageResource(R.drawable.more_select);
						relTwo.setBackgroundResource(R.color.select_select);
						selectTwo.setBackgroundResource(R.color.select_select);
					} else if (!TextUtils.isEmpty(myDataForward.daan_four)) {
						imageTwo.setImageResource(R.drawable.more_select);
						relTwo.setBackgroundResource(R.color.select_select);
						selectTwo.setBackgroundResource(R.color.select_select);
					}
				}
				selectOne.setClickable(true);
				selectTwo.setClickable(true);
				selectThree.setClickable(true);
				selectFour.setClickable(true);
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				break;

			case Net1314080903124_BaseColumns.C:
				if (myDataForward.daan_three.equals("C")) {
					imageThree.setImageResource(R.drawable.more_select);
					relThree.setBackgroundResource(R.color.select_select);
					selectThree.setBackgroundResource(R.color.select_select);
				} else {
					if (!TextUtils.isEmpty(myDataForward.daan_one)) {
						imageThree.setImageResource(R.drawable.more_select);
						relThree.setBackgroundResource(R.color.select_select);
						selectThree.setBackgroundResource(R.color.select_select);
					} else if (!TextUtils.isEmpty(myDataForward.daan_tow)) {
						imageThree.setImageResource(R.drawable.more_select);
						relThree.setBackgroundResource(R.color.select_select);
						selectThree.setBackgroundResource(R.color.select_select);
					} else if (!TextUtils.isEmpty(myDataForward.daan_four)) {
						imageThree.setImageResource(R.drawable.more_select);
						relThree.setBackgroundResource(R.color.select_select);
						selectThree.setBackgroundResource(R.color.select_select);
					}
				}
				selectOne.setClickable(true);
				selectTwo.setClickable(true);
				selectThree.setClickable(true);
				selectFour.setClickable(true);
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				break;

			case Net1314080903124_BaseColumns.D:
				if (myDataForward.daan_four.equals("D")) {
					imageFour.setImageResource(R.drawable.more_select);
					relFour.setBackgroundResource(R.color.select_select);
					selectFour.setBackgroundResource(R.color.select_select);
				} else {
					if (!TextUtils.isEmpty(myDataForward.daan_one)) {
						imageFour.setImageResource(R.drawable.more_select);
						relFour.setBackgroundResource(R.color.select_select);
						selectFour.setBackgroundResource(R.color.select_select);
					} else if (!TextUtils.isEmpty(myDataForward.daan_tow)) {
						imageFour.setImageResource(R.drawable.more_select);
						relFour.setBackgroundResource(R.color.select_select);
						selectFour.setBackgroundResource(R.color.select_select);
					} else if (!TextUtils.isEmpty(myDataForward.daan_three)) {
						imageFour.setImageResource(R.drawable.more_select);
						relFour.setBackgroundResource(R.color.select_select);
						selectFour.setBackgroundResource(R.color.select_select);
					}
				}
				selectOne.setClickable(true);
				selectTwo.setClickable(true);
				selectThree.setClickable(true);
				selectFour.setClickable(true);
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				break;

			case Net1314080903124_BaseColumns.AB:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				isSelectA = true;
				isSelectB = true;
				answer.setText("您提交的答案为：AB");
				break;

			case Net1314080903124_BaseColumns.AC:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				imageOne.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				isSelectA = true;
				isSelectC = true;
				answer.setText("您提交的答案为：AC");
				break;

			case Net1314080903124_BaseColumns.AD:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				imageOne.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				isSelectA = true;
				isSelectD = true;
				answer.setText("您提交的答案为：AD");
				break;

			case Net1314080903124_BaseColumns.BC:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				answer.setText("您提交的答案为：BC");
				isSelectB = true;
				isSelectC = true;
				break;

			case Net1314080903124_BaseColumns.BD:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				imageTwo.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				isSelectB = true;
				isSelectD = true;
				answer.setText("您提交的答案为：BD");
				break;

			case Net1314080903124_BaseColumns.CD:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				isSelectC = true;
				isSelectD = true;
				answer.setText("您提交的答案为：CD");
				break;

			case Net1314080903124_BaseColumns.ABC:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				isSelectA = true;
				isSelectB = true;
				isSelectC = true;
				answer.setText("您提交的答案为：ABC");
				break;

			case Net1314080903124_BaseColumns.ABD:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				isSelectA = true;
				isSelectB = true;
				isSelectD = true;
				answer.setText("您提交的答案为：ABD");
				break;

			case Net1314080903124_BaseColumns.ACD:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				imageOne.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				isSelectA = true;
				isSelectC = true;
				isSelectD = true;
				answer.setText("您提交的答案为：ACD");
				break;

			case Net1314080903124_BaseColumns.BCD:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				isSelectB = true;
				isSelectC = true;
				isSelectD = true;
				answer.setText("您提交的答案为：BCD");
				break;

			case Net1314080903124_BaseColumns.ABCD:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				isSelectA = true;
				isSelectB = true;
				isSelectC = true;
				isSelectD = true;
				answer.setText("您提交的答案为：ABCD");
				break;

			case Net1314080903124_BaseColumns.TRUE:
				if (myDataForward.daan_one.equals("A")) {
					imageOne.setImageResource(R.drawable.more_select);
					relOne.setBackgroundResource(R.color.select_select);
					selectOne.setBackgroundResource(R.color.select_select);
					imageTwo.setImageResource(R.drawable.defaults);
					selectTwo.setBackgroundResource(R.color.select_default);
					relTwo.setBackgroundResource(R.color.select_default);
				} else {
					imageOne.setImageResource(R.drawable.more_select);
					relOne.setBackgroundResource(R.color.select_select);
					selectOne.setBackgroundResource(R.color.select_select);
					imageTwo.setImageResource(R.drawable.defaults);
					selectTwo.setBackgroundResource(R.color.select_default);
					relTwo.setBackgroundResource(R.color.select_default);
				}
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText(myDataForward.timu_one);
				selectTwo.setText(myDataForward.timu_tow);
				selectOne.setClickable(true);
				selectTwo.setClickable(true);
				selectThree.setClickable(true);
				selectFour.setClickable(true);
				break;

			case Net1314080903124_BaseColumns.FALSE:
				if (myDataForward.daan_tow.equals("B")) {
					imageTwo.setImageResource(R.drawable.more_select);
					relTwo.setBackgroundResource(R.color.select_select);
					selectTwo.setBackgroundResource(R.color.select_select);
					imageOne.setImageResource(R.drawable.defaults);
					selectOne.setBackgroundResource(R.color.select_default);
					relOne.setBackgroundResource(R.color.select_default);
				} else {
					imageTwo.setImageResource(R.drawable.more_select);
					relTwo.setBackgroundResource(R.color.select_select);
					selectTwo.setBackgroundResource(R.color.select_select);
					imageOne.setImageResource(R.drawable.defaults);
					selectOne.setBackgroundResource(R.color.select_default);
					relOne.setBackgroundResource(R.color.select_default);
				}
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText(myDataForward.timu_one);
				selectTwo.setText(myDataForward.timu_tow);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				break;

			default:
				break;
			}

			break;

		default:
			break;
		}
	}

	private int getRightIntValue(String daan_one, String daan_tow, String daan_three, String daan_four) {
		boolean isRightA = TextUtils.isEmpty(daan_one) ? false : true;
		boolean isRightB = TextUtils.isEmpty(daan_tow) ? false : true;
		boolean isRightC = TextUtils.isEmpty(daan_three) ? false : true;
		boolean isRightD = TextUtils.isEmpty(daan_four) ? false : true;
		int RIGHT_ANSWER = 0;
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
		return RIGHT_ANSWER;
	}

	private String rightStr(int i) {
		String str = "";
		switch (i) {
		case 5:
			str = "AB";
			break;

		case 6:
			str = "AC";
			break;

		case 7:
			str = "AD";
			break;

		case 8:
			str = "BC";
			break;

		case 9:
			str = "BD";
			break;

		case 10:
			str = "CD";
			break;

		case 11:
			str = "ABC";
			break;

		case 12:
			str = "ABD";
			break;

		case 13:
			str = "ACD";
			break;

		case 14:
			str = "BCD";
			break;

		case 15:
			str = "ABCD";
			break;

		default:
			break;
		}
		return str;
	}

	private String selectColor(int i) {
		String str = "";
		switch (i) {
		case 5:
			relOne.setBackgroundResource(R.color.select_select);
			selectOne.setBackgroundResource(R.color.select_select);
			relTwo.setBackgroundResource(R.color.select_select);
			selectTwo.setBackgroundResource(R.color.select_select);
			break;

		case 6:
			relOne.setBackgroundResource(R.color.select_select);
			selectOne.setBackgroundResource(R.color.select_select);
			relThree.setBackgroundResource(R.color.select_select);
			selectThree.setBackgroundResource(R.color.select_select);
			break;

		case 7:
			relOne.setBackgroundResource(R.color.select_select);
			selectOne.setBackgroundResource(R.color.select_select);
			relFour.setBackgroundResource(R.color.select_select);
			selectFour.setBackgroundResource(R.color.select_select);
			break;

		case 8:
			relTwo.setBackgroundResource(R.color.select_select);
			selectTwo.setBackgroundResource(R.color.select_select);
			relThree.setBackgroundResource(R.color.select_select);
			selectThree.setBackgroundResource(R.color.select_select);
			break;

		case 9:
			relTwo.setBackgroundResource(R.color.select_select);
			selectTwo.setBackgroundResource(R.color.select_select);
			relFour.setBackgroundResource(R.color.select_select);
			selectFour.setBackgroundResource(R.color.select_select);
			break;

		case 10:
			relThree.setBackgroundResource(R.color.select_select);
			selectThree.setBackgroundResource(R.color.select_select);
			relFour.setBackgroundResource(R.color.select_select);
			selectFour.setBackgroundResource(R.color.select_select);
			break;

		case 11:
			relOne.setBackgroundResource(R.color.select_select);
			selectOne.setBackgroundResource(R.color.select_select);
			relTwo.setBackgroundResource(R.color.select_select);
			selectTwo.setBackgroundResource(R.color.select_select);
			relThree.setBackgroundResource(R.color.select_select);
			selectThree.setBackgroundResource(R.color.select_select);
			break;

		case 12:
			relOne.setBackgroundResource(R.color.select_select);
			selectOne.setBackgroundResource(R.color.select_select);
			relTwo.setBackgroundResource(R.color.select_select);
			selectTwo.setBackgroundResource(R.color.select_select);
			relFour.setBackgroundResource(R.color.select_select);
			selectFour.setBackgroundResource(R.color.select_select);
			break;

		case 13:
			relOne.setBackgroundResource(R.color.select_select);
			selectOne.setBackgroundResource(R.color.select_select);
			relThree.setBackgroundResource(R.color.select_select);
			selectThree.setBackgroundResource(R.color.select_select);
			relFour.setBackgroundResource(R.color.select_select);
			selectFour.setBackgroundResource(R.color.select_select);
			break;

		case 14:
			relTwo.setBackgroundResource(R.color.select_select);
			selectTwo.setBackgroundResource(R.color.select_select);
			relThree.setBackgroundResource(R.color.select_select);
			selectThree.setBackgroundResource(R.color.select_select);
			relFour.setBackgroundResource(R.color.select_select);
			selectFour.setBackgroundResource(R.color.select_select);
			break;

		case 15:
			relOne.setBackgroundResource(R.color.select_select);
			selectOne.setBackgroundResource(R.color.select_select);
			relTwo.setBackgroundResource(R.color.select_select);
			selectTwo.setBackgroundResource(R.color.select_select);
			relThree.setBackgroundResource(R.color.select_select);
			selectThree.setBackgroundResource(R.color.select_select);
			relFour.setBackgroundResource(R.color.select_select);
			selectFour.setBackgroundResource(R.color.select_select);
			break;

		default:
			break;
		}
		return str;
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return listHasId.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_gridview_selectsubject, null);
			}
			TextView tv = Net1314080903124_ViewHolder.get(convertView, R.id.textView1);
			Net1314080903124_CauseInfoHasId myData = listHasId.get(position);
			switch (myData.reply) {
			case Net1314080903124_BaseColumns.NULL:
				tv.setBackgroundResource(R.color.select_default);
				break;

			case Net1314080903124_BaseColumns.A:
				tv.setBackgroundResource(R.color.select_answered);
				break;

			case Net1314080903124_BaseColumns.B:
				tv.setBackgroundResource(R.color.select_answered);
				break;

			case Net1314080903124_BaseColumns.C:
				tv.setBackgroundResource(R.color.select_answered);
				break;

			case Net1314080903124_BaseColumns.D:
				tv.setBackgroundResource(R.color.select_answered);
				break;

			case Net1314080903124_BaseColumns.AB:
				tv.setBackgroundResource(R.color.select_answered);
				break;

			case Net1314080903124_BaseColumns.AC:
				tv.setBackgroundResource(R.color.select_answered);
				break;

			case Net1314080903124_BaseColumns.AD:
				tv.setBackgroundResource(R.color.select_answered);
				break;

			case Net1314080903124_BaseColumns.BC:
				tv.setBackgroundResource(R.color.select_answered);
				break;

			case Net1314080903124_BaseColumns.BD:
				tv.setBackgroundResource(R.color.select_answered);
				break;

			case Net1314080903124_BaseColumns.CD:
				tv.setBackgroundResource(R.color.select_answered);
				break;

			case Net1314080903124_BaseColumns.ABC:
				tv.setBackgroundResource(R.color.select_answered);
				break;

			case Net1314080903124_BaseColumns.ABD:
				tv.setBackgroundResource(R.color.select_answered);
				break;

			case Net1314080903124_BaseColumns.ACD:
				tv.setBackgroundResource(R.color.select_answered);
				break;

			case Net1314080903124_BaseColumns.BCD:
				tv.setBackgroundResource(R.color.select_answered);
				break;

			case Net1314080903124_BaseColumns.ABCD:
				tv.setBackgroundResource(R.color.select_answered);
				break;

			case Net1314080903124_BaseColumns.TRUE:
				tv.setBackgroundResource(R.color.select_answered);
				break;

			case Net1314080903124_BaseColumns.FALSE:
				tv.setBackgroundResource(R.color.select_answered);
				break;

			default:
				break;
			}
			tv.setText("" + myData.id);
			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		isSelectA = false;
		isSelectB = false;
		isSelectC = false;
		isSelectD = false;
		imageOne.setImageResource(R.drawable.defaults);
		imageTwo.setImageResource(R.drawable.defaults);
		imageThree.setImageResource(R.drawable.defaults);
		imageFour.setImageResource(R.drawable.defaults);
		selectOne.setBackgroundResource(R.color.select_default);
		selectTwo.setBackgroundResource(R.color.select_default);
		selectThree.setBackgroundResource(R.color.select_default);
		selectFour.setBackgroundResource(R.color.select_default);
		relOne.setBackgroundResource(R.color.select_default);
		relTwo.setBackgroundResource(R.color.select_default);
		relThree.setBackgroundResource(R.color.select_default);
		relFour.setBackgroundResource(R.color.select_default);
		i = position;
		Net1314080903124_ConfigPreferences.getInstance(this).setLastSelectExam(position + 1);
		isClickSelectSubject = true;
		Net1314080903124_CauseInfo myData = list.get(position);
		subjectTop.setText((position + 1) + "/" + list.size());
		if ("单选".equals(myData.types)) {
			submit.setVisibility(View.GONE);
			relThree.setVisibility(View.VISIBLE);
			relFour.setVisibility(View.VISIBLE);
		} else if ("多选".equals(myData.types)) {
			submit.setVisibility(View.VISIBLE);
			relThree.setVisibility(View.VISIBLE);
			relFour.setVisibility(View.VISIBLE);
		} else if ("判断".equals(myData.types)) {
			submit.setVisibility(View.GONE);
			relThree.setVisibility(View.GONE);
			relFour.setVisibility(View.GONE);
		}
		int replyForward = myData.getReply();
		switch (replyForward) {
		case Net1314080903124_BaseColumns.NULL:
			type.setText("题型：" + myData.types);
			title.setText((position + 1) + "." + myData.timu_title);
			if ("单选".equals(myData.types)) {
				selectOne.setText("A." + myData.timu_one);
				selectTwo.setText("B." + myData.timu_tow);
				selectThree.setText("C." + myData.timu_three);
				selectFour.setText("D." + myData.timu_four);
			} else if ("多选".equals(myData.types)) {
				selectOne.setText("A." + myData.timu_one);
				selectTwo.setText("B." + myData.timu_tow);
				selectThree.setText("C." + myData.timu_three);
				selectFour.setText("D." + myData.timu_four);
			} else if ("判断".equals(myData.types)) {
				selectOne.setText(myData.timu_one);
				selectTwo.setText(myData.timu_tow);
			}
			selectOne.setClickable(true);
			selectTwo.setClickable(true);
			selectThree.setClickable(true);
			selectFour.setClickable(true);
			if ("多选".equals(myData.types)) {
				submit.setVisibility(View.VISIBLE);
			}
			break;

		case Net1314080903124_BaseColumns.A:
			if (myData.daan_one.equals("A")) {
				imageOne.setImageResource(R.drawable.right);
				relOne.setBackgroundResource(R.color.select_right);
				selectOne.setBackgroundResource(R.color.select_right);
			} else {
				if (!TextUtils.isEmpty(myData.daan_tow)) {
					imageTwo.setImageResource(R.drawable.right);
					imageOne.setImageResource(R.drawable.wrong);
					relTwo.setBackgroundResource(R.color.select_right);
					relOne.setBackgroundResource(R.color.select_error);
					selectTwo.setBackgroundResource(R.color.select_right);
					selectOne.setBackgroundResource(R.color.select_error);
				} else if (!TextUtils.isEmpty(myData.daan_three)) {
					imageThree.setImageResource(R.drawable.right);
					imageOne.setImageResource(R.drawable.wrong);
					relThree.setBackgroundResource(R.color.select_right);
					relOne.setBackgroundResource(R.color.select_error);
					selectThree.setBackgroundResource(R.color.select_right);
					selectOne.setBackgroundResource(R.color.select_error);
				} else if (!TextUtils.isEmpty(myData.daan_four)) {
					imageFour.setImageResource(R.drawable.right);
					imageOne.setImageResource(R.drawable.wrong);
					relFour.setBackgroundResource(R.color.select_right);
					relOne.setBackgroundResource(R.color.select_error);
					selectFour.setBackgroundResource(R.color.select_right);
					selectOne.setBackgroundResource(R.color.select_error);
				}
			}
			type.setText("题型：" + myData.types);
			title.setText((position + 1) + "." + myData.timu_title);
			selectOne.setText("A." + myData.timu_one);
			selectTwo.setText("B." + myData.timu_tow);
			selectThree.setText("C." + myData.timu_three);
			selectFour.setText("D." + myData.timu_four);
			selectOne.setClickable(false);
			selectTwo.setClickable(false);
			selectThree.setClickable(false);
			selectFour.setClickable(false);
			break;

		case Net1314080903124_BaseColumns.B:
			if (myData.daan_tow.equals("B")) {
				imageTwo.setImageResource(R.drawable.right);
				relTwo.setBackgroundResource(R.color.select_right);
				selectTwo.setBackgroundResource(R.color.select_right);
			} else {
				if (!TextUtils.isEmpty(myData.daan_one)) {
					imageOne.setImageResource(R.drawable.right);
					imageTwo.setImageResource(R.drawable.wrong);
					relOne.setBackgroundResource(R.color.select_right);
					relTwo.setBackgroundResource(R.color.select_error);
					selectOne.setBackgroundResource(R.color.select_right);
					selectTwo.setBackgroundResource(R.color.select_error);
				} else if (!TextUtils.isEmpty(myData.daan_three)) {
					imageThree.setImageResource(R.drawable.right);
					imageTwo.setImageResource(R.drawable.wrong);
					relThree.setBackgroundResource(R.color.select_right);
					relTwo.setBackgroundResource(R.color.select_error);
					selectThree.setBackgroundResource(R.color.select_right);
					selectTwo.setBackgroundResource(R.color.select_error);
				} else if (!TextUtils.isEmpty(myData.daan_four)) {
					imageFour.setImageResource(R.drawable.right);
					imageTwo.setImageResource(R.drawable.wrong);
					relFour.setBackgroundResource(R.color.select_right);
					relTwo.setBackgroundResource(R.color.select_error);
					selectFour.setBackgroundResource(R.color.select_right);
					selectTwo.setBackgroundResource(R.color.select_error);
				}
			}
			type.setText("题型：" + myData.types);
			title.setText((position + 1) + "." + myData.timu_title);
			selectOne.setText("A." + myData.timu_one);
			selectTwo.setText("B." + myData.timu_tow);
			selectThree.setText("C." + myData.timu_three);
			selectFour.setText("D." + myData.timu_four);
			selectOne.setClickable(false);
			selectTwo.setClickable(false);
			selectThree.setClickable(false);
			selectFour.setClickable(false);
			break;

		case Net1314080903124_BaseColumns.C:
			if (myData.daan_three.equals("C")) {
				imageThree.setImageResource(R.drawable.right);
				relThree.setBackgroundResource(R.color.select_right);
				selectThree.setBackgroundResource(R.color.select_right);
			} else {
				if (!TextUtils.isEmpty(myData.daan_one)) {
					imageOne.setImageResource(R.drawable.right);
					imageThree.setImageResource(R.drawable.wrong);
					relOne.setBackgroundResource(R.color.select_right);
					relThree.setBackgroundResource(R.color.select_error);
					selectOne.setBackgroundResource(R.color.select_right);
					selectThree.setBackgroundResource(R.color.select_error);
				} else if (!TextUtils.isEmpty(myData.daan_tow)) {
					imageTwo.setImageResource(R.drawable.right);
					imageThree.setImageResource(R.drawable.wrong);
					relTwo.setBackgroundResource(R.color.select_right);
					relThree.setBackgroundResource(R.color.select_error);
					selectTwo.setBackgroundResource(R.color.select_right);
					selectThree.setBackgroundResource(R.color.select_error);
				} else if (!TextUtils.isEmpty(myData.daan_four)) {
					imageFour.setImageResource(R.drawable.right);
					imageThree.setImageResource(R.drawable.wrong);
					relFour.setBackgroundResource(R.color.select_right);
					relThree.setBackgroundResource(R.color.select_error);
					selectFour.setBackgroundResource(R.color.select_right);
					selectThree.setBackgroundResource(R.color.select_error);
				}
			}
			type.setText("题型：" + myData.types);
			title.setText((position + 1) + "." + myData.timu_title);
			selectOne.setText("A." + myData.timu_one);
			selectTwo.setText("B." + myData.timu_tow);
			selectThree.setText("C." + myData.timu_three);
			selectFour.setText("D." + myData.timu_four);
			selectOne.setClickable(false);
			selectTwo.setClickable(false);
			selectThree.setClickable(false);
			selectFour.setClickable(false);
			break;

		case Net1314080903124_BaseColumns.D:
			if (myData.daan_four.equals("D")) {
				imageFour.setImageResource(R.drawable.right);
				relFour.setBackgroundResource(R.color.select_right);
				selectFour.setBackgroundResource(R.color.select_right);
			} else {
				if (!TextUtils.isEmpty(myData.daan_one)) {
					imageOne.setImageResource(R.drawable.right);
					imageFour.setImageResource(R.drawable.wrong);
					relOne.setBackgroundResource(R.color.select_right);
					relFour.setBackgroundResource(R.color.select_error);
					selectOne.setBackgroundResource(R.color.select_right);
					selectFour.setBackgroundResource(R.color.select_error);
				} else if (!TextUtils.isEmpty(myData.daan_tow)) {
					imageTwo.setImageResource(R.drawable.right);
					imageFour.setImageResource(R.drawable.wrong);
					relTwo.setBackgroundResource(R.color.select_right);
					relFour.setBackgroundResource(R.color.select_error);
					selectTwo.setBackgroundResource(R.color.select_right);
					selectFour.setBackgroundResource(R.color.select_error);
				} else if (!TextUtils.isEmpty(myData.daan_three)) {
					imageThree.setImageResource(R.drawable.right);
					imageFour.setImageResource(R.drawable.wrong);
					relThree.setBackgroundResource(R.color.select_right);
					relFour.setBackgroundResource(R.color.select_error);
					selectThree.setBackgroundResource(R.color.select_right);
					selectFour.setBackgroundResource(R.color.select_error);
				}
			}
			type.setText("题型：" + myData.types);
			title.setText((position + 1) + "." + myData.timu_title);
			selectOne.setText("A." + myData.timu_one);
			selectTwo.setText("B." + myData.timu_tow);
			selectThree.setText("C." + myData.timu_three);
			selectFour.setText("D." + myData.timu_four);
			selectOne.setClickable(false);
			selectTwo.setClickable(false);
			selectThree.setClickable(false);
			selectFour.setClickable(false);
			break;

		case Net1314080903124_BaseColumns.AB:
			type.setText("题型：" + myData.types);
			title.setText((position + 1) + "." + myData.timu_title);
			selectOne.setText("A." + myData.timu_one);
			selectTwo.setText("B." + myData.timu_tow);
			selectThree.setText("C." + myData.timu_three);
			selectFour.setText("D." + myData.timu_four);
			selectColor(myData.reply);
			selectOne.setClickable(false);
			selectTwo.setClickable(false);
			selectThree.setClickable(false);
			selectFour.setClickable(false);
			submit.setVisibility(View.GONE);
			imageOne.setImageResource(R.drawable.more_select);
			imageTwo.setImageResource(R.drawable.more_select);
			break;

		case Net1314080903124_BaseColumns.AC:
			type.setText("题型：" + myData.types);
			title.setText((position + 1) + "." + myData.timu_title);
			selectOne.setText("A." + myData.timu_one);
			selectTwo.setText("B." + myData.timu_tow);
			selectThree.setText("C." + myData.timu_three);
			selectFour.setText("D." + myData.timu_four);
			selectColor(myData.reply);
			selectOne.setClickable(false);
			selectTwo.setClickable(false);
			selectThree.setClickable(false);
			selectFour.setClickable(false);
			submit.setVisibility(View.GONE);
			imageOne.setImageResource(R.drawable.more_select);
			imageThree.setImageResource(R.drawable.more_select);
			break;

		case Net1314080903124_BaseColumns.AD:
			type.setText("题型：" + myData.types);
			title.setText((position + 1) + "." + myData.timu_title);
			selectOne.setText("A." + myData.timu_one);
			selectTwo.setText("B." + myData.timu_tow);
			selectThree.setText("C." + myData.timu_three);
			selectFour.setText("D." + myData.timu_four);
			selectColor(myData.reply);
			selectOne.setClickable(false);
			selectTwo.setClickable(false);
			selectThree.setClickable(false);
			selectFour.setClickable(false);
			submit.setVisibility(View.GONE);
			imageOne.setImageResource(R.drawable.more_select);
			imageFour.setImageResource(R.drawable.more_select);
			break;

		case Net1314080903124_BaseColumns.BC:
			type.setText("题型：" + myData.types);
			title.setText((position + 1) + "." + myData.timu_title);
			selectOne.setText("A." + myData.timu_one);
			selectTwo.setText("B." + myData.timu_tow);
			selectThree.setText("C." + myData.timu_three);
			selectFour.setText("D." + myData.timu_four);
			selectColor(myData.reply);
			selectOne.setClickable(false);
			selectTwo.setClickable(false);
			selectThree.setClickable(false);
			selectFour.setClickable(false);
			submit.setVisibility(View.GONE);
			imageTwo.setImageResource(R.drawable.more_select);
			imageThree.setImageResource(R.drawable.more_select);
			break;

		case Net1314080903124_BaseColumns.BD:
			type.setText("题型：" + myData.types);
			title.setText((position + 1) + "." + myData.timu_title);
			selectOne.setText("A." + myData.timu_one);
			selectTwo.setText("B." + myData.timu_tow);
			selectThree.setText("C." + myData.timu_three);
			selectFour.setText("D." + myData.timu_four);
			selectColor(myData.reply);
			selectOne.setClickable(false);
			selectTwo.setClickable(false);
			selectThree.setClickable(false);
			selectFour.setClickable(false);
			submit.setVisibility(View.GONE);
			imageTwo.setImageResource(R.drawable.more_select);
			imageFour.setImageResource(R.drawable.more_select);
			break;

		case Net1314080903124_BaseColumns.CD:
			type.setText("题型：" + myData.types);
			title.setText((position + 1) + "." + myData.timu_title);
			selectOne.setText("A." + myData.timu_one);
			selectTwo.setText("B." + myData.timu_tow);
			selectThree.setText("C." + myData.timu_three);
			selectFour.setText("D." + myData.timu_four);
			selectColor(myData.reply);
			selectOne.setClickable(false);
			selectTwo.setClickable(false);
			selectThree.setClickable(false);
			selectFour.setClickable(false);
			submit.setVisibility(View.GONE);
			imageThree.setImageResource(R.drawable.more_select);
			imageFour.setImageResource(R.drawable.more_select);
			break;

		case Net1314080903124_BaseColumns.ABC:
			type.setText("题型：" + myData.types);
			title.setText((position + 1) + "." + myData.timu_title);
			selectOne.setText("A." + myData.timu_one);
			selectTwo.setText("B." + myData.timu_tow);
			selectThree.setText("C." + myData.timu_three);
			selectFour.setText("D." + myData.timu_four);
			selectColor(myData.reply);
			selectOne.setClickable(false);
			selectTwo.setClickable(false);
			selectThree.setClickable(false);
			selectFour.setClickable(false);
			submit.setVisibility(View.GONE);
			imageOne.setImageResource(R.drawable.more_select);
			imageTwo.setImageResource(R.drawable.more_select);
			imageThree.setImageResource(R.drawable.more_select);
			break;

		case Net1314080903124_BaseColumns.ABD:
			type.setText("题型：" + myData.types);
			title.setText((position + 1) + "." + myData.timu_title);
			selectOne.setText("A." + myData.timu_one);
			selectTwo.setText("B." + myData.timu_tow);
			selectThree.setText("C." + myData.timu_three);
			selectFour.setText("D." + myData.timu_four);
			selectColor(myData.reply);
			selectOne.setClickable(false);
			selectTwo.setClickable(false);
			selectThree.setClickable(false);
			selectFour.setClickable(false);
			submit.setVisibility(View.GONE);
			imageOne.setImageResource(R.drawable.more_select);
			imageTwo.setImageResource(R.drawable.more_select);
			imageFour.setImageResource(R.drawable.more_select);
			break;

		case Net1314080903124_BaseColumns.ACD:
			type.setText("题型：" + myData.types);
			title.setText((position + 1) + "." + myData.timu_title);
			selectOne.setText("A." + myData.timu_one);
			selectTwo.setText("B." + myData.timu_tow);
			selectThree.setText("C." + myData.timu_three);
			selectFour.setText("D." + myData.timu_four);
			selectColor(myData.reply);
			selectOne.setClickable(false);
			selectTwo.setClickable(false);
			selectThree.setClickable(false);
			selectFour.setClickable(false);
			submit.setVisibility(View.GONE);
			imageOne.setImageResource(R.drawable.more_select);
			imageThree.setImageResource(R.drawable.more_select);
			imageFour.setImageResource(R.drawable.more_select);
			break;

		case Net1314080903124_BaseColumns.BCD:
			type.setText("题型：" + myData.types);
			title.setText((position + 1) + "." + myData.timu_title);
			selectOne.setText("A." + myData.timu_one);
			selectTwo.setText("B." + myData.timu_tow);
			selectThree.setText("C." + myData.timu_three);
			selectFour.setText("D." + myData.timu_four);
			selectColor(myData.reply);
			selectOne.setClickable(false);
			selectTwo.setClickable(false);
			selectThree.setClickable(false);
			selectFour.setClickable(false);
			submit.setVisibility(View.GONE);
			imageTwo.setImageResource(R.drawable.more_select);
			imageThree.setImageResource(R.drawable.more_select);
			imageFour.setImageResource(R.drawable.more_select);
			break;

		case Net1314080903124_BaseColumns.ABCD:
			type.setText("题型：" + myData.types);
			title.setText((position + 1) + "." + myData.timu_title);
			selectOne.setText("A." + myData.timu_one);
			selectTwo.setText("B." + myData.timu_tow);
			selectThree.setText("C." + myData.timu_three);
			selectFour.setText("D." + myData.timu_four);
			selectColor(myData.reply);
			selectOne.setClickable(false);
			selectTwo.setClickable(false);
			selectThree.setClickable(false);
			selectFour.setClickable(false);
			submit.setVisibility(View.GONE);
			imageOne.setImageResource(R.drawable.more_select);
			imageTwo.setImageResource(R.drawable.more_select);
			imageThree.setImageResource(R.drawable.more_select);
			imageFour.setImageResource(R.drawable.more_select);
			break;

		case Net1314080903124_BaseColumns.TRUE:
			if (myData.daan_one.equals("A")) {
				imageOne.setImageResource(R.drawable.right);
				relOne.setBackgroundResource(R.color.select_right);
				selectOne.setBackgroundResource(R.color.select_right);
			} else {
				imageOne.setImageResource(R.drawable.wrong);
				imageTwo.setImageResource(R.drawable.right);
				relOne.setBackgroundResource(R.color.select_error);
				relTwo.setBackgroundResource(R.color.select_right);
				selectOne.setBackgroundResource(R.color.select_error);
				selectTwo.setBackgroundResource(R.color.select_right);
			}
			type.setText("题型：" + myData.types);
			title.setText((position + 1) + "." + myData.timu_title);
			selectOne.setText(myData.timu_one);
			selectTwo.setText(myData.timu_tow);
			selectOne.setClickable(false);
			selectTwo.setClickable(false);
			selectThree.setClickable(false);
			selectFour.setClickable(false);
			break;

		case Net1314080903124_BaseColumns.FALSE:
			if (myData.daan_tow.equals("B")) {
				imageTwo.setImageResource(R.drawable.right);
				relTwo.setBackgroundResource(R.color.select_right);
				selectTwo.setBackgroundResource(R.color.select_right);
			} else {
				imageOne.setImageResource(R.drawable.right);
				imageTwo.setImageResource(R.drawable.wrong);
				relOne.setBackgroundResource(R.color.select_right);
				relTwo.setBackgroundResource(R.color.select_error);
				selectOne.setBackgroundResource(R.color.select_right);
				selectTwo.setBackgroundResource(R.color.select_error);
			}
			type.setText("题型：" + myData.types);
			title.setText((position + 1) + "." + myData.timu_title);
			selectOne.setText(myData.timu_one);
			selectTwo.setText(myData.timu_tow);
			selectOne.setClickable(false);
			selectTwo.setClickable(false);
			selectThree.setClickable(false);
			selectFour.setClickable(false);
			break;

		default:
			break;

		}
		dialogs.dismiss();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		timer.cancel();
		Net1314080903124_ConfigPreferences.getInstance(this).setLastSelectExam(1);
		Net1314080903124_DBManager.getInstance(this).updateWhenDestroy(Net1314080903124_AnswerColumns.TABLE_NAME);
		Net1314080903124_DBManager.getInstance(this).updateWhenDestroy(Net1314080903124_CollectColumns.TABLE_NAME);
		Net1314080903124_DBManager.getInstance(this).updateWhenDestroy(Net1314080903124_ErrorColumns.TABLE_NAME);
	}

}
