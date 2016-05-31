package edu.hzuapps.androidworks.homeworks.net1314080903118;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by RImpression on 2016/5/18 0018.
 */
public class Net1314080903118PreviewActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView img = new ImageView(this);
        String path = getIntent().getStringExtra("path");
        if (path != null) {
            img.setImageURI(Uri.fromFile(new File(path)));
        }
        setContentView(img);
    }
}
