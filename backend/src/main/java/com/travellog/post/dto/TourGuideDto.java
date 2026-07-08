package com.travellog.post.dto;

import com.travellog.post.ContactMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TourGuideDto(
        @NotBlank @Size(max = 120) String name,
        @NotNull ContactMethod contactMethod,
        @NotBlank @Size(max = 200) String contactValue,
        @Size(max = 500) String note) {
}
