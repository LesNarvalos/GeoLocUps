package com.m2dl.geolocups.geolocups;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class AnomalieActivity extends AppCompatActivity {

    ImageView imageView;
    private Path path = new Path();
    private DragRectView view ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anomalie);
        imageView = (ImageView)findViewById(R.id.imageView);
        view = (DragRectView) findViewById(R.id.dragRect);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
        if (null != view) {
            view.setOnUpCallback(new DragRectView.OnUpCallback() {
                @Override
                public void onRectFinished(final Rect rect) {
                    /*Toast.makeText(getApplicationContext(), "Rect is (" + rect.left + ", " + rect.top + ", " + rect.right + ", " + rect.bottom + ")",
                            Toast.LENGTH_LONG).show();*/
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
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
            case R.id.menu_geolocalisation :
                MyIntent = new Intent(this, GeolocalisationMaps.class);
                item.setIntent(MyIntent);
                return false;
            case R.id.menu_information :
                MyIntent = new Intent(this, InformationActivity.class);
                item.setIntent(MyIntent);
                return false;
            case R.id.menu_parametres :
                MyIntent = new Intent(this, ConfigurationActivity.class);
                item.setIntent(MyIntent);
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
