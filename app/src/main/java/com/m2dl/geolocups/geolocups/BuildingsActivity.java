package com.m2dl.geolocups.geolocups;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by reapy on 15/03/18.
 */

public class BuildingsActivity extends AppCompatActivity {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    ListView listView;

    private List<HashMap<String, String>> buildings;

    private static final Logger LOGGER = Logger.getLogger(BuildingsActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildings);
        listView = (ListView) findViewById(R.id.listView);
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                buildings = (List<HashMap<String, String>>) dataSnapshot.getValue();

                final ArrayAdapter<String> adapter = new ArrayAdapter<>(BuildingsActivity.this,
                        R.layout.activity_buildings, R.id.text_list_view, populateBuildings());
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        printPosition(adapterView.getItemAtPosition(i).toString());
                    }
                });
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
    }

    public void printPosition(String name) {
        String coordonates = "";
        for (HashMap<String, String> building : buildings) {
            if (building.get("name").equals(name)) {
                coordonates = building.get("coordonates");
            }
        }

        Intent intent;
        intent = new Intent(this, GeolocalisationMaps.class);
        intent.putExtra("coordonates", coordonates);
        startActivity(intent);
    }

    public List<String> populateBuildings() {
        List<String> buildingsNames = new ArrayList<>();
        for (HashMap<String, String> building : buildings) {
            buildingsNames.add(building.get("name"));
        }

        return buildingsNames;
    }
}
