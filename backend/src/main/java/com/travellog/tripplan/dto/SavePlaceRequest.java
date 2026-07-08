package com.travellog.tripplan.dto;

import com.travellog.tripplan.SavedPlaceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;

public record SavePlaceRequest(
        @NotBlank String placeId,
        @NotNull SavedPlaceStatus status,
        @Size(max = 1000) String notes,
        Instant targetDate) {
}
