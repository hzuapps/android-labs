package edu.hzuapps.androidworks.examples;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TableLayout;

import edu.hzuapps.androidworks.R;

public class TableLayoutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_layout);

        View root = findViewById(R.id.table_layout);

        TableLayout tableLayout = (TableLayout) root;

//        tableLayout.setColumnCollapsed(0, true);
//        tableLayout.setColumnStretchable(1, true);
    }

}
