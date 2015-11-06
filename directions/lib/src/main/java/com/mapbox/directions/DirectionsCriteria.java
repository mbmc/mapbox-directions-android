package com.mapbox.directions;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by antonio on 11/6/15.
 */
public class DirectionsCriteria {

    /*
     * DirectionsProfile "typedef"
     */

    @StringDef({PROFILE_DRIVING, PROFILE_WALKING, PROFILE_CYCLING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DirectionsProfile {}

    public static final String PROFILE_DRIVING = "mapbox.driving";
    public static final String PROFILE_WALKING = "mapbox.walking";
    public static final String PROFILE_CYCLING = "mapbox.cycling";

    /*
     * DirectionsInstructions "typedef"
     */

    @StringDef({INSTRUCTIONS_TEXT, INSTRUCTIONS_HTML})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DirectionsInstructions {}

    public static final String INSTRUCTIONS_TEXT = "text";
    public static final String INSTRUCTIONS_HTML = "html";

    /*
     * DirectionsGeometry "typedef"
     */

    @StringDef({GEOMETRY_GEOJSON, GEOMETRY_POLYLINE, GEOMETRY_FALSE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DirectionsGeometry {}

    public static final String GEOMETRY_GEOJSON = "geojson";
    public static final String GEOMETRY_POLYLINE = "polyline";
    public static final String GEOMETRY_FALSE = "false";

}
