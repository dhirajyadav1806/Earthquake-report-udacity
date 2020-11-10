package com.jeeduniya.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

//        // Create a fake list of earthquake locations.
//        ArrayList<Earthquake> earthquakes = new ArrayList<>();
//        earthquakes.add(new Earthquake("7.2", "San Francisco", "Feb 2, 2016"));
//        earthquakes.add(new Earthquake("7.2", "London", "Feb 2, 2016"));
//        earthquakes.add(new Earthquake("7.2", "Tokyo", "Feb 2, 2016"));
//        earthquakes.add(new Earthquake("7.2", "Mexico City", "Feb 2, 2016"));
//        earthquakes.add(new Earthquake("7.2", "Rio de Janeiro", "Feb 2, 2016"));
//        earthquakes.add(new Earthquake("7.2", "Paris", "Feb 2, 2016"));

        // Get the list of earthquakes from {@link QueryUtils}
        ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = findViewById(R.id.list);

        // Create a new adapter that takes the list of earthquakes as input
        final EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes);//(Note that we also had to add the “final” modifier on the EarthquakeAdapter local variable, so that we could access the adapter variable within the OnItemClickListener.)

        // Set the adapter on the {@link ListView} so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Find the current earthquake that was clicked on
                Earthquake currentEarthquake = adapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                assert currentEarthquake != null;
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);//we'll create an intent with the action of viewing something. What do we want to view? Well this Intent constructor also accepts a URI for the data resource we want to view, and Android will sort out the best app to handle this sort of content. For instance, if the URI represented a location, Android would open up a mapping app. In this case, the resource is an HTTP URL, so Android will usually open up a browser.

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }
}