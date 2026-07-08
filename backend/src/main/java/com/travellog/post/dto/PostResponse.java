package com.travellog.post.dto;

import com.travellog.post.Post;
import com.travellog.post.Ratings;
import com.travellog.post.TourGuide;
import java.time.Instant;

public record PostResponse(
        String id,
        String placeId,
        String authorId,
        String title,
        String body,
        Ratings ratings,
        TourGuide tourGuide,
        Instant visitDate,
        Instant createdAt) {

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getPlaceId(),
                post.getAuthorId(),
                post.getTitle(),
                post.getBody(),
                post.getRatings(),
                post.getTourGuide(),
                post.getVisitDate(),
                post.getCreatedAt());
    }
}
