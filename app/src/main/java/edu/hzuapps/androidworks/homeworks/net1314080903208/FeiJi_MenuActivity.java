package skyward.com.myapplication;

import com.baidu.mobstat.StatService;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FeiJi_MenuActivity extends FeiJi_BaseActivity {

	private Button _FeiJi_Button_New, _FeiJi_Button_Score,
			_FeiJi_Button_Exit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feiji_menu);
		Init();
	}

	private void Init() {
		// TODO Auto-generated method stub
		_FeiJi_Button_New = (Button) findViewById(R.id.feiji_bu_new);
		_FeiJi_Button_Score = (Button) findViewById(R.id.feiji_bu_score);
		_FeiJi_Button_Exit = (Button) findViewById(R.id.feiji_bu_exit);

		_FeiJi_Button_New.setOnClickListener(new OnClick());
		_FeiJi_Button_Score.setOnClickListener(new OnClick());
		_FeiJi_Button_Exit.setOnClickListener(new OnClick());
	}

	private class OnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.feiji_bu_new:
				Intent intent = new Intent(FeiJi_MenuActivity.this, FeiJi_Main.class);
				startActivity(intent);
				finish();
				break;

			case R.id.feiji_bu_score:
				Intent i = new Intent(FeiJi_MenuActivity.this, FeiJi_ScoreActivity.class);
				startActivity(i);
				break;

			case R.id.feiji_bu_exit:
				finish();
				break;
			}
		}

	}
	
	public void onResume() {
		super.onResume();

		/**
		 * ҳ����ʼ��ÿ��Activity�ж���Ҫ��ӣ�����м̳еĸ�Activity���Ѿ�����˸õ��ã���ô��Activity����ز�����ӣ�
		 * ������StatService.onPageStartһ��onPageEnd��������ʹ��
		 */
		StatService.onResume(this);
	}

	public void onPause() {
		super.onPause();

		/**
		 * ҳ�������ÿ��Activity�ж���Ҫ��ӣ�����м̳еĸ�Activity���Ѿ�����˸õ��ã���ô��Activity����ز�����ӣ�
		 * ������StatService.onPageStartһ��onPageEnd��������ʹ��
		 */
		StatService.onPause(this);
	}
}
