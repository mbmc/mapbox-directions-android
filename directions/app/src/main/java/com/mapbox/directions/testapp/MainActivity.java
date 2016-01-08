package com.mapbox.directions.testapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.mapbox.directions.DirectionsCriteria;
import com.mapbox.directions.MapboxDirections;
import com.mapbox.directions.service.models.DirectionsResponse;
import com.mapbox.directions.service.models.DirectionsRoute;
import com.mapbox.directions.service.models.Waypoint;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.views.MapView;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = "MainActivity";

    private final static String MAPBOX_ACCESS_TOKEN = "";

    private MapView mapView = null;
    private DirectionsRoute currentRoute = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Dupont Circle (Washington, DC)
        Waypoint origin = new Waypoint(-77.04341, 38.90962);

        // The White House (Washington, DC)
        Waypoint destination = new Waypoint(-77.0365, 38.8977);

        // Centroid
        LatLng centroid = new LatLng(
                (origin.getLatitude() + destination.getLatitude()) / 2,
                (origin.getLongitude() + destination.getLongitude()) / 2);

        // Set up a standard Mapbox map
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setAccessToken(MAPBOX_ACCESS_TOKEN);
        mapView.setStyleUrl(Style.MAPBOX_STREETS);
        mapView.setCenterCoordinate(centroid);
        mapView.setZoomLevel(14);
        mapView.onCreate(savedInstanceState);

        // We're gonna use this to demo off-route detection
        mapView.setOnMapClickListener(new MapView.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng point) {
                Waypoint target = new Waypoint(point.getLongitude(), point.getLatitude());
                checkOffRoute(target);
            }
        });

        // Add origin and destination to the map
        mapView.addMarker(new MarkerOptions()
                .position(new LatLng(origin.getLatitude(), origin.getLongitude()))
                .title("Origin")
                .snippet("Dupont Circle"));
        mapView.addMarker(new MarkerOptions()
                .position(new LatLng(destination.getLatitude(), destination.getLongitude()))
                .title("Destination")
                .snippet("The White House"));

        // Get route from API
        getRoute(origin, destination);
    }

    private void getRoute(Waypoint origin, Waypoint destination) {
        MapboxDirections md = new MapboxDirections.Builder()
                .setAccessToken(MAPBOX_ACCESS_TOKEN)
                .setOrigin(origin)
                .setDestination(destination)
                .setProfile(DirectionsCriteria.PROFILE_WALKING)
                .build();

        md.enqueue(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Response<DirectionsResponse> response, Retrofit retrofit) {
                // You can get generic HTTP info about the response
                Log.d(LOG_TAG, "Response code: " + response.code());

                // Print some info about the route
                currentRoute = response.body().getRoutes().get(0);
                Log.d(LOG_TAG, "Distance: " + currentRoute.getDistance());
                showMessage(String.format("Route is %d meters long.", currentRoute.getDistance()));

                // Draw the route on the map
                drawRoute(currentRoute);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(LOG_TAG, "Error: " + t.getMessage());
                showMessage("Error: " + t.getMessage());
            }
        });
    }

    private void drawRoute(DirectionsRoute route) {
        // Convert List<Waypoint> into LatLng[]
        List<Waypoint> waypoints = route.getGeometry().getWaypoints();
        LatLng[] point = new LatLng[waypoints.size()];
        for (int i = 0; i < waypoints.size(); i++) {
            point[i] = new LatLng(
                    waypoints.get(i).getLatitude(),
                    waypoints.get(i).getLongitude());
        }

        // Draw Points on MapView
        mapView.addPolyline(new PolylineOptions()
                .add(point)
                .color(Color.parseColor("#3887be"))
                .width(5));
    }

    private void checkOffRoute(Waypoint target) {
        if (currentRoute.isOffRoute(target)) {
            showMessage("You are off-route.");
        } else {
            showMessage("You are not off-route.");
        }
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause()  {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
