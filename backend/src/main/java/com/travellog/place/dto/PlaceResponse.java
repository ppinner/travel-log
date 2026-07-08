package com.travellog.place.dto;

import com.travellog.place.Place;
import com.travellog.place.PlaceCategory;
import com.travellog.place.RatingSummary;
import java.time.Instant;

public record PlaceResponse(
        String id,
        String name,
        String description,
        PlaceCategory category,
        String country,
        String city,
        double lat,
        double lng,
        String createdBy,
        RatingSummary ratingSummary,
        Instant createdAt) {

    public static PlaceResponse from(Place place) {
        return new PlaceResponse(
                place.getId(),
                place.getName(),
                place.getDescription(),
                place.getCategory(),
                place.getCountry(),
                place.getCity(),
                place.getLocation().getY(),
                place.getLocation().getX(),
                place.getCreatedBy(),
                place.getRatingSummary(),
                place.getCreatedAt());
    }
}
