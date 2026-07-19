package org.hs.wcc.distance.service;

import org.hs.wcc.postcode.service.PostcodeLookupService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class DistanceCalculatorServiceTest {

    @Mock
    private PostcodeLookupService postcodeLookupService;

    @InjectMocks
    private DistanceCalculatorService service;

    @Test
    void shouldReturnZeroForSameCoordinates() {
        double distance = service.calculateDistanceInKm(51.501009d, -0.141588d, 51.501009d, -0.141588d);

        assertEquals(0.0d, distance, 0.000001d);
    }

    @Test
    void shouldCalculateKnownDistanceForLondonPostcodes() {
        double distance = service.calculateDistanceInKm(51.501009d, -0.141588d, 51.520200d, -0.097700d);

        assertTrue(distance > 3.0d && distance < 4.5d);
    }
}
