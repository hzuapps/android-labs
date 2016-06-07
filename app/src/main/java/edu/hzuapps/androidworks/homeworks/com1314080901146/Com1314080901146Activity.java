package edu.hzuapps.androidworks.homeworks.com1314080901146;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import edu.hzuapps.androidworks.homeworks.com1314080901146.R;

public class Com1314080901146Activity extends Activity {

	private LinearLayout mylayout;
	private Resources myColor;
	private int li;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HideStatusBase();
        setContentView(R.layout.main);
        

        mylayout=(LinearLayout)findViewById(R.id.mylayout);
        
        SetColor(R.color.white);

        li=0;
        SetBright(1.0f);
        
    }
    
   

    @Override
    public boolean onTouchEvent(MotionEvent event){

    	openOptionsMenu();
    	return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	super.onCreateOptionsMenu(menu);
    	getMenuInflater().inflate(R.menu.menu, menu);

    	return true;
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    switch(item.getItemId())
    {

    case R.id.setcolor:

    	selectColor();
    	return true;
    case R.id.setbright:
    	selectBright();

    	return true;
    case R.id.seteffer:

    	finish();
    	return true;
    
    }
    return false;
    }

    public void selectColor()
    {
    	final String[] items = {"White", "Red", "Black","Yellow","Pink"};
    	new AlertDialog.Builder(this) 
    	.setTitle("Select Color")
    	.setItems(items, new DialogInterface.OnClickListener() { 
    	public void onClick(DialogInterface dialog, int item) { 
    	Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show(); 
    	switch (item) {
		case 0:
			SetColor(R.color.white);
			break;
		case 1:
			SetColor(R.color.red);
			break;
		case 2:
			SetColor(R.color.black);
			break;
		case 3:
			SetColor(R.color.yellow);
			break;
		case 4:
			SetColor(R.color.fs);
			break;
		default:
			SetColor(R.color.white);
			break;
		}
    	} 
    	}).show();
    }
    

    public void selectBright()
    {
    	final String[] items = {"100%", "75%", "50%","25%","10%"}; 
    	new AlertDialog.Builder(this) 
    	.setTitle("Select Brightness")
    	.setSingleChoiceItems(items, li, new DialogInterface.OnClickListener() { 
    	public void onClick(DialogInterface dialog, int item) { 
    	Toast.makeText(getApplicationContext(), items[item],Toast.LENGTH_SHORT).show(); 
    	li=item;
    	switch (item) {
		case 0:
			SetBright(1.0F);
			break;
		case 1:
			SetBright(0.75F);
			break;
		case 2:
			SetBright(0.5F);
			break;
		case 3:
			SetBright(0.25F);
			break;
		case 4:
			SetBright(0.1F);
			break;
		default:
			SetBright(1.0F);
			break;
		}	
    	dialog.cancel(); 
    	} 
    	}).show();
    }


    

    private void HideStatusBase()
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		int flag=WindowManager.LayoutParams.FLAG_FULLSCREEN;
		Window myWindow=this.getWindow();
		myWindow.setFlags(flag,flag);
	}
    
    

    private void SetColor(int color_1)
    {
    	myColor = getBaseContext().getResources();
		Drawable color_M = myColor.getDrawable(color_1);
    	mylayout.setBackgroundDrawable(color_M);

    }
    

    private void SetBright(float light)
    {
    	WindowManager.LayoutParams lp=getWindow().getAttributes();
    	lp.screenBrightness=light;
    	getWindow().setAttributes(lp);
    	
    }
}