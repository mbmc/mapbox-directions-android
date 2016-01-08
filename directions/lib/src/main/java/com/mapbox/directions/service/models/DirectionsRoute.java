package com.mapbox.directions.service.models;

import com.mapbox.directions.MapboxDirections;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonio on 11/6/15.
 */
public class DirectionsRoute {

    private int distance;
    private int duration;
    private String summary;
    private RouteGeometry geometry;
    private List<RouteStep> steps;

    public DirectionsRoute() {
        steps = new ArrayList<>();
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public RouteGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(RouteGeometry geometry) {
        this.geometry = geometry;
    }

    public List<RouteStep> getSteps() {
        return steps;
    }

    public void setSteps(List<RouteStep> steps) {
        this.steps = steps;
    }

    /*
     * A first approximation, reducing external library dependencies, to off-route detection.
     * It checks that the user distance to any of the points that defines the route is less than
     * the OFF_ROUTE_THRESHOLD (0.1 miles).
     */

    public boolean isOffRoute(Waypoint target) {
        List<Waypoint> waypoints = this.getGeometry().getWaypoints();

        double distance;
        for (Waypoint waypoint: waypoints) {
            distance = computeDistance(target, waypoint);
            if (distance <= MapboxDirections.OFF_ROUTE_THRESHOLD) {
                return false;
            }
        }

        return true;
    }

    /*
     * See: https://github.com/Turfjs/turf-distance
     * The result is provided in in miles.
     */

    private double computeDistance(Waypoint from, Waypoint to) {
        double dLat = Math.toRadians(to.getLatitude() - from.getLatitude());
        double dLon = Math.toRadians(to.getLongitude() - from.getLongitude());
        double lat1 = Math.toRadians(from.getLatitude());
        double lat2 = Math.toRadians(to.getLatitude());

        double a = Math.pow(Math.sin(dLat/2), 2) + Math.pow(Math.sin(dLon/2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double R = 3960;

        double distance = R * c;
        return distance;
    }
}
