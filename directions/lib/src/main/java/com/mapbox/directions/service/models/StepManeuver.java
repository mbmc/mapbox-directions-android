package com.mapbox.directions.service.models;

/**
 * Created by antonio on 11/6/15.
 */
public class StepManeuver {

    public String type;
    public ManeuverPoint location;
    public String instruction;
    public String mode;

    public String getType() {
        return type;
    }

    public ManeuverPoint getLocation() {
        return location;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getMode() {
        return mode;
    }

}
