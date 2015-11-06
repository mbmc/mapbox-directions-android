package com.mapbox.directions.service.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonio on 11/6/15.
 */
public class DirectionsResponse {

    public DirectionsFeature origin;
    public DirectionsFeature destination;
    public List<DirectionsFeature> waypoints;
    public List<DirectionsRoute> routes;

    public DirectionsResponse() {
        waypoints = new ArrayList<>();
        routes = new ArrayList<>();
    }

    public DirectionsFeature getOrigin() {
        return origin;
    }

    public DirectionsFeature getDestination() {
        return destination;
    }

    public List<DirectionsFeature> getWaypoints() {
        return waypoints;
    }

    public List<DirectionsRoute> getRoutes() {
        return routes;
    }
}
