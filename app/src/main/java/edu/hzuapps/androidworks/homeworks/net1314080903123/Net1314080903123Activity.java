package edu.hzuapps.androidworks.homeworks.net1314080903123;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class Net1314080903123Activity extends Activity implements OnClickListener {
    TextView dollars;
    TextView eros;
    RadioButton dtoe;
    RadioButton etod;
    Button convert;
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_net1314080903123);
        dollars = (TextView)this.findViewById(R.id.dollars);
        eros = (TextView)this.findViewById(R.id.Eros);

        dtoe = (RadioButton)this.findViewById(R.id.dtoe);
        dtoe.setChecked(true);
        etod = (RadioButton)this.findViewById(R.id.etod);

        convert = (Button)this.findViewById(R.id.convert);
        convert.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_net1314080903123, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (dtoe.isChecked()) {
            convertDollarsToEuros();
        }
        if (etod.isChecked()) {
            convertEurosToDollars();
        }
    }

    private void convertEurosToDollars() {
        // TODO Auto-generated method stub
        double val = Double.parseDouble(eros.getText().toString());
        // in a real app, we'd get this off the 'net
        dollars.setText(Double.toString(val / 0.67));
    }

    private void convertDollarsToEuros() {
        // TODO Auto-generated method stub
        double val = Double.parseDouble(dollars.getText().toString());
        // in a real app, we'd get this off the 'net
        eros.setText(Double.toString(val*0.67));
    }

}
