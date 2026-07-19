package org.hs.wcc.postcode.service;

import org.hs.wcc.postcode.exception.InvalidPostcodeException;
import org.hs.wcc.postcode.exception.PostcodeNotFoundException;
import org.hs.wcc.postcode.model.PostcodeLocation;
import org.hs.wcc.postcode.repository.PostcodeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostcodeLookupServiceTest {

    @Mock
    private PostcodeRepository postcodeRepository;

    @InjectMocks
    private PostcodeLookupService service;

    @Test
    void shouldFindPostcodeAfterNormalization() {
        PostcodeLocation expected = new PostcodeLocation("SW1A1AA", 51.501009d, -0.141588d);
        when(postcodeRepository.findByNormalizedPostcode("SW1A1AA")).thenReturn(Optional.of(expected));

        PostcodeLocation result = service.findByPostcode(" sw1a 1aa ");

        assertEquals(expected, result);
    }

    @Test
    void shouldThrowInvalidPostcodeExceptionForBadInput() {
        assertThrows(InvalidPostcodeException.class, () -> service.findByPostcode("?"));
    }

    @Test
    void shouldThrowPostcodeNotFoundExceptionWhenUnknown() {
        when(postcodeRepository.findByNormalizedPostcode("ZZ991ZZ")).thenReturn(Optional.empty());

        assertThrows(PostcodeNotFoundException.class, () -> service.findByPostcode("ZZ99 1ZZ"));
    }
}
