package edu.hzuapps.androidworks.homeworks.net1314080903219;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.EditText;
import android.widget.Toast;



public class Net1314080903219MainActivity extends AppCompatActivity {
    private String listName;
    private final int LIST_CONTENT_CODE=100;
    private final int FILE_SELECT_CODE=1000;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903219_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton email = (FloatingActionButton) findViewById(R.id.email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "contact us", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton info = (FloatingActionButton) findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "this is our displayer", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_net1314080903219_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onClick(View view){
    /*    Button bu=(Button) findViewById(R.id.button);
        if(bu.getText().equals("OPEN THE DOOR")){
            bu.setText("SEE YOUR SISTER");
        }
        else {
            bu.setText("OPEN THE DOOR");

        }*/

        final EditText inputServer = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("List name").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                .setNegativeButton("Cancel", null);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                listName = inputServer.getText().toString();

                Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("*/mp3");
                intent1.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent1, LIST_CONTENT_CODE);

            }
        });
        builder.show();


      //  startActivityForResult(intent,LIST_CONTENT_CODE);
       // startService(intent);

    }

    public void play(View view){
        showFileChooser();

    }
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/mp3");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(intent, FILE_SELECT_CODE );
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    intent = new Intent();
                    intent.setClass(this, Net1314080903219MediaPlayerService.class);
                    intent.putExtra("song", uri.getPath());
                 //   System.out.println(intent.getStringExtra("song"));
                    startService(intent);
                }
                break;
            case LIST_CONTENT_CODE:
                if (resultCode == RESULT_OK) {{
                    Uri uri = data.getData();
                    intent = new Intent();
                    intent.setClass(this, Net1314080903219CreateMusicList.class);
                    intent.putExtra("songPath",uri.getPath());
                    intent.putExtra("listName",listName);
                    startService(intent);
                }}
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

