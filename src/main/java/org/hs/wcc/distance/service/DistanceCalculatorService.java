package org.hs.wcc.distance.service;

import org.hs.wcc.postcode.model.PostcodeLocation;
import org.hs.wcc.postcode.service.PostcodeLookupService;
import org.springframework.stereotype.Service;

@Service
public class DistanceCalculatorService {

    private static final double EARTH_RADIUS_KM = 6371.0d;
    private final PostcodeLookupService postcodeLookupService;

    public DistanceCalculatorService(PostcodeLookupService postcodeLookupService) {
        this.postcodeLookupService = postcodeLookupService;
    }

    public DistanceResult calculateDistance(String fromPostcode, String toPostcode) {
        PostcodeLocation from = postcodeLookupService.findByPostcode(fromPostcode);
        PostcodeLocation to = postcodeLookupService.findByPostcode(toPostcode);

        double distanceKm = calculateDistanceInKm(
                from.getLatitude(),
                from.getLongitude(),
                to.getLatitude(),
                to.getLongitude()
        );

        return new DistanceResult(from, to, distanceKm);
    }

    public double calculateDistanceInKm(double latitude1, double longitude1, double latitude2, double longitude2) {
        double lon1Radians = Math.toRadians(longitude1);
        double lon2Radians = Math.toRadians(longitude2);
        double lat1Radians = Math.toRadians(latitude1);
        double lat2Radians = Math.toRadians(latitude2);

        double a = haversine(lat1Radians, lat2Radians)
                + Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

    private double haversine(double a, double b) {
        return square(Math.sin((a - b) / 2.0d));
    }

    private double square(double x) {
        return x * x;
    }
}
