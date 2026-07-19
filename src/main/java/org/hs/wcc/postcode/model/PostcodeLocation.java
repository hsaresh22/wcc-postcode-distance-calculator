package org.hs.wcc.postcode.model;

import lombok.Value;

@Value
public class PostcodeLocation {
    String postcode;
    double latitude;
    double longitude;
}
