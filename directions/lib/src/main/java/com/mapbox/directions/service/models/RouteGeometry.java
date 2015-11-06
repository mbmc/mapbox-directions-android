package com.mapbox.directions.service.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonio on 11/6/15.
 */
public class RouteGeometry {

    public String type;
    public List<List<Double>> coordinates;

    public RouteGeometry() {
        coordinates = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }

}
