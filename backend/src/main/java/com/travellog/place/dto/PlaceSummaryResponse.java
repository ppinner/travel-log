package com.travellog.place.dto;

import com.travellog.place.Place;
import com.travellog.place.PlaceCategory;
import com.travellog.place.RatingSummary;

public record PlaceSummaryResponse(
        String id, String name, PlaceCategory category, double lat, double lng, RatingSummary ratingSummary) {

    public static PlaceSummaryResponse from(Place place) {
        return new PlaceSummaryResponse(
                place.getId(),
                place.getName(),
                place.getCategory(),
                place.getLocation().getY(),
                place.getLocation().getX(),
                place.getRatingSummary());
    }
}
