package com.example.account;

//import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

public class Shezhi_Net1314080903221 extends Activity{
	ImageView iv1;
	ImageView iv2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.querybytype__net1314080903221);
    	iv1=(ImageView)findViewById(R.id.beifengshezi);
    	iv1.setOnTouchListener(new OnTouchListener(){



			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				SaveDB_Net1314080903221 db = new SaveDB_Net1314080903221();
				Toast.makeText(getApplicationContext(), db.Save(),Toast.LENGTH_SHORT).show();
				//Toast.makeText(getApplicationContext(), "Touch",Toast.LENGTH_SHORT).show();

				return false;
			}
    		
    		
    	});
    	iv2=(ImageView)findViewById(R.id.beifengshezi);
    	iv2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
    		
    		
    	});
    }
	
	
}
