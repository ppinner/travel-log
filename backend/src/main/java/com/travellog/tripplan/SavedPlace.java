package com.travellog.tripplan;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("saved_places")
@CompoundIndex(name = "user_place_unique", def = "{'userId': 1, 'placeId': 1}", unique = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavedPlace {

    @Id
    private String id;

    private String userId;

    private String placeId;

    private SavedPlaceStatus status;

    private String notes;

    private Instant targetDate;

    private Instant visitedDate;

    private boolean active;

    private Instant createdAt;

    private Instant updatedAt;
}
