package com.mapbox.directions.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mapbox.directions.DirectionsCriteria;
import com.mapbox.directions.MapboxDirections;
import com.mapbox.directions.service.models.DirectionsResponse;
import com.mapbox.directions.service.models.Waypoint;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = "MainActivity";

    private final static String MAPBOX_ACCESS_TOKEN = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Debug
        testDirections();
    }

    private void testDirections() {
        // Dupont Circle
        Waypoint origin = new Waypoint(-77.04341, 38.90962);

        // White House
        Waypoint destination = new Waypoint(-77.0365, 38.8977);

        MapboxDirections md = new MapboxDirections.Builder()
                .setAccessToken(MAPBOX_ACCESS_TOKEN)
                .setOrigin(origin)
                .setDestination(destination)
                .setProfile(DirectionsCriteria.PROFILE_WALKING)
                .build();

        md.enqueue(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Response<DirectionsResponse> response, Retrofit retrofit) {
                Log.d(LOG_TAG, "Success: " + response.isSuccess());
                Log.d(LOG_TAG, "Distance: " + response.body().getRoutes().get(0).getDistance());
                Log.d(LOG_TAG, "Duration: " + response.body().getRoutes().get(0).getDuration());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(LOG_TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
