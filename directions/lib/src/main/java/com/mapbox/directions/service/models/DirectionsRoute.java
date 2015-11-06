package com.mapbox.directions.service.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonio on 11/6/15.
 */
public class DirectionsRoute {

    public int distance;
    public int duration;
    public String summary;
    public RouteGeometry geometry;
    public List<RouteStep> steps;

    public DirectionsRoute() {
        steps = new ArrayList<>();
    }

    public int getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public String getSummary() {
        return summary;
    }

    public RouteGeometry getGeometry() {
        return geometry;
    }

    public List<RouteStep> getSteps() {
        return steps;
    }

}
