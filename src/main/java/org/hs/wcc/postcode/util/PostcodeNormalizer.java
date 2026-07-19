package org.hs.wcc.postcode.util;

import org.springframework.util.StringUtils;

import java.util.Locale;

public final class PostcodeNormalizer {

    private PostcodeNormalizer() {
    }

    public static String normalize(String rawPostcode) {
        if (!StringUtils.hasText(rawPostcode)) {
            throw new IllegalArgumentException("Postcode must not be blank");
        }

        String normalized = StringUtils.trimAllWhitespace(rawPostcode).toUpperCase(Locale.ROOT);
        if (normalized.length() < 5 || normalized.length() > 7) {
            throw new IllegalArgumentException("Postcode must be between 5 and 7 characters");
        }

        return normalized;
    }
}
