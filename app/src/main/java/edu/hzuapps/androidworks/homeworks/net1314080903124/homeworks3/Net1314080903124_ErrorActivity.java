package edu.hzuapps.androidworks.homeworks3;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_BaseColumns;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_CauseInfo;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_CauseInfoHasId;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_DBManager;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_Digital;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_ErrorColumns;
import edu.hzuapps.androidworks.homeworks4.Net1314080903124_ConfigPreferences;
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
 * 我的错题
 * @author Summer
 *
 */
public class Net1314080903124_ErrorActivity extends Activity implements OnItemClickListener, OnClickListener {

	private TextView title;
	private TextView selectOne;
	private TextView selectTwo;
	private TextView selectThree;
	private TextView selectFour;
	private TextView rightAnswer;
	private TextView answer;
	private TextView type;
	private RelativeLayout relOne;
	private RelativeLayout relTwo;
	private RelativeLayout relThree;
	private RelativeLayout relFour;
	private ImageView imageOne;
	private ImageView imageTwo;
	private ImageView imageThree;
	private ImageView imageFour;
	private TextView subjectTop;
	private TextView submit;
	private ArrayList<Net1314080903124_CauseInfo> list;
	private int i = 0;
	private boolean isSelectA;
	private boolean isSelectB;
	private boolean isSelectC;
	private boolean isSelectD;
	private int replySub = 0;
	private double rightSub = 0;
	private double wrongSub = 0;
	private MyAdapter myAdapter;
	private ArrayList<Net1314080903124_CauseInfoHasId> listHasId;
	private ArrayList<Net1314080903124_Digital> listNumb = new ArrayList<Net1314080903124_Digital>();
	private Dialog dialog;
	private boolean isClickSelectSubject;
	private RelativeLayout subject;
	private RelativeLayout remove;
	private RelativeLayout forward;
	private TextView help;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_error);
		resetTitlebar();
		title = (TextView) findViewById(R.id.title);
		selectOne = (TextView) findViewById(R.id.selectOne);
		selectTwo = (TextView) findViewById(R.id.selectTwo);
		selectThree = (TextView) findViewById(R.id.selectThree);
		selectFour = (TextView) findViewById(R.id.selectFour);
		rightAnswer = (TextView) findViewById(R.id.rightAnswer);
		answer = (TextView) findViewById(R.id.answer);
		type = (TextView) findViewById(R.id.type);
		RelativeLayout back = (RelativeLayout) findViewById(R.id.back);
		subject = (RelativeLayout) findViewById(R.id.subject);
		remove = (RelativeLayout) findViewById(R.id.remove);
		forward = (RelativeLayout) findViewById(R.id.forward);
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
		help = (TextView) findViewById(R.id.help);
		RelativeLayout rel_help = (RelativeLayout) findViewById(R.id.rel_help);
		new QueryTask().execute();
		submit.setOnClickListener(this);
		selectOne.setOnClickListener(this);
		selectTwo.setOnClickListener(this);
		selectThree.setOnClickListener(this);
		selectFour.setOnClickListener(this);
		back.setOnClickListener(this);
		subject.setOnClickListener(this);
		remove.setOnClickListener(this);
		forward.setOnClickListener(this);
		rel_help.setOnClickListener(this);
	}

	private class QueryTask extends AsyncTask<Void, Void, ArrayList<Net1314080903124_CauseInfo>> {
		@Override
		protected ArrayList<Net1314080903124_CauseInfo> doInBackground(Void... params) {
			list = Net1314080903124_DBManager.getInstance(Net1314080903124_ErrorActivity.this).query(Net1314080903124_ErrorColumns.TABLE_NAME);
			return list;
		}

		@Override
		protected void onPostExecute(ArrayList<Net1314080903124_CauseInfo> list) {
			if (list.size() == 0) {
				Toast.makeText(Net1314080903124_ErrorActivity.this, "您暂时没有错误的题目", Toast.LENGTH_SHORT).show();
				subjectTop.setText("0/0");
				selectOne.setText("");
				selectTwo.setText("");
				selectThree.setText("");
				selectFour.setText("");
				return;
			}
			int lastSelect = Net1314080903124_ConfigPreferences.getInstance(Net1314080903124_ErrorActivity.this).isLastSelectError();
			i = lastSelect - 1;
			Net1314080903124_CauseInfo myData = list.get(lastSelect - 1);
			title.setText("" + lastSelect + "." + myData.timu_title);
			type.setText("题型：" + myData.types);
			subjectTop.setText("" + lastSelect + "/" + list.size());
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
				selectOne.setText( myData.timu_one);
				selectTwo.setText(myData.timu_tow);
			}
		}
	}

	private void resetTitlebar() {
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.view_comm_titlebar);
		TextView title = (TextView) findViewById(R.id.titlebar_title);
		LinearLayout back = (LinearLayout) findViewById(R.id.titlebar_left_layout);
		TextView right = (TextView) findViewById(R.id.titlebar_right_text);
		title.setText("我的错题");
		right.setText("清空");
		right.setOnClickListener(this);
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
		
		case R.id.rel_help:
			help.setText(myData.detail);
			break;
		
		case R.id.titlebar_right_text:
			Net1314080903124_DBManager.getInstance(this).removeAll(Net1314080903124_ErrorColumns.TABLE_NAME);
			list.clear();
			subjectTop.setText("0/0");
			help.setText("");
			title.setText("");
			type.setText("");
			selectOne.setText("");
			selectTwo.setText("");
			selectThree.setText("");
			selectFour.setText("");
			submit.setVisibility(View.GONE);
			selectOne.setClickable(false);
			selectTwo.setClickable(false);
			selectThree.setClickable(false);
			selectFour.setClickable(false);
			subject.setClickable(false);
			remove.setClickable(false);
			forward.setClickable(false);
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
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				replySub++;
				int RIGHT_ANSWER = getRightIntValue(daan_one, daan_tow, daan_three, daan_four);
				String rightStr = rightStr(RIGHT_ANSWER);

				int XUANXIANG;
				if (isSelectA == true && isSelectB == true && isSelectC == false && isSelectD == false) {
					XUANXIANG = Net1314080903124_BaseColumns.AB;
					myData.setReply(XUANXIANG);
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
						rightSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
						Toast.makeText(this, "恭喜您答对了", Toast.LENGTH_SHORT).show();
					} else {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
					}
				} else if (isSelectA == true && isSelectC == true && isSelectB == false && isSelectD == false) {
					XUANXIANG = Net1314080903124_BaseColumns.AC;
					myData.setReply(XUANXIANG);
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
						rightSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
						Toast.makeText(this, "恭喜您答对了", Toast.LENGTH_SHORT).show();
					} else {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
					}
				} else if (isSelectA == true && isSelectD == true && isSelectB == false && isSelectC == false) {
					XUANXIANG = Net1314080903124_BaseColumns.AD;
					myData.setReply(XUANXIANG);
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
						rightSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
						Toast.makeText(this, "恭喜您答对了", Toast.LENGTH_SHORT).show();
					} else {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
					}
				} else if (isSelectB == true && isSelectC == true && isSelectA == false && isSelectD == false) {
					XUANXIANG = Net1314080903124_BaseColumns.BC;
					myData.setReply(XUANXIANG);
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
						rightSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
						Toast.makeText(this, "恭喜您答对了", Toast.LENGTH_SHORT).show();
					} else {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
					}
				} else if (isSelectB == true && isSelectD == true && isSelectA == false && isSelectC == false) {
					XUANXIANG = Net1314080903124_BaseColumns.BD;
					myData.setReply(XUANXIANG);
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
						rightSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
						Toast.makeText(this, "恭喜您答对了", Toast.LENGTH_SHORT).show();
					} else {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
					}
				} else if (isSelectC == true && isSelectD == true && isSelectA == false && isSelectB == false) {
					XUANXIANG = Net1314080903124_BaseColumns.CD;
					myData.setReply(XUANXIANG);
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
						rightSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
						Toast.makeText(this, "恭喜您答对了", Toast.LENGTH_SHORT).show();
					} else {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
					}
				} else if (isSelectA == true && isSelectB == true && isSelectC == true && isSelectD == false) {
					XUANXIANG = Net1314080903124_BaseColumns.ABC;
					myData.setReply(XUANXIANG);
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
						rightSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
						Toast.makeText(this, "恭喜您答对了", Toast.LENGTH_SHORT).show();
					} else {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
					}
				} else if (isSelectA == true && isSelectB == true && isSelectD == true && isSelectC == false) {
					XUANXIANG = Net1314080903124_BaseColumns.ABD;
					myData.setReply(XUANXIANG);
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
						rightSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
						Toast.makeText(this, "恭喜您答对了", Toast.LENGTH_SHORT).show();
					} else {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
					}
				} else if (isSelectA == true && isSelectC == true && isSelectD == true && isSelectB == false) {
					XUANXIANG = Net1314080903124_BaseColumns.ACD;
					myData.setReply(XUANXIANG);
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
						rightSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
						Toast.makeText(this, "恭喜您答对了", Toast.LENGTH_SHORT).show();
					} else {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
					}
				} else if (isSelectB == true && isSelectC == true && isSelectD == true && isSelectA == false) {
					XUANXIANG = Net1314080903124_BaseColumns.BCD;
					myData.setReply(XUANXIANG);
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
						rightSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
						Toast.makeText(this, "恭喜您答对了", Toast.LENGTH_SHORT).show();
					} else {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
					}
				} else if (isSelectA == true && isSelectB == true && isSelectC == true && isSelectD == true) {
					XUANXIANG = Net1314080903124_BaseColumns.ABCD;
					myData.setReply(XUANXIANG);
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					if (RIGHT_ANSWER == XUANXIANG) {
						rightSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
						Toast.makeText(this, "恭喜您答对了", Toast.LENGTH_SHORT).show();
					} else {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						rightAnswer.setText("正确答案：" + rightStr);
					}
				}
			}
			break;

		case R.id.selectOne:
			if ("单选".equals(types)) {
				replySub++;
				myData.setReply(Net1314080903124_BaseColumns.A);
				Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
				if (!TextUtils.isEmpty(daan_one)) {
					rightSub++;
					answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
							+ (int) ((rightSub / replySub) * 100) + "%");
					imageOne.setImageResource(R.drawable.right);
					relOne.setBackgroundResource(R.color.select_right);
					selectOne.setBackgroundResource(R.color.select_right);
					Toast.makeText(this, "恭喜您答对了", Toast.LENGTH_SHORT).show();
				} else {
					if (!TextUtils.isEmpty(daan_tow)) {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						imageTwo.setImageResource(R.drawable.right);
						imageOne.setImageResource(R.drawable.wrong);
						relTwo.setBackgroundResource(R.color.select_right);
						relOne.setBackgroundResource(R.color.select_error);
						selectTwo.setBackgroundResource(R.color.select_right);
						selectOne.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(daan_three)) {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						imageThree.setImageResource(R.drawable.right);
						imageOne.setImageResource(R.drawable.wrong);
						relThree.setBackgroundResource(R.color.select_right);
						relOne.setBackgroundResource(R.color.select_error);
						selectThree.setBackgroundResource(R.color.select_right);
						selectOne.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(daan_four)) {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						imageFour.setImageResource(R.drawable.right);
						imageOne.setImageResource(R.drawable.wrong);
						relFour.setBackgroundResource(R.color.select_right);
						relOne.setBackgroundResource(R.color.select_error);
						selectFour.setBackgroundResource(R.color.select_right);
						selectOne.setBackgroundResource(R.color.select_error);
					}
				}
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
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
				replySub++;
				if (!TextUtils.isEmpty(daan_one)) {
					rightSub++;
					myData.setReply(Net1314080903124_BaseColumns.TRUE);
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
							+ (int) ((rightSub / replySub) * 100) + "%");
					imageOne.setImageResource(R.drawable.right);
					relOne.setBackgroundResource(R.color.select_right);
					selectOne.setBackgroundResource(R.color.select_right);
					Toast.makeText(this, "恭喜您答对了", Toast.LENGTH_SHORT).show();
				} else {
					wrongSub++;
					myData.setReply(Net1314080903124_BaseColumns.TRUE);
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
							+ (int) ((rightSub / replySub) * 100) + "%");
					imageTwo.setImageResource(R.drawable.right);
					imageOne.setImageResource(R.drawable.wrong);
					relTwo.setBackgroundResource(R.color.select_right);
					relOne.setBackgroundResource(R.color.select_error);
					selectTwo.setBackgroundResource(R.color.select_right);
					selectOne.setBackgroundResource(R.color.select_error);
				}
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
			}
			break;

		case R.id.selectTwo:
			if ("单选".equals(types)) {
				replySub++;
				myData.setReply(Net1314080903124_BaseColumns.B);
				Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
				if (!TextUtils.isEmpty(daan_tow)) {
					rightSub++;
					answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
							+ (int) ((rightSub / replySub) * 100) + "%");
					imageTwo.setImageResource(R.drawable.right);
					relTwo.setBackgroundResource(R.color.select_right);
					selectTwo.setBackgroundResource(R.color.select_right);
					Toast.makeText(this, "恭喜您答对了", Toast.LENGTH_SHORT).show();
				} else {
					if (!TextUtils.isEmpty(daan_one)) {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						imageOne.setImageResource(R.drawable.right);
						imageTwo.setImageResource(R.drawable.wrong);
						relOne.setBackgroundResource(R.color.select_right);
						relTwo.setBackgroundResource(R.color.select_error);
						selectOne.setBackgroundResource(R.color.select_right);
						selectTwo.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(daan_three)) {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						imageThree.setImageResource(R.drawable.right);
						imageTwo.setImageResource(R.drawable.wrong);
						relThree.setBackgroundResource(R.color.select_right);
						relTwo.setBackgroundResource(R.color.select_error);
						selectThree.setBackgroundResource(R.color.select_right);
						selectTwo.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(daan_four)) {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						imageFour.setImageResource(R.drawable.right);
						imageTwo.setImageResource(R.drawable.wrong);
						relFour.setBackgroundResource(R.color.select_right);
						relTwo.setBackgroundResource(R.color.select_error);
						selectFour.setBackgroundResource(R.color.select_right);
						selectTwo.setBackgroundResource(R.color.select_error);
					}
				}
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
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
				replySub++;
				if (!TextUtils.isEmpty(daan_tow)) {
					rightSub++;
					myData.setReply(Net1314080903124_BaseColumns.FALSE);
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
							+ (int) ((rightSub / replySub) * 100) + "%");
					imageTwo.setImageResource(R.drawable.right);
					relTwo.setBackgroundResource(R.color.select_right);
					selectTwo.setBackgroundResource(R.color.select_right);
					Toast.makeText(this, "恭喜您答对了", Toast.LENGTH_SHORT).show();
				} else {
					wrongSub++;
					myData.setReply(Net1314080903124_BaseColumns.FALSE);
					Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
					answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
							+ (int) ((rightSub / replySub) * 100) + "%");
					imageTwo.setImageResource(R.drawable.wrong);
					imageOne.setImageResource(R.drawable.right);
					relTwo.setBackgroundResource(R.color.select_error);
					relOne.setBackgroundResource(R.color.select_right);
					selectTwo.setBackgroundResource(R.color.select_error);
					selectOne.setBackgroundResource(R.color.select_right);
				}
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
			}
			break;

		case R.id.selectThree:
			if ("单选".equals(types)) {
				replySub++;
				myData.setReply(Net1314080903124_BaseColumns.C);
				Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
				if (!TextUtils.isEmpty(daan_three)) {
					rightSub++;
					answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
							+ (int) ((rightSub / replySub) * 100) + "%");
					imageThree.setImageResource(R.drawable.right);
					relThree.setBackgroundResource(R.color.select_right);
					selectThree.setBackgroundResource(R.color.select_right);
					Toast.makeText(this, "恭喜您答对了", Toast.LENGTH_SHORT).show();
				} else {
					if (!TextUtils.isEmpty(daan_one)) {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						imageOne.setImageResource(R.drawable.right);
						imageThree.setImageResource(R.drawable.wrong);
						relOne.setBackgroundResource(R.color.select_right);
						relThree.setBackgroundResource(R.color.select_error);
						selectOne.setBackgroundResource(R.color.select_right);
						selectThree.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(daan_tow)) {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						imageTwo.setImageResource(R.drawable.right);
						imageThree.setImageResource(R.drawable.wrong);
						relTwo.setBackgroundResource(R.color.select_right);
						relThree.setBackgroundResource(R.color.select_error);
						selectTwo.setBackgroundResource(R.color.select_right);
						selectThree.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(daan_four)) {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						imageFour.setImageResource(R.drawable.right);
						imageThree.setImageResource(R.drawable.wrong);
						relFour.setBackgroundResource(R.color.select_right);
						relThree.setBackgroundResource(R.color.select_error);
						selectFour.setBackgroundResource(R.color.select_right);
						selectThree.setBackgroundResource(R.color.select_error);
					}
				}
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
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
				replySub++;
				myData.setReply(Net1314080903124_BaseColumns.D);
				Net1314080903124_DBManager.getInstance(this).update(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
				if (!TextUtils.isEmpty(daan_four)) {
					rightSub++;
					answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
							+ (int) ((rightSub / replySub) * 100) + "%");
					imageFour.setImageResource(R.drawable.right);
					relFour.setBackgroundResource(R.color.select_right);
					selectFour.setBackgroundResource(R.color.select_right);
					Toast.makeText(this, "恭喜您答对了", Toast.LENGTH_SHORT).show();
				} else {
					if (!TextUtils.isEmpty(daan_one)) {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						imageOne.setImageResource(R.drawable.right);
						imageFour.setImageResource(R.drawable.wrong);
						relOne.setBackgroundResource(R.color.select_right);
						relFour.setBackgroundResource(R.color.select_error);
						selectOne.setBackgroundResource(R.color.select_right);
						selectFour.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(daan_tow)) {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						imageTwo.setImageResource(R.drawable.right);
						imageFour.setImageResource(R.drawable.wrong);
						relTwo.setBackgroundResource(R.color.select_right);
						relFour.setBackgroundResource(R.color.select_error);
						selectTwo.setBackgroundResource(R.color.select_right);
						selectFour.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(daan_three)) {
						wrongSub++;
						answer.setText("共答" + replySub + "题，答对" + (int) rightSub + "题，答错" + (int) wrongSub + "题，正确率"
								+ (int) ((rightSub / replySub) * 100) + "%");
						imageThree.setImageResource(R.drawable.right);
						imageFour.setImageResource(R.drawable.wrong);
						relThree.setBackgroundResource(R.color.select_right);
						relFour.setBackgroundResource(R.color.select_error);
						selectThree.setBackgroundResource(R.color.select_right);
						selectFour.setBackgroundResource(R.color.select_error);
					}
				}
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
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
			help.setText("");
			answer.setText("");
			rightAnswer.setText("");
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
				i = Net1314080903124_ConfigPreferences.getInstance(this).isLastSelectError() - 1;
				i -= 1;
				isClickSelectSubject = false;
			} else {
				i -= 1;
			}
			Net1314080903124_ConfigPreferences.getInstance(this).setLastSelectError(i + 1);
			list.clear();
			list = Net1314080903124_DBManager.getInstance(this).query(Net1314080903124_ErrorColumns.TABLE_NAME);
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
					imageOne.setImageResource(R.drawable.right);
					relOne.setBackgroundResource(R.color.select_right);
					selectOne.setBackgroundResource(R.color.select_right);
				} else {
					if (!TextUtils.isEmpty(myDataBack.daan_tow)) {
						imageTwo.setImageResource(R.drawable.right);
						imageOne.setImageResource(R.drawable.wrong);
						relTwo.setBackgroundResource(R.color.select_right);
						relOne.setBackgroundResource(R.color.select_error);
						selectTwo.setBackgroundResource(R.color.select_right);
						selectOne.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataBack.daan_three)) {
						imageThree.setImageResource(R.drawable.right);
						imageOne.setImageResource(R.drawable.wrong);
						relThree.setBackgroundResource(R.color.select_right);
						relOne.setBackgroundResource(R.color.select_error);
						selectThree.setBackgroundResource(R.color.select_right);
						selectOne.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataBack.daan_four)) {
						imageFour.setImageResource(R.drawable.right);
						imageOne.setImageResource(R.drawable.wrong);
						relFour.setBackgroundResource(R.color.select_right);
						relOne.setBackgroundResource(R.color.select_error);
						selectFour.setBackgroundResource(R.color.select_right);
						selectOne.setBackgroundResource(R.color.select_error);
					}
				}
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				break;

			case Net1314080903124_BaseColumns.B:
				if (myDataBack.daan_tow.equals("B")) {
					imageTwo.setImageResource(R.drawable.right);
					relTwo.setBackgroundResource(R.color.select_right);
					selectTwo.setBackgroundResource(R.color.select_right);
				} else {
					if (!TextUtils.isEmpty(myDataBack.daan_one)) {
						imageOne.setImageResource(R.drawable.right);
						imageTwo.setImageResource(R.drawable.wrong);
						relOne.setBackgroundResource(R.color.select_right);
						relTwo.setBackgroundResource(R.color.select_error);
						selectOne.setBackgroundResource(R.color.select_right);
						selectTwo.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataBack.daan_three)) {
						imageThree.setImageResource(R.drawable.right);
						imageTwo.setImageResource(R.drawable.wrong);
						relThree.setBackgroundResource(R.color.select_right);
						relTwo.setBackgroundResource(R.color.select_error);
						selectThree.setBackgroundResource(R.color.select_right);
						selectTwo.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataBack.daan_four)) {
						imageFour.setImageResource(R.drawable.right);
						imageTwo.setImageResource(R.drawable.wrong);
						relFour.setBackgroundResource(R.color.select_right);
						relTwo.setBackgroundResource(R.color.select_error);
						selectFour.setBackgroundResource(R.color.select_right);
						selectTwo.setBackgroundResource(R.color.select_error);
					}
				}
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				break;

			case Net1314080903124_BaseColumns.C:
				if (myDataBack.daan_three.equals("C")) {
					imageThree.setImageResource(R.drawable.right);
					relThree.setBackgroundResource(R.color.select_right);
					selectThree.setBackgroundResource(R.color.select_right);
				} else {
					if (!TextUtils.isEmpty(myDataBack.daan_one)) {
						imageOne.setImageResource(R.drawable.right);
						imageThree.setImageResource(R.drawable.wrong);
						relOne.setBackgroundResource(R.color.select_right);
						relThree.setBackgroundResource(R.color.select_error);
						selectOne.setBackgroundResource(R.color.select_right);
						selectThree.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataBack.daan_tow)) {
						imageTwo.setImageResource(R.drawable.right);
						imageThree.setImageResource(R.drawable.wrong);
						relTwo.setBackgroundResource(R.color.select_right);
						relThree.setBackgroundResource(R.color.select_error);
						selectTwo.setBackgroundResource(R.color.select_right);
						selectThree.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataBack.daan_four)) {
						imageFour.setImageResource(R.drawable.right);
						imageThree.setImageResource(R.drawable.wrong);
						relFour.setBackgroundResource(R.color.select_right);
						relThree.setBackgroundResource(R.color.select_error);
						selectFour.setBackgroundResource(R.color.select_right);
						selectThree.setBackgroundResource(R.color.select_error);
					}
				}
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				break;

			case Net1314080903124_BaseColumns.D:
				if (myDataBack.daan_four.equals("D")) {
					imageFour.setImageResource(R.drawable.right);
					relFour.setBackgroundResource(R.color.select_right);
					selectFour.setBackgroundResource(R.color.select_right);
				} else {
					if (!TextUtils.isEmpty(myDataBack.daan_one)) {
						imageOne.setImageResource(R.drawable.right);
						imageFour.setImageResource(R.drawable.wrong);
						relOne.setBackgroundResource(R.color.select_right);
						relFour.setBackgroundResource(R.color.select_error);
						selectOne.setBackgroundResource(R.color.select_right);
						selectFour.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataBack.daan_tow)) {
						imageTwo.setImageResource(R.drawable.right);
						imageFour.setImageResource(R.drawable.wrong);
						relTwo.setBackgroundResource(R.color.select_right);
						relFour.setBackgroundResource(R.color.select_error);
						selectTwo.setBackgroundResource(R.color.select_right);
						selectFour.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataBack.daan_three)) {
						imageThree.setImageResource(R.drawable.right);
						imageFour.setImageResource(R.drawable.wrong);
						relThree.setBackgroundResource(R.color.select_right);
						relFour.setBackgroundResource(R.color.select_error);
						selectThree.setBackgroundResource(R.color.select_right);
						selectFour.setBackgroundResource(R.color.select_error);
					}
				}
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				break;

			case Net1314080903124_BaseColumns.AB:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				rightAnswer
						.setText("正确答案："
								+ rightStr(getRightIntValue(myDataBack.daan_one, myDataBack.daan_tow, myDataBack.daan_three,
										myDataBack.daan_four)));
				break;

			case Net1314080903124_BaseColumns.AC:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				rightAnswer
						.setText("正确答案："
								+ rightStr(getRightIntValue(myDataBack.daan_one, myDataBack.daan_tow, myDataBack.daan_three,
										myDataBack.daan_four)));
				break;

			case Net1314080903124_BaseColumns.AD:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer
						.setText("正确答案："
								+ rightStr(getRightIntValue(myDataBack.daan_one, myDataBack.daan_tow, myDataBack.daan_three,
										myDataBack.daan_four)));
				break;

			case Net1314080903124_BaseColumns.BC:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				rightAnswer
						.setText("正确答案："
								+ rightStr(getRightIntValue(myDataBack.daan_one, myDataBack.daan_tow, myDataBack.daan_three,
										myDataBack.daan_four)));
				break;

			case Net1314080903124_BaseColumns.BD:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageTwo.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer
						.setText("正确答案："
								+ rightStr(getRightIntValue(myDataBack.daan_one, myDataBack.daan_tow, myDataBack.daan_three,
										myDataBack.daan_four)));
				break;

			case Net1314080903124_BaseColumns.CD:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer
						.setText("正确答案："
								+ rightStr(getRightIntValue(myDataBack.daan_one, myDataBack.daan_tow, myDataBack.daan_three,
										myDataBack.daan_four)));
				break;

			case Net1314080903124_BaseColumns.ABC:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				rightAnswer
						.setText("正确答案："
								+ rightStr(getRightIntValue(myDataBack.daan_one, myDataBack.daan_tow, myDataBack.daan_three,
										myDataBack.daan_four)));
				break;

			case Net1314080903124_BaseColumns.ABD:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer
						.setText("正确答案："
								+ rightStr(getRightIntValue(myDataBack.daan_one, myDataBack.daan_tow, myDataBack.daan_three,
										myDataBack.daan_four)));
				break;

			case Net1314080903124_BaseColumns.ACD:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer
						.setText("正确答案："
								+ rightStr(getRightIntValue(myDataBack.daan_one, myDataBack.daan_tow, myDataBack.daan_three,
										myDataBack.daan_four)));
				break;

			case Net1314080903124_BaseColumns.BCD:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer
						.setText("正确答案："
								+ rightStr(getRightIntValue(myDataBack.daan_one, myDataBack.daan_tow, myDataBack.daan_three,
										myDataBack.daan_four)));
				break;

			case Net1314080903124_BaseColumns.ABCD:
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText("A." + myDataBack.timu_one);
				selectTwo.setText("B." + myDataBack.timu_tow);
				selectThree.setText("C." + myDataBack.timu_three);
				selectFour.setText("D." + myDataBack.timu_four);
				selectColor(myDataBack.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer
						.setText("正确答案："
								+ rightStr(getRightIntValue(myDataBack.daan_one, myDataBack.daan_tow, myDataBack.daan_three,
										myDataBack.daan_four)));
				break;

			case Net1314080903124_BaseColumns.TRUE:
				if (myDataBack.daan_one.equals("A")) {
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
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText(myDataBack.timu_one);
				selectTwo.setText(myDataBack.timu_tow);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				break;

			case Net1314080903124_BaseColumns.FALSE:
				if (myDataBack.daan_tow.equals("B")) {
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
				type.setText("题型：" + myDataBack.types);
				title.setText((i + 1) + "." + myDataBack.timu_title);
				selectOne.setText(myDataBack.timu_one);
				selectTwo.setText(myDataBack.timu_tow);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				break;

			default:
				break;
			}
			break;

		case R.id.subject:
			listHasId = Net1314080903124_DBManager.getInstance(this).queryHasId(Net1314080903124_ErrorColumns.TABLE_NAME);
			for (int i = 0; i < list.size(); i++) {
				Net1314080903124_Digital net1314080903124_Digital = new Net1314080903124_Digital(i + 1);
				listNumb.add(net1314080903124_Digital);
			}
			View layout = getLayoutInflater().inflate(R.layout.select_subject, null);
			dialog = new AlertDialog.Builder(this).create();
			dialog.show();
			dialog.getWindow().setContentView(layout);
			GridView gridView1 = (GridView) layout.findViewById(R.id.gridView1);
			myAdapter = new MyAdapter();
			gridView1.setAdapter(myAdapter);
			gridView1.setOnItemClickListener(this);
			break;

		case R.id.remove:
			help.setText("");
			answer.setText("");
			rightAnswer.setText("");
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
				i = Net1314080903124_ConfigPreferences.getInstance(this).isLastSelectError() - 1;
				isClickSelectSubject = false;
			}
			Net1314080903124_ConfigPreferences.getInstance(this).setLastSelectError(i + 1);
			list.clear();
			Net1314080903124_DBManager.getInstance(this).remove(Net1314080903124_ErrorColumns.TABLE_NAME, myData);
			list = Net1314080903124_DBManager.getInstance(this).query(Net1314080903124_ErrorColumns.TABLE_NAME);
			if ((i + 1) > list.size()) {
				i -= 1;
			}
			if (list.size() == 0) {
				subjectTop.setText("0/0");
				type.setText("");
				title.setText("");
				answer.setText("");
				rightAnswer.setText("");
				selectOne.setText("");
				selectTwo.setText("");
				selectThree.setText("");
				selectFour.setText("");
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
				return;
			}
			Net1314080903124_CauseInfo myDataRemove = list.get(i);
			subjectTop.setText((i + 1) + "/" + list.size());
			if ("单选".equals(myDataRemove.types)) {
				submit.setVisibility(View.GONE);
				relThree.setVisibility(View.VISIBLE);
				relFour.setVisibility(View.VISIBLE);
			} else if ("多选".equals(myDataRemove.types)) {
				submit.setVisibility(View.VISIBLE);
				relThree.setVisibility(View.VISIBLE);
				relFour.setVisibility(View.VISIBLE);
			} else if ("判断".equals(myDataRemove.types)) {
				submit.setVisibility(View.GONE);
				relThree.setVisibility(View.GONE);
				relFour.setVisibility(View.GONE);
			}
			int replyRemove = myDataRemove.getReply();
			switch (replyRemove) {
			case Net1314080903124_BaseColumns.NULL:
				type.setText("题型：" + myDataRemove.types);
				title.setText((i + 1) + "." + myDataRemove.timu_title);
				if ("单选".equals(myDataRemove.types)) {
					selectOne.setText("A." + myDataRemove.timu_one);
					selectTwo.setText("B." + myDataRemove.timu_tow);
					selectThree.setText("C." + myDataRemove.timu_three);
					selectFour.setText("D." + myDataRemove.timu_four);
				} else if ("多选".equals(myDataRemove.types)) {
					selectOne.setText("A." + myDataRemove.timu_one);
					selectTwo.setText("B." + myDataRemove.timu_tow);
					selectThree.setText("C." + myDataRemove.timu_three);
					selectFour.setText("D." + myDataRemove.timu_four);
				} else if ("判断".equals(myDataRemove.types)) {
					selectOne.setText(myDataRemove.timu_one);
					selectTwo.setText(myDataRemove.timu_tow);
					selectThree.setText(myDataRemove.timu_three);
					selectFour.setText(myDataRemove.timu_four);
				}
				selectOne.setClickable(true);
				selectTwo.setClickable(true);
				selectThree.setClickable(true);
				selectFour.setClickable(true);
				if ("多选".equals(myDataRemove.types)) {
					submit.setVisibility(View.VISIBLE);
				}
				break;

			case Net1314080903124_BaseColumns.A:
				if (myDataRemove.daan_one.equals("A")) {
					imageOne.setImageResource(R.drawable.right);
					relOne.setBackgroundResource(R.color.select_right);
					selectOne.setBackgroundResource(R.color.select_right);
				} else {
					if (!TextUtils.isEmpty(myDataRemove.daan_tow)) {
						imageTwo.setImageResource(R.drawable.right);
						imageOne.setImageResource(R.drawable.wrong);
						relTwo.setBackgroundResource(R.color.select_right);
						relOne.setBackgroundResource(R.color.select_error);
						selectTwo.setBackgroundResource(R.color.select_right);
						selectOne.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataRemove.daan_three)) {
						imageThree.setImageResource(R.drawable.right);
						imageOne.setImageResource(R.drawable.wrong);
						relThree.setBackgroundResource(R.color.select_right);
						relOne.setBackgroundResource(R.color.select_error);
						selectThree.setBackgroundResource(R.color.select_right);
						selectOne.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataRemove.daan_four)) {
						imageFour.setImageResource(R.drawable.right);
						imageOne.setImageResource(R.drawable.wrong);
						relFour.setBackgroundResource(R.color.select_right);
						relOne.setBackgroundResource(R.color.select_error);
						selectFour.setBackgroundResource(R.color.select_right);
						selectOne.setBackgroundResource(R.color.select_error);
					}
				}
				type.setText("题型：" + myDataRemove.types);
				title.setText((i + 1) + "." + myDataRemove.timu_title);
				selectOne.setText("A." + myDataRemove.timu_one);
				selectTwo.setText("B." + myDataRemove.timu_tow);
				selectThree.setText("C." + myDataRemove.timu_three);
				selectFour.setText("D." + myDataRemove.timu_four);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				break;

			case Net1314080903124_BaseColumns.B:
				if (myDataRemove.daan_tow.equals("B")) {
					imageTwo.setImageResource(R.drawable.right);
					relTwo.setBackgroundResource(R.color.select_right);
					selectTwo.setBackgroundResource(R.color.select_right);
				} else {
					if (!TextUtils.isEmpty(myDataRemove.daan_one)) {
						imageOne.setImageResource(R.drawable.right);
						imageTwo.setImageResource(R.drawable.wrong);
						relOne.setBackgroundResource(R.color.select_right);
						relTwo.setBackgroundResource(R.color.select_error);
						selectOne.setBackgroundResource(R.color.select_right);
						selectTwo.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataRemove.daan_three)) {
						imageThree.setImageResource(R.drawable.right);
						imageTwo.setImageResource(R.drawable.wrong);
						relThree.setBackgroundResource(R.color.select_right);
						relTwo.setBackgroundResource(R.color.select_error);
						selectThree.setBackgroundResource(R.color.select_right);
						selectTwo.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataRemove.daan_four)) {
						imageFour.setImageResource(R.drawable.right);
						imageTwo.setImageResource(R.drawable.wrong);
						relFour.setBackgroundResource(R.color.select_right);
						relTwo.setBackgroundResource(R.color.select_error);
						selectFour.setBackgroundResource(R.color.select_right);
						selectTwo.setBackgroundResource(R.color.select_error);
					}
				}
				type.setText("题型：" + myDataRemove.types);
				title.setText((i + 1) + "." + myDataRemove.timu_title);
				selectOne.setText("A." + myDataRemove.timu_one);
				selectTwo.setText("B." + myDataRemove.timu_tow);
				selectThree.setText("C." + myDataRemove.timu_three);
				selectFour.setText("D." + myDataRemove.timu_four);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				break;

			case Net1314080903124_BaseColumns.C:
				if (myDataRemove.daan_three.equals("C")) {
					imageThree.setImageResource(R.drawable.right);
					relThree.setBackgroundResource(R.color.select_right);
					selectThree.setBackgroundResource(R.color.select_right);
				} else {
					if (!TextUtils.isEmpty(myDataRemove.daan_one)) {
						imageOne.setImageResource(R.drawable.right);
						imageThree.setImageResource(R.drawable.wrong);
						relOne.setBackgroundResource(R.color.select_right);
						relThree.setBackgroundResource(R.color.select_error);
						selectOne.setBackgroundResource(R.color.select_right);
						selectThree.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataRemove.daan_tow)) {
						imageTwo.setImageResource(R.drawable.right);
						imageThree.setImageResource(R.drawable.wrong);
						relTwo.setBackgroundResource(R.color.select_right);
						relThree.setBackgroundResource(R.color.select_error);
						selectTwo.setBackgroundResource(R.color.select_right);
						selectThree.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataRemove.daan_four)) {
						imageFour.setImageResource(R.drawable.right);
						imageThree.setImageResource(R.drawable.wrong);
						relFour.setBackgroundResource(R.color.select_right);
						relThree.setBackgroundResource(R.color.select_error);
						selectFour.setBackgroundResource(R.color.select_right);
						selectThree.setBackgroundResource(R.color.select_error);
					}
				}
				type.setText("题型：" + myDataRemove.types);
				title.setText((i + 1) + "." + myDataRemove.timu_title);
				selectOne.setText("A." + myDataRemove.timu_one);
				selectTwo.setText("B." + myDataRemove.timu_tow);
				selectThree.setText("C." + myDataRemove.timu_three);
				selectFour.setText("D." + myDataRemove.timu_four);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				break;

			case Net1314080903124_BaseColumns.D:
				if (myDataRemove.daan_four.equals("D")) {
					imageFour.setImageResource(R.drawable.right);
					relFour.setBackgroundResource(R.color.select_right);
					selectFour.setBackgroundResource(R.color.select_right);
				} else {
					if (!TextUtils.isEmpty(myDataRemove.daan_one)) {
						imageOne.setImageResource(R.drawable.right);
						imageFour.setImageResource(R.drawable.wrong);
						relOne.setBackgroundResource(R.color.select_right);
						relFour.setBackgroundResource(R.color.select_error);
						selectOne.setBackgroundResource(R.color.select_right);
						selectFour.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataRemove.daan_tow)) {
						imageTwo.setImageResource(R.drawable.right);
						imageFour.setImageResource(R.drawable.wrong);
						relTwo.setBackgroundResource(R.color.select_right);
						relFour.setBackgroundResource(R.color.select_error);
						selectTwo.setBackgroundResource(R.color.select_right);
						selectFour.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataRemove.daan_three)) {
						imageThree.setImageResource(R.drawable.right);
						imageFour.setImageResource(R.drawable.wrong);
						relThree.setBackgroundResource(R.color.select_right);
						relFour.setBackgroundResource(R.color.select_error);
						selectThree.setBackgroundResource(R.color.select_right);
						selectFour.setBackgroundResource(R.color.select_error);
					}
				}
				type.setText("题型：" + myDataRemove.types);
				title.setText((i + 1) + "." + myDataRemove.timu_title);
				selectOne.setText("A." + myDataRemove.timu_one);
				selectTwo.setText("B." + myDataRemove.timu_tow);
				selectThree.setText("C." + myDataRemove.timu_three);
				selectFour.setText("D." + myDataRemove.timu_four);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				break;

			case Net1314080903124_BaseColumns.AB:
				type.setText("题型：" + myDataRemove.types);
				title.setText((i + 1) + "." + myDataRemove.timu_title);
				selectOne.setText("A." + myDataRemove.timu_one);
				selectTwo.setText("B." + myDataRemove.timu_tow);
				selectThree.setText("C." + myDataRemove.timu_three);
				selectFour.setText("D." + myDataRemove.timu_four);
				selectColor(myDataRemove.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataRemove.daan_one, myDataRemove.daan_tow, myDataRemove.daan_three,
								myDataRemove.daan_four)));
				break;

			case Net1314080903124_BaseColumns.AC:
				type.setText("题型：" + myDataRemove.types);
				title.setText((i + 1) + "." + myDataRemove.timu_title);
				selectOne.setText("A." + myDataRemove.timu_one);
				selectTwo.setText("B." + myDataRemove.timu_tow);
				selectThree.setText("C." + myDataRemove.timu_three);
				selectFour.setText("D." + myDataRemove.timu_four);
				selectColor(myDataRemove.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataRemove.daan_one, myDataRemove.daan_tow, myDataRemove.daan_three,
								myDataRemove.daan_four)));
				break;

			case Net1314080903124_BaseColumns.AD:
				type.setText("题型：" + myDataRemove.types);
				title.setText((i + 1) + "." + myDataRemove.timu_title);
				selectOne.setText("A." + myDataRemove.timu_one);
				selectTwo.setText("B." + myDataRemove.timu_tow);
				selectThree.setText("C." + myDataRemove.timu_three);
				selectFour.setText("D." + myDataRemove.timu_four);
				selectColor(myDataRemove.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataRemove.daan_one, myDataRemove.daan_tow, myDataRemove.daan_three,
								myDataRemove.daan_four)));
				break;

			case Net1314080903124_BaseColumns.BC:
				type.setText("题型：" + myDataRemove.types);
				title.setText((i + 1) + "." + myDataRemove.timu_title);
				selectOne.setText("A." + myDataRemove.timu_one);
				selectTwo.setText("B." + myDataRemove.timu_tow);
				selectThree.setText("C." + myDataRemove.timu_three);
				selectFour.setText("D." + myDataRemove.timu_four);
				selectColor(myDataRemove.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataRemove.daan_one, myDataRemove.daan_tow, myDataRemove.daan_three,
								myDataRemove.daan_four)));
				break;

			case Net1314080903124_BaseColumns.BD:
				type.setText("题型：" + myDataRemove.types);
				title.setText((i + 1) + "." + myDataRemove.timu_title);
				selectOne.setText("A." + myDataRemove.timu_one);
				selectTwo.setText("B." + myDataRemove.timu_tow);
				selectThree.setText("C." + myDataRemove.timu_three);
				selectFour.setText("D." + myDataRemove.timu_four);
				selectColor(myDataRemove.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageTwo.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataRemove.daan_one, myDataRemove.daan_tow, myDataRemove.daan_three,
								myDataRemove.daan_four)));
				break;

			case Net1314080903124_BaseColumns.CD:
				type.setText("题型：" + myDataRemove.types);
				title.setText((i + 1) + "." + myDataRemove.timu_title);
				selectOne.setText("A." + myDataRemove.timu_one);
				selectTwo.setText("B." + myDataRemove.timu_tow);
				selectThree.setText("C." + myDataRemove.timu_three);
				selectFour.setText("D." + myDataRemove.timu_four);
				selectColor(myDataRemove.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataRemove.daan_one, myDataRemove.daan_tow, myDataRemove.daan_three,
								myDataRemove.daan_four)));
				break;

			case Net1314080903124_BaseColumns.ABC:
				type.setText("题型：" + myDataRemove.types);
				title.setText((i + 1) + "." + myDataRemove.timu_title);
				selectOne.setText("A." + myDataRemove.timu_one);
				selectTwo.setText("B." + myDataRemove.timu_tow);
				selectThree.setText("C." + myDataRemove.timu_three);
				selectFour.setText("D." + myDataRemove.timu_four);
				selectColor(myDataRemove.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataRemove.daan_one, myDataRemove.daan_tow, myDataRemove.daan_three,
								myDataRemove.daan_four)));
				break;

			case Net1314080903124_BaseColumns.ABD:
				type.setText("题型：" + myDataRemove.types);
				title.setText((i + 1) + "." + myDataRemove.timu_title);
				selectOne.setText("A." + myDataRemove.timu_one);
				selectTwo.setText("B." + myDataRemove.timu_tow);
				selectThree.setText("C." + myDataRemove.timu_three);
				selectFour.setText("D." + myDataRemove.timu_four);
				selectColor(myDataRemove.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataRemove.daan_one, myDataRemove.daan_tow, myDataRemove.daan_three,
								myDataRemove.daan_four)));
				break;

			case Net1314080903124_BaseColumns.ACD:
				type.setText("题型：" + myDataRemove.types);
				title.setText((i + 1) + "." + myDataRemove.timu_title);
				selectOne.setText("A." + myDataRemove.timu_one);
				selectTwo.setText("B." + myDataRemove.timu_tow);
				selectThree.setText("C." + myDataRemove.timu_three);
				selectFour.setText("D." + myDataRemove.timu_four);
				selectColor(myDataRemove.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataRemove.daan_one, myDataRemove.daan_tow, myDataRemove.daan_three,
								myDataRemove.daan_four)));
				break;

			case Net1314080903124_BaseColumns.BCD:
				type.setText("题型：" + myDataRemove.types);
				title.setText((i + 1) + "." + myDataRemove.timu_title);
				selectOne.setText("A." + myDataRemove.timu_one);
				selectTwo.setText("B." + myDataRemove.timu_tow);
				selectThree.setText("C." + myDataRemove.timu_three);
				selectFour.setText("D." + myDataRemove.timu_four);
				selectColor(myDataRemove.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataRemove.daan_one, myDataRemove.daan_tow, myDataRemove.daan_three,
								myDataRemove.daan_four)));
				break;

			case Net1314080903124_BaseColumns.ABCD:
				type.setText("题型：" + myDataRemove.types);
				title.setText((i + 1) + "." + myDataRemove.timu_title);
				selectOne.setText("A." + myDataRemove.timu_one);
				selectTwo.setText("B." + myDataRemove.timu_tow);
				selectThree.setText("C." + myDataRemove.timu_three);
				selectFour.setText("D." + myDataRemove.timu_four);
				selectColor(myDataRemove.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataRemove.daan_one, myDataRemove.daan_tow, myDataRemove.daan_three,
								myDataRemove.daan_four)));
				break;

			case Net1314080903124_BaseColumns.TRUE:
				if (myDataRemove.daan_one.equals("A")) {
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
				type.setText("题型：" + myDataRemove.types);
				title.setText((i + 1) + "." + myDataRemove.timu_title);
				selectOne.setText(myDataRemove.timu_one);
				selectTwo.setText(myDataRemove.timu_tow);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				break;

			case Net1314080903124_BaseColumns.FALSE:
				if (myDataRemove.daan_tow.equals("B")) {
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
				type.setText("题型：" + myDataRemove.types);
				title.setText((i + 1) + "." + myDataRemove.timu_title);
				selectOne.setText(myDataRemove.timu_one);
				selectTwo.setText(myDataRemove.timu_tow);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				break;

			default:
				break;
			}
			break;

		case R.id.forward:
			if (i >= list.size() - 1) {
				return;
			}
			help.setText("");
			answer.setText("");
			rightAnswer.setText("");
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
				i = Net1314080903124_ConfigPreferences.getInstance(this).isLastSelectError() - 1;
				i += 1;
				isClickSelectSubject = false;
			} else {
				i += 1;
			}
			Net1314080903124_ConfigPreferences.getInstance(this).setLastSelectError(i + 1);
			list.clear();
			list = Net1314080903124_DBManager.getInstance(this).query(Net1314080903124_ErrorColumns.TABLE_NAME);
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
					imageOne.setImageResource(R.drawable.right);
					relOne.setBackgroundResource(R.color.select_right);
					selectOne.setBackgroundResource(R.color.select_right);
				} else {
					if (!TextUtils.isEmpty(myDataForward.daan_tow)) {
						imageTwo.setImageResource(R.drawable.right);
						imageOne.setImageResource(R.drawable.wrong);
						relTwo.setBackgroundResource(R.color.select_right);
						relOne.setBackgroundResource(R.color.select_error);
						selectTwo.setBackgroundResource(R.color.select_right);
						selectOne.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataForward.daan_three)) {
						imageThree.setImageResource(R.drawable.right);
						imageOne.setImageResource(R.drawable.wrong);
						relThree.setBackgroundResource(R.color.select_right);
						relOne.setBackgroundResource(R.color.select_error);
						selectThree.setBackgroundResource(R.color.select_right);
						selectOne.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataForward.daan_four)) {
						imageFour.setImageResource(R.drawable.right);
						imageOne.setImageResource(R.drawable.wrong);
						relFour.setBackgroundResource(R.color.select_right);
						relOne.setBackgroundResource(R.color.select_error);
						selectFour.setBackgroundResource(R.color.select_right);
						selectOne.setBackgroundResource(R.color.select_error);
					}
				}
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				break;

			case Net1314080903124_BaseColumns.B:
				if (myDataForward.daan_tow.equals("B")) {
					imageTwo.setImageResource(R.drawable.right);
					relTwo.setBackgroundResource(R.color.select_right);
					selectTwo.setBackgroundResource(R.color.select_right);
				} else {
					if (!TextUtils.isEmpty(myDataForward.daan_one)) {
						imageOne.setImageResource(R.drawable.right);
						imageTwo.setImageResource(R.drawable.wrong);
						relOne.setBackgroundResource(R.color.select_right);
						relTwo.setBackgroundResource(R.color.select_error);
						selectOne.setBackgroundResource(R.color.select_right);
						selectTwo.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataForward.daan_three)) {
						imageThree.setImageResource(R.drawable.right);
						imageTwo.setImageResource(R.drawable.wrong);
						relThree.setBackgroundResource(R.color.select_right);
						relTwo.setBackgroundResource(R.color.select_error);
						selectThree.setBackgroundResource(R.color.select_right);
						selectTwo.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataForward.daan_four)) {
						imageFour.setImageResource(R.drawable.right);
						imageTwo.setImageResource(R.drawable.wrong);
						relFour.setBackgroundResource(R.color.select_right);
						relTwo.setBackgroundResource(R.color.select_error);
						selectFour.setBackgroundResource(R.color.select_right);
						selectTwo.setBackgroundResource(R.color.select_error);
					}
				}
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				break;

			case Net1314080903124_BaseColumns.C:
				if (myDataForward.daan_three.equals("C")) {
					imageThree.setImageResource(R.drawable.right);
					relThree.setBackgroundResource(R.color.select_right);
					selectThree.setBackgroundResource(R.color.select_right);
				} else {
					if (!TextUtils.isEmpty(myDataForward.daan_one)) {
						imageOne.setImageResource(R.drawable.right);
						imageThree.setImageResource(R.drawable.wrong);
						relOne.setBackgroundResource(R.color.select_right);
						relThree.setBackgroundResource(R.color.select_error);
						selectOne.setBackgroundResource(R.color.select_right);
						selectThree.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataForward.daan_tow)) {
						imageTwo.setImageResource(R.drawable.right);
						imageThree.setImageResource(R.drawable.wrong);
						relTwo.setBackgroundResource(R.color.select_right);
						relThree.setBackgroundResource(R.color.select_error);
						selectTwo.setBackgroundResource(R.color.select_right);
						selectThree.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataForward.daan_four)) {
						imageFour.setImageResource(R.drawable.right);
						imageThree.setImageResource(R.drawable.wrong);
						relFour.setBackgroundResource(R.color.select_right);
						relThree.setBackgroundResource(R.color.select_error);
						selectFour.setBackgroundResource(R.color.select_right);
						selectThree.setBackgroundResource(R.color.select_error);
					}
				}
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				break;

			case Net1314080903124_BaseColumns.D:
				if (myDataForward.daan_four.equals("D")) {
					imageFour.setImageResource(R.drawable.right);
					relFour.setBackgroundResource(R.color.select_right);
					selectFour.setBackgroundResource(R.color.select_right);
				} else {
					if (!TextUtils.isEmpty(myDataForward.daan_one)) {
						imageOne.setImageResource(R.drawable.right);
						imageFour.setImageResource(R.drawable.wrong);
						relOne.setBackgroundResource(R.color.select_right);
						relFour.setBackgroundResource(R.color.select_error);
						selectOne.setBackgroundResource(R.color.select_right);
						selectFour.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataForward.daan_tow)) {
						imageTwo.setImageResource(R.drawable.right);
						imageFour.setImageResource(R.drawable.wrong);
						relTwo.setBackgroundResource(R.color.select_right);
						relFour.setBackgroundResource(R.color.select_error);
						selectTwo.setBackgroundResource(R.color.select_right);
						selectFour.setBackgroundResource(R.color.select_error);
					} else if (!TextUtils.isEmpty(myDataForward.daan_three)) {
						imageThree.setImageResource(R.drawable.right);
						imageFour.setImageResource(R.drawable.wrong);
						relThree.setBackgroundResource(R.color.select_right);
						relFour.setBackgroundResource(R.color.select_error);
						selectThree.setBackgroundResource(R.color.select_right);
						selectFour.setBackgroundResource(R.color.select_error);
					}
				}
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				break;

			case Net1314080903124_BaseColumns.AB:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataForward.daan_one, myDataForward.daan_tow, myDataForward.daan_three,
								myDataForward.daan_four)));
				break;

			case Net1314080903124_BaseColumns.AC:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataForward.daan_one, myDataForward.daan_tow, myDataForward.daan_three,
								myDataForward.daan_four)));
				break;

			case Net1314080903124_BaseColumns.AD:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataForward.daan_one, myDataForward.daan_tow, myDataForward.daan_three,
								myDataForward.daan_four)));
				break;

			case Net1314080903124_BaseColumns.BC:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataForward.daan_one, myDataForward.daan_tow, myDataForward.daan_three,
								myDataForward.daan_four)));
				break;

			case Net1314080903124_BaseColumns.BD:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageTwo.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataForward.daan_one, myDataForward.daan_tow, myDataForward.daan_three,
								myDataForward.daan_four)));
				break;

			case Net1314080903124_BaseColumns.CD:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataForward.daan_one, myDataForward.daan_tow, myDataForward.daan_three,
								myDataForward.daan_four)));
				break;

			case Net1314080903124_BaseColumns.ABC:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataForward.daan_one, myDataForward.daan_tow, myDataForward.daan_three,
								myDataForward.daan_four)));
				break;

			case Net1314080903124_BaseColumns.ABD:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataForward.daan_one, myDataForward.daan_tow, myDataForward.daan_three,
								myDataForward.daan_four)));
				break;

			case Net1314080903124_BaseColumns.ACD:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataForward.daan_one, myDataForward.daan_tow, myDataForward.daan_three,
								myDataForward.daan_four)));
				break;

			case Net1314080903124_BaseColumns.BCD:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataForward.daan_one, myDataForward.daan_tow, myDataForward.daan_three,
								myDataForward.daan_four)));
				break;

			case Net1314080903124_BaseColumns.ABCD:
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText("A." + myDataForward.timu_one);
				selectTwo.setText("B." + myDataForward.timu_tow);
				selectThree.setText("C." + myDataForward.timu_three);
				selectFour.setText("D." + myDataForward.timu_four);
				selectColor(myDataForward.reply);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				submit.setVisibility(View.GONE);
				imageOne.setImageResource(R.drawable.more_select);
				imageTwo.setImageResource(R.drawable.more_select);
				imageThree.setImageResource(R.drawable.more_select);
				imageFour.setImageResource(R.drawable.more_select);
				rightAnswer.setText("正确答案："
						+ rightStr(getRightIntValue(myDataForward.daan_one, myDataForward.daan_tow, myDataForward.daan_three,
								myDataForward.daan_four)));
				break;

			case Net1314080903124_BaseColumns.TRUE:
				if (myDataForward.daan_one.equals("A")) {
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
				type.setText("题型：" + myDataForward.types);
				title.setText((i + 1) + "." + myDataForward.timu_title);
				selectOne.setText(myDataForward.timu_one);
				selectTwo.setText(myDataForward.timu_tow);
				selectOne.setClickable(false);
				selectTwo.setClickable(false);
				selectThree.setClickable(false);
				selectFour.setClickable(false);
				break;

			case Net1314080903124_BaseColumns.FALSE:
				if (myDataForward.daan_tow.equals("B")) {
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
				if (myData.daan_one.equals("A")) {
					tv.setBackgroundResource(R.color.select_right);
				} else {
					tv.setBackgroundResource(R.color.select_error);
				}
				break;

			case Net1314080903124_BaseColumns.B:
				if (myData.daan_tow.equals("B")) {
					tv.setBackgroundResource(R.color.select_right);
				} else {
					tv.setBackgroundResource(R.color.select_error);
				}
				break;

			case Net1314080903124_BaseColumns.C:
				if (myData.daan_three.equals("C")) {
					tv.setBackgroundResource(R.color.select_right);
				} else {
					tv.setBackgroundResource(R.color.select_error);
				}
				break;

			case Net1314080903124_BaseColumns.D:
				if (myData.daan_four.equals("D")) {
					tv.setBackgroundResource(R.color.select_right);
				} else {
					tv.setBackgroundResource(R.color.select_error);
				}
				break;

			case Net1314080903124_BaseColumns.AB:
				if (myData.reply == getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)) {
					tv.setBackgroundResource(R.color.select_right);
				} else {
					tv.setBackgroundResource(R.color.select_error);
				}
				break;

			case Net1314080903124_BaseColumns.AC:
				if (myData.reply == getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)) {
					tv.setBackgroundResource(R.color.select_right);
				} else {
					tv.setBackgroundResource(R.color.select_error);
				}
				break;

			case Net1314080903124_BaseColumns.AD:
				if (myData.reply == getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)) {
					tv.setBackgroundResource(R.color.select_right);
				} else {
					tv.setBackgroundResource(R.color.select_error);
				}
				break;

			case Net1314080903124_BaseColumns.BC:
				if (myData.reply == getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)) {
					tv.setBackgroundResource(R.color.select_right);
				} else {
					tv.setBackgroundResource(R.color.select_error);
				}
				break;

			case Net1314080903124_BaseColumns.BD:
				if (myData.reply == getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)) {
					tv.setBackgroundResource(R.color.select_right);
				} else {
					tv.setBackgroundResource(R.color.select_error);
				}
				break;

			case Net1314080903124_BaseColumns.CD:
				if (myData.reply == getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)) {
					tv.setBackgroundResource(R.color.select_right);
				} else {
					tv.setBackgroundResource(R.color.select_error);
				}
				break;

			case Net1314080903124_BaseColumns.ABC:
				if (myData.reply == getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)) {
					tv.setBackgroundResource(R.color.select_right);
				} else {
					tv.setBackgroundResource(R.color.select_error);
				}
				break;

			case Net1314080903124_BaseColumns.ABD:
				if (myData.reply == getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)) {
					tv.setBackgroundResource(R.color.select_right);
				} else {
					tv.setBackgroundResource(R.color.select_error);
				}
				break;

			case Net1314080903124_BaseColumns.ACD:
				if (myData.reply == getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)) {
					tv.setBackgroundResource(R.color.select_right);
				} else {
					tv.setBackgroundResource(R.color.select_error);
				}
				break;

			case Net1314080903124_BaseColumns.BCD:
				if (myData.reply == getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)) {
					tv.setBackgroundResource(R.color.select_right);
				} else {
					tv.setBackgroundResource(R.color.select_error);
				}
				break;

			case Net1314080903124_BaseColumns.ABCD:
				if (myData.reply == getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)) {
					tv.setBackgroundResource(R.color.select_right);
				} else {
					tv.setBackgroundResource(R.color.select_error);
				}
				break;

			case Net1314080903124_BaseColumns.TRUE:
				if (myData.daan_one.equals("A")) {
					tv.setBackgroundResource(R.color.select_right);
				} else {
					tv.setBackgroundResource(R.color.select_error);
				}
				break;

			case Net1314080903124_BaseColumns.FALSE:
				if (myData.daan_tow.equals("B")) {
					tv.setBackgroundResource(R.color.select_right);
				} else {
					tv.setBackgroundResource(R.color.select_error);
				}
				break;

			default:
				break;
			}
			Net1314080903124_Digital net1314080903124_Digital = listNumb.get(position);
			tv.setText("" + net1314080903124_Digital.id);
			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		help.setText("");
		answer.setText("");
		rightAnswer.setText("");
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
		list.clear();
		i = position;
		Net1314080903124_ConfigPreferences.getInstance(this).setLastSelectError(position + 1);
		isClickSelectSubject = true;
		list = Net1314080903124_DBManager.getInstance(this).query(Net1314080903124_ErrorColumns.TABLE_NAME);
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
			rightAnswer
					.setText("正确答案：" + rightStr(getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)));
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
			rightAnswer
					.setText("正确答案：" + rightStr(getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)));
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
			rightAnswer
					.setText("正确答案：" + rightStr(getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)));
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
			rightAnswer
					.setText("正确答案：" + rightStr(getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)));
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
			rightAnswer
					.setText("正确答案：" + rightStr(getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)));
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
			rightAnswer
					.setText("正确答案：" + rightStr(getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)));
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
			rightAnswer
					.setText("正确答案：" + rightStr(getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)));
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
			rightAnswer
					.setText("正确答案：" + rightStr(getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)));
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
			rightAnswer
					.setText("正确答案：" + rightStr(getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)));
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
			rightAnswer
					.setText("正确答案：" + rightStr(getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)));
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
			rightAnswer
					.setText("正确答案：" + rightStr(getRightIntValue(myData.daan_one, myData.daan_tow, myData.daan_three, myData.daan_four)));
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
		dialog.dismiss();
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Net1314080903124_DBManager.getInstance(this).updateWhenDestroy(Net1314080903124_ErrorColumns.TABLE_NAME);
	}
	
}
