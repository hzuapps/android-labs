

package edu.hzuapps.androidworks.homeworks.net1314080903127;

import com.donglihan.CollegeLifeManager.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Net1314080903127_DiaryEditActivity extends Activity {

	private EditText mTitleText;
	private EditText mBodyText;
	private Long mRowId;
	private Net1314080903127_DbAdapter mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new Net1314080903127_DbAdapter(this);
		setContentView(R.layout.net1314080903127_diary_edit);

		mTitleText = (EditText) findViewById(R.id.title);
		mBodyText = (EditText) findViewById(R.id.body);

		Button confirmButton = (Button) findViewById(R.id.confirm);

		mRowId = null;
		Bundle extras = getIntent().getExtras();
		//ÅÐ¶ÏÊÇ·ñÎª±à¼­×´Ì¬
		if (extras != null) {
			String title = extras.getString(Net1314080903127_DbAdapter.KEY_TITLE);
			String body = extras.getString(Net1314080903127_DbAdapter.KEY_BODY);
			mRowId = extras.getLong(Net1314080903127_DbAdapter.KEY_ROWID);

			if (title != null) {
				mTitleText.setText(title);
			}
			if (body != null) {
				mBodyText.setText(body);
			}
		}

		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				mDbHelper.open();
				String title = mTitleText.getText().toString();
				String body = mBodyText.getText().toString();
				if (mRowId != null) {
					mDbHelper.updateDiary(mRowId, title, body);
				} else
					mDbHelper.createDiary(title, body);
				Intent mIntent = new Intent();
				setResult(RESULT_OK, mIntent);
				mDbHelper.closeclose();
				finish();
			}

		});
	}
}
