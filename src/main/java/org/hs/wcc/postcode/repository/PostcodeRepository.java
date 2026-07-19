package org.hs.wcc.postcode.repository;

import org.hs.wcc.postcode.model.PostcodeLocation;

import java.util.Optional;

public interface PostcodeRepository {

    Optional<PostcodeLocation> findByNormalizedPostcode(String normalizedPostcode);
}
