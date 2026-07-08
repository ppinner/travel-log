package com.travellog.tripplan.dto;

import com.travellog.place.dto.PlaceSummaryResponse;
import com.travellog.tripplan.SavedPlace;
import com.travellog.tripplan.SavedPlaceStatus;
import java.time.Instant;

public record SavedPlaceResponse(
        String id,
        PlaceSummaryResponse place,
        SavedPlaceStatus status,
        String notes,
        Instant targetDate,
        Instant visitedDate,
        Instant createdAt) {

    public static SavedPlaceResponse from(SavedPlace savedPlace, PlaceSummaryResponse place) {
        return new SavedPlaceResponse(
                savedPlace.getId(),
                place,
                savedPlace.getStatus(),
                savedPlace.getNotes(),
                savedPlace.getTargetDate(),
                savedPlace.getVisitedDate(),
                savedPlace.getCreatedAt());
    }
}
