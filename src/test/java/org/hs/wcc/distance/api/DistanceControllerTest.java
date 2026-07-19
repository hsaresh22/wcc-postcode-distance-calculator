package org.hs.wcc.distance.api;

import org.hs.wcc.distance.service.DistanceCalculatorService;
import org.hs.wcc.distance.service.DistanceResult;
import org.hs.wcc.postcode.exception.InvalidPostcodeException;
import org.hs.wcc.postcode.model.PostcodeLocation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DistanceController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ApiExceptionHandler.class)
class DistanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DistanceCalculatorService distanceCalculatorService;

    @Test
    void shouldReturnDistanceResponse() throws Exception {
        DistanceResult result = new DistanceResult(
                new PostcodeLocation("SW1A1AA", 51.501009d, -0.141588d),
                new PostcodeLocation("EC1A1BB", 51.520200d, -0.097700d),
                3.85d
        );

        when(distanceCalculatorService.calculateDistance("SW1A1AA", "EC1A1BB")).thenReturn(result);

        mockMvc.perform(get("/api/v1/distance")
                        .param("from", "SW1A1AA")
                        .param("to", "EC1A1BB"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.from.postcode").value("SW1A1AA"))
                .andExpect(jsonPath("$.to.postcode").value("EC1A1BB"))
                .andExpect(jsonPath("$.distance").value(3.85d))
                .andExpect(jsonPath("$.unit").value("km"));
    }

    @Test
    void shouldReturnBadRequestForInvalidPostcode() throws Exception {
        when(distanceCalculatorService.calculateDistance("?", "EC1A1BB"))
                .thenThrow(new InvalidPostcodeException("Invalid postcode: ?"));

        mockMvc.perform(get("/api/v1/distance")
                        .param("from", "?")
                        .param("to", "EC1A1BB"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid postcode: ?"));
    }
}
