package com.m2dl.geolocups.geolocups;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.MatrixCursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.m2dl.geolocups.geolocups.domain.Building;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class GeolocalisationMaps extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private static final Logger LOGGER = Logger.getLogger(GeolocalisationMaps.class.getName());

    LocationManager locationManager;

    private SimpleCursorAdapter adapter;

    private List<Building> buildings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocalisation_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final String[] from = new String[] {"cityName"};
        final int[] to = new int[] {android.R.id.text1};
        adapter = new SimpleCursorAdapter(mapFragment.getActivity(),
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                buildings = (List<Building>) dataSnapshot.getValue();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map,menu);

        SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(menu.findItem(R.id.menu_search));
        searchView.setSuggestionsAdapter(adapter);
        searchView.setIconifiedByDefault(false);
        // Getting selected (clicked) item suggestion
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                // Your code here
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                // Your code here
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                populateAdapter(s);
                return false;
            }
        });

        return true;
    }

    public void populateAdapter(String text) {
        final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "cityName" });
        for (int i=0; i<buildings.size(); i++) {
            if (buildings.get(i).getName().startsWith(text.toLowerCase()))
                c.addRow(new Object[] {i, buildings.get(i).getName()});
        }
        adapter.changeCursor(c);
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
        LatLng U4300 = new LatLng(43.5627949, 1.4689871);
        mMap.addMarker(new MarkerOptions().position(U4300).title("U4 300"));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(U4300).zoom(14.0f).build();
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(U4300));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.moveCamera(cameraUpdate);
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/



    }

    public void geolocalisation(MenuItem item) {
        // géolocalisation
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        // si internet est activé
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude =  location.getLatitude();
                    double longitude = location.getLongitude();

                    LatLng latLng = new LatLng(latitude,longitude);

                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList=  geocoder.getFromLocation(latitude,longitude,1);
                        String str = addressList.get(0).getLocality()+" ,";
                        str += addressList.get(0).getCountryName();
                        //mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        //mMap.addCircle(new CircleOptions().center(latLng).visible(true));
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str).icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));
                        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(14.0f).build();
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                        mMap.moveCamera(cameraUpdate);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        }
        else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude =  location.getLatitude();
                    double longitude = location.getLongitude();

                    LatLng latLng = new LatLng(latitude,longitude);

                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList=  geocoder.getFromLocation(latitude,longitude,1);
                        String str = addressList.get(0).getLocality()+" ,";
                        str += addressList.get(0).getCountryName();
                        //mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        //mMap.addCircle(new CircleOptions().center(latLng).visible(true));

                        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                        mMap.addMarker(new MarkerOptions().position(latLng).title(str).icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));
                        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(14.0f).build();
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                        mMap.moveCamera(cameraUpdate);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });

        }

    }
}
