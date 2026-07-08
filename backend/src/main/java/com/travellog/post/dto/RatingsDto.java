package com.travellog.post.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record RatingsDto(
        @Min(1) @Max(5) int cost,
        @Min(1) @Max(5) int authenticity,
        @Min(1) @Max(5) int enjoyment,
        @Min(1) @Max(5) int fitnessRequired) {
}
