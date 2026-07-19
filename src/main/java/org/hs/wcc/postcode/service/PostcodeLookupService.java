package org.hs.wcc.postcode.service;

import org.hs.wcc.postcode.exception.InvalidPostcodeException;
import org.hs.wcc.postcode.exception.PostcodeNotFoundException;
import org.hs.wcc.postcode.model.PostcodeLocation;
import org.hs.wcc.postcode.repository.PostcodeRepository;
import org.hs.wcc.postcode.util.PostcodeNormalizer;
import org.springframework.stereotype.Service;

@Service
public class PostcodeLookupService {

    private final PostcodeRepository postcodeRepository;

    public PostcodeLookupService(PostcodeRepository postcodeRepository) {
        this.postcodeRepository = postcodeRepository;
    }

    public PostcodeLocation findByPostcode(String rawPostcode) {
        final String normalizedPostcode;
        try {
            normalizedPostcode = PostcodeNormalizer.normalize(rawPostcode);
        } catch (IllegalArgumentException ex) {
            throw new InvalidPostcodeException("Invalid postcode: " + rawPostcode);
        }

        return postcodeRepository.findByNormalizedPostcode(normalizedPostcode)
                .orElseThrow(() -> new PostcodeNotFoundException("Postcode not found: " + rawPostcode));
    }
}
