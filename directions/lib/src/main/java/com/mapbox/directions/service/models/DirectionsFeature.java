package com.mapbox.directions.service.models;

/**
 * Created by antonio on 11/6/15.
 */
public class DirectionsFeature {

    public String type;
    public FeatureGeometry geometry;
    public FeatureProperties properties;

    public String getType() {
        return type;
    }

    public FeatureGeometry getGeometry() {
        return geometry;
    }

    public FeatureProperties getProperties() {
        return properties;
    }

}
