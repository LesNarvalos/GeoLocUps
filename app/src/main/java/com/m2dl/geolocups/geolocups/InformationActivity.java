package com.m2dl.geolocups.geolocups;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity implements  SensorEventListener{

    // The sensor manager
    SensorManager sensorManager;
    Sensor light;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Instancier
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        setContentView(R.layout.activity_information);
        tv = (TextView) findViewById(R.id.textView);

        //setContentView(tv);
    }

    @Override
    protected void onResume() {

        super.onResume();
        sensorManager.registerListener(this, light,SensorManager.SENSOR_DELAY_NORMAL);
        //Sensor mMagneticField = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this, light);
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        int sensor = sensorEvent.sensor.getType();
        float [] values = sensorEvent.values;

        synchronized (this) {
            if (sensor == Sensor.TYPE_LIGHT) {
                float lightValue = values[0];
                tv.setText("luminosité : "+lightValue);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent MyIntent;
        switch (item.getItemId()) {
            case R.id.menu_edt:
                MyIntent = new Intent(this, EDTActivity.class);
                item.setIntent(MyIntent);
                return false;
            case R.id.menu_qr:
                MyIntent = new Intent(this, QRcodeActivity.class);
                item.setIntent(MyIntent);
                return false;
            case R.id.menu_detection_danomalie:
                MyIntent = new Intent(this, AnomalieActivity.class);
                item.setIntent(MyIntent);
                return false;
            case R.id.menu_geolocalisation:
                MyIntent = new Intent(this, GeolocalisationMaps.class);
                item.setIntent(MyIntent);
                return false;
            case R.id.menu_parametres:
                MyIntent = new Intent(this, ConfigurationActivity.class);
                item.setIntent(MyIntent);
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
