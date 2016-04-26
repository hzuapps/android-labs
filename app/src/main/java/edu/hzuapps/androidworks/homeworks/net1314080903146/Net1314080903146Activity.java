package edu.hzuapps.androidworks.homeworks.net1314080903146;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class Net1314080903146Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903146);

         imageView = (ImageView) this.findViewById(R.id.imageView);
        imageView.setKeepScreenOn(true);
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    }
    private ImageView imageView;
    private SensorManager manager;
    private SensorListener listener = new SensorListener();

    @Override
    protected void onResume() {
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        manager.registerListener(listener, sensor,
                SensorManager.SENSOR_DELAY_GAME);
        super.onResume();
    }

    @Override
    protected void onPause() {
        manager.unregisterListener(listener);
        super.onPause();
    }

    private final class SensorListener implements SensorEventListener {
        private float predegree = 0;

        public void onSensorChanged(SensorEvent event) {
            float degree = event.values[0];// 存放了方向值 90
            RotateAnimation animation = new RotateAnimation(predegree, -degree,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(200);
            imageView.startAnimation(animation);
            predegree = -degree;
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

}

