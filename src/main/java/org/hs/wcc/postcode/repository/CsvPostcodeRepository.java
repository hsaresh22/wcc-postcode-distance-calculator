package org.hs.wcc.postcode.repository;

import jakarta.annotation.PostConstruct;
import org.hs.wcc.postcode.model.PostcodeLocation;
import org.hs.wcc.postcode.util.PostcodeNormalizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CsvPostcodeRepository implements PostcodeRepository {

    private final ResourceLoader resourceLoader;
    private final String csvPath;
    private final Map<String, PostcodeLocation> postcodeIndex = new ConcurrentHashMap<>();

    public CsvPostcodeRepository(ResourceLoader resourceLoader,
                                 @Value("${app.postcode.csv.path:postcodes-sample.csv}") String csvPath) {
        this.resourceLoader = resourceLoader;
        this.csvPath = csvPath;
    }

    @PostConstruct
    public void load() {
        Resource resource = resourceLoader.getResource(resolveLocation(csvPath));

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    if (line.toLowerCase().startsWith("postcode,")) {
                        continue;
                    }
                }

                String[] columns = line.split(",", -1);
                if (columns.length < 3) {
                    continue;
                }

                String normalizedPostcode;
                try {
                    normalizedPostcode = PostcodeNormalizer.normalize(columns[0]);
                } catch (IllegalArgumentException ex) {
                    continue;
                }

                double latitude;
                double longitude;
                try {
                    latitude = Double.parseDouble(columns[1].trim());
                    longitude = Double.parseDouble(columns[2].trim());
                } catch (NumberFormatException ex) {
                    continue;
                }

                postcodeIndex.put(normalizedPostcode, new PostcodeLocation(normalizedPostcode, latitude, longitude));
            }
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to load postcode data from CSV: " + csvPath, ex);
        }
    }

    @Override
    public Optional<PostcodeLocation> findByNormalizedPostcode(String normalizedPostcode) {
        return Optional.ofNullable(postcodeIndex.get(normalizedPostcode));
    }

    public int size() {
        return postcodeIndex.size();
    }

    private String resolveLocation(String configuredPath) {
        if (configuredPath.startsWith("classpath:") || configuredPath.startsWith("file:")) {
            return configuredPath;
        }
        return "classpath:" + configuredPath;
    }
}
