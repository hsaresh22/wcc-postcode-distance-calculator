package org.hs.wcc.distance.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DistanceResponse(LocationResponse from,
                               LocationResponse to,
                               double distance,
                               String unit) {

    public static DistanceResponse of(LocationResponse from, LocationResponse to, double distanceKm) {
        return new DistanceResponse(from, to, distanceKm, "km");
    }

    public record LocationResponse(String postcode,
                                   @JsonProperty("latitude") double latitude,
                                   @JsonProperty("longitude") double longitude) {
    }
}
