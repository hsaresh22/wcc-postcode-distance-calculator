package org.hs.wcc.distance.service;

import org.hs.wcc.postcode.model.PostcodeLocation;

public record DistanceResult(PostcodeLocation from, PostcodeLocation to, double distanceKm) {
}
