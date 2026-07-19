## Recruitment Task

Congratulations on reaching this stage of the recruitment process.

The next step is to complete and discuss a Java test. Below is the challenge and supporting details.

## Java Test Challenge

Write a REST-style service that returns the geographic (straight-line) distance between two UK postal codes.

### Request

- Provide two UK postal codes as request arguments.
- You may choose how these arguments are supplied.

### Response

For a valid request, return a JSON document containing:

- For both locations: postal code, latitude, and longitude (in degrees)
- The distance between the two locations (in kilometers)
- A fixed string field `unit` with the value `"km"`

## Postal Code Lookup Data

Use the following data sources:

- http://www.freemaptools.com/download-uk-postcode-lat-lng.htm
- http://www.freemaptools.com/download/full-postcodes/postcodes.zip
- http://www.freemaptools.com/download/full-postcodes/fullukpostcodes.zip

You are free to use a database, as long as you provide setup instructions. You may also use the CSV file if more convenient.

## Technology Requirements

- Use any JVM-compatible technology.
- If you want to showcase Spring knowledge, feel free to use Spring.
- Be prepared to explain every part of the submitted code/configuration.
- The solution should include a simple way to build (Maven preferred) and run either from the command line or from Eclipse.

## Bonus Features

- Unit tests
- Postal code updates: add REST calls to query and update the postal-code-to-coordinates mapping (must be persisted)
- Request logging: log the two postal codes per request in a way that can later be aggregated/reported
- Authentication: restrict access to users with a username/password combination

These are optional, but they provide more opportunities to demonstrate your knowledge.

## Useful Code

This Java snippet computes an approximate distance between two points on Earth using longitude/latitude pairs (in degrees):

```java
private static final double EARTH_RADIUS = 6371; // radius in kilometers

private double calculateDistance(double latitude, double longitude, double latitude2, double longitude2) {
	// Using Haversine formula; see Wikipedia
	double lon1Radians = Math.toRadians(longitude);
	double lon2Radians = Math.toRadians(longitude2);
	double lat1Radians = Math.toRadians(latitude);
	double lat2Radians = Math.toRadians(latitude2);

	double a = haversine(lat1Radians, lat2Radians)
			+ Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
	double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

	return EARTH_RADIUS * c;
}

private double haversine(double deg1, double deg2) {
	return square(Math.sin((deg1 - deg2) / 2.0));
}

private double square(double x) {
	return x * x;
}
```

If you have any questions, please contact us.

We wish you success in finalizing the test.