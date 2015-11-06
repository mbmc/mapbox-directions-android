package com.mapbox.directions.service.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by antonio on 11/6/15.
 */
public class RouteStep {

    public int distance;
    public int duration;
    @SerializedName("wayName") public String wayName;
    public String direction;
    public double heading;
    public StepManeuver maneuver;

    public int getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public String getWayName() {
        return wayName;
    }

    public String getDirection() {
        return direction;
    }

    public double getHeading() {
        return heading;
    }

    public StepManeuver getManeuver() {
        return maneuver;
    }

}
