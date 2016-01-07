package com.mapbox.directions.service.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonio on 11/6/15.
 */
public class RouteGeometry {

    private String type;
    private List<List<Double>> coordinates;

    public RouteGeometry() {
        coordinates = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    /*
     * Bring some consistency to our latlon objects
     */

    public List<Waypoint> getWaypoints() {
        List<Waypoint> waypoints = new ArrayList<>();

        // Parse raw pairs
        List<List<Double>> coordinates = this.getCoordinates();
        for (List<Double> coordinate: coordinates) {
            waypoints.add(new Waypoint(coordinate.get(0), coordinate.get(1)));
        }

        return waypoints;
    }

}
