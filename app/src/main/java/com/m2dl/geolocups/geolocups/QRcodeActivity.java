package com.m2dl.geolocups.geolocups;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRcodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView zXingScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_LONG).show();
        zXingScannerView.resumeCameraPreview(this);

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
            case R.id.menu_edt :
                Intent IntentEDT = new Intent(this, EDTActivity.class);
                item.setIntent(IntentEDT);
                return false;
            case R.id.menu_detection_danomalie:
                Intent Intentan = new Intent(this, AnomalieActivity.class);
                item.setIntent(Intentan);
                return false;
            case R.id.menu_parametres :
                Intent Intentparam = new Intent(this, ConfigurationActivity.class);
                item.setIntent(Intentparam);
                return false;
            case R.id.menu_information :
                Intent IntentInfo = new Intent(this, InformationActivity.class);
                item.setIntent(IntentInfo);
                return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
