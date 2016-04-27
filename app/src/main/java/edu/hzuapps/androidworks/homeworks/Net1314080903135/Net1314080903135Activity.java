package edu.hzuapps.androidworks.homeworks.net1314080903135.;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Net1314080903135Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
     }
 
     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         // Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.main, menu);
         return true;
     }
}

