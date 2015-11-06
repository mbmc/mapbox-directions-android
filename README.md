# Mapbox directions client for Android

[Mapbox Directions](https://www.mapbox.com/developers/api/directions/) client for Android.

## Installation

For now, compile the `lib` module in the `directions` folder and include the
resulting `.aar` file in your project as a new module.

Soon, you'll be able to download the latest version from Maven.

## Usage

To benefit from the full Mapbox Directions API, use the `MapboxDirections` object.

For example, to get walking directions from Dupont Circle to the White House
(in Washington, DC) you could do the following:

```
// Dupont Circle
Waypoint origin = new Waypoint(-77.04341, 38.90962);

// White House
Waypoint destination = new Waypoint(-77.0365, 38.8977);

MapboxDirections md = new MapboxDirections.Builder()
        .setAccessToken(MAPBOX_ACCESS_TOKEN)
        .setOrigin(origin)
        .setDestination(destination)
        .setProfile(DirectionsCriteria.PROFILE_WALKING)
        .build();
```

Once the `client` object has been created, you can launch a syncronous request
(remember not do this in the main UI thread):

```
Response<DirectionsResponse> response = client.execute();
```

Or an asynchronous request (you need to provide your own `Callback<DirectionsResponse>`):

```
client.enqueue(callback)
```
