package com.m2dl.geolocups.geolocups;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

public class EDTActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edt);

        String url = "https://edt.univ-tlse3.fr/FSI/2017_2018/M2/M2_INF_DL/g251747.html";
        WebView view = (WebView) this.findViewById(R.id.webView);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
    }

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

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
