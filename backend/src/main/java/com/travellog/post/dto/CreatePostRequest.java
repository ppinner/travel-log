package com.travellog.post.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;

public record CreatePostRequest(
        @NotBlank String placeId,
        @Size(max = 120) String title,
        @NotBlank @Size(max = 4000) String body,
        @NotNull @Valid RatingsDto ratings,
        @Valid TourGuideDto tourGuide,
        Instant visitDate) {
}
