package homeworks.androidworks.hzuapps.edu.net1314080903247;


        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.provider.Settings;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.TextView;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.net.URL;

public class Net1314080903247Activity extends AppCompatActivity {

    private TextView tvPmData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvPmData=(TextView)findViewById(R.id.pmData);
        findViewById(R.id.btnreload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadData();

            }
        });
        reloadData();
    }

    private void reloadData(){
        //tvPmData.setText("Loading");
        new AsyncTask<Void,Void,String>() {

            @Override
            protected String doInBackground(Void... params) {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://aqicn.org/publishingdata/json").openStream(), "utf-8"));
                    String line=null;
                    StringBuffer content =new StringBuffer();
                    while((line=reader.readLine())!=null)
                    {
                        content.append(line);
                    }


                    reader.close();
                    return content.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s!=null){
                    try {
                        JSONArray jsonarr=new JSONArray(s);
                        JSONObject firstJO=jsonarr.getJSONObject(0);
                        JSONArray pollutants=firstJO.getJSONArray("pollutants");
                        JSONObject firstPollutants=pollutants.getJSONObject(0);
                        System.out.println("cityName=" + firstJO.getString("cityName") + ",local=" + firstJO.getString("localName"));
                        String cityName= firstJO.getString("cityName");
                        String localName= firstJO.getString("localName");
                        Double pollutant=firstPollutants.getDouble("value");
                        String a=cityName+" "+localName+":"+pollutant;
                        // String a=String.format("s% s%:f%", firstJO.getString("cityName"), firstJO.getString("localName"),firstPollutants.getDouble("value"));
                        //tvPmData.setText(String.format("s% s%:f%", firstJO.getString("cityName"), firstJO.getString("localName"),firstPollutants.getDouble("value")));
                        tvPmData.setText(a);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
    }

}
