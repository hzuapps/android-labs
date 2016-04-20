package com.example.ljl.mygps;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class Net1314080903234GPS extends ListActivity {

    private EditText editText;
    private LocationManager lm;
    private SQLiteDatabase dbRead,dbWrite;
    private Db db;
    private SimpleCursorAdapter adapter;
    private Button button;
    private static final String TAG="GpsActivity";

    //鑾峰彇褰撳墠鏃堕棿
    SimpleDateFormat formatter= new SimpleDateFormat ("yyyy.MM.dd HH:mm:ss");
    Date curDate  = new Date(System.currentTimeMillis());
    String str = formatter.format(curDate);

    //璁剧疆鎸夐挳
    private View.OnClickListener btnListener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(getListView().getVisibility()==View.VISIBLE) {
                Toast.makeText(getApplicationContext(),"鏄剧ず鍘嗗彶璁板綍",Toast.LENGTH_SHORT).show();
                setListAdapter(adapter);
                refresh();
            }


        }
    };




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (Button) findViewById(R.id.button);
        button.setOnClickListener(btnListener);


        //璇绘暟鎹簱
        db =new Db(this);
        dbRead = db.getReadableDatabase();
        dbWrite=db.getWritableDatabase();

        adapter = new SimpleCursorAdapter(this,R.layout.where_you,null,
                new String[]{"longitude","latitude"},
                new int[]{R.id.tvLong,R.id.tvLat});
