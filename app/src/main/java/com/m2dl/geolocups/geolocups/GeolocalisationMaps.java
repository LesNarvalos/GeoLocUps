package com.m2dl.geolocups.geolocups;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.logging.Logger;

public class GeolocalisationMaps extends AppCompatActivity implements OnMapReadyCallback, SearchView.OnQueryTextListener {

    private GoogleMap mMap;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private static final Logger LOGGER = Logger.getLogger(GeolocalisationMaps.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocalisation_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map,menu);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String text) {
        database.child("buildings").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // TODO Add some treatment
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // TODO Add some treatment
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // TODO Add some treatment
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                // TODO Add some treatment
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // TODO Add some treatment
            }
        });

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        LOGGER.info("Sends the request but do nothing");
        return false;
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
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}
