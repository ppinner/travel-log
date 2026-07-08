package com.travellog.post;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("posts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    private String id;

    @Indexed
    private String placeId;

    @Indexed
    private String authorId;

    private String title;

    private String body;

    private Ratings ratings;

    private TourGuide tourGuide;

    private Instant visitDate;

    private boolean active;

    private Instant createdAt;

    private Instant updatedAt;
}
