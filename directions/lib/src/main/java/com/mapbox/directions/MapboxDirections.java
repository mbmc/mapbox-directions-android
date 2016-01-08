package com.mapbox.directions;

import android.text.TextUtils;
import android.util.Log;

import com.mapbox.directions.service.DirectionsService;
import com.mapbox.directions.service.models.DirectionsResponse;
import com.mapbox.directions.service.models.Waypoint;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by antonio on 11/6/15.
 */
public class MapboxDirections {

    private final static String LOG_TAG = "MapboxDirections";

    private final static String BASE_URL = "https://api.mapbox.com";

    // 0.1 miles
    public final static double OFF_ROUTE_THRESHOLD = 0.1;

    private Call<DirectionsResponse> _call;

    public MapboxDirections(Builder builder) {
        DirectionsService service = getService();
        _call = service.calculate(
                builder._profile,
                builder.getWaypointsFormatted(),
                builder._accessToken,
                builder._alternatives,
                builder._instructions,
                builder._geometry,
                builder._steps);
    }

    /*
     * Retrofit API
     */

    public Response<DirectionsResponse> execute() throws IOException {
        return _call.execute();
    }

    public void enqueue(Callback<DirectionsResponse> callback) {
        _call.enqueue(callback);
    }

    public void cancel() {
        _call.cancel();
    }

    public Call<DirectionsResponse> clone() {
        return _call.clone();
    }

    DirectionsService getService() {
        // Log the URL for debugging purposes
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request();
                Log.d(LOG_TAG, String.format("Mapbox URL: %s", request.url()));
                com.squareup.okhttp.Response response = chain.proceed(request);
                return response;
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DirectionsService service = retrofit.create(DirectionsService.class);
        return service;
    }

    /*
     * Builder
     */

    public static class Builder {

        private String _accessToken;
        private String _profile;
        private List<Waypoint> _waypoints;
        private Waypoint _origin;
        private Waypoint _destination;
        private boolean _alternatives;
        private String _instructions;
        private String _geometry;
        private boolean _steps;

        public Builder setAccessToken(String accessToken) {
            _accessToken = accessToken;
            return this;
        }

        public Builder setProfile(@DirectionsCriteria.DirectionsProfile String profile) {
            _profile = profile;
            return this;
        }

        /*
         * We offer some convenience for the typical case where we only have an origin
         * and a destination. Instead of having to create a List of waypoints, we just
         * call setOrigin() and setDestination() which is more meaningful. That's taken
         * into account in getWaypointsFormatted()
         */

        public Builder setWaypoints(List<Waypoint> waypoints) {
            _waypoints = waypoints;
            return this;
        }

        public Builder setOrigin(Waypoint origin) {
            _origin = origin;
            return this;
        }

        public Builder setDestination(Waypoint destination) {
            _destination = destination;
            return this;
        }

        public String getWaypointsFormatted() {
            String waypointsFormatted = "";

            // Set origin and destination
            if (_origin != null && _destination != null) {
                _waypoints = new ArrayList<>(Arrays.asList(_origin, _destination));
            }

            // Empty list
            if (_waypoints == null || _waypoints.size() == 0) {
                return waypointsFormatted;
            }

            // Convert to {lon},{lat} coordinate pairs
            List<String> pieces = new ArrayList<>();
            for (Waypoint waypoint: _waypoints) {
                pieces.add(String.format("%f,%f", waypoint.getLongitude(), waypoint.getLatitude()));
            }

            // The waypoints parameter should be a semicolon-separated list of locations to visit
            waypointsFormatted = TextUtils.join(";", pieces);
            return waypointsFormatted;
        }

        public Builder setAlternatives(boolean alternatives) {
            _alternatives = alternatives;
            return this;
        }

        public Builder setInstructions(@DirectionsCriteria.DirectionsInstructions String instructions) {
            _instructions = instructions;
            return this;
        }

        public Builder setGeometry(@DirectionsCriteria.DirectionsGeometry String geometry) {
            _geometry = geometry;
            return this;
        }

        public Builder setSteps(boolean steps) {
            _steps = steps;
            return this;
        }

        // Checks if the given token is valid
        private void validateAccessToken(String accessToken) {
            if (TextUtils.isEmpty(accessToken) || (!accessToken.startsWith("pk.") && !accessToken.startsWith("sk."))) {
                throw new RuntimeException("Using the Mapbox Directions API requires setting a valid access token.");
            }
        }

        public MapboxDirections build() {
            validateAccessToken(_accessToken);
            return new MapboxDirections(this);
        }

    }
}
