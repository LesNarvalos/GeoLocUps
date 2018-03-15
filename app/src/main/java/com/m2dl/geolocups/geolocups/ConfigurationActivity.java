package com.m2dl.geolocups.geolocups;

import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ConfigurationActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent MyIntent;
        switch (item.getItemId()){
            case R.id.menu_edt :
                MyIntent= new Intent(this, EDTActivity.class);
                item.setIntent(MyIntent);
                return false;
            case R.id.menu_qr :
                MyIntent = new Intent(this, QRcodeActivity.class);
                item.setIntent(MyIntent);
                return false;
            case R.id.menu_detection_danomalie :
                MyIntent = new Intent(this, AnomalieActivity.class);
                item.setIntent(MyIntent);
                return false;
            case R.id.menu_geolocalisation :
                MyIntent = new Intent(this, GeolocalisationMaps.class);
                item.setIntent(MyIntent);
                return false;
            case R.id.menu_information :
                MyIntent = new Intent(this, InformationActivity.class);
                item.setIntent(MyIntent);
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
