package com.travellog.place;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("places")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Place {

    @Id
    private String id;

    private String name;

    private String description;

    private PlaceCategory category;

    private String country;

    private String city;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;

    private String createdBy;

    private RatingSummary ratingSummary;

    private boolean active;

    private Instant createdAt;

    private Instant updatedAt;
}
