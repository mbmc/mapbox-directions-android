package com.mapbox.directions.service.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonio on 11/6/15.
 */
public class ManeuverPoint {

    public String type;
    List<Double> coordinates;

    public ManeuverPoint() {
        coordinates = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

}
