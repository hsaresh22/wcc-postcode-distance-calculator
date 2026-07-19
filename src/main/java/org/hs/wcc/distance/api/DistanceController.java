package org.hs.wcc.distance.api;

import org.hs.wcc.distance.api.dto.DistanceResponse;
import org.hs.wcc.distance.service.DistanceCalculatorService;
import org.hs.wcc.distance.service.DistanceResult;
import org.hs.wcc.postcode.model.PostcodeLocation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DistanceController {

    private final DistanceCalculatorService distanceCalculatorService;

    public DistanceController(DistanceCalculatorService distanceCalculatorService) {
        this.distanceCalculatorService = distanceCalculatorService;
    }

    @GetMapping("/distance")
    public DistanceResponse calculateDistance(@RequestParam("from") String from,
                                              @RequestParam("to") String to) {
        DistanceResult result = distanceCalculatorService.calculateDistance(from, to);

        return DistanceResponse.of(toLocationResponse(result.from()), toLocationResponse(result.to()), result.distanceKm());
    }

    private DistanceResponse.LocationResponse toLocationResponse(PostcodeLocation location) {
        return new DistanceResponse.LocationResponse(
                location.getPostcode(),
                location.getLatitude(),
                location.getLongitude()
        );
    }
}
