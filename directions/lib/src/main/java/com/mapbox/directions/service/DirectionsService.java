package com.mapbox.directions.service;

import com.mapbox.directions.service.models.DirectionsResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by antonio on 11/6/15.
 */
public interface DirectionsService {

    @GET("/v4/directions/{profile}/{waypoints}.json")
    Call<DirectionsResponse> calculate(
            @Path("profile") String profile,
            @Path("waypoints") String waypoints,
            @Query("access_token") String accessToken,
            @Query("alternatives") boolean alternatives,
            @Query("instructions") String instructions,
            @Query("geometry") String geometry,
            @Query("steps") boolean steps
    );

}
