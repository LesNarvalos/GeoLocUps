package com.m2dl.geolocups.geolocups;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

public class EDTActivity extends AppCompatActivity {

    String url;
    WebView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edt);
        //url= getSharedPreferences(",MODE_PRIVATE).getString("urlKey","https://edt.univ-tlse3.fr/FSI/2017_2018/M2/M2_INF_DL/g251747.xml") ;
        view = (WebView) this.findViewById(R.id.webView);
        view.getSettings().setJavaScriptEnabled(true);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        url = settings.getString(getString(R.string.url_key), "string value");
        view.loadUrl(url);

    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        url = settings.getString(getString(R.string.url_key), "string value");
        view.loadUrl(url);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_geolocalisation:
                Intent IntentGeo = new Intent(this, GeolocalisationMaps.class);
                item.setIntent(IntentGeo);
                return false;
            case R.id.menu_qr :
                Intent IntentQR = new Intent(this, QRcodeActivity.class);
                item.setIntent(IntentQR);
                return false;
            case R.id.menu_detection_danomalie :
                Intent IntentDA = new Intent(this, AnomalieActivity.class);
                item.setIntent(IntentDA);
                return false;
            case R.id.menu_parametres :
                Intent IntentC = new Intent(this, ConfigurationActivity.class);
                item.setIntent(IntentC);
                return false;
            case R.id.menu_information :
                Intent IntentInf = new Intent(this, InformationActivity.class);
                item.setIntent(IntentInf);
                return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