//        setListAdapter(adapter);
//        refresh();




        editText=(EditText)findViewById(R.id.editText);
        lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);

        //鍒ゆ柇GPS鏄惁姝ｅ父鍚姩
        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this, "璇峰紑鍚疓PS瀵艰埅...", Toast.LENGTH_SHORT).show();
            //杩斿洖寮�鍚疓PS瀵艰埅璁剧疆鐣岄潰
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent,0);
            return;
        }

        //涓鸿幏鍙栧湴鐞嗕綅缃俊鎭椂璁剧疆鏌ヨ鏉′欢
        String bestProvider = lm.getBestProvider(getCriteria(), true);
        //鑾峰彇浣嶇疆淇℃伅
        //濡傛灉涓嶈缃煡璇㈣姹傦紝getLastKnownLocation鏂规硶浼犱汉鐨勫弬鏁颁负LocationManager.GPS_PROVIDER
        Location location= lm.getLastKnownLocation(bestProvider);
        updateView(location);
        //鐩戝惉鐘舵��
        lm.addGpsStatusListener(listener);
        //缁戝畾鐩戝惉锛屾湁4涓弬鏁�
        //鍙傛暟1锛岃澶囷細鏈塆PS_PROVIDER鍜孨ETWORK_PROVIDER涓ょ
        //鍙傛暟2锛屼綅缃俊鎭洿鏂板懆鏈燂紝鍗曚綅姣
        //鍙傛暟3锛屼綅缃彉鍖栨渶灏忚窛绂伙細褰撲綅缃窛绂诲彉鍖栬秴杩囨鍊兼椂锛屽皢鏇存柊浣嶇疆淇℃伅
        //鍙傛暟4锛岀洃鍚�
        //澶囨敞锛氬弬鏁�2鍜�3锛屽鏋滃弬鏁�3涓嶄负0锛屽垯浠ュ弬鏁�3涓哄噯锛涘弬鏁�3涓�0锛屽垯閫氳繃鏃堕棿鏉ュ畾鏃舵洿鏂帮紱涓よ�呬负0锛屽垯闅忔椂鍒锋柊

        // 1绉掓洿鏂颁竴娆★紝鎴栨渶灏忎綅绉诲彉鍖栬秴杩�1绫虫洿鏂颁竴娆★紱
        //娉ㄦ剰锛氭澶勬洿鏂板噯纭害闈炲父浣庯紝鎺ㄨ崘鍦╯ervice閲岄潰鍚姩涓�涓猅hread锛屽湪run涓璼leep(10000);鐒跺悗鎵цhandler.sendMessage(),鏇存柊浣嶇疆
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
    }

    //浣嶇疆鐩戝惉
    private LocationListener locationListener=new LocationListener() {

        /**
         * 浣嶇疆淇℃伅鍙樺寲鏃惰Е鍙�
         */
             public void onLocationChanged(Location location) {
            updateView(location);

                 ContentValues cv = new ContentValues();
                 cv.put("longitude",String.valueOf(location.getLongitude()));
                 cv.put("latitude", String.valueOf(location.getLatitude()));
                 dbWrite.insert("whereYou", null, cv);
                 refresh();




//
//            Log.i(TAG, "鏃堕棿锛�" + location.getTime());
//            Log.i(TAG, "缁忓害锛�"+location.getLongitude());
//            Log.i(TAG, "绾害锛�"+location.getLatitude());
//            Log.i(TAG, "娴锋嫈锛�"+location.getAltitude());
        }

        /**
         * GPS鐘舵�佸彉鍖栨椂瑙﹀彂
         */
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                //GPS鐘舵�佷负鍙鏃�
                case LocationProvider.AVAILABLE:
                    Log.i(TAG, "褰撳墠GPS鐘舵�佷负鍙鐘舵��");
                    break;
                //GPS鐘舵�佷负鏈嶅姟鍖哄鏃�
                case LocationProvider.OUT_OF_SERVICE:
                    Log.i(TAG, "褰撳墠GPS鐘舵�佷负鏈嶅姟鍖哄鐘舵��");
                    break;
                //GPS鐘舵�佷负鏆傚仠鏈嶅姟鏃�
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.i(TAG, "褰撳墠GPS鐘舵�佷负鏆傚仠鏈嶅姟鐘舵��");
                    break;
            }
        }

        /**
         * GPS寮�鍚椂瑙﹀彂
         */
        public void onProviderEnabled(String provider) {
            Location location=lm.getLastKnownLocation(provider);
            updateView(location);
        }

        /**
         * GPS绂佺敤鏃惰Е鍙�
         */
        public void onProviderDisabled(String provider) {
            updateView(null);
        }


    };

    //鐘舵�佺洃鍚�
    GpsStatus.Listener listener = new GpsStatus.Listener() {
        public void onGpsStatusChanged(int event) {
            switch (event) {
                //绗竴娆″畾浣�
                case GpsStatus.GPS_EVENT_FIRST_FIX:
                    Log.i(TAG, "绗竴娆″畾浣�");
                    break;
                //鍗槦鐘舵�佹敼鍙�
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    Log.i(TAG, "鍗槦鐘舵�佹敼鍙�");
                    //鑾峰彇褰撳墠鐘舵��
                    GpsStatus gpsStatus=lm.getGpsStatus(null);
                    //鑾峰彇鍗槦棰楁暟鐨勯粯璁ゆ渶澶у��
                    int maxSatellites = gpsStatus.getMaxSatellites();
                    //鍒涘缓涓�涓凯浠ｅ櫒淇濆瓨鎵�鏈夊崼鏄�
                    Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
                    int count = 0;
                    while (iters.hasNext() && count <= maxSatellites) {
                        GpsSatellite s = iters.next();
                        count++;
                    }
                    System.out.println("鎼滅储鍒帮細"+count+"棰楀崼鏄�");
                    break;
                //瀹氫綅鍚姩
                case GpsStatus.GPS_EVENT_STARTED:
                    Log.i(TAG, "瀹氫綅鍚姩");
                    break;
                //瀹氫綅缁撴潫
                case GpsStatus.GPS_EVENT_STOPPED:
                    Log.i(TAG, "瀹氫綅缁撴潫");
                    break;
            }
        };
    };

    /**
     * 瀹炴椂鏇存柊鏂囨湰鍐呭
     *
     * @param location
     */
    private void updateView(Location location){
        if(location!=null){
            editText.setText("浣嶇疆淇℃伅\n\n缁忓害锛�");
            editText.append(String.valueOf(location.getLongitude()));
            editText.append("\n绾害锛�");
            editText.append(String.valueOf(location.getLatitude()));
            editText.append("\n娴锋嫈锛�");
            editText.append(String.valueOf(location.getAltitude()));



            editText.append("\n鏃堕棿锛�");
            editText.append(str);


        }else{
            //娓呯┖EditText瀵硅薄
            editText.getEditableText().clear();
        }
    }

    /**
     * 杩斿洖鏌ヨ鏉′欢
     * @return
     */
    private Criteria getCriteria(){
        Criteria criteria=new Criteria();
        //璁剧疆瀹氫綅绮剧‘搴� Criteria.ACCURACY_COARSE姣旇緝绮楃暐锛孋riteria.ACCURACY_FINE鍒欐瘮杈冪簿缁�
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //璁剧疆鏄惁瑕佹眰閫熷害
        criteria.setSpeedRequired(false);
        // 璁剧疆鏄惁鍏佽杩愯惀鍟嗘敹璐�
        criteria.setCostAllowed(false);
        //璁剧疆鏄惁闇�瑕佹柟浣嶄俊鎭�
        criteria.setBearingRequired(false);
        //璁剧疆鏄惁闇�瑕佹捣鎷斾俊鎭�
        criteria.setAltitudeRequired(false);
        // 璁剧疆瀵圭數婧愮殑闇�姹�
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        lm.removeUpdates(locationListener);
    }

    private void refresh(){
        Cursor c= dbRead.query("whereYou", null, null, null, null, null, null);
        adapter.changeCursor(c);
    }


}

